package com.graction.developer.zoocaster.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.graction.developer.zoocaster.Adapter.AddressListAdapter;
import com.graction.developer.zoocaster.Adapter.Listener.ItemOnClickListener;
import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Model.Address.AddressModel;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.ViewAttributeManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.databinding.ActivitySearchAddressBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAddressActivity extends BaseActivity {
    private ActivitySearchAddressBinding binding;
    private Intent intent = new Intent();
    private AddressListAdapter adapter;
    private ItemOnClickListener itemOnClickListener = item -> {
        intent.putExtra(DataStorage.Key.KEY_ADDRESS_ITEM, item);
        activityEnd(DataStorage.Request.SEARCH_ADDRESS_OK);
    };

    @Override
    protected void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_address);
        binding.activitySearchAddressRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.setActivity(this);
        ViewAttributeManager.getInstance().setDoneOption(this, binding.activitySearchAddressETKeyword, (v, actionId, event) -> {
            onSearch();
            return false;
        });
    }

    public void onSearch() {
        logger.log(HLogger.LogType.INFO, "onSearch(View view)", "onSearch");
        Net.getInstance().getAddressFactoryIm().searchAddress(AddressModel.getParameter(binding.activitySearchAddressETKeyword.getText() + "")).enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                if (response.isSuccessful()) {
                    logger.log(HLogger.LogType.INFO, "onSearch(View view) - onResponse(Call<AddressModel> call, Response<AddressModel> response)", "isSuccess");
                    logger.log(HLogger.LogType.INFO, "onSearch(View view) - onResponse(Call<AddressModel> call, Response<AddressModel> response)", "" + response.body());
                    adapter = new AddressListAdapter(response.body().getPredictions(), itemOnClickListener);
                    binding.activitySearchAddressRV.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<AddressModel> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "onSearch(View view) - onFailure(Call<AddressModel> call, Throwable t)", t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activityEnd(DataStorage.Request.SEARCH_ADDRESS_NONE_SELECTED);
    }

    private void activityEnd(int code) {
        setResult(code, intent);
        finish();
    }
}
