package com.graction.developer.zoocaster.Adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.Adapter.Listener.ItemOnClickListener;
import com.graction.developer.zoocaster.Model.Address.AddressModel;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.UIFactory;
import com.graction.developer.zoocaster.databinding.ItemSearchAddressBinding;

import java.util.ArrayList;

import static com.graction.developer.zoocaster.UI.UIFactory.TYPE_BASIC;

/**
 * Created by Graction06 on 2018-01-16.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    private ItemOnClickListener itemOnClickListener;
    private ArrayList<AddressModel.Prediction> items;

    public AddressListAdapter(ArrayList<AddressModel.Prediction> items, ItemOnClickListener itemOnClickListener) {
        this.items = items;
        this.itemOnClickListener = itemOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_search_address, parent, false));
//        return new ViewHolder(DataBindingUtil.setContentView((Activity) parent.getContext(), R.layout.item_search_address));
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
        private ItemSearchAddressBinding binding;

        public ViewHolder(ItemSearchAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            UIFactory.setViewWithRateParams(binding.itemSearchAddressRoot, TYPE_BASIC);
        }

        public void onBind(AddressModel.Prediction item, boolean isLast) {
            binding.setItem(item);
            binding.setViewHolder(this);
            binding.setIsLast(isLast);
            binding.executePendingBindings();
        }

        public void onClick(AddressModel.Prediction item) {
            itemOnClickListener.onClick(item);
        }
    }
}
