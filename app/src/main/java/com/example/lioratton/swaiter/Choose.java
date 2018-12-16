package com.example.lioratton.swaiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Choose extends AppCompatActivity {
    DatabaseReference df, d1, d2, d3;
    EditText name, price;
    RadioButton main, desserts, drinks;
    RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        df=FirebaseDatabase.getInstance().getReference("dishes");
        d1=df.child("main");
        d2=df.child("desserts");
        d3=df.child("drinks");

        name = (EditText) findViewById(R.id.name);
        price = (EditText) findViewById(R.id.price);
        group = (RadioGroup) findViewById(R.id.group);
        main = (RadioButton) findViewById(R.id.main);
        desserts = (RadioButton) findViewById(R.id.desserts);
        drinks = (RadioButton) findViewById(R.id.drinks);
    }

    public void push(View view) {
        String n = name.getText().toString();
        String p = price.getText().toString();

        //Check the type of menu user select and push the object to the appropriate branch in firebase
        if (!n.isEmpty() && !p.isEmpty() && !p.equals(".") && (main.isChecked() || desserts.isChecked() || drinks.isChecked()))
        {
            if (main.isChecked()) {
                String id = d1.push().getKey();
                Dish d = new Dish(id, n, Double.parseDouble(p));
                d1.child(id).setValue(d);
            }
            else if (desserts.isChecked()) {
                String id = d2.push().getKey();
                Dish d = new Dish(id, n, Double.parseDouble(p));
                d2.child(id).setValue(d);
            }
            else {
                String id = d3.push().getKey();
                Dish d = new Dish(id, n, Double.parseDouble(p));
                d3.child(id).setValue(d);
            }

            Toast.makeText(this, "Upload successfull", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "You didn't enter all the information", Toast.LENGTH_SHORT).show();
    }

    public void next(View view) {
        Intent t = new Intent(this, Menu.class);
        startActivity(t);
    }

}
