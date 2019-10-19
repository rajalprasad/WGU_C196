package com.example.wgu_c196.ui;

import android.content.Context;
import android.content.Intent;
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
import com.example.wgu_c196.utilities.TextFormatter;
import com.example.wgu_c196.view.CourseDetails;
import com.example.wgu_c196.view.CourseEdit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.wgu_c196.utilities.Constants.COURSEIDKEY;

public class CAdapter extends RecyclerView.Adapter<CAdapter.VHolder> {
    private final List<mCourse> crses;
    private final Context context;
    private final RecContext recContext;
    private CSelectedListener cSelectedListener;

    public CAdapter(List<mCourse> courses, Context context, RecContext recyclerContext, CSelectedListener cSelectedListener) {
        this.crses = courses;
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
        private VHolder(View itmView, CSelectedListener cSelectedListener) {
            super(itmView);
            ButterKnife.bind(this, itmView);
            this.cSelectedListener = cSelectedListener;

            itmView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            cSelectedListener.onCSelected(getAdapterPosition(), crses.get(getAdapterPosition()));
        }
    }
    @Override
    public int getItemCount() {
        return crses.size();
    }
    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int vType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_list_cv, parent, false);
        return new VHolder(view, cSelectedListener);
    }
    @Override
    public void onBindViewHolder(@NonNull CAdapter.VHolder holdr, int posit) {
        final mCourse crse = crses.get(posit);
        holdr.textViewTitle.setText(crse.getTitle());
        String sAndEnd = TextFormatter.crdDateFrmat.format(crse.getStrtDate()) + " to " + TextFormatter.crdDateFrmat.format(crse.getEndDate());
        holdr.textViewDates.setText(sAndEnd);
        switch(recContext) {
            case MAIN:
                holdr.courseFab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_edit_black_24dp));
                holdr.courseImageButton.setOnClickListener(v -> {
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra(COURSEIDKEY, crse.getId());
                    context.startActivity(intent);
                });
                holdr.courseFab.setOnClickListener(v -> {
                    Intent intent = new Intent(context, CourseEdit.class);
                    intent.putExtra(COURSEIDKEY, crse.getId());
                    context.startActivity(intent);
                });
                break;
            case CHILD:
                holdr.courseFab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_delete_black_24dp));
                holdr.courseFab.setOnClickListener(v -> {
                    if(cSelectedListener != null){
                        cSelectedListener.onCSelected(posit, crse);
                    }
                });
                break;
        }
    }
}
