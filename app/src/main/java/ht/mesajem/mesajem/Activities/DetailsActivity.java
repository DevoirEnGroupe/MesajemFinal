package ht.mesajem.mesajem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.parceler.Parcels;

import ht.mesajem.mesajem.Models.Post;
import ht.mesajem.mesajem.R;

public class DetailsActivity extends AppCompatActivity {


    ImageView postimdet;
    TextView idpostdet;
    TextView expdet;

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

        Glide.with(this).load(post.getKeyImage().getUrl()).override(70,70).transform(new RoundedCorners(10)).into(postimdet);

    }



}