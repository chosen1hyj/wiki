package com.hyj.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyj.wiki.domain.Content;
import com.hyj.wiki.domain.Doc;
import com.hyj.wiki.domain.DocExample;
import com.hyj.wiki.exception.BusinessException;
import com.hyj.wiki.exception.BusinessExceptionCode;
import com.hyj.wiki.mapper.ContentMapper;
import com.hyj.wiki.mapper.DocMapper;
import com.hyj.wiki.mapper.DocMapperCust;
import com.hyj.wiki.req.DocQueryReq;
import com.hyj.wiki.req.DocSaveReq;
import com.hyj.wiki.resp.DocQueryResp;
import com.hyj.wiki.resp.PageResp;
import com.hyj.wiki.util.CopyUtil;
import com.hyj.wiki.util.RedisUtil;
import com.hyj.wiki.util.RequestContext;
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
    private DocMapperCust docMapperCust;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    public RedisUtil redisUtil;

    @Resource
    public WsService wsService;

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

    public List<DocQueryResp> all(Long ebookId){
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);
        return respList;

    }
    //保存
    public void save(DocSaveReq req){
        Doc doc = CopyUtil.copy(req, Doc.class);
        Content content = CopyUtil.copy(req, Content.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            long nextId = snowFlake.nextId();
            doc.setId(nextId);
            content.setId(nextId);
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);
            contentMapper.insert(content);
        }else{
            //更新
            docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if(count == 0)
                contentMapper.insert(content);
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

    public String findContent(Long id){
        Content content = contentMapper.selectByPrimaryKey(id);
        docMapperCust.increaseViewCount(id);
        if(ObjectUtils.isEmpty(content))
            return "";
        else
            return content.getContent();
    }

    public void vote(Long id) {

        String key = RequestContext.getRemoteAddr();
        if(redisUtil.validateRepeat("DOC_VOTE" + id + "_" + key, 3600 * 24)){
            docMapperCust.increaseVoteCount(id);
        }else{
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }
        //推送消息
        Doc docDb = docMapper.selectByPrimaryKey(id);
        wsService.sendInfo("[" + docDb.getName() + "]被点赞!");
    }


    public void updateEbookInfo() {
        docMapperCust.updateEbookInfo();
    }
}
