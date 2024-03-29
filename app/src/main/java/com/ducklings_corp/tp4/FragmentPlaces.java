package com.ducklings_corp.tp4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragmentPlaces extends Fragment {
    public ArrayList<String> places;
    ArrayAdapter<String> placesAdapter;
    View view;
    String urlSearch = "https://epok.buenosaires.gob.ar/buscar/?texto=%s";
    String _toSearch;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        view = layoutInflater.inflate(R.layout.places_fragment,viewGroup,false);

        places= new ArrayList<>();
        placesAdapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,places);

        return view;
    }

    public void update(String toSearch) {
        _toSearch = toSearch;

        (new GetPlaces()).execute();
    }

    private class GetPlaces extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            URL url;
            HttpURLConnection cnx;
            try {
                url = new URL(String.format(urlSearch,_toSearch));
                cnx = (HttpURLConnection) url.openConnection();
                Log.d("Epok-P","Cnx");
                if(cnx.getResponseCode()==200) {
                    InputStream body;
                    InputStreamReader reader;

                    body = cnx.getInputStream();
                    reader = new InputStreamReader(body,"UTF-8");
                    streamToJson(reader);
                } else {
                    Log.d("Epok-P", "Non 200 code");
                }
            } catch (Exception e) {
                Log.d("Epok-P","Error: "+e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            ListView listPlaces;
            listPlaces = view.findViewById(R.id.places);
            listPlaces.setAdapter(placesAdapter);
        }
    }

    private void streamToJson(InputStreamReader stream) {
        JsonReader jsonReader;
        int totalFull;

        jsonReader = new JsonReader(stream);
        try {
            Log.d("Json-P","Starting parsing");
            jsonReader.beginObject();

            jsonReader.nextName();//totalFull
            totalFull = jsonReader.nextInt();
            if(totalFull==0) {
                //noPlacesFound();
                return;
            }

            jsonReader.nextName();//clasesEncontradas
            jsonReader.skipValue();

            jsonReader.nextName();//instancias

            jsonReader.beginArray();
            for(int i=0;i<totalFull;i++) {
                jsonReader.beginObject();//one instance
                jsonReader.nextName();//headline
                jsonReader.skipValue();
                jsonReader.nextName();//nombre
                places.add(jsonReader.nextString());//<---
                Log.d("Json-P",places.get(places.size()-1));
                jsonReader.nextName();//claseId
                jsonReader.skipValue();
                jsonReader.nextName();//clase
                jsonReader.skipValue();
                jsonReader.nextName();//id
                jsonReader.skipValue();
                jsonReader.endObject();
            }
            jsonReader.endArray();

            jsonReader.nextName();
            jsonReader.skipValue();

            jsonReader.endObject();

            Log.d("Json-P","Finished parsing");
        } catch (Exception e) {
            Log.d("Json-P","Error: "+e.getMessage());
        }
    }

}
