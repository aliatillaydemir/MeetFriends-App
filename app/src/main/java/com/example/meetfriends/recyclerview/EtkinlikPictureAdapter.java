package com.example.meetfriends.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetfriends.etkinlik.AktiviteContext;
import com.example.meetfriends.R;

import java.util.ArrayList;

public class EtkinlikPictureAdapter extends RecyclerView.Adapter<EtkinlikPictureViewHolder> implements View.OnClickListener {

    private ArrayList<AktiviteContext> aktiviteContexts;
    private LayoutInflater layoutInflater;

    private AppCompatActivity appCompatActivity;
    private OnContextListener onContextListener;

    public EtkinlikPictureAdapter(AppCompatActivity appCompatActivity, OnContextListener onContextListener) {
        this.appCompatActivity = appCompatActivity;
        this.onContextListener = onContextListener;

        layoutInflater = appCompatActivity.getLayoutInflater();
        aktiviteContexts = new ArrayList<>();
    }

    public ArrayList<AktiviteContext> getAktiviteContexts(){
        return aktiviteContexts;
    }

    @NonNull
    @Override
    public EtkinlikPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listView = layoutInflater.inflate(R.layout.etkinlik_picture,parent,false);
        listView.setOnClickListener(this);

        return new EtkinlikPictureViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull EtkinlikPictureViewHolder holder, int position) {
        holder.getPict(appCompatActivity,aktiviteContexts.get(position));
    }

    @Override
    public int getItemCount() {
        return aktiviteContexts.size();
    }


    @Override
    public void onClick(View v) {

        if(v.getTag() instanceof AktiviteContext){ //viewe tıkladığımda gerçekten aktivite contexe aitse işlem yap

            AktiviteContext aktiviteContext = (AktiviteContext) v.getTag();//getTag ile etiketlemeyi almış oluyoz
            onContextListener.OnContextListener(aktiviteContext);
        }

    }

    public interface OnContextListener{ //fotoya tıkladığımızda yerini dinleyecek.(kendi listenerımız)

        public void OnContextListener(AktiviteContext aktiviteContext);

    }

}
