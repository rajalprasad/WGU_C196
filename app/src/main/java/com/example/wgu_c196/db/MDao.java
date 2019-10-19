package com.example.wgu_c196.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wgu_c196.model.mMentor;

import java.util.List;
@Dao
public interface MDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insrtMentor(mMentor mentor);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insrtAll(List<mMentor> mentors);
    @Delete
    void delMentor(mMentor mentor);
    @Query("SELECT * FROM mentors WHERE id = :id")
    mMentor gMentorById(int id);
    @Query("SELECT * FROM mentors ORDER BY name DESC")
    LiveData<List<mMentor>> gAll();
    @Query("Select * FROM mentors WHERE crseId = :courseId")
    LiveData<List<mMentor>> gMentorsByCourse(final int courseId);
    @Query("DELETE FROM mentors")
    int delAll();
    @Query("SELECT COUNT(*) FROM mentors")
    int gCount();
}
