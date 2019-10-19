package com.example.wgu_c196.db;
import android.text.TextUtils;
import androidx.room.TypeConverter;
import com.example.wgu_c196.model.mAssessType;
public class AssessTypeConvert {
    @TypeConverter
    public static String frmAssessTypeToString(mAssessType assessType) {
        if(assessType == null) {
            return null;
        }
        return assessType.name();
    }
    @TypeConverter
    public static mAssessType frmStringToAssessType(String assessType) {
        if(TextUtils.isEmpty(assessType)) {
            return mAssessType.OBJECTIVEASSESSMENT;
        }
        return mAssessType.valueOf(assessType);
    }
}
