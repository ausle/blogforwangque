package com.asule.blog.shiro.tags;

import freemarker.template.SimpleHash;
import org.apache.shiro.web.tags.HasPermissionTag;

public class ShiroTags extends SimpleHash{

    public ShiroTags() {
        put("hasAdminPermission",new HasAdminPermissionTag());
    }
}
