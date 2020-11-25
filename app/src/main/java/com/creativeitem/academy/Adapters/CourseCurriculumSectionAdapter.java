package com.creativeitem.academy.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeitem.academy.Activities.LessonActivity;
import com.creativeitem.academy.JSONSchemas.LessonSchema;
import com.creativeitem.academy.Models.Lesson;
import com.creativeitem.academy.Models.Section;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Utils.Helpers;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CourseCurriculumSectionAdapter extends RecyclerView.Adapter<CourseCurriculumSectionAdapter.ViewHolder>{
    private static final String TAG = "CourseCurriculumSectionAdapter";
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mSectionTItle;
        private TextView msectionDuration;
        private RecyclerView mLessonRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mSectionTItle = itemView.findViewById(R.id.sectionTitle);
            this.msectionDuration = itemView.findViewById(R.id.sectionDuration);
            this.mLessonRecyclerView = itemView.findViewById(R.id.sectionWiseLessonsRecyclerView);
        }
    }

    private ArrayList<Section> mSection;
    private Context mContext;
    public CourseCurriculumSectionAdapter(Context context, ArrayList<Section> section) {
        mContext = context;
        mSection = section;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_curriculum_section_cell, parent, false);
        final CourseCurriculumSectionAdapter.ViewHolder holder = new CourseCurriculumSectionAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Section currentSection = mSection.get(position);
        holder.mSectionTItle.setText("● "+currentSection.getTitle());
        holder.msectionDuration.setText(currentSection.getTotalDuration());
        List<LessonSchema> mLessons = currentSection.getmLesson();
        CourseCurriculumLessonAdapter adapter = new CourseCurriculumLessonAdapter(mContext, mLessons);
        holder.mLessonRecyclerView.setAdapter(adapter);
        holder.mLessonRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        setHeight(mLessons.size(), holder.mLessonRecyclerView);
    }

    private void setHeight(int numberOfItems, RecyclerView mRecyclerView) {
        int pixels = Helpers.convertDpToPixel((numberOfItems) + 10); // numberOfItems is the number of categories and the 90 is each items height with the margin of top and bottom. Extra 10 dp for readability
        ViewGroup.LayoutParams params1 = mRecyclerView.getLayoutParams();
        mRecyclerView.setMinimumHeight(249);
        mRecyclerView.requestLayout();
    }

    @Override
    public int getItemCount() {
        return mSection.size();
    }
}
