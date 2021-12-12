package com.hyj.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyj.wiki.domain.User;
import com.hyj.wiki.domain.UserExample;
import com.hyj.wiki.mapper.UserMapper;
import com.hyj.wiki.req.UserQueryReq;
import com.hyj.wiki.req.UserSaveReq;
import com.hyj.wiki.resp.UserQueryResp;
import com.hyj.wiki.resp.PageResp;
import com.hyj.wiki.util.CopyUtil;
import com.hyj.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Chosen1
 * @date: 2021/12/6 16:48
 */
@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<UserQueryResp> list(UserQueryReq req){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName()))
            criteria.andLoginNameEqualTo(req.getLoginName());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        List<UserQueryResp> respList = CopyUtil.copyList(userList, UserQueryResp.class);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
//        LOG.info("总行数：{}",pageInfo.getTotal());
//        LOG.info("总页数：{}",pageInfo.getPages());
        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;

    }

    //保存
    public void save(UserSaveReq req){
        User user = CopyUtil.copy(req, User.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            user.setId(snowFlake.nextId());
            userMapper.insert(user);
        }else{
            //更新
            userMapper.updateByPrimaryKey(user);
        }
    }

    public void delete(long id){
        userMapper.deleteByPrimaryKey(id);
    }
}
