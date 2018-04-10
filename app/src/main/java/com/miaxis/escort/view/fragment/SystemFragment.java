package com.miaxis.escort.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miaxis.escort.R;

public class SystemFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    public SystemFragment() {
        // Required empty public constructor
    }

    public static SystemFragment newInstance() {
        SystemFragment fragment = new SystemFragment();
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onSystemFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onSystemFragmentInteraction();
    }
}
