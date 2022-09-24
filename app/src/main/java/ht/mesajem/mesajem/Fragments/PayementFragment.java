package ht.mesajem.mesajem.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ht.mesajem.mesajem.R;


public class PayementFragment extends Fragment {

    Button btpayement;
    TextView tvpay;
    TextView tvmount;


    public PayementFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btpayement= view.findViewById(R.id.btpayement);
        tvmount = view.findViewById(R.id.tvmount);
        tvpay = view.findViewById(R.id.tvpay);
        btpayement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "FELISITASYON", Toast.LENGTH_SHORT).show();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.relativ, new ConfirmationFragment())
                        .commit();
            }
        });

    }
}