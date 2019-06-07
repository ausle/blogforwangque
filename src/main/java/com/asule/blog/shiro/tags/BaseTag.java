package com.asule.blog.shiro.tags;

import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.Map;

public abstract class BaseTag implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env,
                        Map params,
                        TemplateModel[] templateModels, TemplateDirectiveBody body) throws TemplateException, IOException {
        verifyParameters(params);
        render(env, params, body);
    }


    public abstract void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException;

    protected String getParam(Map params, String name) {
        Object value = params.get(name);

        if (value instanceof SimpleScalar) {
            return ((SimpleScalar) value).getAsString();
        }

        return null;
    }

    protected void verifyParameters(Map params) throws TemplateModelException {
        String permission = getName(params);
        if (permission == null || permission.length() == 0) {
            throw new TemplateModelException("The 'name' tag attribute must be set.");
        }
    }

    protected String getName(Map params) {
        return getParam(params, "name");
    }

    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }

}
