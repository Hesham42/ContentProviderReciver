package com.example.contentproviderproject;

import android.app.LoaderManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.contentproviderproject.App.CHANEL_1_ID;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private MyBroadcastReceiver MyReceiver;
    private CursorAdapter cursorAdapter;
    ArrayList<ModelPerson> Contacts;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final String CONTACT_NAME = "myName";
    private static final String CONTACT_AGE = "myAge";
    public static final String CONTACT_IDS = "_id";
    boolean flag = false;
    private static final String AUTHORITY = "com.guinness.own.PROVIDER";
    private static final String BASE_PATH = "contacts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    // Constant to identify the requested operation
    private static final int CONTACTS = 1;
    private static final int CONTACT_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, CONTACTS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", CONTACT_ID);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("com.pkg.perform.Ruby");
        if(intentFilter != null)
        {
            registerReceiver(MyReceiver, intentFilter);
        }

        Contacts = new ArrayList<ModelPerson>();
        cursorAdapter = new ContactsCursorAdapter(this, null, 0);
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
    protected void onDestroy()
    {
        super.onDestroy();
        if(MyReceiver != null)
            unregisterReceiver(MyReceiver);
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, CONTACT_IDS + "ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);

    }

    private ArrayList<ModelPerson> FetchContact() {
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(CONTENT_URI, projection, selection, selectionArgs, sortOrder);
        while (cursor.moveToNext()) {
            String Name = cursor.getString(cursor.getColumnIndex(CONTACT_NAME));
            Integer Number = cursor.getInt(cursor.getColumnIndex(CONTACT_AGE));
            Integer Id = cursor.getInt(
                    cursor.getColumnIndex(CONTACT_IDS));
            ModelPerson person = new ModelPerson(Name, Number, Id);
            Contacts.add(person);
        }
        return Contacts;

    }
}



