package com.example.imagedealwith.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.imagedealwith.R;
import com.example.imagedealwith.View.PinchImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class PreviewSelectedImageActivity extends AppCompatActivity {

    @BindView(R.id.PreviewImageView)
    PinchImageView PreviewImageView;


    @BindView(R.id.HomeArrowBtn)
    ImageButton HomeArrowBtn;

    private Uri CurrentPreviewImageUri;
    private int CurrentPreviewImageFromGridIndex = 0;
    private boolean IsNormalPreview = false;
    private boolean IsDisableDelete = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_selected_image);
        ButterKnife.bind(this);
        InitClickListeners();

        CurrentPreviewImageFromGridIndex = getIntent().getIntExtra("PreviewImageIndex",-1);
        CurrentPreviewImageUri = getIntent().getParcelableExtra("PreviewImageUri");

        IsNormalPreview = getIntent().getBooleanExtra("IsNormalPreview",false);
        IsDisableDelete = getIntent().getBooleanExtra("IsDisableDelete",false);
        RequestRenderImage(CurrentPreviewImageUri);
    }

    private void InitClickListeners()
    {
        HomeArrowBtn.setOnClickListener(this::HandleReturnArrow);
    }


    private void RequestRenderImage(Uri ImageUri)
    {
        Picasso.with(PreviewSelectedImageActivity.this)
                .load(ImageUri)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(PreviewImageView);
    }

    private void HandleReturnArrow(View v)
    {
        PassInfoToPublishPage(false);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //Handle从UCrop回来
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == UCrop.REQUEST_CROP) {//如果旧的存在，先把旧的删了
                if (CurrentPreviewImageUri != null) {
                    File file = new File(CurrentPreviewImageUri.getPath());
                    if (file.exists()) {
                        file.delete();
                    }
                }
                CurrentPreviewImageUri = UCrop.getOutput(data);
                RequestRenderImage(CurrentPreviewImageUri);
            }
        }
    }


    private void PassInfoToPublishPage(boolean ShouldDeleteImage)
    {
        Intent intent = new Intent();
        intent.putExtra("ShouldDeleteImage",ShouldDeleteImage);
        intent.putExtra("PreviewImageUri",CurrentPreviewImageUri);
        intent.putExtra("PreviewImageIndex",CurrentPreviewImageFromGridIndex);
        PreviewSelectedImageActivity.this.setResult(Activity.RESULT_OK,intent);
    }

    @Override
    public void onBackPressed()
    {
        PassInfoToPublishPage(false);
        finish();
    }
}
