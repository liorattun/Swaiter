package com.example.lioratton.swaiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Edit extends AppCompatActivity {

    EditText name, price;
    DatabaseReference df;
    Intent gt;

    String id;
    String n;
    double p;
    int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        name = (EditText) findViewById(R.id.name);
        price = (EditText) findViewById(R.id.price);

        gt = getIntent();
        id = gt.getStringExtra("id");
        n = gt.getStringExtra("name");
        p = gt.getDoubleExtra("price", 0);
        choice = gt.getIntExtra("choice", 1);

        switch (choice)
        {
            case 1: df = FirebaseDatabase.getInstance().getReference("dishes").child("main").child(id); break;
            case 2: df = FirebaseDatabase.getInstance().getReference("dishes").child("desserts").child(id); break;
            case 3: df = FirebaseDatabase.getInstance().getReference("dishes").child("drinks").child(id); break;
        }


        name.setText(n);
        price.setText(""+p);

    }

    public void save(View view) {

        if (!name.getText().toString().isEmpty() && !price.getText().toString().isEmpty() && !price.getText().toString().equals("."))
        {

            n = name.getText().toString();
            p = Double.parseDouble(price.getText().toString());

            Dish d = new Dish(id, n, p);

            df.setValue(d);

            finish();
        }
        else
            Toast.makeText(this, "You didn't enter all the information", Toast.LENGTH_SHORT).show();

    }

    public void cancel(View view) {
        finish();
    }
}
