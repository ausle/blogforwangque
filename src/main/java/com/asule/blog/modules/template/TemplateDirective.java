package com.asule.blog.modules.template;

import com.asule.blog.base.lang.Consts;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.Map;

public abstract  class TemplateDirective implements TemplateDirectiveModel {

    protected static String RESULT = "result";
    protected static String RESULTS = "results";

    public abstract String getName();


    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                        TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        try {
            execute(new DirectiveHandler(environment,map,templateModels,templateDirectiveBody));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected abstract void execute(DirectiveHandler directiveHandler)throws Exception;

    public PageRequest wrapPageable(DirectiveHandler handler) throws Exception {
        return wrapPageable(handler, Sort.by(Sort.Direction.DESC, "id"));
    }

    /**
     * 包装分页对象
     */
    public PageRequest wrapPageable(DirectiveHandler handler, Sort sort) throws Exception {
        int pageNo = handler.getInteger("pageNo", 1);
        int size = handler.getInteger("size", Consts.PAGE_DEFAULT_SIZE);
        if (null == sort) {
            return PageRequest.of(pageNo - 1, size);
        } else {
            return PageRequest.of(pageNo - 1, size, sort);
        }
    }
}
