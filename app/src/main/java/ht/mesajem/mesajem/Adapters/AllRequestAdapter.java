package ht.mesajem.mesajem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ht.mesajem.mesajem.Models.Post;
import ht.mesajem.mesajem.R;

public class AllRequestAdapter extends RecyclerView.Adapter<AllRequestAdapter.ViewHolder> {


    List<Post> posts;
    Context context;



    public AllRequestAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View postView = LayoutInflater.from(context).inflate(R.layout.allrequestitem,parent,false);

        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView objectid;
        ImageView postuser;
        TextView userexped;
        TextView location;
        RelativeLayout itemview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            objectid = itemView.findViewById(R.id.objectid);
            postuser = itemView.findViewById(R.id.postuser);
            userexped = itemView.findViewById(R.id.userexped);
            location = itemView.findViewById(R.id.location);
            itemview =  itemView.findViewById(R.id.itemview);
        }

        public void bind(Post post) {
        }
    }

}
