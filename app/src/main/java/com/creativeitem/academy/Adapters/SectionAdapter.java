package com.creativeitem.academy.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeitem.academy.Activities.LessonActivity;
import com.creativeitem.academy.JSONSchemas.LessonSchema;
import com.creativeitem.academy.JSONSchemas.SectionSchema;
import com.creativeitem.academy.Models.Lesson;
import com.creativeitem.academy.Models.Section;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Utils.Helpers;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> implements LessonAdapter.OnLessonClickListener {
    private static final String TAG = "SectionAdapter";
    private PassLessonToActivity mPassLessonToActivity;

    @Override
    public void onLessonClick(LessonSchema eachLesson, LessonAdapter.ViewHolder viewHolder) {
        mPassLessonToActivity.PassLesson(eachLesson, viewHolder);
    }

    @Override
    public void onLessonCompletionListener(LessonSchema eachLesson, boolean lessonCompletionStatus) {
        mPassLessonToActivity.ToggleLessonCompletionStatus(eachLesson, lessonCompletionStatus);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mSectionTItle, mNumberOfCompletedLessons, mNumberOfTotalLessons, mSectionNumberTitle;
        private RecyclerView mLessonRecyclerView;
        private LessonAdapter.OnLessonClickListener mOnLessonClickListener;
        private PassLessonToActivity passLessonToActivity;
        public ViewHolder(@NonNull View itemView, PassLessonToActivity passLessonIdToActivity) {
            super(itemView);
            this.mSectionTItle = itemView.findViewById(R.id.sectionTitle);
            this.mNumberOfCompletedLessons = itemView.findViewById(R.id.numberOfCompletedLessons);
            this.mLessonRecyclerView = itemView.findViewById(R.id.lessonRecyclerView);
            this.mNumberOfTotalLessons = itemView.findViewById(R.id.numberOfTotalLessons);
            this.mSectionNumberTitle = itemView.findViewById(R.id.sectionNumberTitle);
            this.passLessonToActivity = passLessonIdToActivity;
        }
    }

    private ArrayList<Section> mSection;
    boolean firstLessonFlag;
    private Context mContext;
    private ViewHolder mViewHolder;
    public SectionAdapter(Context context, ArrayList<Section> section, PassLessonToActivity passLessonActivity, boolean firstLesson) {
        mContext = context;
        mSection = section;
        mPassLessonToActivity = passLessonActivity;
        firstLessonFlag = firstLesson;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_cell, parent, false);
        final SectionAdapter.ViewHolder holder = new SectionAdapter.ViewHolder(view, mPassLessonToActivity);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Section currentSection = mSection.get(position);
        holder.mSectionTItle.setText(currentSection.getTitle());
        List<LessonSchema> mLessons = currentSection.getmLesson();
        LessonAdapter adapter = new LessonAdapter(mContext, mLessons, this, firstLessonFlag, currentSection.getLessonCounterStarts());
        firstLessonFlag = false;
        holder.mLessonRecyclerView.setAdapter(adapter);
        holder.mLessonRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        // this is hidden because of future work
        //holder.mNumberOfCompletedLessons.setText(Integer.toString(currentSection.getNumberOfCompletedLessons()));
        holder.mSectionNumberTitle.setText("Section: "+position+1);
        holder.mNumberOfTotalLessons.setText(mLessons.size()+" Lessons");
        setHeight(mLessons.size(), holder.mLessonRecyclerView);
    }

    private void setHeight(int numberOfItems, RecyclerView mRecyclerView) {
        int pixels = Helpers.convertDpToPixel((numberOfItems * 90) + 10); // numberOfItems is the number of categories and the 90 is each items height with the margin of top and bottom. Extra 10 dp for readability
        ViewGroup.LayoutParams params1 = mRecyclerView.getLayoutParams();
        mRecyclerView.setMinimumHeight(pixels);
        mRecyclerView.requestLayout();
    }

    @Override
    public int getItemCount() {
        return mSection.size();
    }

    // This interface is responsible for passing the data to lesson activity
    public interface PassLessonToActivity {
        void PassLesson(LessonSchema eachLesson, LessonAdapter.ViewHolder viewHolder);
        int ToggleLessonCompletionStatus(LessonSchema eachLesson, boolean lessonCompletionStatus);
    }
}
