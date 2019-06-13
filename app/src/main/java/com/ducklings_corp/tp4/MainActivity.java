package com.ducklings_corp.tp4;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

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

    public void search(View view) {
        Button button = (Button)view;
        String tag = button.getTag().toString();

        if(tag.compareTo("by_cat")==0) {
            searchByCat();
        }
    }

    private void searchByCat() {
        FrameLayout categoryFragment = findViewById(R.id.categories);
    }
}
