package com.example.contentproviderproject;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.contentproviderproject.App.CHANEL_1_ID;

public class ContactsCursorAdapter extends CursorAdapter {

    public static final String CONTACT_NAME = "myName";
    public static final String CONTACT_AGE = "myAge";


    public ContactsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        return LayoutInflater.from(context).inflate(
                R.layout.contact_list_item,parent,false );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String contactName = cursor.getString(
                cursor.getColumnIndex(CONTACT_NAME));
        Integer contactAge = cursor.getInt(
                cursor.getColumnIndex(CONTACT_AGE));

        TextView nameTextView = (TextView) view.findViewById(R.id.Name);
        TextView phoneTextView = (TextView) view.findViewById(R.id.Age);
        nameTextView.setText(contactName);
        phoneTextView.setText(contactAge.toString());

    }




}
