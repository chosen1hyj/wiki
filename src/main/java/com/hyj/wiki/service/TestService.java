package com.hyj.wiki.service;

import com.hyj.wiki.domain.Test;
import com.hyj.wiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Chosen1
 * @date: 2021/12/6 16:48
 */
@Service
public class TestService {

    @Resource
    private TestMapper testMapper;
    public List<Test> list(){
        return testMapper.list();
    }
}
