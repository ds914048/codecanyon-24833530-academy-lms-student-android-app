package com.creativeitem.academy.Adapters;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.creativeitem.academy.R;
import java.util.List;

public class CourseOutcomesAdapter extends RecyclerView.Adapter<CourseOutcomesAdapter.ViewHolder> {
    private static final String TAG = "CourseOutcomesAdapter";
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseOutcomesCellTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mCourseOutcomesCellTextView = itemView.findViewById(R.id.courseOutcomeCell);
        }
    }
    private List<String> mCourseOutcomesList;
    private Context mContext;
    public CourseOutcomesAdapter(Context context, List<String> courseOutcomes) {
        this.mCourseOutcomesList = courseOutcomes;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_outcome_cell, parent, false);
        final CourseOutcomesAdapter.ViewHolder holder = new CourseOutcomesAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String currentCourseOutcome = mCourseOutcomesList.get(position);
        holder.mCourseOutcomesCellTextView.setText(currentCourseOutcome);
    }

    @Override
    public int getItemCount() {
        return mCourseOutcomesList.size();
    }
}

