package com.example.wgu_c196.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mMentor;
import java.util.List;

public class MentorPopAdapter extends RecyclerView.Adapter<MentorPopAdapter.MVHolder>{
    private List<mMentor> mentrs;
    private MentorSelectListner mentorSelectListner;
    @Override
    public void onBindViewHolder(@NonNull MentorPopAdapter.MVHolder hlder, final int position) {
        final mMentor mentor = mentrs.get(position);
        hlder.textViewMentorName.setText(mentor.getName());
        hlder.itemView.setOnClickListener(view -> {
            if(mentorSelectListner != null) {
                mentorSelectListner.onMentorSelect(position, mentor);
            }
        });
    }
    public void setMentorSelectListner(MentorSelectListner mentorSelectListner) {
        this.mentorSelectListner = mentorSelectListner;
    }
    public interface MentorSelectListner {
        void onMentorSelect(int position, mMentor mentor);
    }
    @NonNull
    @Override
    public MentorPopAdapter.MVHolder onCreateViewHolder(@NonNull ViewGroup parent, int vType) {
        return new MentorPopAdapter.MVHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mentor_itm, parent, false));
    }
    public MentorPopAdapter(List<mMentor> mentrs) {
        super();
        this.mentrs = mentrs;
    }
    @Override
    public int getItemCount() {
        return mentrs.size();
    }
    static class MVHolder extends RecyclerView.ViewHolder {
        TextView textViewMentorName;
        ImageView imageView;

        public MVHolder(View itmView) {
            super(itmView);
            textViewMentorName = itmView.findViewById(R.id.mentor_itm_name);
            imageView = itmView.findViewById(R.id.mentor_itm_image_view);
        }
    }
}
