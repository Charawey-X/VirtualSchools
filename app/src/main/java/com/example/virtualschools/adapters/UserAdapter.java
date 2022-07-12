package com.example.virtualschools.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.virtualschools.R;
import com.example.virtualschools.models.ChatUser;
import com.example.virtualschools.ui.MessageActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final Context mContext;
    private final List<ChatUser> mUsers;

    public UserAdapter(Context mContext, List<ChatUser> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_profile_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatUser chatUser = mUsers.get(position);
        holder.username.setText(chatUser.getUsername());
        if (chatUser.getImageUrl().equals("default")) {
            holder.profile_image.setImageResource(R.drawable.ic_person);
        } else {
            Glide.with(mContext).load(chatUser.getImageUrl()).into(holder.profile_image);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MessageActivity.class);
            intent.putExtra("userid",chatUser.getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView profile_image;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.chat_username);
            profile_image = itemView.findViewById(R.id.chat_profile_image);
        }
    }
}
