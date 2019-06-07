package com.asule.blog.jpa;


import com.asule.blog.BlogApplication;
import com.asule.blog.base.lang.Consts;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.template.DirectiveHandler;
import com.asule.blog.modules.vo.PostVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BlogApplication.class})
public class Test1 {



    @Autowired
    private PostService postService;

    @Test
    public void testPaging(){
        Pageable pageable =
                wrapPageable(Sort.by(Sort.Direction.DESC,new String[]{"weight"}));

        Page<PostVO> carousel = postService.pagingCarousel(pageable);

        System.out.println(carousel.getNumber());       //  1   页码，从0开始计算
        System.out.println(carousel.getSize());         //  1
        System.out.println(carousel.getTotalElements());    //5
        System.out.println(carousel.getTotalPages());       //5


        System.out.println(carousel);
    }

    public PageRequest wrapPageable(Sort sort){
        int pageNo = 1;
        int size = 1;
        return PageRequest.of(pageNo, size, sort);//  limit page,pagesize  page从第0页开始
    }


}
