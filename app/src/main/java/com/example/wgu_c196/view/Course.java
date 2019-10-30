package com.example.wgu_c196.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mCourse;
import com.example.wgu_c196.ui.CAdapter;
import com.example.wgu_c196.ui.RecContext;
import com.example.wgu_c196.view_model.CViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Course extends AppCompatActivity implements CAdapter.CSelectedListener{
    @BindView(R.id.course_recycler_view)
    RecyclerView courseRecyclerView;
    @OnClick(R.id.course_fab)
    void fClickHandlr() {
        Intent intent = new Intent(this, CourseEdit.class);
        startActivity(intent);
    }
    @Override
    public void onCSelected(int position, mCourse crse) {

    }
    private List<mCourse> crseData = new ArrayList<>();
    private CAdapter crseAdapter;
    private CViewModel cViewModel;
    private void initViewModel() {
        final Observer<List<mCourse>> crseObserver =
                crseEntities -> {
                    crseData.clear();
                    crseData.addAll(crseEntities);

                    if(crseAdapter == null) {
                        crseAdapter = new CAdapter(crseData, Course.this, RecContext.MAIN, this);
                        courseRecyclerView.setAdapter(crseAdapter);
                    } else {
                        crseAdapter.notifyDataSetChanged();
                    }
                };
        cViewModel = ViewModelProviders.of(this).get(CViewModel.class);
        cViewModel.crse.observe(this, crseObserver);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);
        //TODO
        //Tool bar
        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }
    private void initRecyclerView() {
        courseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager LManager = new LinearLayoutManager(this);
        courseRecyclerView.setLayoutManager(LManager);
    }
}
