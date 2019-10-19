package com.example.wgu_c196.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wgu_c196.db.AppRepo;
import com.example.wgu_c196.model.mTerm;

import java.util.List;

public class TViewModel extends AndroidViewModel {
    public LiveData<List<mTerm>> Terms;
    private AppRepo Repo;

    public TViewModel(@NonNull Application app) {
        super(app);

        Repo = AppRepo.gInstance(app.getApplicationContext());
        Terms = Repo.Terms;
    }

}
