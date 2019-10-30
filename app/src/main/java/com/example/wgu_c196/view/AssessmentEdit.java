package com.example.wgu_c196.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mAssessType;
import com.example.wgu_c196.utilities.TextFormatter;
import com.example.wgu_c196.view_model.EViewModel;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wgu_c196.utilities.Constants.ASSESSIDKEY;
import static com.example.wgu_c196.utilities.Constants.COURSEIDKEY;
import static com.example.wgu_c196.utilities.Constants.EDITINGKEY;


public class AssessmentEdit extends AppCompatActivity {

    @BindView(R.id.assess_edit_title)
    EditText editTextAssessTitle;
    @BindView(R.id.assess_edit_date)
    EditText editTextAssessDate;
    @BindView(R.id.assess_edit_type_dropdown)
    Spinner spinnerAssessType;
    private EViewModel eViewModel;
    private boolean newAssess, aEdit;
    private int cId = -1;
    private ArrayAdapter<mAssessType> assessTypeAdaptr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        if(savedInstanceState != null) {
            aEdit = savedInstanceState.getBoolean(EDITINGKEY);
        }
        initViewModel();
        addSpinItms();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!newAssess) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edit_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    private void addSpinItms() {
        assessTypeAdaptr = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mAssessType.values());
        spinnerAssessType.setAdapter(assessTypeAdaptr);
    }
    private void initViewModel() {
        eViewModel = ViewModelProviders.of(this).get(EViewModel.class);

        eViewModel.MLDAssess.observe(this, assess -> {
            if(assess != null && !aEdit) {
                editTextAssessTitle.setText(assess.getTitle());
                editTextAssessDate.setText(TextFormatter.fulDateFrmat.format(assess.getDate()));
                int position = getSpinPosition(assess.getAssesstype());
                spinnerAssessType.setSelection(position);
            }
        });
        Bundle xtras = getIntent().getExtras();
        if(xtras == null) {
            setTitle("New Assessment");
            newAssess = true;
        } else if (xtras.containsKey(COURSEIDKEY)) {
            cId = xtras.getInt(COURSEIDKEY);
            setTitle("New Assessment");
        } else {
            setTitle("Edit Assessment");
            int assessId = xtras.getInt(ASSESSIDKEY);
            eViewModel.LAssessData(assessId);
        }
    }
    private mAssessType gSpinVal() {
        return (mAssessType) spinnerAssessType.getSelectedItem();
    }
    private int getSpinPosition(mAssessType assessType) {
        return assessTypeAdaptr.getPosition(assessType);
    }
    @Override
    public void onBackPressed() {
        savAndRetrn();
    }
    public void savAndRetrn() {
        try {
            Date date = TextFormatter.fulDateFrmat.parse(editTextAssessDate.getText().toString());
            eViewModel.savAssess(editTextAssessTitle.getText().toString(), date, gSpinVal(), cId);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
        }
        finish();
    }
    @Override
    protected void onSaveInstanceState(Bundle oState) {
        oState.putBoolean(EDITINGKEY, true);
        super.onSaveInstanceState(oState);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            savAndRetrn();
            return true;
        } else if(item.getItemId() == R.id.edit_menu_delete_button) {
            eViewModel.delAssess();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @OnClick(R.id.assess_edit_date_button)
    public void assessDatePickr() {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            editTextAssessDate.setText(TextFormatter.fulDateFrmat.format(cal.getTime()));
        };
        new DatePickerDialog(this, date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }
}
