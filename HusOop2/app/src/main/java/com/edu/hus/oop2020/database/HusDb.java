package com.edu.hus.oop2020.database;

import android.content.Context;

import com.edu.hus.androidsdk.utils.SqliteBase;

public class HusDb extends SqliteBase<HusEntity> {
    private final static String DATABASE_NAME = "HusDb";
    private final static int DATABASE_VERSION = 1;

    public HusDb(Context context) {
        super(context, DATABASE_NAME, DATABASE_VERSION);
    }
}
