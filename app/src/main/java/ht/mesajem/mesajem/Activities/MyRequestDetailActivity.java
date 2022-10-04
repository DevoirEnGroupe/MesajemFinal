package ht.mesajem.mesajem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

import ht.mesajem.mesajem.Models.Delivery;
import ht.mesajem.mesajem.Models.Post;
import ht.mesajem.mesajem.R;

public class MyRequestDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final int REQUEST_LOCATION = 1;

    LocationManager locationManager;

    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_detail);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        final Button orderbutton = findViewById(R.id.btorderpacked);
        final Button onthewaybutton = findViewById(R.id.btontheway);
        final Button deliverbutton = findViewById(R.id.btproductdeliver);

        if(post.getStatus().equals(0)) {

            orderbutton.setVisibility(View.VISIBLE);

            orderbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showarlet("CONGRATULATIONS", "There are no secrets to success. It is the result of preparation, hard work, and learning from failure");

                    savePickupdate();
                }
            });
        }
        else if(post.getStatus().equals(1)){
            onthewaybutton.setVisibility(View.VISIBLE);


            onthewaybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showarlet("CONGRATULATIONS", "There are no secrets to success. It is the result of preparation, hard work, and learning from failure");
                    saveOnthegrounddate();

                }
            });

        }
        else if(post.getStatus().equals(2)){

            deliverbutton.setVisibility(View.VISIBLE);

            deliverbutton .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showarlet("CONGRATULATIONS", "There are no secrets to success. It is the result of preparation, hard work, and learning from failure");
                    saveArrivedate();
                }
            });

        }

        else{

                    showarlet("CONGRATULATIONS", "Good Job");
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final int function = getIntent().getExtras().getInt("function");

        // showing current user location
        if (function == 1) {
            showCurrentUserInMap(mMap);
        } else {
            showClosestStore(mMap);
        }

    }

    private void saveCurrentUserLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyRequestDetailActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                ParseGeoPoint currentUserLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                    currentUser.put("Location", currentUserLocation);
                    currentUser.saveInBackground();
                } else {
                    alertDisplayer("Well... you're not logged in...", "Login first!");
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            } else {
                Log.d("userLocation", "Unable to find current user location.");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                saveCurrentUserLocation();
                break;
        }
    }

    private ParseGeoPoint getCurrentUserLocation() {
        // saving the currentUserLocation to allow it's return
        saveCurrentUserLocation();

        // finding currentUser
        ParseUser currentUser = ParseUser.getCurrentUser();

        // if it's not possible to find the user, return to login
        if (currentUser == null) {
            alertDisplayer("Well... you're not logged in...", "Login first!");
            Intent intent = new Intent(this, LoginActivity.class);
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
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentUser, 300));
    }


    private void showClosestStore(final GoogleMap googleMap) {
        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");

        query.whereNear("location", getCurrentUserLocation());
        // setting the limit of near stores to 1, you'll have in the nearStores list only one object: the closest store from the current user
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> nearStores, ParseException e) {
                if (e == null) {

                    ParseObject closestStore = nearStores.get(0);

                    // showing current user location
                    showCurrentUserInMap(mMap);

                    // finding and displaying the distance between the current user and the closest store to him
                    double distance = getCurrentUserLocation().distanceInKilometersTo(post.getLocation());
                    alertDisplayer("The Post !", "It's " + post.getFullname() + ". \n You are " + Math.round(distance * 100.0) / 100.0 + " km from this store.");

                    // creating a marker in the map showing the closest store to the current user
                    LatLng closestStoreLocation = new LatLng(post.getLocation().getLatitude(), post.getLocation().getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(closestStoreLocation).title(post.getFullname()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                    // zoom the map to the closestStoreLocation
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(closestStoreLocation, 7));

                } else {
                    Log.d("store", "Error: " + e.getMessage());
                }
            }
        });

        ParseQuery.clearAllCachedResults();

    }

    private void savePickupdate() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<Delivery> delivery = ParseQuery.getQuery(Delivery.class);

        delivery.findInBackground(new FindCallback<Delivery>() {
            @Override
            public void done(List<Delivery> deliverys, ParseException e) {
                if (e == null) {

                    for (Delivery delivery : deliverys) {
                        if (delivery.getUserd().getObjectId().equals(currentUser.getObjectId())) {

                            ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
                            query.findInBackground(new FindCallback<Post>() {
                                @Override
                                public void done(List<Post> posts, ParseException e) {

                                    Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
                                    post.setStatus(1);
                                    post.setPickupdate(new Date());

                                    post.saveInBackground();
                                    //Toast.makeText(MapActivity.this, "DATE +10 " + estimate +" DATE" + today, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MyRequestDetailActivity.this, "" + delivery.getObjectId(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                }

            }
        });

    }

    private void saveOnthegrounddate() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<Delivery> delivery = ParseQuery.getQuery(Delivery.class);

        delivery.findInBackground(new FindCallback<Delivery>() {
            @Override
            public void done(List<Delivery> deliverys, ParseException e) {
                if (e == null) {

                    for (Delivery delivery : deliverys) {
                        if (delivery.getUserd().getObjectId().equals(currentUser.getObjectId())) {

                            ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
                            query.findInBackground(new FindCallback<Post>() {
                                @Override
                                public void done(List<Post> posts, ParseException e) {

                                    Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
                                    post.setOntheground(new Date());
                                    post.setStatus(2);
                                    post.saveInBackground();
                                    //Toast.makeText(MapActivity.this, "DATE +10 " + estimate +" DATE" + today, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MyRequestDetailActivity.this, "" + delivery.getObjectId(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                }

            }
        });

    }
    private void saveArrivedate() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<Delivery> delivery = ParseQuery.getQuery(Delivery.class);

        delivery.findInBackground(new FindCallback<Delivery>() {
            @Override
            public void done(List<Delivery> deliverys, ParseException e) {
                if (e == null) {

                    for (Delivery delivery : deliverys) {
                        if (delivery.getUserd().getObjectId().equals(currentUser.getObjectId())) {

                            ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
                            query.findInBackground(new FindCallback<Post>() {
                                @Override
                                public void done(List<Post> posts, ParseException e) {

                                    Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
                                    post.setArrivedate(new Date());
                                    post.setStatus(3);

                                    post.saveInBackground();
                                    //Toast.makeText(MapActivity.this, "DATE +10 " + estimate +" DATE" + today, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MyRequestDetailActivity.this, "" + delivery.getObjectId(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                }

            }
        });

    }

    private void showarlet(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();


                    }

                });
        android.app.AlertDialog ok = builder.create();
        ok.show();
    }

    private void alertDisplayer(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this)
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