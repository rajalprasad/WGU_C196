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
import com.example.wgu_c196.model.mMentor;
import com.example.wgu_c196.ui.MAdapter;
import com.example.wgu_c196.ui.RecContext;
import com.example.wgu_c196.view_model.MViewModel;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Mentor extends AppCompatActivity implements MAdapter.MSelectListner {
    @BindView(R.id.mentor_list)
    RecyclerView mentorRecyclerView;

    @OnClick(R.id.mentor_fab)
    void fClickHandlr() {
        Intent intent = new Intent(this, MentorEdit.class);
        startActivity(intent);
    }
    private List<mMentor> mData = new ArrayList<>();
    private MAdapter mAdapter;
    private MViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_main);
        Toolbar tbar = findViewById(R.id.toolbar);
        setSupportActionBar(tbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }
    @Override
    public void onMentrSelect(int position, mMentor mentr) {
    }
    private void initViewModel() {
        final Observer<List<mMentor>> mentrObserver =
                mentrEntities -> {
                    mData.clear();
                    mData.addAll(mentrEntities);

                    if(mAdapter == null) {
                        mAdapter = new MAdapter(mData, Mentor.this, RecContext.MAIN, this);
                        mentorRecyclerView.setAdapter(mAdapter);
                    } else {
                        mAdapter.notifyDataSetChanged();
                    }
                };
        mViewModel = ViewModelProviders.of(this).get(MViewModel.class);
        mViewModel.mentors.observe(this, mentrObserver);
    }
    private void initRecyclerView() {
        mentorRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lManager = new LinearLayoutManager(this);
        mentorRecyclerView.setLayoutManager(lManager);
    }
}
