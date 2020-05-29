package com.edu.hus.oop2020.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.edu.hus.androidsdk.widget.IRecycleView;
import com.edu.hus.oop2020.R;
import com.edu.hus.oop2020.database.HusEntity;

public class HusListView extends IRecycleView<HusEntity> {

    public HusListView(@NonNull Context context) {
        super(context);
    }

    public HusListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HusListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected int getItemLayoutId() {
        return R.layout.vh_hus_item;
    }

    @Override
    protected void onBindItemView(View itemView, HusEntity item) {
        TextView textView = itemView.findViewById(R.id.textview_name);
        textView.setText(item.getName());
    }

}
