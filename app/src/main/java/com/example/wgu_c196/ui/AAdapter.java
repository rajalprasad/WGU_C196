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
import com.example.wgu_c196.model.mAssessment;
import com.example.wgu_c196.utilities.TextFormatter;
import com.example.wgu_c196.view.AssessmentDetails;
import com.example.wgu_c196.view.AssessmentEdit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.wgu_c196.utilities.Constants.ASSESSIDKEY;

public class AAdapter extends RecyclerView.Adapter<AAdapter.VHolder>  {
    private final RecContext rContxt;
    private ASelectedListener assessSelectListener;
    private final List<mAssessment> assess;
    private final Context contxt;
    public AAdapter(List<mAssessment> assess, Context contxt, RecContext rContxt, ASelectedListener aSelectedListener) {
        this.assess = assess;
        this.contxt = contxt;
        this.rContxt = rContxt;
        this.assessSelectListener = aSelectedListener;
    }
    public class VHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.assess_list_cv_title)
        TextView assessTitle;
        @BindView(R.id.assess_list_cv_fab)
        FloatingActionButton assessFab;
        @BindView(R.id.assess_list_cv_date)
        TextView assessDate;
        @BindView(R.id.assess_list_cv_details_image_button)
        ImageButton assessImageButton;
        ASelectedListener assessSelectListner;
        public VHolder(View itmView, ASelectedListener assessSelectListner) {
            super(itmView);
            ButterKnife.bind(this, itmView);
            this.assessSelectListner = assessSelectListner;
            itmView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            assessSelectListner.onAssessSelect(getAdapterPosition(), assess.get(getAdapterPosition()));
        }
    }
    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int vType) {
        LayoutInflater inflatr = LayoutInflater.from(parent.getContext());
        View view = inflatr.inflate(R.layout.assess_list_cv, parent, false);
        return new VHolder(view, assessSelectListener);
    }
    @Override
    public int getItemCount() {
        return assess.size();
    }

    public interface ASelectedListener {
        void onAssessSelect(int position, mAssessment assess);
    }
    @Override
    public void onBindViewHolder(@NonNull AAdapter.VHolder hlder, int position) {
        final mAssessment assess = this.assess.get(position);
        hlder.assessTitle.setText(assess.getTitle());
        hlder.assessDate.setText(TextFormatter.crdDateFrmat.format(assess.getDate()));

        switch(rContxt) {
            case MAIN:
                hlder.assessFab.setImageDrawable(ContextCompat.getDrawable(contxt, R.drawable.ic_edit_black_24dp));
                hlder.assessImageButton.setOnClickListener(v -> {
                    Intent intent = new Intent(contxt, AssessmentDetails.class);
                    intent.putExtra(ASSESSIDKEY, assess.getId());
                    contxt.startActivity(intent);
                });
                hlder.assessFab.setOnClickListener(v -> {
                    Intent intent = new Intent(contxt, AssessmentEdit.class);
                    intent.putExtra(ASSESSIDKEY, assess.getId());
                    contxt.startActivity(intent);
                });
                break;
            case CHILD:
                hlder.assessFab.setImageDrawable(ContextCompat.getDrawable(contxt, R.drawable.ic_delete_black_24dp));
                hlder.assessFab.setOnClickListener(v -> {
                    if(assessSelectListener != null) {
                        assessSelectListener.onAssessSelect(position, assess);
                    }
                });
                break;
        }
    }
}
