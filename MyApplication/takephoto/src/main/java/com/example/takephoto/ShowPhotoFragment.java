package com.example.takephoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ShowPhotoFragment extends Fragment {

    private ImageView imageView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = getActivity().findViewById(R.id.image);
        showImage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_photo, container, false);
    }

    //    显示图片
    private void showImage(){
        Bundle bundle = getArguments();
        String imagePath = bundle.getString("imagePath");
        Toast.makeText(getContext().getApplicationContext(),imagePath,Toast.LENGTH_SHORT).show();

//        读取图片
        try {
            FileInputStream in = new FileInputStream(imagePath);
            Bitmap bitmap = BitmapFactory.decodeStream(in);

            Matrix matrix  = new Matrix();
            matrix.setRotate(90);

            Bitmap bitmap1 = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            imageView.setImageBitmap(bitmap1);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }



}
