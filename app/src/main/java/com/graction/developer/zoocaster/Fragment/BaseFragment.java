package com.graction.developer.zoocaster.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.graction.developer.zoocaster.Util.Log.HLogger;

abstract public class BaseFragment extends Fragment {
    protected HLogger logger;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        init(view);
    }

    protected abstract void init(View view);

    private void init(){
        logger = new HLogger(getClass());
    }
}
