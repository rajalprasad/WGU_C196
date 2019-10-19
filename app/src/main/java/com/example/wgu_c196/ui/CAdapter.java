package com.example.wgu_c196.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CAdapter extends RecyclerView.Adapter<CAdapter.VHolder> {
    private final List<mCourse> mCourses;
    private final Context context;
    private final RecContext recContext;
    private CSelectedListener cSelectedListener;

    public CAdapter(List<mCourse> courses, Context context, RecContext recyclerContext, CSelectedListener cSelectedListener) {
        this.mCourses = courses;
        this.context = context;
        this.recContext = recyclerContext;
        this.cSelectedListener = cSelectedListener;
    }
    public interface CSelectedListener {
        void onCSelected(int position, mCourse crse);
    }
    public class VHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.course_list_cv_title)
        TextView textViewTitle;
        @BindView(R.id.course_list_cv_fab)
        FloatingActionButton courseFab;
        @BindView(R.id.course_list_cv_course_dates)
        TextView textViewDates;
        @BindView(R.id.course_list_cv_image_btn)
        ImageButton courseImageButton;
        CSelectedListener cSelectedListener;
        public VHolder(View itmView, CSelectedListener cSelectedListener) {
            super(itmView);
            ButterKnife.bind(this, itmView);
            this.cSelectedListener = cSelectedListener;

            itmView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            cSelectedListener.onCSelected(getAdapterPosition(), mCourses.get(getAdapterPosition()));
        }
    }
    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int vType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_list_cv, parent, false);
        return new VHolder(view, cSelectedListener);
    }
    //TODO
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        final Course course = mCourses.get(position);
        holder.tvTitle.setText(course.getTitle());
        String startAndEnd = TextFormatting.cardDateFormat.format(course.getStartDate()) + " to " + TextFormatting.cardDateFormat.format(course.getAnticipatedEndDate());
        holder.tvDates.setText(startAndEnd);

        switch(rContext) {
            case MAIN:
                Log.v("rContext", "rContext is " + rContext.name());
                holder.courseFab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_edit));
                holder.courseImageBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, CourseDetailsActivity.class);
                    intent.putExtra(COURSE_ID_KEY, course.getId());
                    mContext.startActivity(intent);
                });

                holder.courseFab.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, CourseEditActivity.class);
                    intent.putExtra(COURSE_ID_KEY, course.getId());
                    mContext.startActivity(intent);
                });
                break;
            case CHILD:
                holder.courseFab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_delete));
                holder.courseFab.setOnClickListener(v -> {
                    if(courseSelectedListener != null){
                        courseSelectedListener.onCourseSelected(position, course);
                    }
                });
                break;
        }
    }
}
