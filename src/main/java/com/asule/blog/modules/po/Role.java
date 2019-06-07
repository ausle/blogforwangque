package com.asule.blog.modules.po;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "shiro_role")
public class Role implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, updatable = false, length = 32)
    private String name;

    @Column(length = 140)
    private String description;

    private int status;

    @Transient
    private List<Permission> permissions;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
