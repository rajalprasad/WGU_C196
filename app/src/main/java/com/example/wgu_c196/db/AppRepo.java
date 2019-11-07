package com.example.wgu_c196.db;
import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.wgu_c196.model.mAssessment;
import com.example.wgu_c196.model.mCourse;
import com.example.wgu_c196.model.mMentor;
import com.example.wgu_c196.model.mTerm;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepo {
    private static AppRepo oInstance;
    public LiveData<List<mTerm>> Terms;
    public LiveData<List<mCourse>> Courses;
    public LiveData<List<mAssessment>> Assessments;
    public LiveData<List<mMentor>> Mentors;
    private AppDb Db;
    private Executor exe = Executors.newSingleThreadExecutor();
    public static AppRepo gInstance(Context contxt) {
        if (oInstance == null) {
            oInstance = new AppRepo(contxt);
        }
        return  oInstance;
    }
    private AppRepo(Context context) {
        Db = AppDb.gInstance(context);
        Terms = gAllTerms();
        Courses = gAllCourses();
        Assessments = gAllAssessments();
        Mentors = gAllMentors();
    }
    public LiveData<List<mTerm>> gAllTerms() {
        return Db.tDao().gAll();
    }
    public LiveData<List<mCourse>> gAllCourses() {
        return Db.cDao().gAll();
    }
    public LiveData<List<mAssessment>> gAllAssessments() {
        return Db.aDao().gAll();
    }
    public LiveData<List<mMentor>> gAllMentors() {
        return Db.mDao().gAll();
    }

    public mCourse gCourseById(int cId) {
        return Db.cDao().gCourseById(cId);
    }
    public mTerm gTermById(int tId) {
        return Db.tDao().gTermById(tId);
    }
    public mAssessment gAssessById(int assessId) {
        return Db.aDao().gAssessById(assessId);
    }
    public mMentor gMentorById(int mId) {
        return Db.mDao().gMentorById(mId);
    }
    public void insrtTerm(final mTerm term) {
        exe.execute(() -> Db.tDao().insrtTerm(term));
    }
    public void insrtCourse(final mCourse course) {
        exe.execute(() -> Db.cDao().insrtCourse(course));
    }
    public void insrtAssess(final mAssessment assess) {
        exe.execute(() -> Db.aDao().insrtAssess(assess));
    }
    public void insrtMentor(final mMentor mentor) {
        exe.execute(() -> Db.mDao().insrtMentor(mentor));
    }
    public void delTerm(final mTerm term) {
        exe.execute(() -> Db.tDao().delTerm(term));
    }
    public void delCourse(final mCourse course) {
        exe.execute(() -> Db.cDao().delCourse(course));
    }
    public void delAssess(final mAssessment assess) {
        exe.execute(() -> Db.aDao().delAssess(assess));
    }
    public void delMentor(final mMentor mentor) {
        exe.execute(() -> Db.mDao().delMentor(mentor));
    }
    public LiveData<List<mCourse>> gCoursesByTrm(final int tId) {
        return Db.cDao().gCoursesByTrm(tId);
    }
    public LiveData<List<mAssessment>> gAssessByCourse(final int cId) {
        return Db.aDao().gAssessByCourse(cId);
    }
    public LiveData<List<mMentor>> gMentorsByCourse(final int cId) {
        return Db.mDao().gMentorsByCourse(cId);
    }
}
