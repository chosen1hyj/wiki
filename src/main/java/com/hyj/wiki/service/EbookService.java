package com.hyj.wiki.service;

import com.hyj.wiki.domain.Ebook;
import com.hyj.wiki.domain.EbookExample;
import com.hyj.wiki.mapper.EbookMapper;
import com.hyj.wiki.req.EbookReq;
import com.hyj.wiki.resp.EbookResp;
import com.hyj.wiki.util.CopyUtil;
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
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;
    public List<EbookResp> list(EbookReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName()))
            criteria.andNameLike("%" + req.getName() + "%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);
        return respList;
    }
}
