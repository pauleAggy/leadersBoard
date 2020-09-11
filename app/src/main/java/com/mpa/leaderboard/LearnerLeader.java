package com.mpa.leaderboard;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.squareup.picasso.Picasso;

public  class LearnerLeader extends Leader implements Parcelable {
    public ObservableField<Integer> hours = new ObservableField<Integer>();

    public LearnerLeader(String name, String country, String badgeUrl,int hours) {

        try {
            this.name.set(name);
            this.country.set(country);
            this.badgeUrl.set(badgeUrl);
            this.hours.set(hours);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected LearnerLeader(Parcel in) {
        name.set(in.readString());
        country.set(in.readString());
        badgeUrl.set(in.readString());
        hours.set(in.readInt());

    }

    public static final Creator<LearnerLeader> CREATOR = new Creator<LearnerLeader>() {
        @Override
        public LearnerLeader createFromParcel(Parcel in) {
            return new LearnerLeader(in);
        }

        @Override
        public LearnerLeader[] newArray(int size) {
            return new LearnerLeader[size];
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
        parcel.writeInt(hours.get());
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {

        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.learner)
                .into(view);
    }

}
