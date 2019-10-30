package com.example.wgu_c196.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wgu_c196.R;
import com.example.wgu_c196.view_model.EViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wgu_c196.utilities.Constants.MENTORIDKEY;

public class MentorDetails extends AppCompatActivity {

    @BindView(R.id.mentor_detail_email_address)
    TextView textViewMentorEmailAddress;
    @BindView(R.id.mentor_details_phone_number)
    TextView textViewMentorPhoneNumber;

    private Toolbar tbar;
    private int mId;
    private EViewModel eViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_details);
        tbar = findViewById(R.id.toolbar);
        setSupportActionBar(tbar);

        ButterKnife.bind(this);
        initViewModel();
    }
    @OnClick(R.id.mentor_details_fab)
    public void openEdit() {
        Intent intent = new Intent(this, MentorEdit.class);
        intent.putExtra(MENTORIDKEY, mId);
        this.startActivity(intent);
        finish();
    }
    private void initViewModel() {
        eViewModel = ViewModelProviders.of(this).get(EViewModel.class);

        eViewModel.MLDMentor.observe(this, mentr -> {
            textViewMentorEmailAddress.setText(mentr.getEmailaddress());
            textViewMentorPhoneNumber.setText(mentr.getPhonenumber());
            tbar.setTitle(mentr.getName());
        });
        Bundle xtras = getIntent().getExtras();
        if(xtras != null) {
            mId = xtras.getInt(MENTORIDKEY);
            eViewModel.LMentorData(mId);
        } else {
            finish();
        }
    }
}