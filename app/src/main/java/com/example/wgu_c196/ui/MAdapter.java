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
import com.example.wgu_c196.model.mMentor;
import com.example.wgu_c196.view.MentorDetails;
import com.example.wgu_c196.view.MentorEdit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.wgu_c196.utilities.Constants.MENTORIDKEY;

public class MAdapter extends RecyclerView.Adapter<MAdapter.VHolder> {
    private final RecContext rContxt;
    private MSelectListner mSelectListner;
    private final List<mMentor> mentrs;
    private final Context contxt;
    public MAdapter(List<mMentor> mntrs, Context contxt, RecContext recContxt, MSelectListner mSelectListner) {
        this.mentrs = mntrs;
        this.rContxt = recContxt;
        this.mSelectListner = mSelectListner;
        this.contxt = contxt;
    }
    public class VHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.mentor_list_cv_name)
        TextView mentrName;
        @BindView(R.id.mentor_list_cv_fab)
        FloatingActionButton mentrFab;
        @BindView(R.id.mentor_list_cv_email_address)
        TextView mentrEmailAddress;
        @BindView(R.id.mentor_list_cv_details_image_button)
        ImageButton mentrImageButton;
        MSelectListner mSelectListner;
        public VHolder(View itmView, MSelectListner mSelectListner) {
            super(itmView);
            ButterKnife.bind(this, itmView);
            this.mSelectListner = mSelectListner;
            itmView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            mSelectListner.onMentrSelect(getAdapterPosition(), mentrs.get(getAdapterPosition()));
        }
    }
    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int vType) {
        LayoutInflater inflatr = LayoutInflater.from(parent.getContext());
        View view = inflatr.inflate(R.layout.mentor_list_cv, parent, false);
        return new VHolder(view, mSelectListner);
    }
    @Override
    public void onBindViewHolder(@NonNull MAdapter.VHolder hlder, int position) {
        final mMentor mntr = mentrs.get(position);
        hlder.mentrName.setText(mntr.getName());
        hlder.mentrEmailAddress.setText(mntr.getEmailaddress());
        switch(rContxt) {
            case MAIN:
                hlder.mentrFab.setImageDrawable(ContextCompat.getDrawable(contxt, R.drawable.ic_edit_black_24dp));
                hlder.mentrImageButton.setOnClickListener(v -> {
                    Intent intent = new Intent(contxt, MentorDetails.class);
                    intent.putExtra(MENTORIDKEY, mntr.getId());
                    contxt.startActivity(intent);
                });
                hlder.mentrFab.setOnClickListener(v -> {
                    Intent intent = new Intent(contxt, MentorEdit.class);
                    intent.putExtra(MENTORIDKEY, mntr.getId());
                    contxt.startActivity(intent);
                });
                break;
            case CHILD:
                hlder.mentrFab.setImageDrawable(ContextCompat.getDrawable(contxt, R.drawable.ic_delete_black_24dp));
                hlder.mentrFab.setOnClickListener(v -> {
                    if(mSelectListner != null) {
                        mSelectListner.onMentrSelect(position, mntr);
                    }
                });
                break;
        }
    }
    @Override
    public int getItemCount() {
        return mentrs.size();
    }

    public interface MSelectListner {
        void onMentrSelect(int position, mMentor mentr);
    }
}
