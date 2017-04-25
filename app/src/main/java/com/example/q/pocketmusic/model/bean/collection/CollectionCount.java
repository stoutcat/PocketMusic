package com.example.q.pocketmusic.model.bean.collection;

/**
 * Created by 鹏君 on 2017/4/25.
 */

public class CollectionCount {
    private int num;
    private String name;

    public CollectionCount(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
