package ht.mesajem.mesajem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ht.mesajem.mesajem.Fragments.AllActivityFragment;
import ht.mesajem.mesajem.Fragments.AllRequestFragment;
import ht.mesajem.mesajem.Fragments.MyRequestFragment;
import ht.mesajem.mesajem.Fragments.ProfilFragment;
import ht.mesajem.mesajem.Fragments.ReceivedFragment;
import ht.mesajem.mesajem.Fragments.SendFragment;
import ht.mesajem.mesajem.R;

public class MainDeliveyActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    String TAG = "TrackActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_delivey);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        //queryposts();


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new AllRequestFragment();
                        break;
                    case R.id.action_compose:
                        fragment = new MyRequestFragment();
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

    }
}