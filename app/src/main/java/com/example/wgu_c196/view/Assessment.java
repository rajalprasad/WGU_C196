package com.example.wgu_c196.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mAssessment;
import com.example.wgu_c196.ui.AAdapter;
import com.example.wgu_c196.ui.RecContext;
import com.example.wgu_c196.view_model.AViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Assessment extends AppCompatActivity implements AAdapter.ASelectedListener {
    @BindView(R.id.assess_recycler_view)
    RecyclerView assessRecyclerView;
    @OnClick(R.id.activity_assess_main_fab)
    void fClickHandlr() {
        Intent intent = new Intent(this, AssessmentEdit.class);
        startActivity(intent);
    }
    private List<mAssessment> assessData = new ArrayList<>();
    private AAdapter aAdapter;
    private AViewModel aViewModel;
    private void initRecyclerView() {
        assessRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lManager = new LinearLayoutManager(this);
        assessRecyclerView.setLayoutManager(lManager);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }
    @Override
    public void onAssessSelect(int position, mAssessment assess) {
    }
    private void initViewModel() {
        final Observer<List<mAssessment>> assessObserver =
                assessEntities -> {
                    assessData.clear();
                    assessData.addAll(assessEntities);
                    if(aAdapter == null) {
                        aAdapter = new AAdapter(assessData, Assessment.this, RecContext.MAIN, this);
                        assessRecyclerView.setAdapter(aAdapter);
                    } else {
                        aAdapter.notifyDataSetChanged();
                    }
                };
        aViewModel = ViewModelProviders.of(this).get(AViewModel.class);
        aViewModel.assess.observe(this, assessObserver);
    }
}
