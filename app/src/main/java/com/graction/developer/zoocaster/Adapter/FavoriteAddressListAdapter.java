package com.graction.developer.zoocaster.Adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.Listener.FavoriteItemOnClickListener;
import com.graction.developer.zoocaster.Method.CommonMethodManager;
import com.graction.developer.zoocaster.Model.DataBase.FavoriteTable;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.UIFactory;
import com.graction.developer.zoocaster.Util.Parser.AddressParser;
import com.graction.developer.zoocaster.databinding.ItemSearchAddressBinding;

import java.util.ArrayList;

import static com.graction.developer.zoocaster.UI.UIFactory.TYPE_BASIC;

/**
 * Created by JeongTaehyun
 */

/*
 * 즐겨찾기 List Adapter
 */

public class FavoriteAddressListAdapter extends RecyclerView.Adapter<FavoriteAddressListAdapter.ViewHolder> {
    private FavoriteItemOnClickListener favoriteItemOnClickListener;
    private AddressHandleListener addressHandleListener;
    private ArrayList<FavoriteTable> items;

    public FavoriteAddressListAdapter(ArrayList<FavoriteTable> items, AddressHandleListener addressHandleListener) {
        this.items = items;
        this.addressHandleListener = addressHandleListener;
    }

    public FavoriteAddressListAdapter(ArrayList<FavoriteTable> items, AddressHandleListener addressHandleListener, FavoriteItemOnClickListener favoriteItemOnClickListener) {
        this(items, addressHandleListener);
        this.favoriteItemOnClickListener = favoriteItemOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_search_address, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(items.get(position), position + 1 == getItemCount());
    }

    public ArrayList<FavoriteTable> getItems() {
        return items;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    /*
     * 아이템 삭제
     */
    public boolean deleteItem(FavoriteTable item){
        for(FavoriteTable table  :items){
            if(table.getFavorite_origin_address().equals(item.getFavorite_origin_address())){
                items.remove(table);
                return true;
            }
        }
        return false;
    }

    /*
     * 아이템 추가
     */
    public void addItem(FavoriteTable item){
        items.add(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchAddressBinding binding;

        public ViewHolder(ItemSearchAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            UIFactory.setViewWithRateParams(binding.itemSearchAddressRoot, TYPE_BASIC);
        }

        /*
         * 각각의 데이터 별로 View 설정
         */
        public void onBind(FavoriteTable table, boolean isLast) {
            String newAddress = AddressParser.getInstance().parseAddress(table.getFavorite_origin_address());
            binding.itemSearchAddressIVStar.setSelected(true);
            binding.itemSearchAddressTVAddress.setOnClickListener((view) -> addressHandleListener.setAddress(newAddress, table.getFavorite_origin_address()));
            binding.itemSearchAddressIVStar.setOnClickListener((view) -> {
                // 즐겨찾기에 존재 할 경우
                if (binding.itemSearchAddressIVStar.isSelected()) {
                    // 삭제
                    CommonMethodManager.getInstance().favoriteRemove(table.getFavorite_origin_address());
                    items.remove(table);
                    if (favoriteItemOnClickListener != null)
                        favoriteItemOnClickListener.favoriteOnClick(table, false);
                    notifyDataSetChanged();
                }
            });
            binding.setAddress(newAddress);
            binding.setIsLast(isLast);
            binding.executePendingBindings();
        }
    }
}
