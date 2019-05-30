package com.asule.blog.modules.template;


import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.util.Map;

public class DirectiveHandler {


    private Environment env;
    private Map<String, TemplateModel> parameters;
    private TemplateModel[] loopVars;
    private TemplateDirectiveBody body;
    private Environment.Namespace namespace;


    public DirectiveHandler(Environment env, Map<String, TemplateModel> parameters, TemplateModel[] loopVars,
                            TemplateDirectiveBody body) {
        this.env = env;
        this.loopVars = loopVars;
        this.parameters = parameters;
        this.body = body;
        this.namespace = env.getCurrentNamespace();
    }


    public void render() throws IOException, TemplateException {
        body.render(this.env.getOut());
    }

    public void renderString(String text) throws IOException, TemplateException {
        this.env.getOut().write(text);
    }


    public DirectiveHandler put(String key, Object value) throws TemplateModelException {
        namespace.put(key, wrap(value));
        return this;
    }


    public String getContextPath() {
        String ret = null;
        try {
            ret =  TemplateModelUtils.convertString(getEnvModel("base"));
        } catch (TemplateModelException e) {
        }
        return ret;
    }

    public String getString(String name) throws TemplateModelException {
        return TemplateModelUtils.convertString(getModel(name));
    }

    public Integer getInteger(String name) throws TemplateModelException {
        return TemplateModelUtils.convertInteger(getModel(name));
    }

    public Long getLong(String name) throws TemplateModelException {
        return TemplateModelUtils.convertLong(getModel(name));
    }

    public String getString(String name, String defaultValue) throws Exception {
        String result = getString(name);
        return null == result ? defaultValue : result;
    }

    public Integer getInteger(String name, int defaultValue) throws Exception {
        Integer result = getInteger(name);
        return null == result ? defaultValue : result;
    }

    public Long getLong(String name, long defaultValue) throws Exception {
        Long result = getLong(name);
        return null == result ? defaultValue : result;
    }


    /**
     * 获取局部变量
     * @param name
     * @return
     * @throws TemplateModelException
     */
    public TemplateModel getEnvModel(String name) throws TemplateModelException {
        return env.getVariable(name);
    }


    /**
     *  获取参数
     */
    private TemplateModel getModel(String name) {
        return parameters.get(name);
    }



    /**
     * 把java对象包装为TemplateModel实现类对象
     * @param object
     * @return
     * @throws TemplateModelException
     */
    public TemplateModel wrap(Object object) throws TemplateModelException {
        return env.getObjectWrapper().wrap(object);
    }


    public Map<String, TemplateModel> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, TemplateModel> parameters) {
        this.parameters = parameters;
    }

    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }
}
