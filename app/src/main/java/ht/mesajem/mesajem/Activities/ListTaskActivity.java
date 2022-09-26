package ht.mesajem.mesajem.Activities;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import java.util.List;

import ht.mesajem.mesajem.Models.Delivery;
import ht.mesajem.mesajem.R;

public class ListTaskActivity extends AppCompatActivity {

    GoogleSignInClient googleSignInClient;
    RelativeLayout trackNavigation;
    RelativeLayout  sendNavigation;
    RelativeLayout receivedNavigation;
    TextView tvUsername;
    ParseUser currentuser = ParseUser.getCurrentUser();
    String TAG = "ListTaskActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);

        // Assign variable
        trackNavigation = findViewById(R.id.ThirdRel);
        sendNavigation = findViewById(R.id.FirstRel);
        receivedNavigation = findViewById(R.id.SecondRel);
        tvUsername = findViewById(R.id.tvusernameShow);

        tvUsername.setText(currentuser.getUsername());




        sendNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTaskActivity.this, SendActivity.class);
                startActivity(intent);


            }
        });

        receivedNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTaskActivity.this, ReceivedActivity.class);
                startActivity(intent);
            }
        });

        trackNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListTaskActivity.this, TrackActivity.class);
                startActivity(intent);

            }
        });

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
                            if(delivery.getStatus().equals(true)) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ListTaskActivity.this)
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