package me.mguerrero.cityworld2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import me.mguerrero.cityworld2.R;
import me.mguerrero.cityworld2.models.City;

public class EditCityActivity extends AppCompatActivity {

    private EditText cityName;
    private EditText cityImageUrl;
    private EditText cityDescription;
    private ImageView cityImage;
    private Button prevButton;
    private FloatingActionButton fabSave;
    private RatingBar ratingBar;
    private Realm realm;
    private City city;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_city);

        realm = Realm.getDefaultInstance();

        /*-- ELEMENTOS UI --*/
        cityName = findViewById(R.id.cityName);
        cityImageUrl = findViewById(R.id.cityImageUrl);
        cityDescription = findViewById(R.id.cityDescription);
        cityImage = findViewById(R.id.cityImage);
        prevButton = findViewById(R.id.prevButton);
        fabSave = findViewById(R.id.fabSave);
        ratingBar = findViewById(R.id.ratingBar);

        /*-- DATOS INTENT --*/
        if (getIntent().getExtras() != null) {
            long id = getIntent().getExtras().getLong("id");
            city = realm.where(City.class).equalTo("id", id).findFirst();
            cityName.setText(city.getName());
            cityDescription.setText(city.getDescription());
            cityImageUrl.setText(city.getImageLink());
            Picasso.get().load(city.getImageLink()).error(R.mipmap.city_placeholder_foreground).fit().into(cityImage);
            ratingBar.setRating(city.getRating());
            editMode = true;
        } else {
            Picasso.get().load(R.mipmap.city_placeholder_foreground).into(cityImage);
        }

        /*-- LISTENERS --*/
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = cityImageUrl.getText().toString();
                if (url.length()>0) {
                    Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                    Picasso.get().load(url).fit().into(cityImage);
                } else {
                    Toast.makeText(getApplicationContext(), "You must specify an url for the image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInputDataValid()) {
                    String name = cityName.getText().toString();
                    String description = cityDescription.getText().toString();
                    String url = cityImageUrl.getText().toString();
                    float rating = ratingBar.getRating();

                    if (editMode) {
                        realm.beginTransaction();
                        city.setName(name);
                        city.setDescription(description);
                        city.setRating(rating);
                        city.setImageLink(url);
                        realm.commitTransaction();
                    } else {
                        city = new City(name, description, url, rating);
                    }

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(city);
                    realm.commitTransaction();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "You must fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isInputDataValid() {
        return !cityName.getText().toString().isEmpty()
                && !cityImageUrl.getText().toString().isEmpty()
                && !cityDescription.getText().toString().isEmpty();
    }
}