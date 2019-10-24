package com.example.wgu_c196.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mCourse;
import java.util.List;

public class CoursePopAdapter extends RecyclerView.Adapter<CoursePopAdapter.CVHolder> {
    private List<mCourse> crses;
    private CSelectListner CSelectListner;
    public CoursePopAdapter(List<mCourse> lCrses){
        super();
        this.crses = lCrses;
    }
    static class CVHolder extends RecyclerView.ViewHolder{
        TextView textViewCourseTitle;
        ImageView imageView;

        public CVHolder(View itmView) {
            super(itmView);
            textViewCourseTitle = itmView.findViewById(R.id.course_itm_course_title);
            imageView = itmView.findViewById(R.id.course_itm_image_view_icon);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull CVHolder hldr, final int position) {
        final mCourse crse = crses.get(position);
        hldr.textViewCourseTitle.setText(crse.getTitle());
        hldr.itemView.setOnClickListener(view -> {
            if(CSelectListner != null){
                CSelectListner.onCrseSelect(position, crse);
            }
        });
    }
    public void setCSelectListner(CoursePopAdapter.CSelectListner cSelectListner) {
        this.CSelectListner = cSelectListner;
    }
    @NonNull
    @Override
    public CVHolder onCreateViewHolder(@NonNull ViewGroup parent, int vType) {
        return new CVHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_itm, parent, false));
    }
    public interface CSelectListner {
        void onCrseSelect(int position, mCourse crse);
    }
    @Override
    public int getItemCount() {
        return crses.size();
    }
}
