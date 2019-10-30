package com.example.wgu_c196.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mAssessment;
import com.example.wgu_c196.model.mMentor;
import com.example.wgu_c196.ui.AAdapter;
import com.example.wgu_c196.ui.AssessDropMenu;
import com.example.wgu_c196.ui.MAdapter;
import com.example.wgu_c196.ui.MentorDropMenu;
import com.example.wgu_c196.ui.RecContext;
import com.example.wgu_c196.utilities.TextFormatter;
import com.example.wgu_c196.view_model.EViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wgu_c196.utilities.Constants.COURSEIDKEY;

public class CourseDetails extends AppCompatActivity implements AAdapter.ASelectedListener, MAdapter.MSelectListner {
    @BindView(R.id.course_details_start_date)
    TextView textViewCourseStartDate;
    @BindView(R.id.course_details_end_date)
    TextView textViewCourseEndDate;
    @BindView(R.id.course_details_assessments_list)
    RecyclerView recyclerViewCourseDetailsAssessList;
    @BindView(R.id.course_details_mentors_list)
    RecyclerView recyclerViewCourseDetailsMentorList;
    @BindView(R.id.course_details_status)
    TextView textViewCourseDetailsStatus;
    @BindView(R.id.course_details_notes)
    TextView textViewCourseDetailsNotes;
    @BindView(R.id.course_details_fab_add_assess)
    FloatingActionButton fabCourseDetailsAddAssess;
    @BindView(R.id.course_details_fab_add_mentor)
    FloatingActionButton fabCourseDetailsAddMentor;

    private List<mAssessment> assessData = new ArrayList<>();
    private List<mMentor> mentrData = new ArrayList<>();
    private List<mAssessment> UAAssess = new ArrayList<>();
    private int cId;
    private AAdapter assessAdapter;
    private MAdapter mentrAdapter;
    private EViewModel eViewModel;
    private List<mMentor> UAMentors = new ArrayList<>();
    private Toolbar tbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);
        tbar = findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        initRecyclerView();
        initViewModel();
    }
    @OnClick(R.id.activity_course_details_edit_fab)
    public void openEdit() {
        Intent intent = new Intent(this, CourseEdit.class);
        intent.putExtra(COURSEIDKEY, cId);
        this.startActivity(intent);
        finish();
    }
    private void initRecyclerView() {
        recyclerViewCourseDetailsAssessList.setHasFixedSize(true);
        LinearLayoutManager LManager = new LinearLayoutManager(this);
        recyclerViewCourseDetailsAssessList.setLayoutManager(LManager);
        recyclerViewCourseDetailsMentorList.setHasFixedSize(true);
        LinearLayoutManager LManager_1 = new LinearLayoutManager(this);
        recyclerViewCourseDetailsMentorList.setLayoutManager(LManager_1);
    }
    private void initViewModel() {
        eViewModel = ViewModelProviders.of(this).get(EViewModel.class);

        eViewModel.MLDCourse.observe(this, crse -> {
            textViewCourseStartDate.setText(TextFormatter.fulDateFrmat.format(crse.getStrtDate()));
            textViewCourseEndDate.setText(TextFormatter.fulDateFrmat.format(crse.getEndDate()));
            textViewCourseDetailsStatus.setText(crse.getCrseStat().toString());
            textViewCourseDetailsNotes.setText(crse.getNote());
            tbar.setTitle(crse.getTitle());
        });
        final Observer<List<mAssessment>> assessObserver =
                assessEntities -> {
                    assessData.clear();
                    assessData.addAll(assessEntities);

                    if(assessAdapter == null) {
                        assessAdapter = new AAdapter(assessData, CourseDetails.this, RecContext.CHILD, this);
                        recyclerViewCourseDetailsAssessList.setAdapter(assessAdapter);
                    } else {
                        assessAdapter.notifyDataSetChanged();
                    }
                };
        final Observer<List<mMentor>> mentrObserver =
                mentrEntities -> {
                    mentrData.clear();
                    mentrData.addAll(mentrEntities);
                    if(mentrAdapter == null) {
                        mentrAdapter = new MAdapter(mentrData, CourseDetails.this, RecContext.CHILD, this);
                        recyclerViewCourseDetailsMentorList.setAdapter(mentrAdapter);
                    } else {
                        mentrAdapter.notifyDataSetChanged();
                    }
                };
        final Observer<List<mAssessment>> UAAssessObserver =
                assessEntities -> {
                    UAAssess.clear();
                    UAAssess.addAll(assessEntities);
                };
        final Observer<List<mMentor>> UAMentorObserver =
                mentrEntities -> {
                    UAMentors.clear();
                    UAMentors.addAll(mentrEntities);
                };
        Bundle xtras = getIntent().getExtras();
        if(xtras != null) {
            cId = xtras.getInt(COURSEIDKEY);
            eViewModel.LCourseData(cId);
        } else {
            finish();
        }

        eViewModel.gAssessInCourse(cId).observe(this, assessObserver);
        eViewModel.gMentorInCourse(cId).observe(this, mentrObserver);
        eViewModel.gUAAssess().observe(this, UAAssessObserver);
        eViewModel.gUAMentors().observe(this, UAMentorObserver);
    }

    @OnClick(R.id.course_details_share_fab)
    public void sharingNotes() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String sharingBody = textViewCourseDetailsNotes.getText().toString();
        String sharingSub = "Notes for course: " + getTitle();
        intent.putExtra(Intent.EXTRA_SUBJECT, sharingSub);
        intent.putExtra(Intent.EXTRA_TEXT, sharingBody);
        startActivity(Intent.createChooser(intent, "Share using following app:"));
    }
    @OnClick(R.id.course_details_fab_add_assess)
    public void assessAddButton() {
        AlertDialog.Builder blder = new AlertDialog.Builder(this);
        blder.setTitle("Add new or existing Assessment?");
        blder.setMessage("You like to add an existing assessment to course or create a new assessment?");
        blder.setIcon(R.drawable.ic_add_black_24dp);
        blder.setPositiveButton("New", (dialog, id) -> {
            dialog.dismiss();
            Intent intent = new Intent(this, AssessmentEdit.class);
            intent.putExtra(COURSEIDKEY, cId);
            this.startActivity(intent);
        });
        blder.setNegativeButton("Existing", (dialog, id) -> {
            if(UAAssess.size() >= 1) {
                final AssessDropMenu menus = new AssessDropMenu(this, UAAssess);
                menus.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                menus.setWidth(getPixFrmDp(200));
                menus.setOutsideTouchable(true);
                menus.setFocusable(true);
                menus.showAsDropDown(fabCourseDetailsAddAssess);
                menus.setAssessSelectListner((position, assess) -> {
                    menus.dismiss();
                    assess.setCrseId(cId);
                    eViewModel.ovrWrAssess(assess, cId);
                });
            } else {
                Toast.makeText(getApplicationContext(), "No unassigned assessments. Create a new assessment.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = blder.create();
        dialog.show();
    }
    @OnClick(R.id.course_details_fab_add_mentor)
    public void mentrAddButton() {
        AlertDialog.Builder blder = new AlertDialog.Builder(this);
        blder.setTitle("Add a new or existing Mentor?");
        blder.setMessage("You like to add a existing or new mentor to this course?");
        blder.setIcon(R.drawable.ic_add_black_24dp);
        blder.setPositiveButton("New", (dialog, id) -> {
            dialog.dismiss();
            Intent intent = new Intent(this, MentorEdit.class);
            intent.putExtra(COURSEIDKEY, cId);
            this.startActivity(intent);
        });
        blder.setNegativeButton("Existing", (dialog, id) -> {
            if(UAMentors.size() >= 1) {
                final MentorDropMenu menus = new MentorDropMenu(this, UAMentors);
                menus.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                menus.setWidth(getPixFrmDp(200));
                menus.setOutsideTouchable(true);
                menus.setFocusable(true);
                menus.showAsDropDown(fabCourseDetailsAddMentor);
                menus.setMentorSelectListner((position, mentor) -> {
                    menus.dismiss();
                    mentor.setCrseId(cId);
                    eViewModel.ovrWrMentor(mentor, cId);
                });
            } else {
                Toast.makeText(getApplicationContext(), "No unassigned mentors, create a new mentor.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = blder.create();
        dialog.show();
    }
    @Override
    public void onMentrSelect(int position, mMentor mntr) {
        AlertDialog.Builder blder = new AlertDialog.Builder(this);
        blder.setTitle("You sure you want to remove mentor?");
        blder.setMessage("This will only remove mentor from this course.");
        blder.setIcon(android.R.drawable.ic_dialog_alert);
        blder.setPositiveButton("Continue", (dialog, id) -> {
            dialog.dismiss();
            eViewModel.ovrWrMentor(mntr, -1);
            mentrAdapter.notifyDataSetChanged();
        });
        blder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = blder.create();
        dialog.show();
    }
    private int getPixFrmDp(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
    @Override
    public void onAssessSelect(int position, mAssessment assess) {
        AlertDialog.Builder blder = new AlertDialog.Builder(this);
        blder.setTitle("You sure you want to remove assessment?");
        blder.setMessage("This will only remove assessment from this course.");
        blder.setIcon(android.R.drawable.ic_dialog_alert);
        blder.setPositiveButton("Continue", (dialog, id) -> {
            dialog.dismiss();
            eViewModel.ovrWrAssess(assess, -1);
            assessAdapter.notifyDataSetChanged();
        });
        blder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = blder.create();
        dialog.show();
    }
}
