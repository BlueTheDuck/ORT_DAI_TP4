package com.ducklings_corp.tp4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class FragmentPlaces extends Fragment {
    public ArrayList<String> places;
    ArrayAdapter<String> placesAdapter;
    View view;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        view = layoutInflater.inflate(R.layout.places_fragment,viewGroup,false);

        places= new ArrayList<>();
        placesAdapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,places);

        (new GetPlaces()).execute();

        return view;
    }
}
