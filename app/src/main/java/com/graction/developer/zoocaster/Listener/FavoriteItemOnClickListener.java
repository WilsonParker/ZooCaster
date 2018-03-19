package com.graction.developer.zoocaster.Listener;

import com.graction.developer.zoocaster.Model.DataBase.FavoriteTable;

/**
 * Created by Graction06 on 2018-03-14.
 */

public interface FavoriteItemOnClickListener {
    void favoriteOnClick(FavoriteTable table, boolean add);
}
