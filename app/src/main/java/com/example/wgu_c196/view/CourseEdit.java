package com.example.wgu_c196.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            bEdit = savedInstanceState.getBoolean(EDITINGKEY);
        }
        initViewModel();
        addSpinItms();
    }
    private void addSpinItms() {
        crseStatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCourseStat.values());
        spinnerCourseStatus.setAdapter(crseStatAdapter);
    }

    private mCourseStat getSpinVal() {
        return (mCourseStat) spinnerCourseStatus.getSelectedItem();
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
                editTextEditCourseNotes.setSelection(position);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
        if(!bNCourse) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.course_edit_menu, menus);
        }
        return super.onCreateOptionsMenu(menus);
    }

    private int getSpinPosition(mCourseStat crseStat) {
        return crseStatAdapter.getPosition(crseStat);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem itm) {
        if(itm.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if(itm.getItemId() == R.id.course_edit_menu_delete) {
            mViewModel.deleteCourse();
            finish();
        }
        return super.onOptionsItemSelected(itm);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    public void saveAndReturn() {
        try {
            Date startDate = TextFormatting.fullDateFormat.parse(tvCourseStartDate.getText().toString());
            Date endDate = TextFormatting.fullDateFormat.parse(tvCourseEndDate.getText().toString());
            mViewModel.saveCourse(tvCourseTitle.getText().toString(), startDate, endDate, getSpinVal(), termId, tvNote.getText().toString());
            Log.v("Saved Course", tvCourseTitle.toString());
        } catch (ParseException e) {
            Log.v("Exception", e.getLocalizedMessage());
        }
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.course_edit_start_btn)
    public void courseStartDatePicker() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            tvCourseStartDate.setText(TextFormatting.fullDateFormat.format(myCalendar.getTime()));
        };
        new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.course_edit_end_btn)
    public void courseEndDatePicker() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            tvCourseEndDate.setText(TextFormatting.fullDateFormat.format(myCalendar.getTime()));
        };
        new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
