package com.asule.blog.modules.po;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "shiro_role_permission")
public class RolePermission implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role_id")
    private long roleId;


    @Column(name = "permission_id")
    private long permissionId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }
}
