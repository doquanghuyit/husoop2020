package com.edu.hus.androidsdk.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.LinkedList;

public abstract class IRecycleView<E> extends IRecycleViewBase {


    private LinkedList<E> items;


    public IRecycleView(@NonNull Context context) {
        super(context);
        init();
    }

    public IRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        items = new LinkedList<>();
    }


    public void setItems(Collection<E> items)
    {
        this.items.clear();
        this.items.addAll(items);
        invalideItems();
    }

    public Collection<E> getItems()
    {
        return  this.items;
    }

    public void addItem(E item)
    {
        if (items.contains(item))
            items.set(items.indexOf(item),item);
        else items.add(item);
        invalideItems();
    }

    public void clearItems()
    {
        items.clear();
        invalideItems();
    }


    public E getItem(int position)
    {
        return this.items.get(position);
    }





    @Override
    protected int getItemCount() {
        return items != null ? items.size() : 0;
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        onBindItemView(viewHolder.itemView,getItem(position));
    }


    protected abstract void onBindItemView(View itemView, E item);
}
