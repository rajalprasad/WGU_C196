package com.example.wgu_c196.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.wgu_c196.model.mAssessment;
import com.example.wgu_c196.model.mCourse;
import com.example.wgu_c196.model.mMentor;
import com.example.wgu_c196.model.mTerm;

@Database(entities = {mTerm.class, mCourse.class, mAssessment.class, mMentor.class}, version = 8, exportSchema = false)
@TypeConverters({DateConvert.class, CourseStatConvert.class, AssessTypeConvert.class})
public abstract class AppDb extends RoomDatabase {
    private  static final String DBNAME = "AppDb.db";
    private static volatile AppDb instance;
    private static final Object LOCK = new Object();
    public abstract TDao tDao();
    public abstract CDao cDao();
    public abstract ADao aDao();
    public abstract MDao mDao();

    public static AppDb gInstance(Context contxt) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(contxt.getApplicationContext(),
                            AppDb.class, DBNAME).fallbackToDestructiveMigration().build();
                }
            }
        }

        return instance;
    }
}
