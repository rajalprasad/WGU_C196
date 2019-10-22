package com.example.wgu_c196.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.wgu_c196.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;

public class CourseDetails extends AppCompatActivity extends AppCompatActivity implements AAdapter, MAdapter {
    @BindView(R.id.course_detail_status)
    TextView tvCourseStatus;

    @BindView(R.id.course_detail_note)
    TextView tvCourseNote;

    @BindView(R.id.fab_add_assessment)
    FloatingActionButton fabAddAssessment;

    @BindView(R.id.fab_add_mentor)
    FloatingActionButton fabAddMentor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
    }
}
