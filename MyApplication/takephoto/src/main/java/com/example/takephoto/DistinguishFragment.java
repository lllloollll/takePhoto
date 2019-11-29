package com.example.takephoto;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

public class DistinguishFragment extends Fragment {

    private ImageButton buttonCancel;
    Thread thread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_distinguish, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
                    backToTackPhoto();
                    return true;
                }
                return false;
            }


        });

        //        跳转结果页面
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    synchronized (this){
                        if (getActivity().getSupportFragmentManager().getFragments().get(getFragmentManager().getFragments().size()-1).getClass() == DistinguishFragment.class){
                            getFragmentManager().popBackStack();
                            getFragmentManager().popBackStack();
                            FragmentUtils.addFragment(getFragmentManager(), new RecognizedResultFragment(), R.id.container, false, true);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buttonCancel = getActivity().findViewById(R.id.button_cancel);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Fragment> list = getFragmentManager().getFragments();
                for (Fragment fragment:list)System.out.println(fragment);
                backToTackPhoto();
            }
        });




    }

    private void backToTackPhoto(){
        thread.interrupt();
        synchronized (this){
            if (getFragmentManager().getFragments().get(getFragmentManager().getFragments().size()-1).getClass() == DistinguishFragment.class){
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                getFragmentManager().beginTransaction().show(getFragmentManager().getFragments().get(0));
            }
        }
    }
}
