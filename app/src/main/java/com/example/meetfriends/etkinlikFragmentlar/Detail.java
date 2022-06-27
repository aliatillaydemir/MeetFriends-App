package com.example.meetfriends.etkinlikFragmentlar;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.meetfriends.R;
import com.example.meetfriends.contentProvider.NoteContentProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Detail extends Fragment {

    FloatingActionButton button;
    ListView listView;


    public static Detail newInstance(){
        return new Detail();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detay_fragment,container,false);

        button = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        listView = rootView.findViewById(R.id.listView);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), NoteActivity.class); //fragment olduğu için getActivity kullandım.
                intent.putExtra("info","new"); //old new kullanıyoruz çünkü ne yapacağımıza güncelleme mi yaratmak için mi vs.
                startActivity(intent);

            }
        });

        ArrayList noteList = new ArrayList<String>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,noteList);
        listView.setAdapter(arrayAdapter);

        String Url = "content://com.example.meetfriends.contentProvider.NoteContentProvider";
        Uri noteUri = Uri.parse(Url);

        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(noteUri,null,null,null,"name");

        if(cursor != null){

            while(cursor.moveToNext()){

                int sayi = cursor.getColumnIndex(NoteContentProvider.NAME);
                noteList.add(cursor.getString(sayi));


                arrayAdapter.notifyDataSetChanged();
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(),NoteActivity.class);

                intent.putExtra("info","old"); //old new kullanıyoruz çünkü ne yapacağımıza güncelleme mi yaratmak için mi vs.
                intent.putExtra("name", noteList.get(i).toString());
                intent.putExtra("position",i);
                startActivity(intent);
            }
        });


        return rootView;
    }




}
