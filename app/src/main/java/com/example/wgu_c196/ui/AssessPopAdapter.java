package com.example.wgu_c196.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mAssessment;
import java.util.List;

public class AssessPopAdapter extends RecyclerView.Adapter<AssessPopAdapter.AssessVHolder> {
    private List<mAssessment> assess;
    private AssessSelectListener assessSelectListener;
    public AssessPopAdapter(List<mAssessment> assess) {
        super();
        this.assess = assess;
    }
    static class AssessVHolder extends RecyclerView.ViewHolder {
        TextView textViewAssessTitle;
        ImageView imageView;

        public AssessVHolder(View itemView) {
            super(itemView);
            textViewAssessTitle = itemView.findViewById(R.id.assess_itm_title);
            imageView = itemView.findViewById(R.id.assess_itm_image_view);
        }
    }
    public void setAssessSelectListener(AssessPopAdapter.AssessSelectListener aSelectListner) {
        this.assessSelectListener = aSelectListner;
    }
    public interface AssessSelectListener {
        void onAssessSelect(int position, mAssessment assess);
    }
    @NonNull
    @Override
    public AssessVHolder onCreateViewHolder(@NonNull ViewGroup parent, int vType) {
        return new AssessVHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.assess_itm, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull AssessVHolder hlder, final int position) {
        final mAssessment assess = this.assess.get(position);
        hlder.textViewAssessTitle.setText(assess.getTitle());
        hlder.itemView.setOnClickListener(view -> {
            if(assessSelectListener != null) {
                assessSelectListener.onAssessSelect(position, assess);
            }
        });
    }
    @Override
    public int getItemCount() {
        return assess.size();
    }
}

