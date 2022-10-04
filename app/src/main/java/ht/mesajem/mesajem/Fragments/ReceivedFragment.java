package ht.mesajem.mesajem.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceivedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceivedFragment extends Fragment {
    RecyclerView rvPosts;
    ReceiverAdapter adapter;
    List<Post> posts;
    Boolean mFirstLoad;
    TextView tvreceive;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReceivedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceivedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceivedFragment newInstance(String param1, String param2) {
        ReceivedFragment fragment = new ReceivedFragment();
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
        return inflater.inflate(R.layout.fragment_received, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        tvreceive = view.findViewById(R.id.tvreceive);

        posts = new ArrayList<>();
        adapter = new ReceiverAdapter(posts,getContext());
        rvPosts.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(layoutManager);
        layoutManager.setReverseLayout(true);
        mFirstLoad=true;
        queryposts();
    }

    protected void queryposts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        ParseUser currentUser = ParseUser.getCurrentUser();
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.whereNotEqualTo("iduser",currentUser.getObjectId());
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