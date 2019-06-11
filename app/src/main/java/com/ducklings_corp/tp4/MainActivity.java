package com.ducklings_corp.tp4;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {
    FragmentManager manager;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getFragmentManager();
        Fragment fragmentCategories = new FragmentCategories();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.categories,fragmentCategories);
        transaction.commit();
    }
}
