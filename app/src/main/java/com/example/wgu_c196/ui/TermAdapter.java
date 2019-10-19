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
import com.example.wgu_c196.model.mTerm;
import com.example.wgu_c196.utilities.TextFormatter;
import com.example.wgu_c196.view.TermDetails;
import com.example.wgu_c196.view.TermEdit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wgu_c196.utilities.Constants.TERMIDKEY;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.VHolder> {

    private final List<mTerm> mTerm;
    private final Context cContext;
    private final RecContext rContext;

    public TermAdapter(List<mTerm> mTerm, Context cContext, RecContext rContext) {
        this.mTerm = mTerm;
        this.cContext = cContext;
        this.rContext = rContext;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int vType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.term_list_cv, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.VHolder holder, int position) {
        final mTerm term = mTerm.get(position);
        holder.textViewTitle.setText(term.getTitle());
        String strtAndEnd = TextFormatter.crdDateFrmat.format(term.getStrtDate()) + " TO " + TextFormatter.crdDateFrmat.format(term.getEndDate());
        holder.textViewDates.setText(strtAndEnd);
        switch (rContext) {
            case MAIN:
                holder.tFab.setImageDrawable(ContextCompat.getDrawable(cContext, R.drawable.ic_edit_black_24dp));
                holder.tImageButton.setOnClickListener( v -> {
                    Intent intent = new Intent(cContext, TermDetails.class);
                    intent.putExtra(TERMIDKEY, term.getId());
                    cContext.startActivity(intent);
                });
                holder.tFab.setOnClickListener((v -> {
                    Intent intent = new Intent(cContext, TermEdit.class);
                    intent.putExtra(TERMIDKEY, term.getId());
                    cContext.startActivity(intent);
                }));
                break;
            case CHILD:
                holder.tFab.setImageDrawable(ContextCompat.getDrawable(cContext, R.drawable.ic_delete_black_24dp));
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mTerm.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.term_list_cv_title)
        TextView textViewTitle;
        @BindView(R.id.term_list_cv_fab)
        FloatingActionButton tFab;
        @BindView(R.id.term_list_cv_date)
        TextView textViewDates;
        @BindView(R.id.term_list_cv_image_button)
        ImageButton tImageButton;

        public VHolder(View itmView) {
            super(itmView);
            ButterKnife.bind(this, itmView);
        }
    }
}
