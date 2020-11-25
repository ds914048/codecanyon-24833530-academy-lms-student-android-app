package com.creativeitem.academy.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.creativeitem.academy.Activities.CourseDetailsActivity;
import com.creativeitem.academy.Models.Course;
import com.creativeitem.academy.R;

import java.util.ArrayList;
import java.util.List;

public class WishlistAdapter extends BaseAdapter {
    //vars

    private Context mContext;
    private ArrayList<Course> mWishLists;
    RemoveItemFromWishList mRemoveItemFromWishList = null;

    public WishlistAdapter(Context context, ArrayList<Course> wishList, RemoveItemFromWishList removeItemFromWishList) {
        mContext = context;
        mWishLists = wishList;
        mRemoveItemFromWishList = removeItemFromWishList;
    }

    @Override
    public int getCount() {
        return mWishLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mWishLists.get(i);
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
            gridView = inflater.inflate(R.layout.wishlist_cell,null);

        }

        ImageView imageView = gridView.findViewById(R.id.image_view);
        TextView name = gridView.findViewById(R.id.courseTitle);
        //ImageButton toggleWishListButton = gridView.findViewById(R.id.toggleWishList);
        Spinner moreButton = gridView.findViewById(R.id.moreButton);

        final Course wishList = mWishLists.get(i);
        Glide.with(mContext)
                .asBitmap()
                .load(wishList.getThumbnail())
                .into(imageView);
        name.setText(wishList.getTitle());

//        toggleWishListButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showConfirmationAlert(wishList.getId());
//            }
//        });

        // Intending To Course Details View
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CourseDetailsActivity.class);
                intent.putExtra("Course", wishList);
                mContext.startActivity(intent);
            }
        });

        // init Spinner
        initMoreOptionSpinner(moreButton, wishList);

        return gridView;
    }

    private void initMoreOptionSpinner(Spinner moreButton, final Course wishList) {
        // initiating the options for option menu
        List<String> moreOptions = new ArrayList<>();
        moreOptions.add(0, "Choose Option");
        moreOptions.add(1, "Remove From Wishlist");

        //Style and populate spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, moreOptions);
        //Drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moreButton.setAdapter(dataAdapter);
        moreButton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemIdAtPosition(i) == 1){
                    showConfirmationAlert(wishList.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void showConfirmationAlert(final int courseId) {
        new AlertDialog.Builder(mContext)
                .setTitle("Confirmation")
                .setMessage("Do you really want to remove this course from your wishlist?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //code
                        mRemoveItemFromWishList.removeFromWishList(courseId);
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public interface RemoveItemFromWishList {
        void removeFromWishList(int courseId);
    }
}
