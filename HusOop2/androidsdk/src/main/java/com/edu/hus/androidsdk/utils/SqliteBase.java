package com.edu.hus.androidsdk.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.edu.hus.androidsdk.HusClass;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 * Created by doquanghuy on 6/8/17.
 */

public abstract class SqliteBase<V extends HusClass> extends SQLiteOpenHelper {
    private Class<V> valueClass = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    private enum TableDefine
    {
        Key,JsonValue
    }

    private final String TAG ="SqliteBase";
    private final String TABLE_NAME = this.getClass().getSimpleName();
    private JsonUtils jsonUtils;


     public SqliteBase(Context context,
                      String DATABASE_NAME,
                      int DATABASE_VERSION)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        jsonUtils = new JsonUtils();
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery ="CREATE TABLE "+ TABLE_NAME + "(";
        for (TableDefine tableDefine : TableDefine.values())
            createTableQuery += tableDefine.name()+" "+ "TEXT"+(tableDefine == TableDefine.Key ? " PRIMARY KEY," : ",");

        createTableQuery = createTableQuery.substring(0,createTableQuery.length()-1)+")";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }


    protected ArrayList<V> queryItems(String rawQuery, String[] selectionArgs)
    {
        Cursor c = queryDb(rawQuery, null);
        ArrayList<V> items = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                V v = jsonUtils.fromJson(getStringValue(c, TableDefine.JsonValue.name()), valueClass);
                items.add(v);
            } while (c.moveToNext());
        }
        return items;
    }

    protected Cursor queryDb(String rawQuery,@Nullable String[] selectionArgs)
    {
        return this.getReadableDatabase().rawQuery(rawQuery, null);
    }

    public HashMap<String,V> clearAndSyncDb(Collection<V> items)
    {
        clearDb();
        for (V v : items)
            insert(v);
        return getItemHashMap();
    }

    public ArrayList<V> getItems()
    {
        Cursor c = queryDb("SELECT  * FROM " + TABLE_NAME, null);
        ArrayList<V> items = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                V v = jsonUtils.fromJson(getStringValue(c, TableDefine.JsonValue.name()), valueClass);
                items.add(v);
            } while (c.moveToNext());
        }
        return items;
    }

    public HashMap<String,V> getItemHashMap()
    {
        Cursor c = queryDb("SELECT  * FROM " + TABLE_NAME, null);
        HashMap<String,V> items = new HashMap<>();
        if (c.moveToFirst()) {
            do {
                String k = getStringValue(c, TableDefine.JsonValue.name());
                V v = jsonUtils.fromJson(getStringValue(c, TableDefine.JsonValue.name()), valueClass);
                items.put(k,v);
            } while (c.moveToNext());
        }
        return items;
    }


    public V get(String key)
    {
        Cursor c = queryDb("SELECT  * FROM " + TABLE_NAME +" WHERE "+ genConditonByField(TableDefine.Key.name(),key),null );
        return getObject(c);
    }




    public boolean insert(V v)
    {
        try {
            ContentValues contentValues = objectToContentValues(v);
            long code = this.getWritableDatabase().insert(TABLE_NAME, null, contentValues);
            return code > 0;
        }catch (Exception e)
        { Log.e(TAG,"insert::",e); }
        return false;
    }


    private ContentValues objectToContentValues(V v)
    {
        if (v.getIdDb() != null)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableDefine.Key.name(),v.getIdDb());
            contentValues.put(TableDefine.JsonValue.name(),jsonUtils.toJson(v));
            return contentValues;
        }else return null;


    }

    public boolean update(V v)
    {
        try {
            if(v != null)
            {
                String id = v.getIdDb();
                ContentValues contentValues = new ContentValues();
                contentValues.put(TableDefine.Key.name(), id);
                contentValues.put(TableDefine.JsonValue.name(),jsonUtils.toJson(v));
                SQLiteDatabase db = this.getWritableDatabase();
                int code = db.update(TABLE_NAME,contentValues, TableDefine.Key.name() + " =?",new String[]{id});
                return code == 1;
            }
        }catch (Exception e)
        {
        }
        return false;
    }

    public boolean insertOrUpdate(V v)
    {
        try {
            String key = v.getIdDb();
            if(isExists(key))
                return update(v);
            else return insert(v);
        }catch (Exception e)
        {
            Log.e(TAG,"updateObject::",e);
        }
        return false;
    }

    public boolean delete(V v)
    {
        try {
            String key = v.getIdDb();
            int code = this.getWritableDatabase().delete(TABLE_NAME, genConditonByField(TableDefine.Key.name(),key),
                    null);
            return code > 0;
        }catch (Exception e) { Log.e(TAG,"delete::",e); }
        return false;
    }


    public boolean isExists(String id)
    {
        Cursor c = queryDb("SELECT  * FROM " + TABLE_NAME +" WHERE "+ genConditonByField(TableDefine.Key.name(),id),null );
        boolean exists = (c.getCount() > 0);
        c.close();
        return exists;
    }


    protected String genConditonByField(String fieldName, String value)
    {
        return  fieldName + " = "+"\'" +value+"\'";
    }



    protected String getStringValue(Cursor c, String column)
    {
        String value = c.getString(c.getColumnIndex(column));
        return  value != null ? value : "";
    }



    private V getObject(Cursor c)
    {
        if (c != null)
        {
            if(c.getCount()==1)
            {
                c.moveToFirst();
                return jsonUtils.fromJson(getStringValue(c, TableDefine.JsonValue.name()), valueClass);
            }else return null;
        }else return null;
    }


    public void clearDb()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}
