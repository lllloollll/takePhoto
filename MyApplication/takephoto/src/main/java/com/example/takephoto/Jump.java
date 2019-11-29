package com.example.takephoto;

import android.content.Context;
import android.text.Layout;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Jump {
    public void JumpFragment( int id,Fragment fragment1, Fragment fragment2){
        FragmentTransaction transaction = fragment1.getFragmentManager().beginTransaction();
        if (!fragment2.isAdded()){
            transaction.add(id,fragment2)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }else {
            transaction.hide(fragment1).show(fragment2).addToBackStack(null).commitAllowingStateLoss();
        }
    }
    public void JumpFragment( int id,Boolean bool,Fragment fragment1, Fragment fragment2){
        FragmentTransaction transaction = fragment1.getFragmentManager().beginTransaction();
        if (bool == false){
            if (!fragment2.isAdded()){
                transaction.add(id,fragment2)
                        .commitAllowingStateLoss();
            }else {
                transaction.hide(fragment1).show(fragment2).commitAllowingStateLoss();
            }
        }
        else JumpFragment(id,fragment1,fragment2);
    }
}
