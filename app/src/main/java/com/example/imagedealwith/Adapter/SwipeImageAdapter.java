package com.example.imagedealwith.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.imagedealwith.Activity.PreviewSelectedImageActivity;

import java.util.ArrayList;
import java.util.List;

public class SwipeImageAdapter extends PagerAdapter {

    private List<Uri> ImageUriList;
    private List<ImageView> imageViews;
    private Activity activity;
    private OnImageListChanged mListener;
    private boolean enablePinchView;

    public SwipeImageAdapter(List<Uri> imageUriList, Activity activity, boolean EnablePinchImageView) {
        this.activity = activity;
        imageViews = new ArrayList<>();
        enablePinchView = EnablePinchImageView;
        setImageUriList(imageUriList);

    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setImageUriList(List<Uri> imageUriList) {
        ImageUriList = imageUriList;
        imageViews.clear();
        int size = ImageUriList.size();

        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(activity);
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Uri uri  = ImageUriList.get(i);
            if (enablePinchView)
                iv.setOnClickListener((v) -> EnterImagePreview(uri));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(activity).load(uri).into(iv);
            imageViews.add(iv);
        }
        if(mListener != null)
        {
            mListener.handle(imageViews);
        }
    }

    public List<ImageView> GetInnerImageViews()
    {
        return imageViews;
    }

    private void EnterImagePreview(Uri uri)
    {
        Log.d("xxx","准备进入图片预览吧");
        Intent intent = new Intent(activity, PreviewSelectedImageActivity.class);
        intent.putExtra("PreviewImageUri",uri);
        intent.putExtra("IsNormalPreview",true);
        intent.putExtra("IsDisableDelete",true);
        activity.startActivity(intent);
    }
    public Activity getActivity() {
        return activity;
    }

    public List<Uri> getImageUriList() {
        return ImageUriList;
    }

    @Override
    public int getCount() {
        return ImageUriList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView(imageViews.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(imageViews.get(position));
        return imageViews.get(position);
    }


    public void SetOnImageChangedListener(OnImageListChanged listener)
    {
        mListener = listener;
    }

    public void RemoveOnImageChangedListener()
    {
        mListener = null;
    }

    public interface OnImageListChanged
    {
        void handle(List<ImageView> ivs);
    }
}
