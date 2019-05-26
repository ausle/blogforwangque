package com.asule.blog.modules.po;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "asule_post", indexes = {
        @Index(name = "IK_CHANNEL_ID", columnList = "channel_id")
})
//定义一个过滤器，过滤掉status = 0的记录
//@FilterDefs({
//        @FilterDef(name = "POST_STATUS_FILTER", defaultCondition = "status = 0" )})
//@Filters({ @Filter(name = "POST_STATUS_FILTER") })
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "channel_id", length = 5)
    private int channelId;


    @Column(name = "title", length = 64)
    private String title;


    /**
     * 摘要
     */
    @Column(length = 140)
    private String summary;

    /**
     * 预览图
     */
    @Column(length = 128)
    private String thumbnail;

    /**
     * 标签, 多个逗号隔开
     */
    @Column(length = 64)
    private String tags;

    /**
     * 作者Id
     */
    @Column(name = "author_id")
    private long authorId;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;

    /**
     * 收藏数
     */
    private int favors;

    /**
     * 评论数
     */
    private int comments;

    /**
     * 阅读数
     */
    private int views;

    /**
     * 文章状态
     */
    private int status;

    /**
     * 推荐状态
     */
    private int featured;

    /**
     * 排序值
     */
    private int weight;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getFavors() {
        return favors;
    }

    public void setFavors(int favors) {
        this.favors = favors;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
