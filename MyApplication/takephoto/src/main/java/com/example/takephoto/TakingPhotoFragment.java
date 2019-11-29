package com.example.takephoto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Matrix;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.FlashMode;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Rational;
import android.util.Size;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class TakingPhotoFragment extends Fragment {

    private TextureView textureView;
    private ImageButton buttonTakePhoto, buttonImage, buttonBack, buttonLightning;
    private Boolean lightning = false;

    private ImageCapture imageCapture;


    File photo = null;

    public static TakingPhotoFragment newInstance() {
        return new TakingPhotoFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taking_photo, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textureView = getActivity().findViewById(R.id.texture_view);
        buttonTakePhoto = getActivity().findViewById(R.id.button_take_photo);
        buttonImage = getActivity().findViewById(R.id.image_button_image);
        buttonBack = getActivity().findViewById(R.id.image_button_back);
        buttonLightning = getActivity().findViewById(R.id.image_button_lightning);

        //  启动相机
        textureView.post(new Runnable() {
            @Override
            public void run() {
                startCamera();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        textureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                updateTransform();
            }
        });

        buttonImage.setOnClickListener(onClickListener);
        buttonBack.setOnClickListener(onClickListener);
        buttonLightning.setOnClickListener(onClickListener);

//返回按钮点击事件
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    getActivity().finish();
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CameraX.unbindAll();
    }

    //    更新相机预览：主要是给TextureView设置一个旋转的矩阵变化，防止预览方向不对
    private void updateTransform() {
        Matrix matrix = new Matrix();
        // Compute the center of the textureview
        float centerX = textureView.getWidth() / 2f;
        float centerY = textureView.getHeight() / 2f;

        float[] rotations = {0, 90, 180, 270};
        // Correct preview output to account for display rotation
        float rotationDegrees = rotations[textureView.getDisplay().getRotation()];

        matrix.postRotate(-rotationDegrees, centerX, centerY);

        // Finally, apply transformations to our TextureView
        textureView.setTransform(matrix);
    }

    //    启动相机,照相
    private void startCamera() {
        // 1. preview
        PreviewConfig previewConfig = new PreviewConfig.Builder()
                .setTargetAspectRatio(new Rational(1, 1))
                .setTargetResolution(new Size(640, 640))
                .build();

        Preview preview = new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput output) {
                ViewGroup parent = (ViewGroup) textureView.getParent();
                parent.removeView(textureView);
                parent.addView(textureView, 0);

                textureView.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();
            }
        });

        //    照相
        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder()
                .setTargetAspectRatio(new Rational(1, 1))
                .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .build();
        imageCapture = new ImageCapture(imageCaptureConfig);
        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    photo = File.createTempFile("shoes", ".jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                System.out.println("ONLONGCLICK:" + photo.toString());
                imageCapture.takePicture(photo, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        System.out.println("Succeed");
                        Bundle bundle = new Bundle();
                        bundle.putString("imagePath", photo.toString());
                        ShowPhotoFragment showPhotoFragment = new ShowPhotoFragment();
                        showPhotoFragment.setArguments(bundle);
                        FragmentUtils.hideFragment(TakingPhotoFragment.this);
                        FragmentUtils.addFragment(getFragmentManager(), showPhotoFragment, R.id.container, false, true);
                        FragmentUtils.addFragment(getFragmentManager(), new DistinguishFragment(), R.id.container, false, true);
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        System.out.println("Faile");
                    }
                });
            }
        });

        CameraX.bindToLifecycle(this, preview, imageCapture);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_button_image:
                    gotoImage();


                    break;
                case R.id.image_button_lightning:
                    if (lightning == false){
                        imageCapture.setFlashMode(FlashMode.ON);
                        lightning = true;
                        Toast.makeText(getActivity().getApplicationContext(),"闪光灯已打开",Toast.LENGTH_SHORT).show();
                    }else {
                        imageCapture.setFlashMode(FlashMode.OFF);
                        lightning = false;
                        Toast.makeText(getActivity().getApplicationContext(),"闪光灯已关闭",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.image_button_back:
                    getActivity().finish();
                    break;

            }
        }
    };

//    跳转到系统相册
    private void gotoImage(){
        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        }
        startActivityForResult(intent,1);
    }

//    系统相册返回
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            Bundle bundle = new Bundle();
            bundle.putString("imagePath",imagePath);
            ShowPhotoFragment showPhotoFragment = new ShowPhotoFragment();
            showPhotoFragment.setArguments(bundle);
            FragmentUtils.hideFragment(TakingPhotoFragment.this);
            FragmentUtils.addFragment(getFragmentManager(),showPhotoFragment,R.id.container,false,true);
            FragmentUtils.addFragment(getFragmentManager(),new DistinguishFragment(),R.id.container,false,true);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden)
        {
            FragmentUtils.showFragment(TakingPhotoFragment.this);
        }
    }
}
