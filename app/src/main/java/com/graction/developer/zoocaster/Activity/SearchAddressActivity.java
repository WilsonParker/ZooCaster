package com.graction.developer.zoocaster.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.graction.developer.zoocaster.Adapter.AddressListAdapter;
import com.graction.developer.zoocaster.Adapter.FavoriteAddressListAdapter;
import com.graction.developer.zoocaster.Adapter.Listener.ItemOnClickListener;
import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.DataBase.DataBaseHelper;
import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.Model.Address.AddressModel;
import com.graction.developer.zoocaster.Model.DataBase.FavoriteTable;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.ViewAttributeManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.databinding.ActivitySearchAddress2Binding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.graction.developer.zoocaster.DataBase.DataBaseStorage.DATABASE_NAME;

public class SearchAddressActivity extends BaseActivity {
//    private ActivitySearchAddressBinding binding;
    private ActivitySearchAddress2Binding binding;
    private Intent intent = new Intent();
    private AddressListAdapter searchAdapter;
    private FavoriteAddressListAdapter favoriteAdapter;
    private AddressHandleListener addressHandleListener = address -> {
        intent.putExtra(DataStorage.Key.KEY_ADDRESS, address);
        activityEnd(DataStorage.Request.SEARCH_ADDRESS_OK);
    };

    @Override
    protected void init() {
        DataBaseHelper.createHelper(getBaseContext());
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_address);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_address2);
        binding.activitySearchAddressRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.activitySearchAddressFavorite.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<FavoriteTable> tables = DataBaseStorage.dataBaseHelper.selectList("SELECT * FROM "+DataBaseStorage.Table.TABLE_FAVORITE, FavoriteTable.class);
        logger.log(HLogger.LogType.INFO, "protected void init()", "table size : "+tables.size());
        DataBaseStorage.dataBaseHelper.select("SELECT count(*) FROM "+DataBaseStorage.Table.TABLE_FAVORITE, (cursor -> {
            cursor.moveToFirst();
            logger.log(HLogger.LogType.INFO, "protected void init()", "count : "+cursor.getInt(0));
        }));
        favoriteAdapter = new FavoriteAddressListAdapter((ArrayList<FavoriteTable>) tables , addressHandleListener);
        binding.activitySearchAddressFavorite.setAdapter(favoriteAdapter);
        favoriteAdapter.notifyDataSetChanged();

        binding.setActivity(this);
        ViewAttributeManager.getInstance().setDoneOption(this, binding.activitySearchAddressETKeyword, (v, actionId, event) -> {
            onSearch();
            return false;
        });

        binding.setAddress(DataStorage.NowAddress);
    }

    public void onSearch() {
        logger.log(HLogger.LogType.INFO, "onSearch(View view)", "onSearch");
        Net.getInstance().getAddressFactoryIm().searchAddress(AddressModel.getParameter(binding.activitySearchAddressETKeyword.getText() + "")).enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                if (response.isSuccessful()) {
                    logger.log(HLogger.LogType.INFO, "onSearch(View view) - onResponse(Call<AddressModel> call, Response<AddressModel> response)", "isSuccess");
                    logger.log(HLogger.LogType.INFO, "onSearch(View view) - onResponse(Call<AddressModel> call, Response<AddressModel> response)", "" + response.body());
                    searchAdapter = new AddressListAdapter(response.body().getPredictions(), addressHandleListener);
                    binding.activitySearchAddressRV.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
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
