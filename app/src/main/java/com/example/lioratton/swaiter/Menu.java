package com.example.lioratton.swaiter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Menu extends AppCompatActivity implements View.OnCreateContextMenuListener {

    ListView menu;
    DatabaseReference df, d1, d2, d3;
    int choice = 1;
    List<Dish> mainList, dessertsList, drinksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu = (ListView) findViewById(R.id.menu);
        mainList = new ArrayList<>();
        dessertsList = new ArrayList<>();
        drinksList = new ArrayList<>();

        df = FirebaseDatabase.getInstance().getReference("dishes");
        d1 = df.child("main");
        d2 = df.child("desserts");
        d3 = df.child("drinks");

        menu.setOnCreateContextMenuListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (choice == 1) {
            d1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    mainList.clear();

                    for (DataSnapshot dishSnapshot : dataSnapshot.getChildren()) {
                        Dish dish = dishSnapshot.getValue(Dish.class);

                        mainList.add(dish);
                    }

                    DishList adapter = new DishList(Menu.this, mainList);
                    menu.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (choice == 2) {
            d2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    dessertsList.clear();

                    for (DataSnapshot dishSnapshot : dataSnapshot.getChildren()) {
                        Dish dish = dishSnapshot.getValue(Dish.class);

                        dessertsList.add(dish);
                    }

                    DishList adapter = new DishList(Menu.this, dessertsList);
                    menu.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            d3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    drinksList.clear();

                    for (DataSnapshot dishSnapshot : dataSnapshot.getChildren()) {
                        Dish dish = dishSnapshot.getValue(Dish.class);

                        drinksList.add(dish);
                    }

                    DishList adapter = new DishList(Menu.this, drinksList);
                    menu.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Options");
        menu.add("Edit");
        menu.add("Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        String id = "";
        switch (choice) {
            case 1:
                id = mainList.get(index).getId();
                break;
            case 2:
                id = dessertsList.get(index).getId();
                break;
            case 3:
                id = drinksList.get(index).getId();
                break;
        }

        String oper = item.getTitle().toString();
        if (oper.equals("Delete")) {
            deleteDish(id);
        } else if (oper.equals("Edit")) {
            editDish(index);
        }

        return super.onContextItemSelected(item);
    }

    public void deleteDish(String id) {
        DatabaseReference dr;
        switch (choice) {
            case 1:
                dr = FirebaseDatabase.getInstance().getReference("dishes").child("main").child(id);
                dr.removeValue();
                break;
            case 2:
                dr = FirebaseDatabase.getInstance().getReference("dishes").child("desserts").child(id);
                dr.removeValue();
                break;
            case 3:
                dr = FirebaseDatabase.getInstance().getReference("dishes").child("drinks").child(id);
                dr.removeValue();
                break;
        }


        Toast.makeText(this, "Dish has been removed", Toast.LENGTH_SHORT).show();
    }

    public void editDish(int index) {
        Dish d = new Dish();
        switch (choice) {
            case 1:
                d = mainList.get(index);
                break;
            case 2:
                d = dessertsList.get(index);
                break;
            case 3:
                d = drinksList.get(index);
                break;
        }

        Intent t = new Intent(this, Edit.class);

        //Send the arguments as separate values:
        t.putExtra("id", d.getId());
        t.putExtra("name", d.getName());
        t.putExtra("price", d.getPrice());
        t.putExtra("choice", choice);
        startActivity(t);
    }


    public void main(View view) {
        choice = 1;
        onStart();
    }

    public void desserts(View view) {
        choice = 2;
        onStart();
    }

    public void drinks(View view) {
        choice = 3;
        onStart();
    }

    public void back(View view) {
        finish();
    }
}
