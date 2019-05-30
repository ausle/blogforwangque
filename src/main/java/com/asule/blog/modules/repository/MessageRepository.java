package com.asule.blog.modules.repository;

import com.asule.blog.modules.po.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long>{
}
