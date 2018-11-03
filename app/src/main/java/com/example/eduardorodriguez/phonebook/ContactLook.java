package com.example.eduardorodriguez.phonebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

public class ContactLook extends AppCompatActivity {

    TextView contactNumber;
    TextView name;
    TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_look);
        contactNumber = (TextView) findViewById(R.id.number);
        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b != null){
            contactNumber.setText(b.getString("contact"));
            name.setText(b.getString("name"));
            address.setText(b.getString("address"));
        }
    }
}
