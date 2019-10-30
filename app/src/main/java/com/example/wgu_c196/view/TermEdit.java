package com.example.wgu_c196.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mCourse;
import com.example.wgu_c196.utilities.TextFormatter;
import com.example.wgu_c196.view_model.EViewModel;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wgu_c196.utilities.Constants.EDITINGKEY;
import static com.example.wgu_c196.utilities.Constants.TERMIDKEY;

public class TermEdit extends AppCompatActivity {
    @BindView(R.id.term_edit_title)
    EditText TermTitle;
    @BindView(R.id.term_edit_start_date)
    EditText TermStartDate;
    @BindView(R.id.term_edit_end_date)
    EditText TermEndDate;
    @BindView(R.id.term_edit_start_date_button)
    ImageButton StartDateButton;
    @BindView(R.id.term_edit_end_date_button)
    ImageButton EndDateButton;
    private EViewModel eViewModel;
    int tId;
    private boolean newTerm, mEdit;
    private List<mCourse> cData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            mEdit = savedInstanceState.getBoolean(EDITINGKEY);
        }

        initViewModel();
    }

    private void initViewModel() {
        eViewModel = ViewModelProviders.of(this).get(EViewModel.class);
        eViewModel.MLDTerm.observe(this, Term -> {
            if(Term != null && !mEdit) {
                TermTitle.setText(Term.getTitle());
                TermStartDate.setText(TextFormatter.fulDateFrmat.format(Term.getStrtDate()));
                TermEndDate.setText(TextFormatter.fulDateFrmat.format(Term.getEndDate()));
            }
        });
        Bundle xtras = getIntent().getExtras();
        if(xtras == null) {
            setTitle("New Term");
            newTerm = true;
        } else {
            setTitle("Edit Term");
            tId = xtras.getInt(TERMIDKEY);
            eViewModel.LTermData(tId);
        }
        final Observer<List<mCourse>> cObserver = cEntities -> {
            cData.clear();
            cData.addAll(cEntities);
        };
        eViewModel.gCrsesInTerm(tId).observe(this, cObserver);
    }
    private void handleDel() {
        if(eViewModel.MLDTerm.getValue() != null) {
            String tTitle = eViewModel.MLDTerm.getValue().getTitle();
            if(cData != null && cData.size() != 0) {
                AlertDialog.Builder aDBuilder = new AlertDialog.Builder(this);
                aDBuilder.setTitle("Delete " + tTitle + "?");
                aDBuilder.setMessage("You sure you want to delete '" + tTitle + "'?" +
                        "\nIt has courses assigned to it. You will not delete the courses but " +
                        "they will not be assigned to any terms if you delete it.\nDelete the term anyway?");
                aDBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                aDBuilder.setPositiveButton("Yes", (dialog, id) -> {
                    dialog.dismiss();
                    eViewModel.delTerm();
                    finish();
                });
                aDBuilder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
                aDBuilder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
                AlertDialog alertDialog = aDBuilder.create();
                alertDialog.show();
            } else {
                AlertDialog.Builder aDBuilder = new AlertDialog.Builder(this);
                aDBuilder.setTitle("Delete " + tTitle + "?");
                aDBuilder.setMessage("You sure you want to delete the term '" + tTitle + "'?");
                aDBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                aDBuilder.setPositiveButton("Yes", (alertDialog, id) -> {
                    alertDialog.dismiss();
                    eViewModel.delTerm();
                    finish();
                });
                aDBuilder.setNegativeButton("Cancel", (alertDialog, id) -> alertDialog.dismiss());
                AlertDialog alertDialog = aDBuilder.create();
                alertDialog.show();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle oState) {
        oState.putBoolean(EDITINGKEY, true);
        super.onSaveInstanceState(oState);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem itm) {
        if(itm.getItemId() == android.R.id.home) {
            saveNdRetrn();
            return true;
        } else if (itm.getItemId() == R.id.edit_menu_delete_button) {
            handleDel();
        }
        return super.onOptionsItemSelected(itm);
    }
    @Override
    public void onBackPressed() {
        saveNdRetrn();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
        if(!newTerm) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edit_menu, menus);
        }
        return super.onCreateOptionsMenu(menus);
    }
    private void saveNdRetrn() {
        try {
            Date sDate = TextFormatter.fulDateFrmat.parse(TermStartDate.getText().toString());
            Date eDate = TextFormatter.fulDateFrmat.parse(TermEndDate.getText().toString());
            eViewModel.savTerm(TermTitle.getText().toString(), sDate, eDate);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
        }
        finish();
    }
    @OnClick(R.id.term_edit_end_date_button)
    public void tEDatePicker() {
        final Calendar mCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, yr, monthOfYr, dayOfMth) -> {
            mCalendar.set(Calendar.YEAR, yr);
            mCalendar.set(Calendar.MONTH, monthOfYr);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMth);

            TermEndDate.setText(TextFormatter.fulDateFrmat.format(mCalendar.getTime()));
        };
        new DatePickerDialog(this, date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    @OnClick(R.id.term_edit_start_date_button)
    public void tSDatePicker() {
        final Calendar mCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, yr, monthOfYr, dayOfMth) -> {
            mCalendar.set(Calendar.YEAR, yr);
            mCalendar.set(Calendar.MONTH, monthOfYr);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMth);

            TermStartDate.setText(TextFormatter.fulDateFrmat.format(mCalendar.getTime()));
        };
        new DatePickerDialog(this, date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
