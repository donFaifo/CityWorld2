package me.mguerrero.cityworld2;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import me.mguerrero.cityworld2.models.City;

public class MyApplication extends Application {

    public static AtomicInteger CityId = new AtomicInteger();

    @Override
    public void onCreate() {
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        CityId = getIdByTable(realm, City.class);
        realm.close();
        super.onCreate();
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();
    }
}
