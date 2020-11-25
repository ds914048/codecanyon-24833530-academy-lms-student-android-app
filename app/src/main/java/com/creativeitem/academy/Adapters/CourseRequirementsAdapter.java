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

public class CourseRequirementsAdapter extends RecyclerView.Adapter<CourseRequirementsAdapter.ViewHolder> {
    private static final String TAG = "CourseRequirementsAdapter";
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseRequirementsCellTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mCourseRequirementsCellTextView = itemView.findViewById(R.id.courseRequirementCellTitle);
        }
    }
    private List<String> mCourseRequirementsList;
    private Context mContext;
    public CourseRequirementsAdapter(Context context, List<String> courseRequirements) {
        this.mContext = context;
        this.mCourseRequirementsList = courseRequirements;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_requirement_cell, parent, false);
        final CourseRequirementsAdapter.ViewHolder holder = new CourseRequirementsAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String currentCourseOutcome = mCourseRequirementsList.get(position);
        holder.mCourseRequirementsCellTextView.setText(currentCourseOutcome);
    }

    @Override
    public int getItemCount() {
        return mCourseRequirementsList.size();
    }
}

