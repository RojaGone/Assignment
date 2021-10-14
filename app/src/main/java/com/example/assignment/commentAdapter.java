package com.example.assignment;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.ViewHolder> {
    List<commentModel> listdata
            = Collections.emptyList();
    Context context;

    public commentAdapter(List<commentModel> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.comment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final commentModel myListData = listdata.get(position);
        holder.username.setText(myListData.getUsername());
        Uri myUri = Uri.parse(myListData.getUser_img());
        holder.user_img.setImageURI(myUri);
        holder.comment_view.setText(myListData.getComment_view());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView user_img;
        TextView username,comment_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.user_img = (CircleImageView)itemView.findViewById(R.id.user_img);
            this.username = (TextView)itemView.findViewById(R.id.username);
            this.comment_view = (TextView)itemView.findViewById(R.id.comment_view);
        }
    }
}
