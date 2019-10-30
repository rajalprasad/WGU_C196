package com.example.wgu_c196.view;

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
import com.example.wgu_c196.model.mCourse;
import com.example.wgu_c196.ui.CAdapter;
import com.example.wgu_c196.ui.CourseDropMenu;
import com.example.wgu_c196.ui.RecContext;
import com.example.wgu_c196.utilities.TextFormatter;
import com.example.wgu_c196.view_model.EViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wgu_c196.utilities.Constants.TERMIDKEY;

public class TermDetails extends AppCompatActivity implements CAdapter.CSelectedListener{

    @BindView(R.id.term_details_start_date)
    TextView textViewTermStartDate;
    @BindView(R.id.term_details_end_date)
    TextView textViewTermsEndDate;
    @BindView(R.id.term_details_course_recycler_view)
    RecyclerView recyclerViewTermDetails;
    @BindView(R.id.term_details_fab_courses)
    FloatingActionButton fabTermDetailsAddCourse;

    private List<mCourse> cData = new ArrayList<>();
    private List<mCourse> UACourses = new ArrayList<>();
    private int tId;
    private CAdapter crseAdapter;
    private EViewModel eViewModel;
    private Toolbar tbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        ButterKnife.bind(this);
        initRecyclerView();
        tbar = findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        initViewModel();
    }
    private void initViewModel() {
        eViewModel = ViewModelProviders.of(this).get(EViewModel.class);

        eViewModel.MLDTerm.observe(this, trm -> {
            textViewTermStartDate.setText(TextFormatter.fulDateFrmat.format(trm.getStrtDate()));
            textViewTermsEndDate.setText(TextFormatter.fulDateFrmat.format(trm.getEndDate()));
            tbar.setTitle(trm.getTitle());
        });
        final Observer<List<mCourse>> crseObserver =
                crseEntities -> {
                    cData.clear();
                    cData.addAll(crseEntities);

                    if(crseAdapter == null) {
                        crseAdapter = new CAdapter(cData, TermDetails.this, RecContext.CHILD, this);
                        recyclerViewTermDetails.setAdapter(crseAdapter);
                    } else {
                        crseAdapter.notifyDataSetChanged();
                    }
                };
        final Observer<List<mCourse>> UACourseObserver =
                crseEntities -> {
                    UACourses.clear();
                    UACourses.addAll(crseEntities);
                };
        Bundle xtras = getIntent().getExtras();
        if(xtras != null) {
            tId = xtras.getInt(TERMIDKEY);
            eViewModel.LTermData(tId);
        } else {
            finish();
        }

        eViewModel.gCrsesInTerm(tId).observe(this, crseObserver);
        eViewModel.gUACourses().observe(this, UACourseObserver);
    }
    @OnClick(R.id.term_details_fab)
    public void openEdit() {
        Intent intent = new Intent(this, TermEdit.class);
        intent.putExtra(TERMIDKEY, tId);
        this.startActivity(intent);
        finish();
    }
    private void initRecyclerView() {
        recyclerViewTermDetails.setHasFixedSize(true);
        LinearLayoutManager LManager = new LinearLayoutManager(this);
        recyclerViewTermDetails.setLayoutManager(LManager);
    }
    public void onCSelected(int position, mCourse crse) {
        AlertDialog.Builder bldr = new AlertDialog.Builder(this);
        bldr.setTitle("You sure you want to remove this course?");
        bldr.setMessage("This will only remove the course from this term.");
        bldr.setIcon(android.R.drawable.ic_dialog_alert);
        bldr.setPositiveButton("Continue", (dialog, id) -> {
            dialog.dismiss();
            eViewModel.ovrWrCourse(crse, -1);
            crseAdapter.notifyDataSetChanged();
        });
        bldr.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = bldr.create();
        dialog.show();
    }
    @OnClick(R.id.term_details_fab_courses)
    public void crseAddButton() {
        AlertDialog.Builder bldr = new AlertDialog.Builder(this);
        bldr.setTitle("Add new course?");
        bldr.setMessage("You like to add a course to this term?");
        bldr.setIcon(R.drawable.ic_add_black_24dp);
        bldr.setPositiveButton("New", (dialog, id) -> {
            dialog.dismiss();
            Intent intent = new Intent(this, CourseEdit.class);
            intent.putExtra(TERMIDKEY, tId);
            this.startActivity(intent);
        });
        bldr.setNegativeButton("Existing", (dialog, id) -> {
            if(UACourses.size() >= 1) {
                final CourseDropMenu menus = new CourseDropMenu(this, UACourses);
                menus.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                menus.setWidth(getPixFrmDp(200));
                menus.setOutsideTouchable(true);
                menus.setFocusable(true);
                menus.showAsDropDown(fabTermDetailsAddCourse);
                menus.setCrseSelectListner((position, crse) -> {
                    menus.dismiss();
                    crse.setTrmID(tId);
                    eViewModel.ovrWrCourse(crse, tId);
                });
            } else {
                Toast.makeText(getApplicationContext(), "No unassigned courses, create a new course.", Toast.LENGTH_SHORT).show();
            }

        });
        AlertDialog dialog = bldr.create();
        dialog.show();
    }
    private int getPixFrmDp(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}
