package com.creativeitem.academy.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creativeitem.academy.Adapters.CourseIncludesAdapter;
import com.creativeitem.academy.Models.CourseDetails;
import com.creativeitem.academy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseIncludeFragment extends Fragment {
    private CourseDetails mCourseDetails;
    private RecyclerView mCourseIncludeRecyclerView;
    public CourseIncludeFragment(CourseDetails courseDetails) {
        // Required empty public constructor
        mCourseDetails = courseDetails;
    }

    private void init(View view) {
        mCourseIncludeRecyclerView = view.findViewById(R.id.courseIncludesRecyclerView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_course_include, container, false);
        init(view);
        getIncludesData();
        return view;
    }

    private void getIncludesData() {
        CourseIncludesAdapter adapter = new CourseIncludesAdapter(getActivity(), mCourseDetails.getCourseIncludes());
        mCourseIncludeRecyclerView.setAdapter(adapter);
        mCourseIncludeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
