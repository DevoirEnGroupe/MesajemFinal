package ht.mesajem.mesajem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import ht.mesajem.mesajem.Adapters.SenderAdapter;
import ht.mesajem.mesajem.Fragments.AllActivityFragment;
import ht.mesajem.mesajem.Fragments.ProfilFragment;
import ht.mesajem.mesajem.Fragments.ReceivedFragment;
import ht.mesajem.mesajem.Fragments.SendFragment;
import ht.mesajem.mesajem.Models.Post;
import ht.mesajem.mesajem.R;

public class TrackActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    String TAG = "TrackActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);


        bottomNavigation = findViewById(R.id.bottomNavigation);

        //queryposts();


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment;
                switch (item.getItemId()) {

                    case R.id.action_compose:
                        fragment = new ReceivedFragment();
                        break;
                    case R.id.action_send:
                        fragment = new SendFragment();
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
        bottomNavigation.setSelectedItemId(R.id.action_compose);



    }


}