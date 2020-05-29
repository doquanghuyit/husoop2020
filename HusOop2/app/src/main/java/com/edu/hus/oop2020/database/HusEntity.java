package com.edu.hus.oop2020.database;

import com.edu.hus.androidsdk.HusClass;

public class HusEntity extends HusClass {
    private String idTest;
    private String name;

    public HusEntity() {
    }

    public HusEntity(String idTest) {
        this.idTest = idTest;
    }

    public HusEntity(String idTest, String name) {
        this.idTest = idTest;
        this.name = name;
    }

    public String getIdTest() {
        return idTest;
    }

    public void setIdTest(String idTest) {
        this.idTest = idTest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getIdDb() {
        return idTest;
    }
}
