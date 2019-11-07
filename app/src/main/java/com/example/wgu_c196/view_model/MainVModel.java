package com.example.wgu_c196.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wgu_c196.db.AppRepo;
import com.example.wgu_c196.model.mAssessment;
import com.example.wgu_c196.model.mCourse;

import java.util.List;

public class MainVModel extends AndroidViewModel {
    public LiveData<List<mCourse>> crse;
    public LiveData<List<mAssessment>> assess;
    private AppRepo repo;

    public MainVModel(@NonNull Application app) {
        super(app);
        repo = AppRepo.gInstance(app.getApplicationContext());
        crse = repo.gAllCourses();
        assess = repo.gAllAssessments();
    }
}
