package com.anon.drawerapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anon on 2/5/2018.
 */

public class HotelFragment extends Fragment {


    ListView listView;
    List<Place> list;
    ProgressDialog progressDialog;
    //MyAdapter myAdapter;
    //HotelAdapter hotelAdapter;
    PlaceAdapter placeAdapter;

    private DatabaseReference databaseReference;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Hotels");


        listView=view.findViewById(R.id.list);

        list= new ArrayList<>();
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching Please wait");
        progressDialog.show();

        databaseReference= FirebaseDatabase.getInstance().getReference(MainActivity.DATABASE_PATH_HOTEL);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){

                    Place place=snap.getValue(Place.class);
                    list.add(place);

                }

                placeAdapter= new PlaceAdapter(getActivity(), R.layout.list_item,list);
                listView.setAdapter(placeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.list, container, false);
    }

}
