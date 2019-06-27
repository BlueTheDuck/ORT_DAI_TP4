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

enum ProgramState {
    WAITING_SEARCH_METHOD,
    SEARCH_BY_CATEGORY_WAITING,
    SEARCH_BY_TEXT_WAITING,
    SEARCH_BY_LOCATION_WAITING_LOC,
    SEARCH_BY_LOCATION_WAITING_CAT,
    DISPLAYING_RESULTS
}

public class MainActivity extends Activity {
    FragmentManager manager;
    FragmentTransaction transaction;

    private String _category;
    private String _text;
    ProgramState state = ProgramState.WAITING_SEARCH_METHOD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragment(R.id.categoriesFragment,new FragmentCategories(),"categories");
        createFragment(R.id.placesFragment,new FragmentPlaces(),"places");
    }

    private void createFragment(int id,Fragment fragment, String tag) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(id,fragment,tag);
        transaction.commit();
    }

    public void search(View view) {
        findViewById(R.id.categoriesFragment).setVisibility(View.INVISIBLE);
        findViewById(R.id.textFragment).setVisibility(View.INVISIBLE);
        findViewById(R.id.geolocationFragment).setVisibility(View.INVISIBLE);
        findViewById(R.id.placesFragment).setVisibility(View.INVISIBLE);

        Button button = (Button)view;
        String tag = button.getTag().toString();

        if(tag.compareTo("by_cat")==0) {
            state = ProgramState.SEARCH_BY_CATEGORY_WAITING;
            FrameLayout categoryFragment = findViewById(R.id.categoriesFragment);
            categoryFragment.setVisibility(View.VISIBLE);
        }
    }

    public void setCategory(String category) {
        _category = category;
        findViewById(R.id.categoriesFragment).setVisibility(View.INVISIBLE);
        Log.d("","Displaying results");
        displayResults();
    }

    public void displayResults() {
        findViewById(R.id.placesFragment).setVisibility(View.VISIBLE);
        FragmentPlaces places = (FragmentPlaces)manager.findFragmentByTag("places");
        if(state.equals(ProgramState.SEARCH_BY_CATEGORY_WAITING)) {
            places.update(_category);
        } else if(state.equals(ProgramState.SEARCH_BY_TEXT_WAITING)) {
            places.update(_text);
        }

        state = ProgramState.WAITING_SEARCH_METHOD;
    }
}
