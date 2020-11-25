package com.creativeitem.academy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.creativeitem.academy.Activities.LessonActivity;
import com.creativeitem.academy.Models.MyCourse;
import com.creativeitem.academy.R;

import java.util.ArrayList;

public class MyCourseAdapter extends BaseAdapter {
    //vars
    private Context mContext;
    private ArrayList<MyCourse> mMyCourses;


    public MyCourseAdapter(Context context, ArrayList<MyCourse> myCourse) {
        mContext = context;
        mMyCourses = myCourse;
    }

    @Override
    public int getCount() {
        return mMyCourses.size();
    }

    @Override
    public Object getItem(int i) {
        return mMyCourses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridView = view;
        if (view == null){
           LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           gridView = inflater.inflate(R.layout.my_course_cell,null);

        }

        ImageView imageView = gridView.findViewById(R.id.image_view);
        TextView name = gridView.findViewById(R.id.courseTitle);
        TextView courseCompletionNumric = gridView.findViewById(R.id.courseCompletionNumericValue);
        ProgressBar progressBar = gridView.findViewById(R.id.courseCompletionProgressBar);
        RatingBar courseRating = gridView.findViewById(R.id.myCourseRating);
        final MyCourse myCourse = mMyCourses.get(i);
        Glide.with(mContext)
                .asBitmap()
                .load(myCourse.getThumbnail())
                .into(imageView);

        name.setText(myCourse.getTitle());
        progressBar.setProgress(myCourse.getCourseCompletion());
        courseRating.setRating(myCourse.getRating());
        courseCompletionNumric.setText(myCourse.getCourseCompletion()+"% Completed");

        gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LessonActivity.class);
                intent.putExtra("Course", myCourse);
                mContext.startActivity(intent);

            }
        });
        return gridView;
    }
}
