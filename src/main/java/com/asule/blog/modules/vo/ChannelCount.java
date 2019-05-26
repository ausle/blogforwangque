package com.asule.blog.modules.vo;

import java.io.Serializable;


/**
 * freemarker显示的对象，需要定义为public，否则读取不到属性的值。
 * 即使有getter方法也没用。
 */
public class ChannelCount implements Serializable{
    private String name;
    private int count;
    private int id;

    public ChannelCount(String name, int count, int id) {
        this.name = name;
        this.count = count;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
