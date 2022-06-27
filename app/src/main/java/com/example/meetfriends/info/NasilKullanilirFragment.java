package com.example.meetfriends.info;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meetfriends.R;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class NasilKullanilirFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup vg = (ViewGroup)inflater.inflate(R.layout.fragment_nasil_kullanilir,container,false);



        TextView TV = vg.findViewById(R.id.nasilText);




        TV.setText(
                "\n" +
                        "    MeetFriends Nasıl Kullanılır?" +
                        "\n" +
                        "\n" +
                        "    Uygulama sayesinde tanımadığınız insanlarla buluşacağınız konumları " +
                        " harita üzerinden işaretleyebilir, resim ve kısa notlar yükleyerek kullanıcıları bilgilendirebilirsiniz." +
                        " İşaretli alanlarda ya da istediğiniz bölgelerde etkinlik başlatabilirsiniz." +
                        " Bunun yanında çevrenizdeki bölgelerde başlatılan buluşmalardan " +
                        " haberdar olabilir ve bu etkinliklere katılabilirsiniz.\n"
        );

        return vg;
    }
}