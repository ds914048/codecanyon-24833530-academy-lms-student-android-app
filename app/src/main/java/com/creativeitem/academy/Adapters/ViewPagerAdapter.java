package com.creativeitem.academy.Adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.creativeitem.academy.Fragments.CourseIncludeFragment;
import com.creativeitem.academy.Fragments.CourseOutcomeFragment;
import com.creativeitem.academy.Fragments.CourseRequirementFragment;
import com.creativeitem.academy.Models.CourseDetails;
import com.creativeitem.academy.R;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private CourseDetails mCourseDetails;
    public ViewPagerAdapter(FragmentManager fm, CourseDetails courseDetails) {
        super(fm);
        this.mCourseDetails = courseDetails;
    }
    private int mCurrentPosition;
    @Override
    public Fragment getItem(int position) {
        mCurrentPosition = position;
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new CourseIncludeFragment(mCourseDetails);
                break;
            case 1:
                fragment = new CourseOutcomeFragment(mCourseDetails);
                break;
            case 2:
                fragment = new CourseRequirementFragment(mCourseDetails);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String tabTitle = null;
        switch (position){
            case 0:
                tabTitle = "Includes";
                break;
            case 1:
                tabTitle = "Outcomes";
                break;
            case 2:
                tabTitle = "Requirements";
                break;
        }
        return tabTitle;
    }
}
