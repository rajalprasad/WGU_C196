package com.example.wgu_c196.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wgu_c196.db.AppRepo;
import com.example.wgu_c196.model.mAssessment;
import com.example.wgu_c196.model.mCourse;
import com.example.wgu_c196.model.mMentor;
import com.example.wgu_c196.model.mTerm;

import java.util.List;

public class MViewModel extends AndroidViewModel {
    public LiveData<List<mTerm>> terms;
    public LiveData<List<mCourse>> courses;
    public LiveData<List<mAssessment>> assess;
    public LiveData<List<mMentor>> mentors;
    private AppRepo repo;

    public MViewModel(@NonNull Application app) {
        super(app);

        repo = AppRepo.gInstance(app.getApplicationContext());
        terms = repo.gAllTerms();
        courses = repo.gAllCourses();
        assess = repo.gAllAssessments();
        mentors = repo.gAllMentors();

    }

    public LiveData<List<mTerm>> gAllTerms() {
        return repo.gAllTerms();
    }

//    public void addSampleData() {
//        repo.addSampleData();
//    }
//
//    public void deleteAllData() {
//        repo.deleteAllData();
//    }
}
