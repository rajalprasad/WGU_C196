package com.example.wgu_c196.db;
import androidx.room.TypeConverter;
import java.util.Date;
public class DateConvert {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
    @TypeConverter
    public static Long toTstamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

