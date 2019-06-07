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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
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


    @Autowired
    private UserRepository userRepository;


    @Override
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
        channelRepository.updatePostAmount(po.getChannelId(),Consts.IDENTITY_STEP);

        //更新tag，post-tag两张表相关数据
        tagService.batchUpdate(po.getTags(),po.getId());

        //保存文章内容
        PostAttribute attr = new PostAttribute();
        attr.setContent(postVO.getContent());
        attr.setId(po.getId());
        postAttributeRepository.save(attr);

        countResource(po.getId(), null,  attr.getContent());
        //发送一个发布文章的事件，主要作用是更新用户表中，该用户的发表文章的数量
        onPushEvent(po, PostUpdateEvent.ACTION_PUBLISH);

        return po.getId();
    }

    @Override
    public void update(PostVO postVO) {
        Optional<Post> optional = postRepository.findById(postVO.getId());

        if (optional.isPresent()){
            Post po = optional.get();

            po.setTitle(postVO.getTitle());//标题
            po.setChannelId(postVO.getChannelId());
            po.setThumbnail(postVO.getThumbnail());
            po.setStatus(postVO.getStatus());

            // 处理摘要
            if (StringUtils.isBlank(postVO.getSummary())) {
                po.setSummary(trimSummary(postVO.getContent()));
            } else {
                po.setSummary(postVO.getSummary());
            }

            po.setTags(postVO.getTags());//标签


            //保存文章内容
            PostAttribute attr = new PostAttribute();
            attr.setContent(postVO.getContent());
            attr.setId(po.getId());
            postAttributeRepository.save(attr);


            //更新tag，post-tag两张表相关数据
            tagService.batchUpdate(po.getTags(),po.getId());

            countResource(po.getId(), null, postVO.getContent());

        }


    }

    @Override
    public Page<PostVO> paging(Pageable pageable, Set<Integer> includeChannelIds,Set<Long> postIds) {
        //实现分页查询
        Page<Post> pages = postRepository.findAllByChannelIdInAndIdIn(includeChannelIds,postIds,pageable);
        return  new PageImpl<>(toPostVO(pages.getContent()),pageable,pages.getTotalElements());
    }

    @Override
    public Page<PostVO> pagingCarousel(Pageable pageable) {
        Page<Post> pages = postRepository.findAll(pageable);
        return  new PageImpl<>(toPostVO(pages.getContent()),pageable,pages.getTotalElements());
    }

    @Override
    public Page<PostVO> paging4Admin(Pageable pageable, Integer channelId, String title) {
        //实现带条件查询的分页
        Specification specification= new Specification<PostVO>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //root代表实体类对象。从中获取where条件的判断属性
                //criteriaBuilder获取criteria的工厂，获取Predicate，Predicate是条件查询类
                Predicate conjunction = criteriaBuilder.conjunction();

                //添加多个条件
                List<Expression<Boolean>> expressions = conjunction.getExpressions();
                if (channelId>0){
                    expressions.add(criteriaBuilder.equal(root.get("channelId").as(Integer.class),channelId));
                }

                if (!StringUtils.isBlank(title)){
                    expressions.add(criteriaBuilder.like(root.get("title").as(String.class),"%"+title+"%"));
                }

                return conjunction;
            }
        };
        Page<Post> pages= postRepository.findAll(specification,pageable);
        return  new PageImpl<>(toPostVO(pages.getContent()),pageable,pages.getTotalElements());
    }

    @Override
    public void delete(Collection<Long> ids) {
        List<Post> postList = postRepository.findAllById(ids);

        postList.forEach(post -> {
            postRepository.delete(post);

            //删除文章-内容表相关内容
            postAttributeRepository.deleteById(post.getId());


            //删除文章-资源表内容
            List<PostResource> postResourceList = postResourceRepository.findByPostId(post.getId());
            postResourceRepository.deleteByPostId(post.getId());

            List<Long> resourceId=new ArrayList<>();
            postResourceList.forEach(postResource ->
                    resourceId.add(postResource.getResourceId())
            );

            //文章需有引用图片资源，才可减少
            if (resourceId.size()>0){
                //资源表相关资源引用次数减少1
                resourceRepository.updateAmountByRid(resourceId,1);
            }


            //发一个删除文章的事件，减少用户表，文章数量
            onPushEvent(post, PostUpdateEvent.ACTION_DELETE);
        });
    }






    @Override
    public int getPostCount() {
        return postRepository.getPostCount();
    }


    @Override
    public PostVO get(long id) {
        Optional<Post> optional = postRepository.findById(id);
        if (optional.isPresent()){

            Post post = optional.get();

            PostVO postVO = BeanMapUtils.copy(post);

            postVO.setChannel(channelRepository.findById(postVO.getChannelId()).get());
            User user = userRepository.findById(postVO.getAuthorId()).get();
            postVO.setAuthor(BeanMapUtils.copy(user));

            PostAttribute postAttribute = postAttributeRepository.findById(postVO.getId()).get();
            postVO.setContent(postAttribute.getContent());

            return postVO;
        }
        return null;
    }



    @Override
    public void identityViews(long id) {
        postRepository.identityViews(id,Consts.IDENTITY_STEP);
    }

    @Override
    public void identityComments(long id) {

        postRepository.identityComments(id,Consts.IDENTITY_STEP);
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


    private void onPushEvent(Post post, int action) {
        PostUpdateEvent event = new PostUpdateEvent(System.currentTimeMillis());
        event.setPostId(post.getId());
        event.setUserId(post.getAuthorId());
        event.setAction(action);
        event.setChannelId(post.getChannelId());
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

            //引用的资源，每个资源的数量都+1。多次引用某个资源，adds中就有多个对应的MD5，但是并不会多次的+1。
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
