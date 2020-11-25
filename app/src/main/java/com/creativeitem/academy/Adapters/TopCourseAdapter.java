package com.creativeitem.academy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.creativeitem.academy.Activities.CourseDetailsActivity;
import com.creativeitem.academy.Models.Course;
import com.creativeitem.academy.R;

import java.util.ArrayList;

public class TopCourseAdapter extends RecyclerView.Adapter<TopCourseAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private ArrayList<Course> mTopCourses;

    public TopCourseAdapter(Context context, ArrayList<Course> topCourses) {
        mContext = context;
        mTopCourses = topCourses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_course_cell, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Course currentTopCourse = mTopCourses.get(holder.getAdapterPosition());
                switchToCourseDetailsView(currentTopCourse);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Course currentTopCourse = mTopCourses.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(currentTopCourse.getThumbnail())
                .into(holder.image);

        holder.name.setText(currentTopCourse.getTitle());

        holder.coursePrice.setText(currentTopCourse.getPrice());
        holder.topCourseRating.setRating(currentTopCourse.getRating());
    }

    @Override
    public int getItemCount() {
        return mTopCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView coursePrice;
        RatingBar topCourseRating;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
            coursePrice = itemView.findViewById(R.id.topCoursePrice);
            topCourseRating = itemView.findViewById(R.id.topCourseRating);
        }
    }

    private void switchToCourseDetailsView(Course currentTopCourse) {
        Intent intent = new Intent(mContext, CourseDetailsActivity.class);
        intent.putExtra("Course", currentTopCourse);
        mContext.startActivity(intent);
    }
}
