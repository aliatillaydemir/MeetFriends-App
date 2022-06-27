package com.example.meetfriends.etkinlik;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.meetfriends.R;
import com.example.meetfriends.etkinlikFragmentlar.PopupFragment;

public class MeetPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meet_popup_aktivitesi);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.trashPopupActivity);

        if(fragment == null){ //başta boş olacak fragment. Diğerlerinde newInstance çağırıldı ama başta popupta çağırmadık.
           fragment = PopupFragment.newInstance(); //ilk defa popup fragmentı burada kullandık ve yine
                                                   //ilk defa popup fragmentı ile aktiviteyi birbirinme bağlayabildik

           fragmentManager.beginTransaction().add(R.id.trashPopupActivity,fragment).commit();
        }

    }

    public static Intent newIntent(Context context, AktiviteContext aktiviteContext) {

        Intent intent = new Intent(context, MeetPopupActivity.class); //içinde bulunduğumz aktiviteye intent yap. statik olduğu için başka yerden burası sayesinde intent yapmış olacağız almak istediği PopupFramgent fragmentına.
        intent.putExtra("Yolla",aktiviteContext);
        return intent;  //burayı neresi çağırıyor, intent yapıyor? -> EtkinlikFragment classı, 90. satırda kendi yazdığımız listener.
    }

}
