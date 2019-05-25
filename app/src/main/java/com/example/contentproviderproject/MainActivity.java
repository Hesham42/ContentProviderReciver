package com.example.contentproviderproject;

import android.Manifest;
import android.app.LoaderManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.contentproviderproject.App.CHANEL_1_ID;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{
    private NotificationManagerCompat managerCompat;
    private CursorAdapter cursorAdapter;
    ArrayList<ModelPerson> Contacts;
    boolean  flag = false;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final String CONTACT_NAME = "myName";
    private static final String CONTACT_AGE = "myAge";
    public static final String CONTACT_IDS = "_id";

    private static final String AUTHORITY = "com.guinness.own.PROVIDER";
    private static final String BASE_PATH = "contacts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );


    // Constant to identify the requested operation
    private static final int CONTACTS = 1;
    private static final int CONTACT_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH, CONTACTS);
        uriMatcher.addURI(AUTHORITY,BASE_PATH + "/#",CONTACT_ID);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        managerCompat = NotificationManagerCompat.from(this);
        Contacts = new ArrayList<ModelPerson>();
        cursorAdapter = new ContactsCursorAdapter(this,null,0);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);
        getLoaderManager().initLoader(0, null, this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartLoader();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        AddNotification(FetchContact(),flag);

    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0,null,this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,CONTENT_URI,null,null,null,CONTACT_IDS+"ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);

    }

    private  ArrayList<ModelPerson>  FetchContact(){
        String[] projection    = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder= null;
        ContentResolver resolver =getContentResolver();
        Cursor cursor = resolver.query(CONTENT_URI,projection,selection,selectionArgs,sortOrder);
        while (cursor.moveToNext()){
            String Name =cursor.getString(cursor.getColumnIndex(CONTACT_NAME));
            Integer Number =cursor.getInt(cursor.getColumnIndex(CONTACT_AGE));
            Integer Id= cursor.getInt(
                    cursor.getColumnIndex(CONTACT_IDS));
            ModelPerson person= new ModelPerson(Name,Number,Id);
             Contacts.add(person);
        }
        return Contacts;

    }

    private void AddNotification(ArrayList<ModelPerson> contacts ,boolean b) {
        if (b == false){
            int size = Contacts.size();
            if (size > 0) {
                for (int i = 0; i < Contacts.size(); i++) {
                    if (i == Contacts.size() -1){
                        flag = true;
                        Intent activeIntent = new Intent(this,MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(
                                this,
                                0,
                                activeIntent,
                                0);

                        Notification notification =  new
                                NotificationCompat.Builder(this,CHANEL_1_ID)
                                .setSmallIcon(R.drawable.notifaction_24dp)
                                .setContentTitle(Contacts.get(i).getCONTACT_NAME())
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .setContentIntent(pendingIntent)
                                .build();
                        notification.flags = Notification.FLAG_AUTO_CANCEL;
                        managerCompat.notify(Contacts.get(i).getCONTACT_IDS(),notification);


                    }
                }
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("flag", flag);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
         flag = savedInstanceState.getBoolean("flag");
        }

}
