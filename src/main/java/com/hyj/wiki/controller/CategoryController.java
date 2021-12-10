package com.hyj.wiki.controller;

import com.hyj.wiki.req.CategoryQueryReq;
import com.hyj.wiki.req.CategorySaveReq;
import com.hyj.wiki.resp.CommonResp;
import com.hyj.wiki.resp.CategoryQueryResp;
import com.hyj.wiki.resp.PageResp;
import com.hyj.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @description:
 * @author: Chosen1
 * @date: 2021/12/6 13:27
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;


    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req){
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req){
        CommonResp resp = new CommonResp();
        categoryService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp();
        categoryService.delete(id);
        return resp;
    }

}
