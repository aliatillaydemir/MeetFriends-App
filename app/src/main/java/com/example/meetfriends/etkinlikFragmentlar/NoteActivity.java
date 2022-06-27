package com.example.meetfriends.etkinlikFragmentlar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meetfriends.R;
import com.example.meetfriends.contentProvider.NoteContentProvider;

public class NoteActivity extends AppCompatActivity {

    EditText editText;
    Button updateBt, deleteBt, saveBt;

    String firstName; //güncelleme yapacağımız zaman yazıları aramak için kullanılır.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        updateBt = findViewById(R.id.updateBut);
        deleteBt = findViewById(R.id.deleteBut);
        saveBt = findViewById(R.id.saveBut);
        editText = findViewById(R.id.notText);


        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if(info.matches("new")){
            editText.setText("");

            saveBt.setVisibility(View.VISIBLE);
            deleteBt.setVisibility(View.INVISIBLE);
            updateBt.setVisibility(View.INVISIBLE);

        }else{
            String name = intent.getStringExtra("name");
            editText.setText(name);
            firstName = name;

            saveBt.setVisibility(View.INVISIBLE);
            deleteBt.setVisibility(View.VISIBLE);
            updateBt.setVisibility(View.VISIBLE);

        }

        saveFun();
        updateFun();
        deleteFun();

//onCreate sonu
    }


    private void saveFun() {

        saveBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String NoteText = editText.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put(NoteContentProvider.NAME,NoteText);

                getContentResolver().insert(NoteContentProvider.CONTENT_URI,contentValues);
                startActivity(new Intent(getApplicationContext(),Detail.class));

                //Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void updateFun() {

        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"updated",Toast.LENGTH_SHORT).show();

                String noteText = editText.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put(NoteContentProvider.NAME,noteText);

                String[] arguments = {firstName};
                getContentResolver().update(NoteContentProvider.CONTENT_URI,contentValues,"name=?",arguments);

                Intent intent = new Intent(getApplicationContext(),Detail.class);
                startActivity(intent);

            }
        });

    }

    private void deleteFun() {

        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"deleted",Toast.LENGTH_SHORT).show();

                String record = editText.getText().toString();

                String[] arguments = {record};

                getContentResolver().delete(NoteContentProvider.CONTENT_URI,"name=?", arguments);

                startActivity(new Intent(getApplicationContext(),Detail.class));

            }
        });

    }



}