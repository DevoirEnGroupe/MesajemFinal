package ht.mesajem.mesajem.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import ht.mesajem.mesajem.Models.Post;
import ht.mesajem.mesajem.R;


public class DetailsFragment extends Fragment {



    ImageView postimdet;
    TextView idpostdet;
    TextView expdet;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("DATA_ACTION".equals(intent.getAction())){
                //Les données sont passées et peuvent être récupérées via :
                //Post post = Parcels.unwrap(getActivity().getIntent().getParcelableExtra("post"));
                intent.getSerializableExtra("DATA_EXTRA");
                intent.getIntExtra("DATA_EXTRA", 2);

            }
        }
    };

    public DetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter("DATA_ACTION"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

          postimdet = view.findViewById(R.id.postimdet);
          idpostdet=view.findViewById(R.id.idpostdet);
          expdet =view.findViewById(R.id.expdet);


    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }

}