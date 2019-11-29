package com.example.takephoto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;


public class RecognizedResultFragment extends Fragment {

    private RadioGroup radioGroupFeedback;
    private RadioButton radioGood,radioBad;
    private TextView textGood,textBad,reRecognized;
    private ImageButton buttonBack;

    private Boolean feekback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recognized_result, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();

        radioGood.setChecked(true);

        radioGroupFeedback.setOnCheckedChangeListener(ocl);
        textGood.setOnClickListener(onClickListener);
        textBad.setOnClickListener(onClickListener);

        reRecognized.setOnClickListener(onClickListener);
        buttonBack.setOnClickListener(onClickListener);
    }

    @Override
    public void onResume() {
        super.onResume();

        FragmentUtils.hideFragment(getFragmentManager().getFragments().get(0));
        List<Fragment> list = getFragmentManager().getFragments();
        for (Fragment fragment:list)System.out.println(fragment);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
                    getFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });
    }

    private void findView(){
        radioGroupFeedback = getActivity().findViewById(R.id.radio_group_feedback);
        radioGood = getActivity().findViewById(R.id.radio_good);
        radioBad = getActivity().findViewById(R.id.radio_bad);
        textGood = getActivity().findViewById(R.id.text_good);
        textBad = getActivity().findViewById(R.id.text_bad);
        buttonBack = getActivity().findViewById(R.id.button_back);
        reRecognized = getActivity().findViewById(R.id.text_re_recognized);
    }

    RadioGroup.OnCheckedChangeListener ocl = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.radio_good:
                    feekback = true;
                    break;
                case R.id.radio_bad:
                    feekback = false;
                    break;
            }
            showFeedbackResult();
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.text_good:
                    feekback = true;
                    showFeedbackResult();
                    break;
                case R.id.text_bad:
                    feekback = false;
                    showFeedbackResult();
                    break;
                case R.id.button_back:case R.id.text_re_recognized:
                    getFragmentManager().popBackStack();
                    break;

            }
        }
    };

    private void showFeedbackResult(){
//        new Jump().JumpFragment(R.id.container,RecognizedResultFragment.this,new ResultFeedbackFragment());
        getFragmentManager().beginTransaction().add(R.id.container,new ResultFeedbackFragment()).addToBackStack(null).commitAllowingStateLoss();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    getFragmentManager().popBackStack();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
