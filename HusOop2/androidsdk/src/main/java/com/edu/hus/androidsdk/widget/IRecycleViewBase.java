package com.edu.hus.androidsdk.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class IRecycleViewBase extends RecyclerView {
    public class HusHolder extends ViewHolder
    {
        public HusHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class RecycleViewAdapter extends Adapter<ViewHolder>
    {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            try {
                return new HusHolder(LayoutInflater.from(viewGroup.getContext()).inflate(IRecycleViewBase.this.getItemLayoutId(), viewGroup, false));
            }catch (Exception e)
            {
                Log.e(this.getClass().getSimpleName(),"onCreateViewHolder::",e);
                return null;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolderItem, int i) {
            IRecycleViewBase.this.onBindViewHolder(viewHolderItem,i);
        }

        @Override
        public int getItemCount() {
            return IRecycleViewBase.this.getItemCount();
        }

        @Override
        public int getItemViewType(int position) {
            return 1;
        }
    }

    private RecycleViewAdapter recycleViewAdapter;

    public IRecycleViewBase(@NonNull Context context) {
        super(context);
        init();
    }

    public IRecycleViewBase(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IRecycleViewBase(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        recycleViewAdapter = new RecycleViewAdapter();
        LayoutManager layoutManager = layoutManagerRecycleView();
        if (layoutManager != null)
            this.setLayoutManager(layoutManagerRecycleView());
        this.setAdapter(recycleViewAdapter);
    }



    protected abstract int getItemCount();
    protected abstract int getItemLayoutId();
    protected abstract void onBindViewHolder(@NonNull ViewHolder viewHolder, int position);


    public void invalideItems()
    {
        this.post(new Runnable() {
            @Override
            public void run() {
                recycleViewAdapter.notifyDataSetChanged();
            }
        });
    }

    protected LayoutManager layoutManagerRecycleView() {
        return new LinearLayoutManager(getContext());
    }
}
