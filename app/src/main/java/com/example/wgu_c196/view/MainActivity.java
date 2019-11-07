package com.example.wgu_c196.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mAssessment;
import com.example.wgu_c196.model.mCourse;
import com.example.wgu_c196.model.mTerm;
import com.example.wgu_c196.ui.TermAdapter;
import com.example.wgu_c196.utilities.Alrting;
import com.example.wgu_c196.view_model.MViewModel;
import com.example.wgu_c196.view_model.MainVModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<mTerm> tData = new ArrayList<>();
    private List<mCourse> cData = new ArrayList<>();
    private List<mAssessment> assessData = new ArrayList<>();
    private TermAdapter tAdapter;
    private MainVModel mainVModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tbar = findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        initViewModel();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void sTerms(View view) {
        Intent intent = new Intent(this, Term.class);
        startActivity(intent);
    }
    public void sCourses(View view) {
        Intent intent = new Intent(this, Course.class);
        startActivity(intent);
    }
    public void sMentors(View view) {
        Intent intent = new Intent(this, Mentor.class);
        startActivity(intent);
    }
    public void sAssessments(View view) {
        Intent intent = new Intent(this, Assessment.class);
        startActivity(intent);
    }
    //TODO
    private void handlAlrts() {
        ArrayList<String> alrts = new ArrayList<>();
        for(mCourse crse: cData) {
            if(DateUtils.isToday(crse.getStrtDate().getTime())) {
                alrts.add("Course " + crse.getTitle() + " will begin today..");
            } else if(DateUtils.isToday(crse.getEndDate().getTime())) {
                alrts.add("Course " + crse.getTitle() + " will end today..");
            }
        }
        for(mAssessment assess: assessData) {
            if(DateUtils.isToday(assess.getDate().getTime())) {
                alrts.add("The assessment: " + assess.getTitle() + ", is due today..");
            }
        }
        if(alrts.size() > 0) {
            for(String alert: alrts) {
                AlarmManager alarm = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
                Alrting alrting = new Alrting();
                IntentFilter filtr = new IntentFilter("ALARM_ACTION");
                registerReceiver(alrting, filtr);
                Intent intent = new Intent("ALARM_ACTION");
                intent.putExtra("param", alert);
                PendingIntent opration = PendingIntent.getBroadcast(this, 0, intent, 0);
                alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ Toast.LENGTH_SHORT, opration);
            }
        }
    }
    private void initViewModel() {
        final Observer<List<mCourse>> crseObserver =
                crseEntities -> {
                    cData.clear();
                    cData.addAll(crseEntities);
                    handlAlrts();
                };
        final Observer<List<mAssessment>> assessObserver =
                assessEntities -> {
                    assessData.clear();
                    assessData.addAll(assessEntities);
                    handlAlrts();
                };
        mainVModel = ViewModelProviders.of(this).get(MainVModel.class);
        mainVModel.assess.observe(this, assessObserver);
        mainVModel.crse.observe(this, crseObserver);
    }
}
