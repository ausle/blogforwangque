package com.asule.blog.repository;

import com.asule.blog.po.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Person,Long> {

}
