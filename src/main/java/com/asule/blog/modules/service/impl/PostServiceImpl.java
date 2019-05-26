package com.asule.blog.modules.service.impl;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.utils.BeanMapUtils;
import com.asule.blog.base.utils.MarkdownUtils;
import com.asule.blog.base.utils.PreviewTextUtils;
import com.asule.blog.modules.event.PostUpdateEvent;
import com.asule.blog.modules.po.*;
import com.asule.blog.modules.repository.*;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.modules.service.PostService;
import com.asule.blog.modules.service.TagService;
import com.asule.blog.modules.service.UserService;
import com.asule.blog.modules.vo.PostVO;
import com.asule.blog.modules.vo.UserVO;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostServiceImpl implements PostService{


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private PostAttributeRepository postAttributeRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private PostResourceRepository postResourceRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;


    @Override
    @Transactional
    public long post(PostVO postVO) {

        Post po = new Post();
        BeanUtils.copyProperties(postVO, po);
        po.setCreated(new Date());
        po.setStatus(postVO.getStatus());

        // 处理摘要
        if (StringUtils.isBlank(postVO.getSummary())) {
            po.setSummary(trimSummary(postVO.getContent()));
        } else {
            po.setSummary(postVO.getSummary());
        }

        postRepository.save(po);


        //类型中的文章数量+1
        channelRepository.updatePostAmount(po.getChannelId());

        //更新tag，post-tag两张表相关数据
        tagService.batchUpdate(po.getTags(),po.getId());


        //保存文章内容
        PostAttribute attr = new PostAttribute();
        attr.setContent(postVO.getContent());
        attr.setId(po.getId());
        postAttributeRepository.save(attr);

        countResource(po.getId(), null,  attr.getContent());
        onPushEvent(po, PostUpdateEvent.ACTION_PUBLISH);

        return po.getId();
    }

    @Override
    public Page<PostVO> paging(Pageable pageable, Set<Integer> includeChannelIds) {
        //实现分页查询
        Page<Post> pages = postRepository.findAllByChannelIdIn(includeChannelIds,pageable);
        return  new PageImpl<>(toPostVO(pages.getContent()),pageable,pages.getTotalElements());
    }


    private List<PostVO> toPostVO(List<Post> posts){
        List<PostVO> postVOS = new ArrayList<>();
        HashSet<Long> uids = new HashSet<>();
        HashSet<Integer> channelIds = new HashSet<>();


        posts.forEach(post -> {
            uids.add(post.getAuthorId());
            channelIds.add(post.getChannelId());
            postVOS.add(BeanMapUtils.copy(post));
        });


        buildUsers(uids,postVOS);
        buildGroups(channelIds,postVOS);



        return postVOS;
    }

    private void buildGroups(HashSet<Integer> channelIds, List<PostVO> postVOS) {

        Map<Integer, Channel> channelMap = channelService.findByIds(channelIds, Consts.STATUS_CHANNEL_NORMAL);

        postVOS.forEach(postVO -> {
            postVO.setChannel(channelMap.get(postVO.getChannelId()));
        });

    }

    private void buildUsers(HashSet<Long> uids, List<PostVO> postVOS) {

        Map<Long, UserVO> map = userService.findByIdIn(uids);

        postVOS.forEach(postVO -> {
            postVO.setAuthor(map.get(postVO.getAuthorId()));
        });

    }


    //发送一个发布文章的事件，主要作用是更新用户表中，该用户的发表文章的数量
    private void onPushEvent(Post post, int action) {
        PostUpdateEvent event = new PostUpdateEvent(System.currentTimeMillis());
        event.setPostId(post.getId());
        event.setUserId(post.getAuthorId());
        event.setAction(action);
        applicationContext.publishEvent(event);
    }

    /**
     * 计算资源引用的次数(上传图片时生成的MD5，作为图片名。通过比对Resource表，查询)
     * @param postId
     * @param originContent
     * @param newContent
     */
    private void countResource(Long postId, String originContent, String newContent) {
        if (StringUtils.isEmpty(originContent)){
            originContent = "";
        }
        if (StringUtils.isEmpty(newContent)){
            newContent = "";
        }

        //利用正则检索出文章内容中的图片名。之前上传的图片名为MD5的值。
        Set<String> exists = extractImageMd5(originContent);
        Set<String> news = extractImageMd5(newContent);

        //removeAll(A, B)  把A有但B中没有的，添加到一个新list中返回
        List<String> adds = ListUtils.removeAll(news, exists);
        List<String> deleteds = ListUtils.removeAll(exists, news);


        if (adds.size()>0){
            List<Resource> resources = resourceRepository.findAllByMd5In(adds);
            List<PostResource> prs=new ArrayList();
            for (Resource resource:resources) {

                PostResource pr = new PostResource();

                pr.setPath(resource.getPath());
                pr.setPostId(postId);
                pr.setResourceId(resource.getId());
                prs.add(pr);
            }

            //向文章图片表写入相关数据
            postResourceRepository.saveAll(prs);

            //引用的资源，每个资源的数量都+1。多次引用某个资源，adds中就有多个对应的MD5，那么会多次的+1。
            resourceRepository.updateAmount(adds, 1);
        }





    }


    /**
     * 截取文章内容，生成摘要。渲染markdown，读取纯文本
     * @return
     */
    private String trimSummary(final String text){
        return PreviewTextUtils.getText(MarkdownUtils.renderMarkdown(text), 126);
//        if (Consts.EDITOR_MARKDOWN.endsWith(editor)) {
//
//        } else {
//            return PreviewTextUtils.getText(text, 126);
//        }
    }


    private Set<String> extractImageMd5(String text) {
        Pattern pattern = Pattern.compile("(?<=/_signature/)(.+?)(?=\\.)");
//		Pattern pattern = Pattern.compile("(?<=/_signature/)[^/]+?jpg");

        Set<String> md5s = new HashSet<>();

        Matcher originMatcher = pattern.matcher(text);
        while (originMatcher.find()) {
            String key = originMatcher.group();
//			md5s.add(key.substring(0, key.lastIndexOf(".")));
            md5s.add(key);
        }

        return md5s;
    }
}
