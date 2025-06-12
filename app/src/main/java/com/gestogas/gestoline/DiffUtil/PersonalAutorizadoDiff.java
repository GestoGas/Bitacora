package com.gestogas.gestoline.DiffUtil;

import androidx.recyclerview.widget.DiffUtil;

import com.gestogas.gestoline.data.dataPersonal;
import com.gestogas.gestoline.data.dataRecepcionBitacora;

import java.util.List;

public class PersonalAutorizadoDiff extends DiffUtil.Callback{

    private final List<dataPersonal> oldList;
    private final List<dataPersonal> newList;

    public PersonalAutorizadoDiff(List<dataPersonal> oldList, List<dataPersonal> newList) {
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
        return oldList.get(oldItemPosition).getIdfirma() == newList.get(newItemPosition).getIdfirma();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        dataPersonal oldItem = oldList.get(oldItemPosition);
        dataPersonal newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}
