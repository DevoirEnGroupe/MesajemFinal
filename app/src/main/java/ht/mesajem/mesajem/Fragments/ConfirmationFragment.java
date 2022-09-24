package ht.mesajem.mesajem.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ht.mesajem.mesajem.Activities.ListTaskActivity;
import ht.mesajem.mesajem.R;

public class ConfirmationFragment extends Fragment {


    public ConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirmation, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView ivGif = view.findViewById(R.id.ivGif);
        TextView tvfelisitasyon = view.findViewById(R.id.tvfelisitasyon);
        TextView nan_yon_ti_ = view.findViewById(R.id.nan_yon_ti_);
        Button bttounen = view.findViewById(R.id.bttounen);

        Glide.with(this).load(R.raw.git).override(200,200).into(ivGif);
        bttounen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListTaskActivity.class);
                startActivity(intent);
                //getActivity().onBackPressed();

            }
        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}