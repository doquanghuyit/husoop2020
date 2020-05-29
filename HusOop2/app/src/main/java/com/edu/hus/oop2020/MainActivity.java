package com.edu.hus.oop2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.edu.hus.oop2020.database.HusDb;
import com.edu.hus.oop2020.database.HusEntity;
import com.edu.hus.oop2020.listview.HusListView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private HusDb husDb;
    private HusListView husListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        husListView = findViewById(R.id.hus_list);

        husDb = new HusDb(this);
        //Check Exists && insert item
        if (!husDb.isExists("1"))
            husDb.insert(new HusEntity("1","hello 1"));
        if (!husDb.isExists("2"))
            husDb.insert(new HusEntity("2","hello 2"));
        if (!husDb.isExists("3"))
            husDb.insert(new HusEntity("3","hello 3"));

        //Get List Items
        for (HusEntity husEntity : husDb.getItems())
        {
            Log.e(TAG,"husEntity ID::"+husEntity.getIdDb());
            Log.e(TAG,"husEntity NAME::"+husEntity.getName());
        }

        //Delete Item
        husDb.delete(new HusEntity("1"));


        Log.e(TAG,"husEntity items after Delete item with id = 1");
        if (husDb.isExists("1"))
            Log.e(TAG,"husEntity ID == 1 is exists");
        else Log.e(TAG,"husEntity ID == 1 is not exists");


        //Update item
        Log.e(TAG,"husEntity items after update item with id = 2");
        husDb.update(new HusEntity("3","Helle Updated 2"));

        Log.e(TAG,"husEntity items after update item with id = 2");
        HusEntity husEntity = husDb.get("2");
        Log.e(TAG,"husEntity ID::"+husEntity.getIdDb());
        Log.e(TAG,"husEntity NAME::"+husEntity.getName());


        //Show item into main view
        husListView.setItems(husDb.getItems());

    }
}
