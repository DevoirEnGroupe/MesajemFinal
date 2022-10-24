package ht.mesajem.mesajem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.parceler.Parcels;

import ht.mesajem.mesajem.Models.Post;
import ht.mesajem.mesajem.R;

public class DetailsReceivedActivity extends AppCompatActivity {

    ImageView postimdet;
    TextView idpostdet;
    TextView expdet;
    TextView backbutton_details;

    RelativeLayout rl1;
    RelativeLayout rl2;
    RelativeLayout rl3;
    RelativeLayout rl4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_received);
        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        postimdet = findViewById(R.id.postimdet);
        idpostdet=findViewById(R.id.idpostdet);
        expdet =findViewById(R.id.expdet);

        rl1 = findViewById(R.id.Rela1);
        rl2 = findViewById(R.id.Rela2);
        rl3 = findViewById(R.id.Rela3);
        rl4 = findViewById(R.id.Rela4);


        if(post.getStatus().equals(0)){

            rl1.setVisibility(View.VISIBLE);

        }

        else if(post.getStatus().equals(1)){

            rl1.setVisibility(View.VISIBLE);
            rl2.setVisibility(View.VISIBLE);

        }
        else if(post.getStatus().equals(2)){

            rl1.setVisibility(View.VISIBLE);
            rl2.setVisibility(View.VISIBLE);
            rl3.setVisibility(View.VISIBLE);

        }
        else{

            rl1.setVisibility(View.VISIBLE);
            rl2.setVisibility(View.VISIBLE);
            rl3.setVisibility(View.VISIBLE);
            rl4.setVisibility(View.VISIBLE);

        }

        backbutton_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsReceivedActivity.this,getClass());
            }
        });

        idpostdet.setText(post.getObjectId());
        expdet.setText(post.getUser().getUsername());

        Glide.with(this).load(post.getKeyImage().getUrl()).override(70,70).transform(new RoundedCorners(10)).into(postimdet);

    }
}