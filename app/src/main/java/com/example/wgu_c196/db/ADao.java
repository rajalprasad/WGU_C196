package com.example.wgu_c196.db;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wgu_c196.model.mAssessment;

import java.util.List;

@Dao
public interface ADao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insrtAssess(mAssessment assessment);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insrtAll(List<mAssessment> assessments);
    @Delete
    void delAssess(mAssessment assessment);
    @Query("SELECT * FROM assessments WHERE id = :id")
    mAssessment gAssessById(int id);
    @Query("SELECT * FROM assessments ORDER BY date DESC")
    LiveData<List<mAssessment>> gAll();
    @Query("SELECT * FROM assessments WHERE crseId = :courseId")
    LiveData<List<mAssessment>> gAssessByCourse(final int courseId);
    @Query("DELETE FROM assessments")
    int delAll();
    @Query("SELECT COUNT(*) FROM assessments")
    int gCount();
}
