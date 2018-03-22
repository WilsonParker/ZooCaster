package com.graction.developer.zoocaster.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.graction.developer.zoocaster.Adapter.AddressListAdapter;
import com.graction.developer.zoocaster.Adapter.FavoriteAddressListAdapter;
import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.DataBase.DataBaseHelper;
import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.Model.Address.AddressModel;
import com.graction.developer.zoocaster.Model.DataBase.FavoriteTable;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.HandlerManager;
import com.graction.developer.zoocaster.UI.ViewAttributeManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.databinding.ActivitySearchAddress2Binding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JeongTaehyun
 */

/*
 * 주소 검색 Activity
 */

public class SearchAddressActivity extends BaseActivity {
    private ActivitySearchAddress2Binding binding;
    private Intent intent = new Intent();
    private ArrayList<AddressModel.Prediction> addressItems;
    private AddressListAdapter searchAdapter;
    private FavoriteAddressListAdapter favoriteAdapter;
    private AddressHandleListener addressHandleListener = (newAddress, originAddress)-> {
        intent.putExtra(DataStorage.Key.KEY_NEW_ADDRESS, newAddress);
        intent.putExtra(DataStorage.Key.KEY_ORIGIN_ADDRESS, originAddress);
        activityEnd(DataStorage.Request.SEARCH_ADDRESS_OK);
    };

    /*
     * 초기 설정
     */
    @Override
    protected void init() {
        DataBaseHelper.createHelper(getBaseContext());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_address2);
        binding.activitySearchAddressRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        searchAdapter = new AddressListAdapter(addressItems, addressHandleListener, (table, isAdd) -> {
            if(isAdd)
                favoriteAdapter.addItem(table);
            else
                favoriteAdapter.deleteItem(table);
            favoriteAdapter.notifyDataSetChanged();
        });
        binding.activitySearchAddressRV.setAdapter(searchAdapter);

        binding.activitySearchAddressFavorite.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DataBaseStorage.favoriteTables = DataBaseStorage.dataBaseHelper.selectList("SELECT * FROM "+DataBaseStorage.Table.TABLE_FAVORITE, FavoriteTable.class);
        favoriteAdapter = new FavoriteAddressListAdapter((ArrayList<FavoriteTable>) DataBaseStorage.favoriteTables, addressHandleListener, (table, isAdd) -> searchAdapter.notifyDataSetChanged());
        binding.activitySearchAddressFavorite.setAdapter(favoriteAdapter);

        binding.setActivity(this);
        ViewAttributeManager.getInstance().setDoneOption(this, binding.activitySearchAddressETKeyword, (v, actionId, event) -> {
            onSearch();
            return false;
        });

        binding.setAddress(DataStorage.NowNewAddress);
    }

    /*
     * 검색
     */
    public void onSearch() {
        Net.getInstance().getAddressFactoryIm().searchAddress(AddressModel.getParameter(binding.activitySearchAddressETKeyword.getText() + "")).enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                if (response.isSuccessful()) {
                    logger.log(HLogger.LogType.INFO, "onSearch(View view) - onResponse(Call<AddressModel> call, Response<AddressModel> response)", "isSuccess");
                    addressItems = response.body().getPredictions();
                    searchAdapter.setItems(response.body().getPredictions());
                    runOnUiThread(()->searchAdapter.notifyDataSetChanged());
                    HandlerManager.getInstance().post(()->searchAdapter.notifyDataSetChanged());
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

    /*
     * 검색 결과 설정
     */
    private void activityEnd(int code) {
        setResult(code, intent);
        finish();
    }
}
