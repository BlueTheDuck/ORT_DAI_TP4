package com.ducklings_corp.tp4;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.Collections;

public class MainActivity extends Activity {
    FragmentManager manager;
    FragmentTransaction transaction;

    private String _category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragment(R.id.categoriesFragment,new FragmentCategories(),"categories");
        createFragment(R.id.placesFragment,new FragmentPlaces(),"places");
    }

    public void search(View view) {
        Button button = (Button)view;
        String tag = button.getTag().toString();

        if(tag.compareTo("by_cat")==0) {
            searchByCat();
        }
    }

    private void createFragment(int id,Fragment fragment, String tag) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(id,fragment,tag);
        transaction.commit();
    }

    private void searchByCat() {
        FrameLayout categoryFragment = findViewById(R.id.categoriesFragment);
        categoryFragment.setVisibility(View.VISIBLE);
    }

    public void setCategory(String category) {
        _category = category;
        Log.d("Var set",String.format("_category = %s",category));
        findViewById(R.id.categoriesFragment).setVisibility(View.INVISIBLE);
        ((FragmentPlaces)manager.findFragmentByTag("places")).update();
    }
}
