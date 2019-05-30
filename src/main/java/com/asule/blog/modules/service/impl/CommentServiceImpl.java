package com.asule.blog.modules.service.impl;

import com.asule.blog.base.utils.BeanMapUtils;
import com.asule.blog.modules.po.Comment;
import com.asule.blog.modules.repository.CommentRepository;
import com.asule.blog.modules.repository.UserRepository;
import com.asule.blog.modules.service.CommentService;
import com.asule.blog.modules.service.UserEventService;
import com.asule.blog.modules.service.UserService;
import com.asule.blog.modules.vo.CommentVO;
import com.asule.blog.modules.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class CommentServiceImpl implements CommentService{



    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserEventService userEventService;

    @Autowired
    private UserService userService;

    @Override
    public long post(CommentVO commentVO) {
        Comment comment = new Comment();

        comment.setPid(commentVO.getPid());
        comment.setAuthorId(commentVO.getAuthorId());
        comment.setContent(commentVO.getContent());
        comment.setPostId(commentVO.getPostId());

        comment.setCreated(new Date());

        commentRepository.save(comment);

        //用户表评论数+1
        userEventService.identityComment(comment.getAuthorId(),true);

        return comment.getPostId();
    }

    @Override
    public Page<CommentVO> pagingByPostId(Pageable pageable,long postId) {
        Page<Comment> comments = commentRepository.findByPostId(pageable,postId);

        List<CommentVO> commentVOS=new ArrayList<>();
        Set<Long> parentIds=new HashSet<>();
        Set<Long> userIds=new HashSet<>();

        comments.forEach(comment -> {
            commentVOS.add(BeanMapUtils.copy(comment));
            userIds.add(comment.getAuthorId());
            if (comment.getPid() > 0) {
                parentIds.add(comment.getPid());
            }
        });

        buildParent(commentVOS,parentIds);
        buildUser(commentVOS,userIds);
        return new PageImpl<>(commentVOS,pageable,comments.getTotalElements());

    }


    /*
        id    pid
        12     0
        13     12
        14     12
        15     13
        16     12
    */


    /*
        把有子节点的评论，封装为一个MAP，该MAP，可以key为评论id，值为评论的VO数据。

        再进行判断，若某个评论有父节点，会从该MAP中以它的父节点GET，添加到parent属性中。
    */
    @Override
    public Map<Long, CommentVO> findByIds(Set<Long> parentId) {
        //查询有子节点的评论列表
        List<Comment> haveSubComments = commentRepository.findByIdIn(parentId);
        Set<Long> userIds=new HashSet<>();

        Map<Long,CommentVO> results=new HashMap<>();

        haveSubComments.forEach(haveSubComment->{
            results.put(haveSubComment.getId(),BeanMapUtils.copy(haveSubComment));
            userIds.add(haveSubComment.getAuthorId());
        });

        buildUser(results.values(),userIds);

        return results;
    }


    private void buildParent(List<CommentVO> commentVOS, Set<Long> parentIds) {
        Map<Long, CommentVO> commentVOMap = findByIds(parentIds);

        commentVOS.forEach(commentVO -> {
            if(commentVO.getPid()>0){
                commentVO.setParent(commentVOMap.get(commentVO.getPid()));
            }
        });
    }

    private void buildUser(Collection<CommentVO> commentVOS, Set<Long> userIds) {
        Map<Long, UserVO> map = userService.findByIdIn(userIds);
        commentVOS.forEach(commentVO -> commentVO.setAuthor(map.get(commentVO.getAuthorId())));
    }
}


/*



    id      pid

    12      0
    13      12



    查找某文章的评论列表。
    把查询到的文章列表的pid添加到set中，把uid添加set中。


    buildparent---->

    查询pid不为0的评论列表，pid不为0的评论，说明它们有父节点。
    把pid作为id查询评论列表，查询到的是父节点的评论信息。
    把这些父节点的评论信息，封装进uservo。

    然后pid不为0的,把属于他们的父节点设置进去。















*/

