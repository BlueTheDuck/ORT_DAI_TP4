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

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {
    FragmentManager manager;
    FragmentTransaction transaction;

    private String _searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragment(R.id.categoriesFragment,new FragmentCategories(),"categories");
        createFragment(R.id.placesFragment,new FragmentPlaces(),"places");
        createFragment(R.id.textFragment,new FragmentText(),"text");
    }

    private void createFragment(int id,Fragment fragment, String tag) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(id,fragment,tag);
        transaction.commit();
    }

    public void search(View view) {
        findViewById(R.id.categoriesFragment).setVisibility(View.GONE);
        findViewById(R.id.textFragment).setVisibility(View.GONE);
        findViewById(R.id.geolocationFragment).setVisibility(View.GONE);
        findViewById(R.id.placesFragment).setVisibility(View.GONE);

        Button button = (Button)view;
        String tag = button.getTag().toString();

        if(tag.compareTo("by_cat")==0) {
            FrameLayout categoryFragment = findViewById(R.id.categoriesFragment);
            categoryFragment.setVisibility(View.VISIBLE);
        } else if(tag.compareTo("by_text")==0) {
            FrameLayout textFragment = findViewById(R.id.textFragment);
            textFragment.setVisibility(View.VISIBLE);
        }
    }
    public void setSearchText(String searchText) {
        _searchText = searchText;
        findViewById(R.id.categoriesFragment).setVisibility(View.GONE);
        findViewById(R.id.textFragment).setVisibility(View.GONE);
        displayResults();
    }

    public void displayResults() {
        findViewById(R.id.placesFragment).setVisibility(View.VISIBLE);
        ((FragmentPlaces)manager.findFragmentByTag("places")).update(_searchText);
    }
}
