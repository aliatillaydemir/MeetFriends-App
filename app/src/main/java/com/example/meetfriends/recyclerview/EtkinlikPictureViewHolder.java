package com.example.meetfriends.recyclerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetfriends.etkinlik.AktiviteContext;
import com.example.meetfriends.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class EtkinlikPictureViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    ProgressBar progressBar;

    public EtkinlikPictureViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.recyclerView_etkinlik_pictures);
        progressBar = itemView.findViewById(R.id.picture_progressbar);

    }

    public void getPict(Context context, AktiviteContext aktiviteContext){

            itemView.setTag(aktiviteContext); //tag atıyoruz, adapterde alacağız etiketlemeyi
        Picasso.get().load(aktiviteContext.getAktiviteFoto()).fit().centerCrop().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
            progressBar.setVisibility(View.INVISIBLE);  //başarılı olursak progressbarı kaldır

            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context.getApplicationContext(), "hata var kardaşım", Toast.LENGTH_LONG).show();            }
        });

    }

}
