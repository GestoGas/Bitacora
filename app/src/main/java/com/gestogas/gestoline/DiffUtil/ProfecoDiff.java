package com.gestogas.gestoline.DiffUtil;

import androidx.recyclerview.widget.DiffUtil;


import com.gestogas.gestoline.data.dataProfeco;

import java.util.List;
import java.util.Objects;

public class ProfecoDiff extends DiffUtil.Callback{

    private final List<dataProfeco> oldList;
    private final List<dataProfeco> newList;

    public ProfecoDiff(List<dataProfeco> oldList, List<dataProfeco> newList) {
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
        return Objects.equals(oldList.get(oldItemPosition).getId(), newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        dataProfeco oldItem = oldList.get(oldItemPosition);
        dataProfeco newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}