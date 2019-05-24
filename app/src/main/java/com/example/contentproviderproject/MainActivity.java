package com.example.contentproviderproject;

import android.Manifest;
import android.content.ContentResolver;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txv;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final String CONTACT_NAME = "myName";
    private static final String CONTACT_AGE = "myAge";
    private static final String AUTHORITY = "com.guinness.own.PROVIDER";
    private static final String BASE_PATH = "contacts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txv = (TextView) findViewById(R.id.textDBInfo);
        FetchContact();


    }

    private  void FetchContact(){
        ArrayList<String> Contacts = new ArrayList<>();
        String[] projection    = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder= null;
        ContentResolver resolver =getContentResolver();
        Cursor cursor = resolver.query(CONTENT_URI,projection,selection,selectionArgs,sortOrder);
        while (cursor.moveToNext()){
            String Name =cursor.getString(cursor.getColumnIndex(CONTACT_NAME));
            Integer Number =cursor.getInt(cursor.getColumnIndex(CONTACT_AGE));
            Log.d("guinness","Number : "+Number +"  " +"Name:"+ Name);
            Contacts.add("Name: "+Name +"\n"+"Number :" + Number);
        }
        txv.setText(Contacts.toString()+"\n ");
    }
}
