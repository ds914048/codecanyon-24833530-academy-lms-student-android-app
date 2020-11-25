package com.creativeitem.academy.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeitem.academy.Activities.LessonActivity;
import com.creativeitem.academy.JSONSchemas.LessonSchema;
import com.creativeitem.academy.Models.Lesson;
import com.creativeitem.academy.R;
import java.util.List;

public class CourseCurriculumLessonAdapter extends RecyclerView.Adapter<CourseCurriculumLessonAdapter.ViewHolder> {
    private static final String TAG = "CourseCurriculumLessonAdapter";
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mLessonTItle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mLessonTItle = itemView.findViewById(R.id.courseCurriculumLessonCellTitlte);
        }
    }
    private List<LessonSchema> mLesson;
    private Context mContext;
    public CourseCurriculumLessonAdapter(Context context, List<LessonSchema> lesson) {
        this.mLesson = lesson;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_curriculum_lesson_cell, parent, false);
        final CourseCurriculumLessonAdapter.ViewHolder holder = new CourseCurriculumLessonAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LessonSchema currentLesson = mLesson.get(position);
        holder.mLessonTItle.setText(currentLesson.getTitle());
        if (currentLesson.getLessonType().equals("other")){
            ImageView attachmentIcon = holder.itemView.findViewById(R.id.attachmentIcon);
            attachmentIcon.setVisibility(View.VISIBLE);
        }else if(currentLesson.getLessonType().equals("quiz")){
            ImageView quizIcon = holder.itemView.findViewById(R.id.quizIcon);
            quizIcon.setVisibility(View.VISIBLE);
        }else{
            ImageView videoIcon = holder.itemView.findViewById(R.id.videoIcon);
            videoIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mLesson.size();
    }
}
