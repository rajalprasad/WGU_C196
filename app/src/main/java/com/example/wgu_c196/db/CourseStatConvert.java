package com.example.wgu_c196.db;
import android.text.TextUtils;
import androidx.room.TypeConverter;
import com.example.wgu_c196.model.mCourseStat;
public class CourseStatConvert {
    @TypeConverter
    public static String frmCourseStatToString(mCourseStat courseStat) {
        if(courseStat == null) {
            return null;
        }
        return courseStat.name();
    }
    @TypeConverter
    public static mCourseStat frmStringToCourseStat(String courseStat) {
        if(TextUtils.isEmpty(courseStat)) {
            return mCourseStat.INPROGRESS;
        }
        return mCourseStat.valueOf(courseStat);
    }
}
