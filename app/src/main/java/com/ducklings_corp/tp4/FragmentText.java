package com.ducklings_corp.tp4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentText extends Fragment implements View.OnClickListener {
    View view;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        view = layoutInflater.inflate(R.layout.places_fragment,viewGroup,false);
        return view;
    }

    public void onClick(View _) {
        String text = ((EditText)view.findViewById(R.id.text)).getText().toString();
        ((MainActivity)getActivity()).setSearchText(text);
    }
}
