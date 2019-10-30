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
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mCourseStat;
import com.example.wgu_c196.utilities.TextFormatter;
import com.example.wgu_c196.view_model.EViewModel;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wgu_c196.utilities.Constants.COURSEIDKEY;
import static com.example.wgu_c196.utilities.Constants.EDITINGKEY;
import static com.example.wgu_c196.utilities.Constants.TERMIDKEY;

public class CourseEdit extends AppCompatActivity {
    @BindView(R.id._course_edit_title)
    EditText editTextCourseTitle;
    @BindView(R.id.course_edit_start_date)
    EditText editTextCourseStartDate;
    @BindView(R.id.course_edit_end_date)
    EditText editTextCourseEndDate;
    @BindView(R.id.course_edit_start_date_button)
    ImageButton imageButtonCourseStartDate;
    @BindView(R.id.course_edit_end_date_button)
    ImageButton imageButtonCourseEndDate;
    @BindView(R.id.course_edit_status_dropdown)
    Spinner spinnerCourseStatus;
    @BindView(R.id.course_edit_notes)
    EditText editTextEditCourseNotes;
    private EViewModel eViewModel;
    private boolean bNCourse, bEdit;
    private int tId = -1;
    private ArrayAdapter<mCourseStat> crseStatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_edit);

        Toolbar tbar = findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            bEdit = savedInstanceState.getBoolean(EDITINGKEY);
        }
        initViewModel();
        addSpinItms();
    }
    private void initViewModel() {
        eViewModel = ViewModelProviders.of(this).get(EViewModel.class);

        eViewModel.MLDCourse.observe(this, course -> {
            if(course != null && !bEdit) {
                editTextCourseTitle.setText(course.getTitle());
                editTextCourseStartDate.setText(TextFormatter.fulDateFrmat.format(course.getStrtDate()));
                editTextCourseEndDate.setText(TextFormatter.fulDateFrmat.format(course.getEndDate()));
                editTextEditCourseNotes.setText(course.getNote());
                int position = getSpinPosition(course.getCrseStat());
                spinnerCourseStatus.setSelection(position);
            }
        });
        Bundle xtras  = getIntent().getExtras();
        if(xtras == null) {
            setTitle("New course");
            bNCourse = true;
        } else if (xtras.containsKey(TERMIDKEY)){
            tId = xtras.getInt(TERMIDKEY);
            setTitle("New course");
        } else {
            setTitle("Edit Course");
            int cId = xtras.getInt(COURSEIDKEY);
            eViewModel.LCourseData(cId);
        }
    }
    private void addSpinItms() {
        crseStatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCourseStat.values());
        spinnerCourseStatus.setAdapter(crseStatAdapter);
    }
    private mCourseStat getSpinVal() {
        return (mCourseStat) spinnerCourseStatus.getSelectedItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
        if(!bNCourse) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edit_menu, menus);
        }
        return super.onCreateOptionsMenu(menus);
    }
    @OnClick(R.id.course_edit_start_date_button)
    public void crseStrtDatePick() {
        final Calendar mCal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            mCal.set(Calendar.YEAR, year);
            mCal.set(Calendar.MONTH, monthOfYear);
            mCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            editTextCourseStartDate.setText(TextFormatter.fulDateFrmat.format(mCal.getTime()));
        };
        new DatePickerDialog(this, date, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH)).show();
    }
    @OnClick(R.id.course_edit_end_date_button)
    public void crseEndDatePick() {
        final Calendar mCal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            mCal.set(Calendar.YEAR, year);
            mCal.set(Calendar.MONTH, monthOfYear);
            mCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            editTextCourseEndDate.setText(TextFormatter.fulDateFrmat.format(mCal.getTime()));
        };
        new DatePickerDialog(this, date, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH)).show();
    }
    private int getSpinPosition(mCourseStat crseStat) {
        return crseStatAdapter.getPosition(crseStat);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem itm) {
        if(itm.getItemId() == android.R.id.home) {
            savAndRetrn();
            return true;
        } else if(itm.getItemId() == R.id.edit_menu_delete_button) {
            eViewModel.delCourse();
            finish();
        }
        return super.onOptionsItemSelected(itm);
    }
    @Override
    public void onBackPressed() {
        savAndRetrn();
    }

    public void savAndRetrn() {
        try {
            Date strtDate = TextFormatter.fulDateFrmat.parse(editTextCourseStartDate.getText().toString());
            Date eDate = TextFormatter.fulDateFrmat.parse(editTextCourseEndDate.getText().toString());
            eViewModel.savCourse(editTextCourseTitle.getText().toString(), strtDate, eDate, getSpinVal(), tId, editTextEditCourseNotes.getText().toString());
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
}
