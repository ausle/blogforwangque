package com.asule.blog.modules.po;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 文章的分类
 */
@Entity
@Table(name = "asule_channel")
public class Channel implements Serializable {
    private static final long serialVersionUID = 2436696690653745208L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(length = 32)
    private String name;

    /**
     * 唯一关键字
     */
    @Column(name = "key_", unique = true, length = 32)
    private String key;

    /**
     * 预览图
     */
    @Column(length = 128)
    private String thumbnail;

    /**
     * 状态 1显示，0隐藏
     */
    @Column(length = 5)
    private int status;

    /**
     * 该类别下文章的数量
     */
    @Column(length = 32)
    private int amount;


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * 排序值
     */
    private int weight;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
