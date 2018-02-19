package com.graction.developer.zoocaster.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.databinding.FragmentHomeBinding;

public class EmptyFragment extends BaseFragment {
    private FragmentHomeBinding binding;

    public static Fragment getInstance() {
        Fragment fragment = new EmptyFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm, null, false);
        return binding.getRoot();
    }

    @Override
    protected void init(View view) {
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
