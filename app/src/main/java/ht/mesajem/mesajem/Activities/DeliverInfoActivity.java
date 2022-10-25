package ht.mesajem.mesajem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

import ht.mesajem.mesajem.Models.Delivery;
import ht.mesajem.mesajem.Models.Post;
import ht.mesajem.mesajem.R;

public class DeliverInfoActivity extends AppCompatActivity {
    ImageView delim;
    TextView iddel;
    TextView delname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_info);
        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        delim = findViewById(R.id.delim);
        iddel = findViewById(R.id.iddel);
        delname=findViewById(R.id.delname);

        ParseQuery<Delivery> delivery = ParseQuery.getQuery(Delivery.class);
        delivery.whereEqualTo("objectId",post.getPostacc());
        delivery.include("userd");
        delivery.findInBackground(new FindCallback<Delivery>() {
            @Override
            public void done(List<Delivery> deliverys, ParseException e) {
                if(e==null){
                    for (Delivery delivery: deliverys) {

                        if(post.get("takePost").equals(1) ) {
                            delname.setText(delivery.getUserd().getString("Fullname"));
                            iddel.setText(delivery.getObjectId());
                            Glide.with(DeliverInfoActivity.this).load(delivery.getParseFile("profilD").getUrl()).override(70, 70).transform(new RoundedCorners(10)).into(delim);
                           break;
                        }
                        else {
                            delname.setText("");
                            iddel.setText("");
                        }

                    }

                }

            }
        });

    }
}