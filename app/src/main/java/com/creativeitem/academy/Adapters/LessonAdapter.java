package com.creativeitem.academy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeitem.academy.Activities.LessonActivity;
import com.creativeitem.academy.JSONSchemas.LessonSchema;
import com.creativeitem.academy.Models.Lesson;
import com.creativeitem.academy.R;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {

    private static final String TAG = "LessonAdapter";
    private OnLessonClickListener mOnLessonClickListener;
    boolean isFirstLesson;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mLessonTItle;
        private TextView mLessonDuration;
        private TextView mLessonSerialNumber;
        private CheckBox mLessonCompletionCheckbox;
        private OnLessonClickListener onLessonClickListener;

        public ViewHolder(@NonNull View itemView, final OnLessonClickListener onLessonClickListener) {
            super(itemView);
            this.mLessonTItle = itemView.findViewById(R.id.lessonTitle);
            this.mLessonDuration = itemView.findViewById(R.id.lessonDuration);
            this.mLessonSerialNumber = itemView.findViewById(R.id.serialNumber);
            this.onLessonClickListener = onLessonClickListener;
            this.mLessonCompletionCheckbox = itemView.findViewById(R.id.lessonCompletionCheckbox);
            Log.d("Coldplay", "ViewHolder Method");
        }
    }

    interface OnLessonClickListener {
        void onLessonClick(LessonSchema eachLesson, ViewHolder viewHolder);
        void onLessonCompletionListener(LessonSchema eachLesson, boolean lessonCompletionStatus);
    }

    private List<LessonSchema> mLesson;
    private Context mContext;
    private int lessonCounter = 0;
    public LessonAdapter(Context context, List<LessonSchema> lesson, OnLessonClickListener onLessonClickListener, boolean isFirst, int lessonFrom) {
        this.mLesson = lesson;
        this.mContext = context;
        this.mOnLessonClickListener = onLessonClickListener;
        this.isFirstLesson = isFirst;
        this.lessonCounter = lessonFrom;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_cell, parent, false);
        final LessonAdapter.ViewHolder holder = new LessonAdapter.ViewHolder(view, mOnLessonClickListener);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnLessonClickListener.onLessonClick(mLesson.get(holder.getAdapterPosition()), holder);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mLessonSerialNumber.setText(Integer.toString(lessonCounter));
        lessonCounter++;
        if (isFirstLesson){
            mOnLessonClickListener.onLessonClick(mLesson.get(holder.getAdapterPosition()), holder);
            isFirstLesson = false;
        }

        final LessonSchema currentLesson = mLesson.get(position);

        holder.mLessonTItle.setText(currentLesson.getTitle());
        if (currentLesson.getLessonType().equals("other")){
            holder.mLessonDuration.setText("📎 Attachment");
        }else if(currentLesson.getLessonType().equals("quiz")){
            holder.mLessonDuration.setText("📝 Quiz");
        }else{
            holder.mLessonDuration.setText(currentLesson.getDuration());
        }
        if (currentLesson.getIsCompleted() == 1){
            holder.mLessonCompletionCheckbox.setChecked(true);
        }else{
            holder.mLessonCompletionCheckbox.setChecked(false);
        }

        holder.mLessonCompletionCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnLessonClickListener.onLessonCompletionListener(currentLesson, holder.mLessonCompletionCheckbox.isChecked());
            }
        });
        //holder.itemView.setBackgroundColor(Color.parseColor("#dedede"));
        //holder.itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_custom_blue_button));
    }

    @Override
    public int getItemCount() {
        return mLesson.size();
    }
}
