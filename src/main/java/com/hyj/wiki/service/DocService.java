package com.hyj.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyj.wiki.domain.Doc;
import com.hyj.wiki.domain.DocExample;
import com.hyj.wiki.mapper.DocMapper;
import com.hyj.wiki.req.DocQueryReq;
import com.hyj.wiki.req.DocSaveReq;
import com.hyj.wiki.resp.DocQueryResp;
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
public class DocService {
    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);
    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<DocQueryResp> list(DocQueryReq req){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);
        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
//        LOG.info("总行数：{}",pageInfo.getTotal());
//        LOG.info("总页数：{}",pageInfo.getPages());
        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;

    }

    public List<DocQueryResp> all(){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);
        return respList;

    }
    //保存
    public void save(DocSaveReq req){
        Doc doc = CopyUtil.copy(req, Doc.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        }else{
            //更新
            docMapper.updateByPrimaryKey(doc);
        }
    }

    public void delete(long id){
        docMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<Long> ids){
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }
}
