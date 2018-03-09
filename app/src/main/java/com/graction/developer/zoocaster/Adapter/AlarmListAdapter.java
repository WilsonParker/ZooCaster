package com.graction.developer.zoocaster.Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.Activity.ModifyAlarmActivity;
import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Model.Item.AlarmItem;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.UIFactory;
import com.graction.developer.zoocaster.Util.System.AlarmManager;
import com.graction.developer.zoocaster.databinding.ItemAlarmBinding;

import java.util.ArrayList;

import static com.graction.developer.zoocaster.UI.UIFactory.TYPE_BASIC;

/**
 * Created by Graction06 on 2018-01-16.
 */

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {
    private ArrayList<AlarmItem> items;

    public AlarmListAdapter(ArrayList<AlarmItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, null));
//        return new ViewHolder(DataBindingUtil.setContentView((Activity) parent.getContext(), R.layout.item_alarm));
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_alarm, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemAlarmBinding binding;
        private int index;

        public ViewHolder(ItemAlarmBinding binding) {
            super(binding.getRoot());
//            binding = DataBindingUtil.bind(itemView);
            this.binding = binding;
            UIFactory.setViewWithRateParams(binding.itemAlarmRoot, TYPE_BASIC);
        }

        public void onBind(AlarmItem item, int index){
            this.index = index;
            binding.setItem(item);
            binding.setViewHolder(this);
            binding.executePendingBindings();
            if(!item.getIsSpeaker())
                binding.itemAlarmSBVolume.setOnTouchListener((v, e)->true);
        }

        public void deleteItem(AlarmItem item){
            AlarmManager.getInstance().cancelAlarm(item);
            String[] whereArgs = {item.getIndex() + ""};
            DataBaseStorage.dataBaseHelper.delete(DataBaseStorage.Table.TABLE_ALARM, DataBaseStorage.Column.COLUMN_ALARM_INDEX + "=?", whereArgs);
            items.remove(item);
            notifyDataSetChanged();
        }

        public void onItemClick(View view, AlarmItem item){
            Context context = view.getContext();
            Intent intent = new Intent(context, ModifyAlarmActivity.class);
            intent.putExtra(DataStorage.Key.KEY_ALARM_ITEM,item);
            intent.putExtra(DataStorage.Key.KEY_ALARM_INDEX,index);
            context.startActivity(intent);
        }
    }
}
