package com.hyj.wiki.controller;

import com.hyj.wiki.req.UserQueryReq;
import com.hyj.wiki.req.UserSaveReq;
import com.hyj.wiki.resp.CommonResp;
import com.hyj.wiki.resp.UserQueryResp;
import com.hyj.wiki.resp.PageResp;
import com.hyj.wiki.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @description:
 * @author: Chosen1
 * @date: 2021/12/6 13:27
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req){
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        PageResp<UserQueryResp> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq req){
        CommonResp resp = new CommonResp();
        userService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp();
        userService.delete(id);
        return resp;
    }

}
