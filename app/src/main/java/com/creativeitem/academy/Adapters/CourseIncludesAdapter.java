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

public class CourseIncludesAdapter extends RecyclerView.Adapter<CourseIncludesAdapter.ViewHolder> {
    private static final String TAG = "CourseIncludesAdapter";
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseIncludesTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mCourseIncludesTextView = itemView.findViewById(R.id.courseIncludeCell);
        }
    }
    private List<String> mCourseIncludesList;
    private Context mContext;
    public CourseIncludesAdapter(Context context, List<String> courseIncludes) {
        this.mCourseIncludesList = courseIncludes;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_include_cell, parent, false);
        final CourseIncludesAdapter.ViewHolder holder = new CourseIncludesAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String currentCourseInclude = mCourseIncludesList.get(position);
        holder.mCourseIncludesTextView.setText(currentCourseInclude);
    }

    @Override
    public int getItemCount() {
        return mCourseIncludesList.size();
    }
}

