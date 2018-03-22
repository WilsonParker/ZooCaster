package com.graction.developer.zoocaster.Listener;

import com.graction.developer.zoocaster.Model.DataBase.FavoriteTable;

/**
 * Created by JeongTaehyun on 2018-03-14.
 */

/*
 * 즐겨찾기 버튼 클릭 시 실행
 */

public interface FavoriteItemOnClickListener {
    void favoriteOnClick(FavoriteTable table, boolean add);
}
