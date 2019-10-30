package com.example.wgu_c196.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wgu_c196.R;
import com.example.wgu_c196.utilities.TextFormatter;
import com.example.wgu_c196.view_model.EViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wgu_c196.utilities.Constants.ASSESSIDKEY;

public class AssessmentDetails extends AppCompatActivity {

    @BindView(R.id.assess_details_date)
    TextView textViewAssessDate;
    @BindView(R.id.assess_details_type)
    TextView textViewAssessType;

    private Toolbar tbar;
    private int aId;
    private EViewModel eViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_details);
        tbar = findViewById(R.id.toolbar);
        setSupportActionBar(tbar);

        ButterKnife.bind(this);
        initViewModel();
    }
    @OnClick(R.id.fab_assess_edit)
    public void openEdit() {
        Intent intent = new Intent(this, AssessmentEdit.class);
        intent.putExtra(ASSESSIDKEY, aId);
        this.startActivity(intent);
        finish();
    }
    private void initViewModel() {
        eViewModel = ViewModelProviders.of(this).get(EViewModel.class);

        eViewModel.MLDAssess.observe(this, assess -> {
            textViewAssessDate.setText(TextFormatter.fulDateFrmat.format(assess.getDate()));
            textViewAssessType.setText(assess.getAssesstype().toString());
            tbar.setTitle(assess.getTitle());
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            aId = extras.getInt(ASSESSIDKEY);
            eViewModel.LAssessData(aId);
        } else {
            finish();
        }
    }
}
