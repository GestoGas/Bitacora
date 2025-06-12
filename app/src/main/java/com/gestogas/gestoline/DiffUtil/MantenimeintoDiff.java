package com.gestogas.gestoline.DiffUtil;

import androidx.recyclerview.widget.DiffUtil;


import com.gestogas.gestoline.data.dataMantenimiento;

import java.util.List;

public class MantenimeintoDiff extends DiffUtil.Callback{

    private final List<dataMantenimiento> oldList;
    private final List<dataMantenimiento> newList;

    public MantenimeintoDiff(List<dataMantenimiento> oldList, List<dataMantenimiento> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        dataMantenimiento oldItem = oldList.get(oldItemPosition);
        dataMantenimiento newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}
