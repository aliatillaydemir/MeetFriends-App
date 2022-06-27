package com.example.meetfriends.info;


import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.meetfriends.R;

public class NedenFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup vg = (ViewGroup)inflater.inflate(R.layout.fragment_neden,container,false);




        TextView TV = vg.findViewById(R.id.nedenText);
        TV.setText("" +
                "\n \n \n" +
                "    MeetFriends Nedir?\n\n" +
                "    MeetFriends; insanların sosyal ihtiyaçlarını gidermek amacıyla oluşturulmuş," +
                " bu sosyal ihtiyacı karşılarken çevreyi topluluklara tekrar tanıtan" +
                " sosyolojik bir çalışmadır. İnsanların birbirini tanımadan kaynaşabileceği, gezip " +
                "tozabileceği muhteşem bir projeye hazır olun! MeetFriends " +
                "uygulaması, insanın mental ve fiziksel sağlığını arttırarak ilişki boyutuna yeni bir soluk getirmeyi hedeflemektedir." +
                "\n\n\n" +
                "    MeetFriends uygulamasının size katacakları şeyler Nelerdir?\n\n" +
                "\n1. Stresi Azaltır " +
                "\n2. Yaşam Gücünüzü ve Sağlamlığınızı Arttırır, Enerjinizi Yükseltir " +
                "\n3. Sosyal çevrenizin genişlemesini sağlar " +
                "\n4. Doğayla iç içe bir proje olması dolayısıyla sağlığınıza katkı sağlar" +
                "\n5. Kanser Riskini Azaltır " +
                "\n6. Zayıflamanıza Yardımcı Olur " +
                "\n7. Vücut Duruşunuzun (Postür) Düzgün ve Dik Olmasına Yardımcı Olur " +
                "\n8. Diyabet Sorunlarına İyi Gelir " +
                "\n9. Ruh Halinizin Yükselmesine ve Problem Çözme Yeteneğinize Yardımcı Olur " +
                "\n10. Aktivite çeşitliliği sağlar. \n\n"


        );

        TV.setMovementMethod(new ScrollingMovementMethod());
        return vg;
    }
}