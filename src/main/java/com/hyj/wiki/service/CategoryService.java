package com.hyj.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyj.wiki.domain.Category;
import com.hyj.wiki.domain.CategoryExample;
import com.hyj.wiki.mapper.CategoryMapper;
import com.hyj.wiki.req.CategoryQueryReq;
import com.hyj.wiki.req.CategorySaveReq;
import com.hyj.wiki.resp.CategoryQueryResp;
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
public class CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
//        LOG.info("总行数：{}",pageInfo.getTotal());
//        LOG.info("总页数：{}",pageInfo.getPages());
        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;

    }

    public List<CategoryQueryResp> all(){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        return respList;

    }
    //保存
    public void save(CategorySaveReq req){
        Category category = CopyUtil.copy(req, Category.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        }else{
            //更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(long id){
        categoryMapper.deleteByPrimaryKey(id);
    }
}
