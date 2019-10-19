package com.example.wgu_c196.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wgu_c196.model.mTerm;

import java.util.List;

@Dao
public interface TDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insrtTerm(mTerm term);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insrtAll(List<mTerm> terms);
    @Delete
    void delTerm(mTerm term);
    @Query("SELECT * FROM terms WHERE id = :id")
    mTerm gTermById(int id);
    @Query("SELECT * FROM terms ORDER BY strtDate DESC")
    LiveData<List<mTerm>> gAll();
    @Query("DELETE FROM terms")
    int delAll();
    @Query("SELECT COUNT(*) FROM terms")
    int gCount();
}
