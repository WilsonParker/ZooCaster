package com.graction.developer.zoocaster.Method;

import android.util.Log;
import android.view.View;

import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Model.DataBase.FavoriteTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Graction06 on 2018-03-14.
 */

public class CommonMethodManager {
    private static final CommonMethodManager instance = new CommonMethodManager();

    private CommonMethodManager() {
    }

    public static CommonMethodManager getInstance() {
        return instance;
    }

    public void favoriteRemove(String address) {
        //            Map<String, Object> whereClause = new HashMap<>();
//            whereClause.put(DataBaseStorage.Column.COLUMN_FAVORITE_ORIGIN_ADDRESS, item.getDescription());
//            DataBaseStorage.dataBaseHelper.insertIFNull(DataBaseStorage.Table.TABLE_FAVORITE, whereClause, favoriteTable);
//            binding.itemSearchAddressIVStar.setSelected(!DataBaseStorage.dataBaseHelper.selectIsNull(DataBaseStorage.Table.TABLE_FAVORITE, whereClause));

        DataBaseStorage.dataBaseHelper.delete(DataBaseStorage.Table.TABLE_FAVORITE
                , DataBaseStorage.Column.COLUMN_FAVORITE_ORIGIN_ADDRESS + "=?"
                , new String[]{address});
    }

    public void favoriteAdd(FavoriteTable favoriteTable) {
//        Map<String, Object> whereClause = new HashMap<>();
//                    whereClause.put(DataBaseStorage.Column.COLUMN_FAVORITE_ORIGIN_ADDRESS, address);
//                    DataBaseStorage.dataBaseHelper.insertIFNull(DataBaseStorage.Table.TABLE_FAVORITE, whereClause,favoriteTable);
        DataBaseStorage.dataBaseHelper.insert(DataBaseStorage.Table.TABLE_FAVORITE, favoriteTable);
    }
}
