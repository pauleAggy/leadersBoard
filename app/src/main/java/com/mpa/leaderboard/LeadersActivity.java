/*
 *@Author MANDENGUE PAULE AGGY
 * this class will handle activity that displays list of leaders
 */
package com.mpa.leaderboard;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class LeadersActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leaders);


        tabLayout=findViewById(R.id.tab_leaders);
        viewPager=findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_tab_learning_leaders)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_tab_iq_leaders)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        Button buttonCallSubmissionActivity = findViewById(R.id.button_call_submission_activity);
        buttonCallSubmissionActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent loadSubmissionFormIntent = new Intent(getApplicationContext(), SubmissionActivity.class);
                startActivity(loadSubmissionFormIntent);
            }
        });
        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}