package ht.mesajem.mesajem.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import java.util.List;

import ht.mesajem.mesajem.Fragments.AllActivityFragment;
import ht.mesajem.mesajem.Fragments.CalculFragment;
import ht.mesajem.mesajem.Fragments.ListAllTaskFrangment;
import ht.mesajem.mesajem.Fragments.ProfilFragment;
import ht.mesajem.mesajem.Fragments.SendFragment;
import ht.mesajem.mesajem.Models.Delivery;
import ht.mesajem.mesajem.R;

public class ListTaskActivity extends AppCompatActivity {

    String TAG = "ListTaskActivity";
    BottomNavigationView bottomNavigation;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);


        bottomNavigation = findViewById(R.id.bottomNavigation);

        //queryposts();


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new ListAllTaskFrangment();
                        break;
                    case R.id.action_compose:
                        fragment = new CalculFragment();
                        break;
                    case R.id.action_profile:
                    default:
                        fragment = new ProfilFragment();
                        break;

                }

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigation.setSelectedItemId(R.id.action_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.switchitem,menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== R.id.checkable_menu) {
            ParseUser currentuser = ParseUser.getCurrentUser();
            ParseQuery<Delivery> query = ParseQuery.getQuery(Delivery.class);
            query.include(Delivery.KEY_USER);

            query.findInBackground(new FindCallback<Delivery>() {
                @Override
                public void done(List<Delivery> deliverys, ParseException e) {

                    for(Delivery delivery:deliverys){

                        if (e == null) {
                            if(delivery.getStatus().equals(true) && delivery.getUserd().getObjectId().equals(currentuser.getObjectId())) {
                                showAlert("DELIVERY MAN", "WELCOME" + delivery.getStatus());
                                Intent intent = new Intent(ListTaskActivity.this, MainDeliveyActivity.class);
                                startActivity(intent);
                            }

                            else {

                                showAlert("DELIVERY MAN", "SORRY YOU'RE NOT A MESAJEM DELIVERY IF YOU WANNA BECOME A DELIVERY" +"PLEASE CONTACT US" + delivery.getStatus());
                            }
                        }


                    }

                }
            });

        }


        return super.onOptionsItemSelected(item);
    }



    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities

                });
        AlertDialog ok = builder.create();
        ok.show();
    }

}