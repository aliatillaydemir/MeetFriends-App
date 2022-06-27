package com.example.meetfriends.etkinlikFragmentlar;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.meetfriends.etkinlik.AktiviteContext;
import com.example.meetfriends.R;
import com.example.meetfriends.other.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PopupFragment extends Fragment {

    TextView nameText, tarihText,AdresText;
    TextView describText;
    ProgressBar progressBar;
    ImageView imageView;
    TextView userInfo;
    Button btnEtkinlikSil; // yeniiiiiiiiiiiiiiiiiiiiiiiiiiiii
    Button btnEtkinlikBitir;

    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;


    private AktiviteContext cont;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.meet_popup,container,false);

        nameText = rootView.findViewById(R.id.popup_name_textView);
        describText = rootView.findViewById(R.id.popup_description_textView);
        progressBar = rootView.findViewById(R.id.popup_progressbar);
        imageView = rootView.findViewById(R.id.popup_imageView);
        userInfo = rootView.findViewById(R.id.userInfo);



        tarihText = rootView.findViewById(R.id.tvTarih);
        AdresText = rootView.findViewById(R.id.tvAdres);
        btnEtkinlikSil = rootView.findViewById(R.id.btnEtkinlikSil);
        btnEtkinlikBitir = rootView.findViewById(R.id.btnEtkinlikBitir);

// yeniiiiiiiiiii


        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();



        // etkinliği olusturan kisi değil ise pop up u açan etkinlik sil buyonu görnmez
        String  s = cont.getAktiviteUserId();
        String ss = user.getUid();

        Log.w("Etkinlik Id", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + s);
        Log.w("User Id", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + ss);
        if(cont.getAktiviteUserId()==null){ btnEtkinlikSil.setVisibility(View.INVISIBLE);}
        else if(cont.getAktiviteUserId().equals(user.getUid())){ btnEtkinlikSil.setVisibility(View.VISIBLE);}
        else{                                           btnEtkinlikSil.setVisibility(View.INVISIBLE);}




        btnEtkinlikSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            firebaseFirestore.collection("Bilgiler").document(cont.getAktiviteEtkinlikID()).delete();            //whereEqualTo("EtkinlikID",cont.getAktiviteEtkinlikID())
                startActivity(new Intent(getActivity(), MainActivity.class));
                Toast.makeText(getContext(), "The event has been deleted.", Toast.LENGTH_SHORT).show();

                //fotoğraf storage'de depolanıyor onu silmek için bu da. firestoreda sadece link tutuluyor, yukarıda firestorage'daki linki siliyoruz ama storageda kalıyordu.
                String url = cont.getAktiviteFoto();
                storageReference = firebaseStorage.getReferenceFromUrl(url);
                storageReference.delete();

                Log.w("User Id", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +  cont.getAktiviteEtkinlikID());



            }
        });


        if(cont.getAktiviteUserId()==null){ btnEtkinlikBitir.setVisibility(View.INVISIBLE);}
        else if(cont.getAktiviteUserId().equals(user.getUid())){ btnEtkinlikBitir.setVisibility(View.VISIBLE);}
        else{                                           btnEtkinlikBitir.setVisibility(View.INVISIBLE);}


        btnEtkinlikBitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Bilgiler").document(cont.getAktiviteEtkinlikID()).update("isDone",true);
                // etkinlik isDone=true olarak değiş
                startActivity(new Intent(getActivity(), MainActivity.class));

            }
        });





        userInfo.setText("Kurucu: " + cont.getAktiviteInfo());
        tarihText.setText("Tarih: " +cont.getAktiviteTarih());
        AdresText.setText("Adres: "+ cont.getAktiviteAdres());
        describText.setText("Açıklama: " + cont.getAktiviteDescription());



        Picasso.get().load(cont.getAktiviteFoto()).fit().centerCrop().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
                System.out.println("hata bişi yaparsın buraya");
            }
        });


        return rootView;
    }

    public static PopupFragment newInstance(){ // kullanılma durumuna göre constructor hazırladık
        return new PopupFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cont = getActivity().getIntent().getParcelableExtra("Yolla"); //bilgi alma



    }



}
