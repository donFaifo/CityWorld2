package me.mguerrero.cityworld2.models;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import me.mguerrero.cityworld2.MyApplication;

public class City extends RealmObject {

    @PrimaryKey
    private long id;

    private String name;
    private String description;
    private String imageLink;
    private float rating;

    public City() { }

    public City(String name, String description, String imageLink) {
        this.id = MyApplication.CityId.incrementAndGet();
        this.name = name;
        this.description = description;
        this.imageLink = imageLink;
        this.rating = 0;
    }

    public City(String name, String description, String imageLink, float rating) {
        this.id = MyApplication.CityId.incrementAndGet();
        this.name = name;
        this.description = description;
        this.imageLink = imageLink;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getId() {
        return id;
    }
}
