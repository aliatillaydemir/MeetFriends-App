package com.example.meetfriends.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class NoteContentProvider extends ContentProvider {

    //"content://com.example.meetfriends.contentProvider.NoteContentProvider/note" uri oluşturmaya çalışacağız.
    public static final String PROVIDER_NAME = "com.example.meetfriends.contentProvider.NoteContentProvider";
    public static final String URL = "content://" + PROVIDER_NAME + "/note";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String NAME = "name";
    public static final int NOTE = 1;

    public static final UriMatcher uriMatcher; //uri oluşturdupunda birbirleriyle karşılaştırmak için. tek tablo yapıyoruz, fazla gerek yok aslında

    static { //BU SINIFTAN HERHANGİ BİR ŞEYE ULAŞILDIĞINDA BU STATİCİ ÇALIŞTIRACAĞIZ.
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH); //başta herhangi bir eşleşme yok.
        uriMatcher.addURI(PROVIDER_NAME,"note",NOTE);
    }

    static HashMap<String,String> NOTE_PROJECTION_MAP;

    //----------------------DATABASE-------------------------------
    private SQLiteDatabase sqLiteDatabase;
    static final String DATABASE_NAME = "Note";  //başka sınıflarda da kullanabiliriz static olması sayesinde
    static final String NOTE_TABLE_NAME = "Notes";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DATABASE_TABLE = "CREATE TABLE " +
            NOTE_TABLE_NAME + "(name TEXT NOT NULL);"; // Boş olmayacak, kullanıcı doldurmak zorunda kaydetmek için.


    private static class DatabaseHelper extends SQLiteOpenHelper {  //database işlemlerini kolaylaştırmak için

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(CREATE_DATABASE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE_NAME); //güncellersek böyle bir tablo varsa bunu yok et tekrar yaratırız.
            onCreate(sqLiteDatabase);
        }
    }
//DatabaseHelper sonu----------------DATABASE-----------------------------


    @Override
    public boolean onCreate() {  //ilk bu sınıf çağırılıyor, ne yapacağımızı buraya yazıyoruz.

        Context context = getContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        sqLiteDatabase = databaseHelper.getWritableDatabase();

        return sqLiteDatabase != null; //eğer sql. boş değilse, alınabiliyorsa true döndürecek. böylelikle booelan bir ifade etmeyi başarıyoruz.
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(NOTE_TABLE_NAME);

        switch (uriMatcher.match(uri)){
            case NOTE:
                sqLiteQueryBuilder.setProjectionMap(NOTE_PROJECTION_MAP);
                break;
            default:
                // başka bir durum söz konusu değil, 1 tane database var ve çalışacak.

        }

        if(s1 == null || s1.matches("")){ //s1: sortOrder. sıralama, dizme için
            s1 = NAME;
        }
        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase,strings,s,strings1,null,null,s1);

        cursor.setNotificationUri(getContext().getContentResolver(),uri); //uri'da değişiklik olursa takip etmek için kullandığımız bir yöntem.
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

       long rowID = sqLiteDatabase.insert(NOTE_TABLE_NAME,"",contentValues);

       if(rowID>0){
       Uri newUri = ContentUris.withAppendedId(CONTENT_URI,rowID);
       getContext().getContentResolver().notifyChange(newUri,null);
       return newUri;
       }

       throw new SQLException("FAILED");

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        int rowCount = 0;

        switch (uriMatcher.match(uri)){

            case NOTE:
                rowCount = sqLiteDatabase.delete(NOTE_TABLE_NAME,s,strings); //kaç satır sildiğimizi de rowCount'a eşitleyerek bulacağız çünkü bu kullanılacak.
                break;

            default:
                throw new IllegalArgumentException("Error found"); //uri eşleşmediğinde, yanlış bir eşleşmede.
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return rowCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        int rowCount = 0;

        switch (uriMatcher.match(uri)){

            case NOTE:
                rowCount = sqLiteDatabase.update(NOTE_TABLE_NAME,contentValues,s,strings);
                break;

            default:
                throw new IllegalArgumentException("Error found"); //uri eşleşmediğinde, yanlış bir eşleşmede.
        }

        getContext().getContentResolver().notifyChange(uri,null);


        return rowCount;
    }
}
