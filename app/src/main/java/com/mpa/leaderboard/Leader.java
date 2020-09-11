package com.mpa.leaderboard;


import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

public abstract class Leader {

    public ObservableField<String> name= new ObservableField<String>();
    public ObservableField<String> country= new ObservableField<String>();
    public ObservableField<String> badgeUrl= new ObservableField<String>();



}
