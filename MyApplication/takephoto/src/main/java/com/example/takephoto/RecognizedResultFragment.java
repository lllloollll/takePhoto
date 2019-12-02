package com.example.takephoto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


public class RecognizedResultFragment extends Fragment {

    private RadioGroup radioGroupFeedback;
    private RadioButton radioGood,radioBad;
    private TextView textGood,textBad,reRecognized,titleResultShoes;
    private ImageButton buttonBack;
    private ImageView imageResultShoe;
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

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



        setView();
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
        imageResultShoe = getActivity().findViewById(R.id.image_result_shoes);
        titleResultShoes = getActivity().findViewById(R.id.title_result_shoes);
        recyclerView = getActivity().findViewById(R.id.recyclerview);
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

    private void setView(){
        radioGood.setChecked(true);

        radioGroupFeedback.setOnCheckedChangeListener(ocl);
        textGood.setOnClickListener(onClickListener);
        textBad.setOnClickListener(onClickListener);

        reRecognized.setOnClickListener(onClickListener);
        buttonBack.setOnClickListener(onClickListener);

        imageResultShoe.setImageURI(Uri.parse(new ShoeNews().coverImg));
        titleResultShoes.setText(new ShoeNews().shoeName);

        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity().getApplicationContext())
                    .inflate(R.layout.item_recyclerview,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//            holder.itemImage.setImageURI(Uri.parse());
            holder.itemNumber.setText("("+new ShoeNews().commentCount+")");
            holder.itemRating.setRating(new ShoeNews().avgScore);
            holder.itemImage.setImageURI(Uri.parse(new ShoeNews().coverImg));
            holder.itemTitle.setText(new ShoeNews().shoeName);

            if (position == 0){
//                添加new图片
                holder.addView();

            }

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView itemImage;
            TextView itemTitle,itemNumber;
            RatingBar itemRating;
            RelativeLayout itemContainer;

            public MyViewHolder(@NonNull View view) {
                super(view);
                itemImage = (ImageView) view.findViewById(R.id.item_image);
                itemTitle = (TextView) view.findViewById(R.id.item_title);
                itemNumber = (TextView) view.findViewById(R.id.item_number);
                itemRating = (RatingBar) view.findViewById(R.id.item_rating_bar);
                itemContainer = view.findViewById(R.id.item_container);
            }
            public void addView(){

                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.image_new);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(0,12,0,0);
//                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(getContext(), AttributeSet.);
                itemContainer.addView(imageView);

            }
        }
    }
}
