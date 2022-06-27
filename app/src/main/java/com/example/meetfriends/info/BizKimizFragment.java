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

public class BizKimizFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup)inflater.inflate(R.layout.fragment_biz_kimiz,container,false);


        TextView TV = vg.findViewById(R.id.kimizText);
        TV.setText(" " +
                "" +
                "\n" +
                "\n" +
                "\n" +
                "     Bu proje Ali Atilla Aydemir tarafından geliştirilmiştir.\n" +

                "\n" +
                "     İletşim: atilla734@gmail.com" +
                "\n" +
                "\n" +
                "     Destek Olmak isterseniz :" +
                "\n" +
                "TR 0000000000000000000000000000000"

        );


        return vg;
    }
}