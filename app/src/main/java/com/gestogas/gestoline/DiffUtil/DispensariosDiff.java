package com.gestogas.gestoline.DiffUtil;

import androidx.recyclerview.widget.DiffUtil;

import com.gestogas.gestoline.data.dataDispensarios;

import java.util.List;

public class DispensariosDiff extends DiffUtil.Callback{

    private final List<dataDispensarios> oldList;
    private final List<dataDispensarios> newList;

    public DispensariosDiff(List<dataDispensarios> oldList, List<dataDispensarios> newList) {
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
        dataDispensarios oldItem = oldList.get(oldItemPosition);
        dataDispensarios newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}

