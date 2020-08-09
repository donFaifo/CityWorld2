package me.mguerrero.cityworld2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import me.mguerrero.cityworld2.R;
import me.mguerrero.cityworld2.adapters.CityAdapter;
import me.mguerrero.cityworld2.models.City;

public class MainActivity extends AppCompatActivity {

    private RealmResults<City> cities;
    private Realm realm;
    FloatingActionButton fabAdd;
    CityAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*-- REALM --*/

        realm = Realm.getDefaultInstance();
        cities = realm.where(City.class).findAll();

        fabAdd = findViewById(R.id.fabAddCity);

        RecyclerView mainList = findViewById(R.id.mainList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainList.setLayoutManager(layoutManager);

        /*-- RECYCLERVIEW ADAPTER --*/

        myAdapter = new CityAdapter(cities, new CityAdapter.OnItemClickListener() {
            @Override
            public void onDeleteButtonClick(final City city, int position) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete " + city.getName() + "?")
                        .setMessage("Are you sure you want to delete " + city.getName() + " city?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                realm.beginTransaction();
                city.deleteFromRealm();
                realm.commitTransaction();
            }

            @Override
            public void onCardClick(City city, int position) {
                Intent intent = new Intent(getApplicationContext(), EditCityActivity.class);
                intent.putExtra("id", city.getId());
                startActivity(intent);
            }
        });
        mainList.setAdapter(myAdapter);

        /*-- LISTENERS --*/

        cities.addChangeListener(new RealmChangeListener<RealmResults<City>>() {
            @Override
            public void onChange(RealmResults<City> cities) {
                myAdapter.notifyDataSetChanged();
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditCityActivity.class);
                startActivity(intent);
            }
        });

        mainList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fabAdd.hide();
                } else {
                    fabAdd.show();
                }
            }
        });
    }
}