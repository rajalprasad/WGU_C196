package com.example.wgu_c196.view_model;

import android.app.Application;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.wgu_c196.db.AppRepo;
import com.example.wgu_c196.model.mAssessType;
import com.example.wgu_c196.model.mAssessment;
import com.example.wgu_c196.model.mCourse;
import com.example.wgu_c196.model.mCourseStat;
import com.example.wgu_c196.model.mMentor;
import com.example.wgu_c196.model.mTerm;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EViewModel extends AndroidViewModel {
    public LiveData<List<mTerm>> LDTerm;
    public LiveData<List<mCourse>> LDCourse;
    public LiveData<List<mAssessment>> LDAssess;
    public LiveData<List<mMentor>> LDMentor;
    public MutableLiveData<mTerm> MLDTerm = new MutableLiveData<>();
    public MutableLiveData<mCourse> MLDCourse = new MutableLiveData<>();
    public MutableLiveData<mAssessment> MLDAssess = new MutableLiveData<>();
    public MutableLiveData<mMentor> MLDMentor = new MutableLiveData<>();
    private AppRepo Repo;
    private Executor executor = Executors.newSingleThreadExecutor();
    public EViewModel(@NonNull Application app) {
        super(app);
        Repo = AppRepo.gInstance(getApplication());
        LDTerm = Repo.Terms;
        LDCourse = Repo.Courses;
        LDAssess = Repo.Assessments;
        LDMentor = Repo.Mentors;
    }
    public void LTermData(final int tId) {
        executor.execute(() -> {
            mTerm Term = Repo.gTermById(tId);
            MLDTerm.postValue(Term);
        });
    }
    public void LCourseData(final int cId) {
        executor.execute(() -> {
            mCourse course = Repo.gCourseById(cId);
            MLDCourse.postValue(course);
        });
    }
    public void LAssessData(final int assessId) {
        executor.execute(() -> {
            mAssessment assess = Repo.gAssessById(assessId);
            MLDAssess.postValue(assess);
        });
    }
    public void LMentorData(final int mId) {
        executor.execute(() -> {
            mMentor mentor = Repo.gMentorById(mId);
            MLDMentor.postValue(mentor);
        });
    }
    public void savTerm(String tTitle, Date sDate, Date eDate) {
        mTerm term = MLDTerm.getValue();

        if (term == null) {
            if (TextUtils.isEmpty(tTitle.trim())) {
                return;
            }
            term = new mTerm(tTitle.trim(), sDate, eDate);
        } else {
            term.setTitle(tTitle.trim());
            term.setStrtDate(sDate);
            term.setEndDate(eDate);
        }
        Repo.insrtTerm(term);
    }
    public void savCourse(String cTitle, Date sDate, Date eDate, mCourseStat courseStat, int tId, String note) {
        mCourse course = MLDCourse.getValue();

        if(course == null) {
            if (TextUtils.isEmpty(cTitle.trim())) {
                return;
            }
            course = new mCourse(cTitle.trim(), sDate, eDate, courseStat, tId);
        } else {
            course.setTitle(cTitle.trim());
            course.setStrtDate(sDate);
            course.setEndDate(eDate);
            course.setCrseStat(courseStat);
            course.setTrmID(tId);
            course.setNote(note);
        }
        Repo.insrtCourse(course);
    }
    public void ovrWrCourse(mCourse course, int tId) {
        course.setTrmID(tId);
        Repo.insrtCourse(course);
    }
    public void ovrWrAssess(mAssessment assess, int cId) {
        assess.setCrseId(cId);
        Repo.insrtAssess(assess);
    }
    public void ovrWrMentor(mMentor mentor, int cId) {
        mentor.setCrseId(cId);
        Repo.insrtMentor(mentor);
    }
    public void savAssess(String assessTitle, Date date, mAssessType assessType, int cId) {
        mAssessment assess = MLDAssess.getValue();

        if(assess == null) {
            if(TextUtils.isEmpty(assessTitle.trim())) {
                return;
            }
            assess = new mAssessment(assessTitle.trim(), date, assessType, cId);
        } else {
            assess.setTitle(assessTitle.trim());
            assess.setDate(date);
            assess.setAssesstype(assessType);
            assess.setCrseId(cId);
        }
        Repo.insrtAssess(assess);
    }
    public void savMentor(String name, String emailAddress, String phoneNumber, int cId) {
        mMentor mentor = MLDMentor.getValue();

        if(mentor == null) {
            if(TextUtils.isEmpty(name.trim())) {
                return;
            }
            mentor = new mMentor(name, emailAddress, phoneNumber, cId);
        } else {
            mentor.setName(name);
            mentor.setEmailaddress(emailAddress);
            mentor.setPhonenumber(phoneNumber);
            mentor.setCrseId(cId);
        }
        Repo.insrtMentor(mentor);
    }
    public LiveData<List<mCourse>> gCrsesInTerm(int tId) {
        return (Repo.gCoursesByTrm(tId));
    }
    public LiveData<List<mAssessment>> gAssessInCourse(int cId) {
        return (Repo.gAssessByCourse(cId));
    }
    public LiveData<List<mMentor>> gMentorInCourse(int cId) {
        return (Repo.gMentorsByCourse(cId));
    }
    public LiveData<List<mCourse>> gUACourses() {
        return (Repo.gCoursesByTrm(-1));
    }
    public LiveData<List<mAssessment>> gUAAssess() {
        return (Repo.gAssessByCourse(-1));
    }
    public LiveData<List<mMentor>> gUAMentors() {
        return (Repo.gMentorsByCourse(-1));
    }
    public mTerm gTermById(int tId) {
        return Repo.gTermById(tId);
    }
    public void delTerm() {
        Repo.delTerm(MLDTerm.getValue());
    }
    public void delCourse() {
        Repo.delCourse(MLDCourse.getValue());
    }
    public void delAssess() {
        Repo.delAssess(MLDAssess.getValue());
    }
}
