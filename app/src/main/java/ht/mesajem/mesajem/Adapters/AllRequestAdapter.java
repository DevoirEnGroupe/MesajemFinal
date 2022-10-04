package ht.mesajem.mesajem.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

import ht.mesajem.mesajem.Activities.MapActivity;
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
        View postView = LayoutInflater.from(context).inflate(R.layout.allrequest,parent,false);

        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> Posts) {
        posts.addAll(Posts);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView objectid;
        ImageView postuser;
        TextView userexped;
        TextView location;
        TextView userrec;
        TextView addresse;
        RelativeLayout itemview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            objectid = itemView.findViewById(R.id.objectid);
            postuser = itemView.findViewById(R.id.postuser);
            userexped = itemView.findViewById(R.id.userexped);
            location = itemView.findViewById(R.id.location);
            addresse = itemView.findViewById(R.id.addresse);
            userrec = itemView.findViewById(R.id.userrec);
            itemview =  itemView.findViewById(R.id.itemview);
        }

        public void bind(Post post) {
            objectid.setText(post.getObjectId());
            userexped.setText(post.getUser().getUsername());
            userrec.setText(post.getFullname());
            addresse.setText(post.getAddresse());
            //location.setText(post.getLocation().toString());
            ParseFile image = post.getKeyImage();
            if(image !=null){
                Glide.with(context).load(image.getUrl()).override(70,70).transform(new RoundedCorners(10)).into(postuser);
            }
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtra("post", Parcels.wrap(post));
                    context.startActivity(intent);
                }
            });

        }
    }

}
