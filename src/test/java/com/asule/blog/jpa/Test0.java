package com.asule.blog.jpa;


import com.asule.blog.BlogApplication;
import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.utils.BeanMapUtils;
import com.asule.blog.modules.po.Channel;
import com.asule.blog.modules.po.Post;
import com.asule.blog.modules.repository.PostRepository;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.service.PostTagService;
import com.asule.blog.modules.vo.PostVO;
import com.asule.blog.po.Person;
import com.asule.blog.repository.TestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BlogApplication.class})
public class Test0 {


    @Autowired
    private TestRepository testRepository;


    @Autowired
    private PostRepository postRepository;


    @Autowired
    private PostService postService;

    /*测试JPA*/
    @Test
    public void test0(){
        for (int i = 0; i <10; i++) {
            Person testEntity = new Person("阿苏勒" + i + "号");
            testRepository.save(testEntity);
        }
    }


    /*分页查询*/
    @Test
    public void test1(){
        int pageNo=0;
        int pageSize=5;
        Pageable pageable=new PageRequest(pageNo,pageSize,Sort.by(Sort.Direction.DESC,"channelId"));
       Page<Post> page=postRepository.findAll(pageable);


        System.out.println(page.getNumber()+"  "+page.getTotalElements()+" "+page.getSize()+" "+page.getTotalPages()
            +" "
        );
    }


    /*带条件的分页查询*/
    @Test
    public void test2(){

        int pageNo=0;
        int pageSize=5;

        Pageable pageable=new PageRequest(pageNo,pageSize,Sort.by(Sort.Direction.DESC,"channelId"));


        /*使用Specification匿名内部类*/
        Specification specification=new Specification<Post>() {

            @Nullable
            @Override
            public Predicate toPredicate(Root<Post> root, /*root代表*/
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

//                root代表要查询的实体类
//                Path path=root.get("id");


                /*criteriaBuilder，用于创建Criteria的工厂。从中获取到查询条件*/
                /*Predicate代表查询条件*/
//                Predicate predicate = criteriaBuilder.conjunction();

                //设置一个查询条件，channelId=3
//                Predicate predicate = criteriaBuilder.equal(root.get("channelId").as(Integer.class), 3);


                //添加多个查询条件
                Predicate predicate = criteriaBuilder.conjunction();

                Predicate channelId = criteriaBuilder.gt(root.get("channelId"), 3);
                Predicate authorId = criteriaBuilder.equal(root.get("authorId"), 2);

                predicate.getExpressions().add(channelId);
                predicate.getExpressions().add(authorId);


                return predicate;
            }
        };

        Page<Post> all = postRepository.findAll(specification, pageable);
        System.out.println(all);
    }



    @Test
    public void test3(){

        int pageNo=1-1;
        int pageSize=5;
        String[] order=new String[]{"weight", "created"};


        Pageable pageable=new PageRequest(pageNo,pageSize,Sort.by(Sort.Direction.DESC,order));

        Set<Integer> ids=new HashSet<>();
        ids.add(1);
        ids.add(2);

//        postService.paging(pageable,ids);
    }


    @Autowired
    private ChannelService channelService;
    @Autowired
    private PostTagService postTagService;

    @Test
    public void test4(){

//        int pageNo=1-1;
//        int pageSize=5;
//
//        Integer channelId = 0;
//        String order = Consts.order.NEWEST;
//        Long tagId =5L;
//
//        List<Channel> channels;
//
//        //为0获取所有类型的文章
//        if (channelId==0){
//            channels= channelService.findAll(Consts.STATUS_CHANNEL_NORMAL);
//        }else{
////            channels= channelService.findById(Consts.STATUS_CHANNEL_NORMAL,channelId);
//        }
//
//
//        Set<Long> postIds;
//        //为0获取所有标签的文章
//        if (tagId==0){
//            postIds = postTagService.findAllPostId();
//        }else{
//            postIds = postTagService.findPostIdByTagId(tagId);
//        }
//
//        Set<Integer> channelIds = new HashSet<>();
//        if (channels!=null){
//            channels.forEach(channel -> channelIds.add(channel.getId()));
//        }
//
//        //包装一个分页对象Pageable
//        Pageable pageable =
//                new PageRequest(pageNo,pageSize,Sort.by(Sort.Direction.DESC, BeanMapUtils.postOrder(order)));
//        Page<PostVO> postVOS = postService.paging(pageable, channelIds,postIds);
//
//
//        System.out.println(postVOS);
    }



    @Test
    public void test5(){

        int pageNo=1-1;
        int pageSize=5;
        String[] order=new String[]{"weight", "created"};
        Pageable pageable=new PageRequest(pageNo,pageSize,Sort.by(Sort.Direction.DESC,order));

        Page<PostVO> page = postService.paging4Admin(pageable, 0, "女");

        System.out.println(page);

    }



}
