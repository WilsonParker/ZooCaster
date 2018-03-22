package com.graction.developer.zoocaster.Adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.Adapter.Listener.ItemOnClickListener;
import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.Listener.FavoriteItemOnClickListener;
import com.graction.developer.zoocaster.Method.CommonMethodManager;
import com.graction.developer.zoocaster.Model.Address.AddressModel;
import com.graction.developer.zoocaster.Model.DataBase.FavoriteTable;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.UIFactory;
import com.graction.developer.zoocaster.Util.Date.DateManager;
import com.graction.developer.zoocaster.Util.Parser.AddressParser;
import com.graction.developer.zoocaster.databinding.ItemSearchAddressBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.graction.developer.zoocaster.UI.UIFactory.TYPE_BASIC;

/**
 * Created by JeongTaehyun
 */

/*
 * 주소 검색 List Adapter
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    private FavoriteItemOnClickListener favoriteItemOnClickListener;
    private AddressHandleListener addressHandleListener;                // 주소 선택 시 실행하는 Listener
    private ArrayList<AddressModel.Prediction> items;

    public AddressListAdapter(ArrayList<AddressModel.Prediction> items, AddressHandleListener addressHandleListener) {
        this.items = items;
        this.addressHandleListener = addressHandleListener;
    }

    public AddressListAdapter(ArrayList<AddressModel.Prediction> items, AddressHandleListener addressHandleListener, FavoriteItemOnClickListener favoriteItemOnClickListener) {
        this(items, addressHandleListener);
        this.favoriteItemOnClickListener = favoriteItemOnClickListener;
    }

    /*
     * List 데이터 설정
     */
    public void setItems(ArrayList<AddressModel.Prediction> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_search_address, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(items.get(position), position + 1 == getItemCount());
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchAddressBinding binding;
        private FavoriteTable favoriteTable;

        public ViewHolder(ItemSearchAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            UIFactory.setViewWithRateParams(binding.itemSearchAddressRoot, TYPE_BASIC);
        }

        /*
         * 각각의 데이터 별로 View 설정
         */
        public void onBind(AddressModel.Prediction item, boolean isLast) {
            // 해당 주소를 변환
            String newAddress = AddressParser.getInstance().parseAddress(item.getDescription());
            // DB 에 저장할 즐겨찾기 Table 생성
            favoriteTable = new FavoriteTable(item.getDescription(), newAddress, DateManager.getInstance().getDate(FavoriteTable.TIME_FORMAT));

            String[] args = {item.getDescription()};
            // 해당 주소가 즐겨찾기에 존재 여부 확인
            binding.itemSearchAddressIVStar.setSelected(!DataBaseStorage.dataBaseHelper.selectIsNull(DataBaseStorage.Table.TABLE_FAVORITE, DataBaseStorage.Column.COLUMN_FAVORITE_ORIGIN_ADDRESS + "=?", args));
            binding.itemSearchAddressIVStar.setOnClickListener((view) -> {
                boolean isSelected = binding.itemSearchAddressIVStar.isSelected();
                // 즐겨찾기가 되어있을 경우
                if (isSelected)
                    // 삭제
                    CommonMethodManager.getInstance().favoriteRemove(item.getDescription());
                else
                    // 추가
                    CommonMethodManager.getInstance().favoriteAdd(favoriteTable);
                if (favoriteItemOnClickListener != null)
                    favoriteItemOnClickListener.favoriteOnClick(favoriteTable, !isSelected);
                binding.itemSearchAddressIVStar.setSelected(!isSelected);
            });
            binding.itemSearchAddressTVAddress.setOnClickListener((view) -> addressHandleListener.setAddress(newAddress, item.getDescription()));

            binding.setAddress(newAddress);
            binding.setIsLast(isLast);
            binding.executePendingBindings();
        }
    }
}
