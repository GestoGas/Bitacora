package com.gestogas.gestoline.DiffUtil;

import androidx.recyclerview.widget.DiffUtil;
import com.gestogas.gestoline.data.dataRecepcionBitacora;

import java.util.List;

public class RecepcionBitacoraDiff extends DiffUtil.Callback{

    private final List<dataRecepcionBitacora> oldList;
    private final List<dataRecepcionBitacora> newList;

    public RecepcionBitacoraDiff(List<dataRecepcionBitacora> oldList, List<dataRecepcionBitacora> newList) {
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
        dataRecepcionBitacora oldItem = oldList.get(oldItemPosition);
        dataRecepcionBitacora newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}
