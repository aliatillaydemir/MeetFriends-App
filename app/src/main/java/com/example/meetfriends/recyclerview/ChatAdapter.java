package com.example.meetfriends.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetfriends.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<String> chatMessage;

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recycler_row,parent,false);

        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        String chatMes = chatMessage.get(position); //hangi sıradaysak o mesajı alıyoruz.
        holder.chatMessage.setText(chatMes);
    }

    @Override
    public int getItemCount() {
        return chatMessage.size();
    }


    public ChatAdapter(List<String> chatMessage) {
        this.chatMessage = chatMessage;
    }

    //pakette view holderı ayrı tanımlamadık tek adaptörde birleştiriyorum bu sefer.
    public class ChatViewHolder extends RecyclerView.ViewHolder{

        public TextView chatMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            chatMessage = itemView.findViewById(R.id.recycler_view_text); //31. satıra bağlıyoruz.
        }
    }


}
