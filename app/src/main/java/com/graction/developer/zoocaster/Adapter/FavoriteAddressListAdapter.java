package com.graction.developer.zoocaster.Adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.Adapter.Listener.ItemOnClickListener;
import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.Model.Address.AddressModel;
import com.graction.developer.zoocaster.Model.DataBase.FavoriteTable;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.UIFactory;
import com.graction.developer.zoocaster.Util.Date.DateManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.Parser.AddressParser;
import com.graction.developer.zoocaster.databinding.ItemSearchAddressBinding;
import com.graction.developer.zoocaster.databinding.ItemSearchFavoriteAddressBinding;

import java.util.ArrayList;

import static com.graction.developer.zoocaster.UI.UIFactory.TYPE_BASIC;

/**
 * Created by Graction06 on 2018-01-16.
 */

public class FavoriteAddressListAdapter extends RecyclerView.Adapter<FavoriteAddressListAdapter.ViewHolder> {
    private AddressHandleListener addressHandleListener;
    private ArrayList<FavoriteTable> items;

    public FavoriteAddressListAdapter(ArrayList<FavoriteTable> items, AddressHandleListener addressHandleListener) {
        this.items = items;
        this.addressHandleListener = addressHandleListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_search_favorite_address, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(items.get(position), position+1 == getItemCount());
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchFavoriteAddressBinding binding;

        public ViewHolder(ItemSearchFavoriteAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            UIFactory.setViewWithRateParams(binding.itemSearchAddressRoot, TYPE_BASIC);
        }

        public void onBind(FavoriteTable table, boolean isLast) {
            String newAddress = AddressParser.getInstance().parseAddress(table.getFavorite_origin_address());
            binding.setAddress(newAddress);
            binding.setIsLast(isLast);
            binding.setViewHolder(this);
            binding.executePendingBindings();
        }

        public void onClick(String address) {
            new HLogger(getClass()).log(HLogger.LogType.INFO, "onClick(String)","onClick");
            addressHandleListener.setAddress(address);
        }
    }
}
