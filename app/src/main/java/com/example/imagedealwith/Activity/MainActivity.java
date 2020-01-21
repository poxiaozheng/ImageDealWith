package com.example.imagedealwith.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.imagedealwith.Adapter.SwipeImageAdapter;
import com.example.imagedealwith.Adapter.WorkTagRecyclerAdapter;
import com.example.imagedealwith.Model.ImageState;
import com.example.imagedealwith.R;
import com.example.imagedealwith.Utils.AppUtils;
import com.example.imagedealwith.View.MultiStateImageButton;
import com.example.imagedealwith.View.SwipeImageView;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.WorkPreviewImages)
    SwipeImageView ImagePager;

    @BindView(R.id.WorkName)
    TextView WorkName;

    @BindView(R.id.WorkID)
    TextView WorkID;

    @BindView(R.id.ArtistAvatar)
    CircleImageView Avatar;

    @BindView(R.id.ArtistUserName)
    TextView ArtistUserName;

    @BindView(R.id.WorkDescriptions)
    TextView Descriptions;

    @BindView(R.id.LikeWorkButton)
    MultiStateImageButton LikeWorkBtn;

    @BindView(R.id.WorkTags)
    RecyclerView Tags;

    SwipeImageAdapter ImageAdapter;

    WorkTagRecyclerAdapter TagsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        InitWorkImages();
        InitStates();
        InitTags();

    }

    private void InitWorkImages() {
        Uri Image0 = Uri.parse(AppUtils.GetAssetsUrl("image0.jpg"));
        Uri Image1 = Uri.parse(AppUtils.GetAssetsUrl("image1.jpg"));
        ImageAdapter = new SwipeImageAdapter(Arrays.asList(Image0,Image1), this, true);
        ImagePager.SetImageListAdapter(ImageAdapter);
    }

    private void InitStates() {
        LikeWorkBtn.SetStates(
                new ImageState(1,R.drawable.favorite),
                new ImageState(2,R.drawable.favorite_border)
        );

    }

    private void InitTags() {
        TagsAdapter = new WorkTagRecyclerAdapter(Arrays.asList("标签1", "标签2", "标签3"));
        Tags.setAdapter(TagsAdapter);
        Tags.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
    }

    @OnClick({R.id.LikeWorkButton})
    public void OnClickLikeWork(View v) {
        int CurrState = LikeWorkBtn.GetCurrentState();
        switch (CurrState) {
            case 1:
                // 当前喜欢了这个作品，执行取消喜欢
                LikeWorkBtn.ShowState(2);
                break;
            case 2:
                // 当前不喜欢此作品，执行喜欢作品
                LikeWorkBtn.ShowState(1);
                break;
        }
    }

}
