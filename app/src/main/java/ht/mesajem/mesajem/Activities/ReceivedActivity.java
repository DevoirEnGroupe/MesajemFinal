package ht.mesajem.mesajem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import ht.mesajem.mesajem.Adapters.ReceiverAdapter;
import ht.mesajem.mesajem.Models.Post;
import ht.mesajem.mesajem.R;

public class ReceivedActivity extends AppCompatActivity {

    RecyclerView rvPosts;
    ReceiverAdapter adapter;
    List<Post> posts;
    Boolean mFirstLoad;
    TextView tvreceive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        rvPosts = findViewById(R.id.rvPosts);
        tvreceive = findViewById(R.id.tvreceive);

        posts = new ArrayList<>();
        adapter = new ReceiverAdapter(posts,this);
        rvPosts.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(layoutManager);
        layoutManager.setReverseLayout(true);
        mFirstLoad=true;
        queryposts();

    }

    protected void queryposts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        ParseUser currentUser = ParseUser.getCurrentUser();
        query.whereNotEqualTo("iduser",currentUser.getObjectId());
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {

                if(e == null){
                    adapter.clear();
                    adapter.addAll(posts);

                }
                if(mFirstLoad){
                    rvPosts.scrollToPosition(0);
                    mFirstLoad= false;
                }
                else {
                    Log.e("post", "Error Loading post" + e);
                }

            }
        });

    }
}