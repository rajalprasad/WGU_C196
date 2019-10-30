package com.example.wgu_c196.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.wgu_c196.R;
import com.example.wgu_c196.view_model.EViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wgu_c196.utilities.Constants.COURSEIDKEY;
import static com.example.wgu_c196.utilities.Constants.EDITINGKEY;
import static com.example.wgu_c196.utilities.Constants.MENTORIDKEY;

public class MentorEdit extends AppCompatActivity {

    @BindView(R.id.mentor_edit_name)
    EditText editTextMentorName;
    @BindView(R.id.mentor_edit_email_address)
    EditText editTextMentorEmailAddress;
    @BindView(R.id.mentor_edit_phone_number)
    EditText editTextMentorPhoneNumber;

    private EViewModel eViewModel;
    private boolean newMentr, mEdit;
    private int cId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_edit);
        Toolbar tbar = findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        if(savedInstanceState != null) {
            mEdit = savedInstanceState.getBoolean(EDITINGKEY);
        }
        initViewModel();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem itm) {
        if(itm.getItemId() == android.R.id.home) {
            savAndRetrn();
            return true;
        } else if(itm.getItemId() == R.id.edit_menu_delete_button) {
            eViewModel.delAssess();
            finish();
        }
        return super.onOptionsItemSelected(itm);
    }
    private void initViewModel() {
        eViewModel = ViewModelProviders.of(this).get(EViewModel.class);

        eViewModel.MLDMentor.observe(this, mentr -> {
            if(mentr != null && !mEdit) {
                editTextMentorName.setText(mentr.getName());
                editTextMentorEmailAddress.setText(mentr.getEmailaddress());
                editTextMentorPhoneNumber.setText(mentr.getPhonenumber());
            }
        });
        Bundle xtras = getIntent().getExtras();
        if(xtras == null) {
            setTitle("New Mentor");
            newMentr = true;
        } else if (xtras.containsKey(COURSEIDKEY)) {
            cId = xtras.getInt(COURSEIDKEY);
            setTitle("New Mentor");
        } else {
            setTitle("Edit Mentor");
            int mId = xtras.getInt(MENTORIDKEY);
            eViewModel.LMentorData(mId);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!newMentr) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edit_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onSaveInstanceState(Bundle oState) {
        oState.putBoolean(EDITINGKEY, true);
        super.onSaveInstanceState(oState);
    }
    @Override
    public void onBackPressed() {
        savAndRetrn();
    }

    public void savAndRetrn() {
        eViewModel.savMentor(editTextMentorName.getText().toString(), editTextMentorEmailAddress.getText().toString(), editTextMentorPhoneNumber.getText().toString(), cId);
        finish();
    }
}
