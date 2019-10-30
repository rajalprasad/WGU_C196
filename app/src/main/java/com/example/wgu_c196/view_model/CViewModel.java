package com.example.wgu_c196.view_model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.wgu_c196.db.AppRepo;
import com.example.wgu_c196.model.mCourse;
import java.util.List;

public class CViewModel extends AndroidViewModel {

    public LiveData<List<mCourse>> crse;
    private AppRepo appRepo;

    public CViewModel(@NonNull Application app) {
        super(app);
        appRepo = AppRepo.gInstance(app.getApplicationContext());
        crse = appRepo.Courses;
    }
}