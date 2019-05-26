package com.asule.blog.modules.service.impl;

import com.asule.blog.modules.po.Options;
import com.asule.blog.modules.repository.OptionsRepository;
import com.asule.blog.modules.service.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OptionsServiceImpl implements OptionsService{

    @Autowired
    private OptionsRepository optionsRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Options> findAll() {


        List<Options> options = optionsRepository.findAll();


        return options;
    }
}
