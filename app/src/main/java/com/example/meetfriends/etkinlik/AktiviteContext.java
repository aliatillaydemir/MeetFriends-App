package com.example.meetfriends.etkinlik;

import android.os.Parcel;
import android.os.Parcelable;

public class AktiviteContext implements Parcelable { //parcelable ile sınıf objesi oluşturabiliyor ve istediğimiz yere yollayabiliyoruz.

    private String AktiviteAdres;
    private String AktiviteFoto;
    private String AktiviteDescription;
    private String AktiviteInfo;
    private String AktiviteTarih;
    private String AktiviteUserId;
    private String AktiviteEtkinlikID;

    public AktiviteContext( String aktiviteAdres, String aktiviteFoto, String aktiviteDescription, String aktiviteInfo ,String aktiviteTarih,String aktiviteUserId,String aktiviteEtkinlikID ) {



        AktiviteAdres = aktiviteAdres;
        AktiviteFoto = aktiviteFoto;
        AktiviteDescription = aktiviteDescription;
        AktiviteInfo = aktiviteInfo;
        AktiviteTarih = aktiviteTarih;
        AktiviteUserId = aktiviteUserId;
        AktiviteEtkinlikID = aktiviteEtkinlikID;

    }

    public String getAktiviteEtkinlikID() { return AktiviteEtkinlikID; }

    public String getAktiviteTarih() {
        return AktiviteTarih;
    }

    public String getAktiviteUserId() {
        return AktiviteUserId;
    }

    public String getAktiviteAdres() {
        return AktiviteAdres;
    }

    public String getAktiviteFoto() {
        return AktiviteFoto;
    }

    public String getAktiviteDescription() {
        return AktiviteDescription;
    }

    public String getAktiviteInfo() {
        return AktiviteInfo;
    }


    protected AktiviteContext(Parcel in) {

        AktiviteAdres = in.readString();
        AktiviteFoto = in.readString();
        AktiviteDescription = in.readString();
        AktiviteInfo = in.readString();
        AktiviteTarih = in.readString();
        AktiviteUserId = in.readString();
        AktiviteEtkinlikID = in.readString();
    }

    public static final Creator<AktiviteContext> CREATOR = new Creator<AktiviteContext>() {
        @Override
        public AktiviteContext createFromParcel(Parcel in) {
            return new AktiviteContext(in);
        }

        @Override
        public AktiviteContext[] newArray(int size) {
            return new AktiviteContext[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(AktiviteAdres);
        dest.writeString(AktiviteFoto);
        dest.writeString(AktiviteDescription);
        dest.writeString(AktiviteInfo);
        dest.writeString(AktiviteTarih);
        dest.writeString(AktiviteUserId);
        dest.writeString(AktiviteEtkinlikID);
    }
}
