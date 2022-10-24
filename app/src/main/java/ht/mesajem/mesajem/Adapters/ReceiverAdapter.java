package ht.mesajem.mesajem.Adapters;



import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import ht.mesajem.mesajem.Activities.DetailsActivity;
import ht.mesajem.mesajem.Activities.DetailsReceivedActivity;
import ht.mesajem.mesajem.Models.Post;
import ht.mesajem.mesajem.R;

public class ReceiverAdapter extends RecyclerView.Adapter<ReceiverAdapter.ViewHolder> {

    List<Post> posts;
    Context context;



    public ReceiverAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View postView = LayoutInflater.from(context).inflate(R.layout.itemreceived,parent,false);

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
        TextView  datedepart;
        TextView datereceived;
        RelativeLayout itemview;
        TextView statusDoc;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            objectid = itemView.findViewById(R.id.objectid);
            statusDoc= itemView.findViewById(R.id.statusDoc);
            postuser = itemView.findViewById(R.id.postuser);
            userexped = itemView.findViewById(R.id.userexped);
            datedepart = itemView.findViewById(R.id.datedepart);
            datereceived = itemView.findViewById(R.id.datereceived);
            itemview =  itemView.findViewById(R.id.itemview);
        }

        public void bind(Post post) {

            ParseFile image = post.getKeyImage();
            if(image !=null){
                Glide.with(context).load(image.getUrl()).override(70,70).transform(new RoundedCorners(15)).into(postuser);
            }
            try {
                objectid.setText(post.getObjectId());

            }catch (Exception e){
                Log.e("objectid","nul objectid");
            }


            userexped.setText(post.getUser().getUsername());


            if(post.getStatus().equals(0)){
                statusDoc.setText(R.string.pending);
            }
            else if(post.getStatus().equals(1)){
                statusDoc.setText(R.string.order);
            }
            else if(post.getStatus().equals(2)){
                statusDoc.setText(R.string.transit);
            }
            else {
                statusDoc.setText(R.string.deliver);
            }
            try {
                Date date = post.getPickupdate();

                datedepart.setText(DateFormat.getDateInstance().format(date));

            }catch (Exception e){
                e.getMessage();
                datedepart.setVisibility(View.INVISIBLE);
            }

            try {
                Date date = post.getArrivedate();
                datereceived.setText(DateFormat.getDateInstance().format(date));
            }catch (Exception e){
                e.getMessage();
                datereceived.setVisibility(View.INVISIBLE);
            }


            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   Intent intent = new Intent(context, DetailsReceivedActivity.class);
                   intent.putExtra("post", Parcels.wrap(post));
                   context.startActivity(intent);
                }
            });
        }

    }
}
