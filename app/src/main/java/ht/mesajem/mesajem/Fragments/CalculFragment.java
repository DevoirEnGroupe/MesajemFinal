package ht.mesajem.mesajem.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ht.mesajem.mesajem.Activities.LoginActivity;
import ht.mesajem.mesajem.R;

public class CalculFragment extends Fragment implements OnMapReadyCallback {

    LatLng latLng;
    SearchView searchView;
    String locSearch;
    GoogleMap mMap;

    LocationManager locationManager;


        @Override
        public void onMapReady(GoogleMap googleMap) {
             mMap = googleMap;
            LatLng haiti = new LatLng(19.054426, -73.04597100000001);
            googleMap.addMarker(new MarkerOptions().position(haiti).title("Marker in Haiti"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(haiti));
            showCurrentUserInMap(googleMap);
        }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calcul, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);



         locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
       searchView = view.findViewById(R.id.idSearchView);

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                    searchListener();
                   // Toast.makeText(getContext(), ""+locSearch, Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void searchListener( ) {

        String locSearch = searchView.getQuery().toString();

        List<Address> addressesList = null;

        try {


            if(locSearch!=null || locSearch.equals("")){
                Geocoder geocoder = new Geocoder(getContext());

                try {
                    addressesList = geocoder.getFromLocationName(locSearch,1);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                Address address = addressesList.get(0);
                latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(locSearch).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                Toast.makeText(getContext(), ""+latLng, Toast.LENGTH_SHORT).show();
                // below line is to animate camera to that position.
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 100));

            }


            Toast.makeText(getContext(), ""+locSearch, Toast.LENGTH_SHORT).show();

        }catch (Exception e){

            Toast.makeText(getContext(), "Sorry we didn't " +locSearch+ " try again make sure you wrote correctly", Toast.LENGTH_SHORT).show();
        }

    }


    private ParseGeoPoint getCurrentUserLocation() {


        // finding currentUser
        ParseUser currentUser = ParseUser.getCurrentUser();

        // if it's not possible to find the user, return to login
        if (currentUser == null) {
            alertDisplayer("Well... you're not logged in...", "Login first!");
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        // otherwise, return the current user location
        return currentUser.getParseGeoPoint("Location");


    }

    private void showCurrentUserInMap(final GoogleMap googleMap) {

        ParseGeoPoint currentUserLocation = getCurrentUserLocation();

        // creating a marker in the map showing the current user location
        LatLng currentUser = new LatLng(currentUserLocation.getLatitude(), currentUserLocation.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(currentUser).title(ParseUser.getCurrentUser().getUsername()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        // zoom the map to the currentUserLocation
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentUser, 100));
    }

    private void alertDisplayer(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog ok = builder.create();
        ok.show();
    }
}