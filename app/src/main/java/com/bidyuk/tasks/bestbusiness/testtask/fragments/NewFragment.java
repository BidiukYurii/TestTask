package com.bidyuk.tasks.bestbusiness.testtask.fragments;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bidyuk.tasks.bestbusiness.testtask.MainActivity;
import com.bidyuk.tasks.bestbusiness.testtask.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewFragment extends android.support.v4.app.Fragment{
    private static final String ARG_PARAM1 = "Number of fragment";

    private int numberOfFragment;

    @BindView(R.id.minus_btn)
    ImageButton minusButton;
    @BindView(R.id.fragment_number_tv)
    TextView textView;

    public NewFragment() {
        // Required empty public constructor
    }

    public static NewFragment newInstance(int num) {
        NewFragment fragment = new NewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, num);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numberOfFragment = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new, container, false);
        ButterKnife.bind(this, rootView);
        if (numberOfFragment == 1)
            minusButton.setVisibility(View.INVISIBLE);
        setCorrectView(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        textView.setText(String.valueOf(numberOfFragment));
    }

    private void setCorrectView(View rootView) {
        //this heps to set correct position of buttons plus/minus
        rootView.findViewById(R.id.relativeLayout)
                .getLayoutParams().width = rootView.findViewById(R.id.view).getLayoutParams().width
                + minusButton.getDrawable().getIntrinsicWidth()/2;
    }

    @OnClick(R.id.plus_btn)
    public void plusBtnClick(View view) {
        ((MainActivity)getActivity()).createFragment(NewFragment.newInstance(numberOfFragment+1));
    }

    @OnClick(R.id.minus_btn)
    public void minusBtnClick(View view) {
        ((NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE))
                .cancel(numberOfFragment);
        ((MainActivity)getActivity()).removeFragment(this);
    }

    @OnClick(R.id.notification_btn)
    public void notificationBtnClick() {
        //Intent for opening fragment from notification
        Intent resultIntent = new Intent(getContext(), MainActivity.class);
        resultIntent.putExtra(getResources().getString(R.string.saved_tag), numberOfFragment);
        resultIntent.putExtra(getResources().getString(R.string.saved_count_tag),
                ((MainActivity)getActivity()).getFragmentList().size());
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getContext(), 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.n);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.n)
                .setLargeIcon(bitmap)
                .setAutoCancel(true)
                .setContentTitle(getResources().getString(R.string.notif_title))
                .setContentText(getResources().getString(R.string.notif_text) + numberOfFragment)
                .setContentIntent(resultPendingIntent);


        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(numberOfFragment, notification);
    }
}
