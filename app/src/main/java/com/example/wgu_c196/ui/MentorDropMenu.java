package com.example.wgu_c196.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wgu_c196.R;
import com.example.wgu_c196.model.mMentor;

import java.util.List;

public class MentorDropMenu extends PopupWindow {
    private Context contxt;
    private List<mMentor> mentors;
    private RecyclerView recyclerViewPop;
    private MentorPopAdapter mentorPopAdapter;
    public MentorDropMenu(Context context, List<mMentor> mentrs) {
        super(context);
        this.contxt = context;
        this.mentors = mentrs;
        setupView();
    }
    private void setupView() {
        View view = LayoutInflater.from(contxt).inflate(R.layout.pop_menu, null);
        recyclerViewPop = view.findViewById(R.id.pop_menu_recycler_view);
        recyclerViewPop.setHasFixedSize(true);
        recyclerViewPop.setLayoutManager(new LinearLayoutManager(contxt, LinearLayoutManager.VERTICAL, false));
        recyclerViewPop.addItemDecoration(new DividerItemDecoration(contxt, LinearLayoutManager.VERTICAL));
        mentorPopAdapter = new MentorPopAdapter(mentors);
        recyclerViewPop.setAdapter(mentorPopAdapter);
        setContentView(view);
    }
    public void setMentorSelectListner(MentorPopAdapter.MentorSelectListner mentrSelectListner) {
        mentorPopAdapter.setMentorSelectListner(mentrSelectListner);
    }
}
