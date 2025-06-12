package com.gestogas.gestoline.DiffUtil;

import androidx.recyclerview.widget.DiffUtil;

import com.gestogas.gestoline.data.dataExtintores;

import java.util.List;

public class ExtintoresDiff extends DiffUtil.Callback{

    private final List<dataExtintores> oldList;
    private final List<dataExtintores> newList;

    public ExtintoresDiff(List<dataExtintores> oldList, List<dataExtintores> newList) {
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
        dataExtintores oldItem = oldList.get(oldItemPosition);
        dataExtintores newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}

