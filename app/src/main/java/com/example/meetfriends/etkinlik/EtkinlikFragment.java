package com.example.meetfriends.etkinlik;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetfriends.R;
import com.example.meetfriends.chat.ChatActivity;
import com.example.meetfriends.recyclerview.EtkinlikPictureAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class EtkinlikFragment extends Fragment implements EtkinlikPictureAdapter.OnContextListener{

    private EtkinlikPictureAdapter etkinlikPictureAdapter;
    private RecyclerView recyclerView;
    private ArrayList<AktiviteContext> aktiviteContexts;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    FloatingActionButton messageBut;

    public static EtkinlikFragment newInstance(){
        return new EtkinlikFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.etkinlik_fragment,container,false);

        messageBut = rootView.findViewById(R.id.messageBut);
        messageBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChatActivity.class));
            }
        });


        etkinlikPictureAdapter = new EtkinlikPictureAdapter((AppCompatActivity) getActivity(),this);

        aktiviteContexts = etkinlikPictureAdapter.getAktiviteContexts();
        recyclerView = rootView.findViewById(R.id.fragment_etkinlik);
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(isAdded()){
            recyclerView.setAdapter(etkinlikPictureAdapter);
        }

        getAktiviteContexts(aktiviteContexts);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();



        firebaseFirestore.collection("Bilgiler").orderBy("Date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null){
                    Toast.makeText(getActivity(), error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }

                if(value != null){
                    for(DocumentSnapshot snap: value.getDocuments()){
                        Map<String,Object> data = snap.getData();

                        String baslik = (String) data.get("Adres");
                        String aciklama = (String) data.get("Aciklama");
                        String url = (String) data.get("Url");
                        String kisi = (String) data.get("Mail");
                        String tarih = (String) data.get("eventDate");
                        String userID = (String) data.get("kurucuId");
                        String EventID = (String) data.get("EtkinlikID");

                        Boolean isDone = (Boolean) data.get("isDone");

                        //(aktiviteContexts.add(new AktiviteContext("","",""));
                        if(!isDone){
                            aktiviteContexts.add(new AktiviteContext(baslik,url,aciklama,kisi,tarih,userID,EventID));
                        }



                        //(aktiviteContexts.add(new AktiviteContext("","",""));
                    //    aktiviteContexts.add(new AktiviteContext(baslik,url,aciklama,kisi,tarih,userID,EventID));


                    }
                    etkinlikPictureAdapter.notifyDataSetChanged();
                }

            }
        });


        return rootView;
    }



    private ArrayList<AktiviteContext> getAktiviteContexts(ArrayList<AktiviteContext> aktiviteContexts){

        //firebase'den veri(foto) çekme falan hep burada yapılacak. 190.video -> 1.30 sn.


/*
        //bir tane yeter, denemek için
        for(int i=0;i<20;i++) {
            aktiviteContexts.add(new AktiviteContext("izmit/Kocaeli x sokak",
                    "https://mikroplastik.org/wp-content/uploads/2021/01/IMG_0016-1-1024x576.jpg",
                    "İzmit x semtteki aksaklıklar çöp yığınlarına sebep oluyor."));

            aktiviteContexts.add(new AktiviteContext("lelelel",
                    "https://thumbs.dreamstime.com/b/photography-garbage-nature-river-photography-garbage-nature-river-177580738.jpg",
                    "tanım şurada burada"));

            aktiviteContexts.add(new AktiviteContext("x yer",
                    "https://previews.123rf.com/images/darkbird/darkbird1209/darkbird120900025/23036854-.jpg",
                    "tanım"));

            aktiviteContexts.add(new AktiviteContext("izmit",
                    "https://previews.123rf.com/images/mihail39/mihail391706/mihail39170600106/80753608-pile-of-garbage-on-green-grass-in-the-nature-environment-problems.jpg",
                    "tanım şurada burada"));

            aktiviteContexts.add(new AktiviteContext("kou",
                    "https://st3.depositphotos.com/4831813/19539/i/1600/depositphotos_195390766-stock-photo-dump-garbage-in-the-nature.jpg",
                    "tanım şurada burada"));

            aktiviteContexts.add(new AktiviteContext("hocammm",
                    "https://media.istockphoto.com/photos/man-and-trash-man-litters-garbage-in-nature-picture-id1156247235",
                    "tanım şurada burada"));

        } //silinebilir, denemek için bu bloklar, bir tane yeter.

*/

        return aktiviteContexts;
    }

    @Override
    public void OnContextListener(AktiviteContext aktiviteContext) {
        //System.out.println("yeyyyyyy" +aktiviteContext.getAktiviteName()); test başarılı, hangi görüntüye tıkladığımız görülüyor.

        Intent intent = MeetPopupActivity.newIntent(getActivity(),aktiviteContext); //MeetPopupActivity ile sağlanıyor. aktiviteContext bilgileri intentler yollanmış olacak
        startActivity(intent);
    }



}
