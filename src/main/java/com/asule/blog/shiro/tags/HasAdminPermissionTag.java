package com.asule.blog.shiro.tags;

import com.asule.blog.base.lang.Consts;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

public class HasAdminPermissionTag extends BaseTag{

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        String name = getName(params);
        if (isPermitted(name)){
            if (body!=null){
                body.render(env.getOut());
            }
        }
    }


    /**
     * 判断是否有超级权限，也就是是否是admin
     * @param p
     * @return
     */
    protected boolean isPermitted(String p) {
        return getSubject() != null && (getSubject().hasRole(Consts.ROLE_ADMIN) || getSubject().isPermitted(p));
    }
}
