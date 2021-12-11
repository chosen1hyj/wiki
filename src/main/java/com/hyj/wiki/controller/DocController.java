package com.hyj.wiki.controller;

import com.hyj.wiki.req.DocQueryReq;
import com.hyj.wiki.req.DocSaveReq;
import com.hyj.wiki.resp.DocQueryResp;
import com.hyj.wiki.resp.CommonResp;
import com.hyj.wiki.resp.PageResp;
import com.hyj.wiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @description:
 * @author: Chosen1
 * @date: 2021/12/6 13:27
 */

@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;


    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq req){
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        PageResp<DocQueryResp> list = docService.list(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/all")
    public CommonResp all(){
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all();
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req){
        CommonResp resp = new CommonResp();
        docService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp();
        docService.delete(id);
        return resp;
    }

}
