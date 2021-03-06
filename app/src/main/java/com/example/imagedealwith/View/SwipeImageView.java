package com.example.imagedealwith.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.imagedealwith.Adapter.SwipeImageAdapter;
import com.example.imagedealwith.R;

import java.util.ArrayList;
import java.util.List;

public class SwipeImageView extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private LinearLayout dotsContainer;
    private SwipeImageAdapter Adapter;
    private List<ImageView> dotsList;
    private ViewPager.OnPageChangeListener mListener;
    private Context mContext;

    public SwipeImageView(Context context) {
        super(context);
        mContext = context;
        dotsList = new ArrayList<>();
        InitViewPager();
        InitDotsContainer();

    }

    public SwipeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        dotsList = new ArrayList<>();
        InitViewPager();
        InitDotsContainer();
    }

    public SwipeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        dotsList = new ArrayList<>();
        InitViewPager();
        InitDotsContainer();
    }


    private void InitViewPager()
    {
        viewPager = new ViewPager(mContext);
        viewPager.setId(Integer.MAX_VALUE - 2000);
        viewPager.addOnPageChangeListener(this);
        LayoutParams viewPagerLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(viewPager, viewPagerLp);
    }

    private void InitDotsContainer()
    {
        dotsContainer = new LinearLayout(mContext);
        dotsContainer.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        dotsContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams dotsLp = new LayoutParams(Dp2Px(200), Dp2Px(35));
        dotsLp.addRule(RelativeLayout.ALIGN_BOTTOM,viewPager.getId());
        dotsLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        dotsLp.bottomMargin = Dp2Px(10);
        this.addView(dotsContainer, dotsLp);
    }

    public SwipeImageView SetImageListAdapter(SwipeImageAdapter adapter) {
        Adapter = adapter;
        viewPager.setAdapter(Adapter);
        Adapter.SetOnImageChangedListener(new SwipeImageAdapter.OnImageListChanged()
        {
            @Override
            public void handle(List<ImageView> ivs)
            {
                BuildImageDots();
            }
        });
        BuildImageDots();
        return this;
    }

    public void SetOnPageChangedListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    private SwipeImageView BuildImageDots() {
        dotsList.clear();
        for (int i = 0; i < Adapter.getImageUriList().size(); i++) {

            ImageView iv = new ImageView(mContext);
            dotsList.add(iv);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.width = 20;
            lp.height = 20;
            lp.topMargin = 12;
            lp.bottomMargin = 12;
            lp.leftMargin = 12;
            lp.rightMargin = 12;
            dotsContainer.addView(iv, lp);
        }
        onPageSelected(0);
        return this;
    }

    public LinearLayout GetInnerDotsContainer() {
        return dotsContainer;
    }

    public ViewPager GetInnerViewPager() {
        return viewPager;
    }

    public List<ImageView> GetInnerImageViews()
    {
        return Adapter.GetInnerImageViews();
    }

    private int Dp2Px(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsList.size(); i++) {
            if (i == position) {
                dotsList.get(i).setImageResource(R.drawable.image_pager_dots_selected);
            } else dotsList.get(i).setImageResource(R.drawable.image_pager_dots);
        }
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }
}
