package com.example.meetfriends.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meetfriends.R;
import com.example.meetfriends.recyclerview.ChatAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OSDeviceState;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    EditText messageTx;
    Button sendBut;

    ChatAdapter chatAdapter;
    private ArrayList<String> chatMessage = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    private static final String ONESIGNAL_APP_ID = "??????????????";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendBut = findViewById(R.id.sendBut);
        messageTx = findViewById(R.id.message_text);
        recyclerView = findViewById(R.id.chatRecycler);
        chatAdapter = new ChatAdapter(chatMessage);

        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerViewManager);//bununla recylerviewe ekleme yapılabilir.
        recyclerView.setItemAnimator(new DefaultItemAnimator()); //basit bir animasyon eklendi
        recyclerView.setAdapter(chatAdapter);

        //mAuth.getCurrentUser(); ??
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        getData();

        //mesaj gönder butonuna basıldığında...
        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String messageSend = messageTx.getText().toString();

                UUID uuid = UUID.randomUUID(); //bir tane unique id uyduruluyor.
                String idString = uuid.toString();

                FirebaseUser user = mAuth.getCurrentUser(); //şu anki(güncel) kullanıcı
                String userMail = user.getEmail().toString();

                databaseReference.child("Mesajlar").child(idString).child("userMessage").setValue(messageSend);
                databaseReference.child("Mesajlar").child(idString).child("userMail").setValue(userMail);
                databaseReference.child("Mesajlar").child(idString).child("userTime").setValue(ServerValue.TIMESTAMP); //mesajların zamanına göre mesajları çekmiş olacğaız.

                messageTx.setText(""); //sıfırlansın ki kullanıcı temiz bir sayfaya başlasın.

                getData();

                //mesaj gönderdikten sonra push notification göndereceğiz.

                DatabaseReference newRef = database.getReference("Player ids");
                newRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dsnap : snapshot.getChildren()){
                            HashMap<String,String> hashMap = (HashMap<String,String>) dsnap.getValue();
                            String playerid = hashMap.get("player id");

                            try {
                                OneSignal.postNotification(new JSONObject("{'contents': {'en':'"+messageSend+"'}, 'include_player_ids': ['" + playerid + "']}"), null);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        //butona tıkladığında mesaj gönder....



        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        OSDeviceState deviceState = OneSignal.getDeviceState();
        String userId = deviceState != null ? deviceState.getUserId() : null;

        System.out.println("userId: " + userId);

        UUID uuid = UUID.randomUUID();
       final String uuidstr = uuid.toString();

        DatabaseReference newrf = database.getReference("Player ids");
        newrf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {  //daha önceden kullanıcının idsi kaydedildiyse her seferinde veri tabanına yeniden kayıt olunmasın diye bu işlemler yapılır.

                ArrayList<String> playeridsFromServer = new ArrayList<>();

                for(DataSnapshot dsnap : snapshot.getChildren()){

                    HashMap<String,String> hashMap = (HashMap<String,String>) dsnap.getValue();
                    String currentid = hashMap.get("player id");
                    playeridsFromServer.add(currentid);
                 }
                if(!playeridsFromServer.contains(userId)){

                    databaseReference.child("Player ids").child(uuidstr).child("player id").setValue(userId);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void getData(){

        DatabaseReference newRef = database.getReference("Mesajlar"); //real time database'De sadece Mesajlar kısmıyla işlem yapacağımız bir referans verdik.

        Query query = newRef.orderByChild("userTime"); //yukarıda belirttiğimiz zamana göre sıralayacak mesajları

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //veriler yenilendiğinde...

                chatMessage.clear(); //birden fazla eklemesin diye öncekileri sileriz.

                for (DataSnapshot dsnap : snapshot.getChildren()){

                    HashMap<String,String> hashMap = (HashMap<String, String>) dsnap.getValue();
                    String uemail = hashMap.get("userMail");
                    String umessage = hashMap.get("userMessage");

                    chatMessage.add(uemail + ": \n"+ umessage);
                    chatAdapter.notifyDataSetChanged(); //güncelle
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(),error.getMessage().toString(),Toast.LENGTH_LONG).show(); //internet bağlantısı için vs. herhangi bir hata durumunda.

            }
        });

    }



}