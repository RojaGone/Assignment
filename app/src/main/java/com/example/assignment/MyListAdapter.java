package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
//    private MyListDataModel[] listdata;
    List<MyListDataModel> listdata
            = Collections.emptyList();
    Context context;

    public MyListAdapter(List<MyListDataModel> listdata,Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyListDataModel myListData = listdata.get(position);
        Uri myUri = Uri.parse(myListData.getUser_img());
        holder.user_img.setImageURI(myUri);
        holder.username.setText(myListData.getUsername());
        Log.e("helo3","get username : "+myListData.getUser_post_video());
        Log.e("helo3","get username : "+myListData.getUser_post_img());
        holder.user_dob.setText(myListData.getUser_dob());
        holder.description.setText(myListData.getDescription());
        holder.Dislike.setVisibility(View.GONE);
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.Dislike.setVisibility(View.VISIBLE);
                holder.like.setVisibility(View.GONE);
            }
        });
        holder.Dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.Dislike.setVisibility(View.GONE);
                holder.like.setVisibility(View.VISIBLE);
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),commentAtivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        if(myListData.getUser_post_img().isEmpty()){
            holder.user_post_img.setVisibility(View.GONE);
            Uri myUri_video = Uri.parse(myListData.getUser_post_video());
            holder.user_post_video.setVisibility(View.VISIBLE);
            holder.user_post_video.setVideoURI(myUri_video);
            holder.user_post_video.start();
            holder.user_post_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
        }else {

            holder.user_post_video.setVisibility(View.GONE);
            Uri myUri_post_img = Uri.parse(myListData.getUser_post_img());
            holder.user_post_img.setImageURI(myUri_post_img);
        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relativeLayout;
        public ImageView user_img,user_post_img,like,comment,Dislike;
        public TextView username,user_dob,description;
        public VideoView user_post_video;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            this.user_img = (ImageView)itemView.findViewById(R.id.user_img);
            this.user_post_img = (ImageView)itemView.findViewById(R.id.user_post_img);
            this.like = (ImageView)itemView.findViewById(R.id.like);
            this.Dislike = (ImageView)itemView.findViewById(R.id.Dislike);
            this.comment = (ImageView)itemView.findViewById(R.id.comment);
            this.username = (TextView) itemView.findViewById(R.id.username);
            this.user_dob = (TextView) itemView.findViewById(R.id.user_dob);
            this.description = (TextView) itemView.findViewById(R.id.description);
            this.user_post_video = (VideoView) itemView.findViewById(R.id.user_post_video);

        }
    }
}
