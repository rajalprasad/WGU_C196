package com.example.wgu_c196.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wgu_c196.model.mCourse;

import java.util.List;
@Dao
public interface CDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insrtCourse(mCourse course);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insrtAll(List<mCourse> courses);
    @Delete
    void delCourse(mCourse course);
    @Query("SELECT * FROM courses WHERE id = :id")
    mCourse gCourseById(int id);
    @Query("SELECT * FROM courses ORDER BY strtDate DESC")
    LiveData<List<mCourse>> gAll();
    @Query("SELECT * FROM courses WHERE trmID = :tId")
    LiveData<List<mCourse>> gCoursesByTrm(final int tId);
    @Query("DELETE FROM courses")
    int delAll();
    @Query("SELECT COUNT(*) FROM courses")
    int gCount();
}
