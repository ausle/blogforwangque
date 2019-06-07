package com.asule.blog.modules.template.directive;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.utils.BeanMapUtils;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.template.DirectiveHandler;
import com.asule.blog.modules.template.TemplateDirective;
import com.asule.blog.modules.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


@Component
public class CarouselDirective extends TemplateDirective{


    @Autowired
    private PostService postService;

    @Override
    public String getName() {
        return "carousels";
    }

    @Override
    protected void execute(DirectiveHandler directiveHandler) throws Exception {
        Pageable pageable =
                wrapPageable(directiveHandler, Sort.by(Sort.Direction.DESC,new String[]{"weight"}));
        Page<PostVO> carousel = postService.pagingCarousel(pageable);
        directiveHandler.put(getName(),carousel).render();
    }

    public PageRequest wrapPageable(DirectiveHandler handler, Sort sort) throws Exception {
        int pageNo = handler.getInteger("pageNo", 1);
        int size = handler.getInteger("size", Consts.PAGE_CAROUSEL_SIZE);
        if (null == sort) {
            return PageRequest.of(pageNo - 1, size);
        } else {
            return PageRequest.of(pageNo - 1, size, sort);
        }
    }
}
