package ht.mesajem.mesajem.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import ht.mesajem.mesajem.Activities.ListTaskActivity;
import ht.mesajem.mesajem.Activities.MainDeliveyActivity;
import ht.mesajem.mesajem.Activities.ReceivedActivity;
import ht.mesajem.mesajem.Activities.SendActivity;
import ht.mesajem.mesajem.Activities.TrackActivity;
import ht.mesajem.mesajem.Models.Delivery;
import ht.mesajem.mesajem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListAllTaskFrangment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListAllTaskFrangment extends Fragment {


    GoogleSignInClient googleSignInClient;
    RelativeLayout trackNavigation;
    RelativeLayout  sendNavigation;
    RelativeLayout receivedNavigation;
    TextView tvUsername;
    ParseUser currentuser = ParseUser.getCurrentUser();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListAllTaskFrangment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListAllTaskFrangment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListAllTaskFrangment newInstance(String param1, String param2) {
        ListAllTaskFrangment fragment = new ListAllTaskFrangment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_all_task_frangment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

// Assign variable
        trackNavigation = view.findViewById(R.id.ThirdRel);
        sendNavigation = view.findViewById(R.id.FirstRel);
        receivedNavigation = view.findViewById(R.id.SecondRel);
        tvUsername = view.findViewById(R.id.tvusernameShow);

        tvUsername.setText(currentuser.getUsername());

        sendNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SendActivity.class);
                startActivity(intent);


            }
        });

        receivedNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReceivedActivity.class);
                startActivity(intent);
            }
        });

        trackNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TrackActivity.class);
                startActivity(intent);

            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar);
     //  setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu){
    //    getMenuInflater().inflate(R.menu.switchitem,menu);
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
                                Intent intent = new Intent(getContext(), MainDeliveyActivity.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
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