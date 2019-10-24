package com.example.wgu_c196.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mAssessment;

import java.util.List;

public class AssessDropMenu extends PopupWindow {
    private Context context;
    private List<mAssessment> assess;
    private RecyclerView recyclerViewPop;
    private AssessPopAdapter assessPopAdapter;
    private void setView() {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_menu, null);
        recyclerViewPop = view.findViewById(R.id.pop_menu_recycler_view);
        recyclerViewPop.setHasFixedSize(true);
        recyclerViewPop.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerViewPop.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        assessPopAdapter = new AssessPopAdapter(assess);
        recyclerViewPop.setAdapter(assessPopAdapter);
        setContentView(view);
    }
    public void setAssessSelectListner(AssessPopAdapter.AssessSelectListener assessSelectListner) {
        assessPopAdapter.setAssessSelectListener(assessSelectListner);
    }
    public AssessDropMenu(Context contxt, List<mAssessment> assess) {
        super(contxt);
        this.context = contxt;
        this.assess = assess;
        setView();
    }
}