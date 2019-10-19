package com.example.wgu_c196.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mTerm;
import com.example.wgu_c196.ui.RecContext;
import com.example.wgu_c196.ui.TermAdapter;
import com.example.wgu_c196.view_model.TViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Term extends AppCompatActivity {

    @BindView(R.id.term_main_recycler_view)
    RecyclerView tRecyclerView;

    @OnClick(R.id.term_main_fab)
    void fClickHandler() {
        Intent intent = new Intent(this, TermEdit.class);
        startActivity(intent);
    }

    private List<mTerm> tData = new ArrayList<>();
    private TermAdapter tAdapter;
    private TViewModel TVModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_main);
        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }
    private void initRecyclerView() {
        tRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lManager = new LinearLayoutManager(this);
        tRecyclerView.setLayoutManager(lManager);
    }

    private void initViewModel() {
        final Observer<List<mTerm>> tObserver =
                tEntities -> {
                    tData.clear();
                    tData.addAll(tEntities);

                    if(tAdapter == null) {
                        tAdapter = new TermAdapter(tData, Term.this, RecContext.MAIN);
                        tRecyclerView.setAdapter(tAdapter);
                    } else {
                        tAdapter.notifyDataSetChanged();
                    }
                };
        TVModel = ViewModelProviders.of(this).get(TViewModel.class);
        TVModel.Terms.observe(this, tObserver);
    }
}
