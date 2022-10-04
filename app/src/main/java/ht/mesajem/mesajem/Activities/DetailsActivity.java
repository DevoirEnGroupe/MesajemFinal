package ht.mesajem.mesajem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import ht.mesajem.mesajem.Models.Post;
import ht.mesajem.mesajem.R;

public class DetailsActivity extends AppCompatActivity {


    ImageView postimdet;
    TextView idpostdet;
    TextView expdet;
    ImageView order1;
    ImageView checkorder1;
    TextView tvorder1;
    ImageView order2;
    ImageView checkorder2;
    TextView tvorder2;
    ImageView order3;
    ImageView checkorder3;
    TextView tvorder3;
    ImageView order4;
    ImageView checkorder4;
    TextView tvorder4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        postimdet = findViewById(R.id.postimdet);
        idpostdet=findViewById(R.id.idpostdet);
        expdet =findViewById(R.id.expdet);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        idpostdet.setText(post.getObjectId());
        expdet.setText(post.getUser().getUsername());

        Glide.with(this).load(post.getKeyImage().getUrl()).override(70,70).into(postimdet);

    }



}