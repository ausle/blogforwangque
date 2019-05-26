package com.asule.blog.modules.po;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文章内容表
 */
@Table(name = "asule_post_attribute")
@Entity
public class PostAttribute implements Serializable{

    @Id
    private long id;

    /*
	    @Lob注解表示属性将被持久化为Blob或者Clob类型,
	    具体取决于属性的类型,Java.sql.Clob, Character[],char[] 和 java.lang.String这些类型的属性都被持久化为Clob类型,
	    而java.sql.Blob,Byte[], byte[] 和 serializable类型则被持久化为Blob类型.
	*/
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type="text")
    private String content; // 内容

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
