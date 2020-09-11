package com.mpa.leaderboard;


import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.mpa.leaderboard.Leader;
import com.squareup.picasso.Picasso;

public  class IqLeader extends Leader implements Parcelable {
    public ObservableField<Integer> score = new ObservableField<Integer>();

    public IqLeader(String name, String country, String badgeUrl,int score) {

        try {
            this.name.set(name);
            this.country.set(country);
            this.badgeUrl.set(badgeUrl);
            this.score.set(score);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected IqLeader(Parcel in) {
        name.set(in.readString());
        country.set(in.readString());
        badgeUrl.set(in.readString());
        score.set(in.readInt());

    }



    public static final Creator<IqLeader> CREATOR = new Creator<IqLeader>() {
        @Override
        public IqLeader createFromParcel(Parcel in) {
            return new IqLeader(in);
        }

        @Override
        public IqLeader[] newArray(int size) {
            return new IqLeader[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name.get());
        parcel.writeString(country.get());
        parcel.writeString(badgeUrl.get());
        parcel.writeInt(score.get());
    }


    @BindingAdapter({"android:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {

        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.skill)
                .into(view);
    }


}
