package com.example.wgu_c196.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mCourse;
import java.util.List;

public class CourseDropMenu extends PopupWindow {
    private Context contxt;
    private List<mCourse> lcrses;
    private RecyclerView recyclerViewPop;
    private CoursePopAdapter cAdapter;
    private void setupView() {
        View view = LayoutInflater.from(contxt).inflate(R.layout.pop_menu, null);
        recyclerViewPop = view.findViewById(R.id.pop_menu_recycler_view);
        recyclerViewPop.setHasFixedSize(true);
        recyclerViewPop.setLayoutManager(new LinearLayoutManager(contxt, LinearLayoutManager.VERTICAL, false));
        recyclerViewPop.addItemDecoration(new DividerItemDecoration(contxt, LinearLayoutManager.VERTICAL));
        cAdapter = new CoursePopAdapter(lcrses);
        recyclerViewPop.setAdapter(cAdapter);
        setContentView(view);
    }
    public CourseDropMenu(Context contxt, List<mCourse> crses) {
        super(contxt);
        this.contxt = contxt;
        this.lcrses = crses;
        setupView();
    }
    public void setCrseSelectListner(CoursePopAdapter.CSelectListner crseSelectListner) {
        cAdapter.setCSelectListner(crseSelectListner);
    }
}
