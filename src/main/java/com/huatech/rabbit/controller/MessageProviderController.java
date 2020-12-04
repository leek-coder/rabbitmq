package com.huatech.rabbit.controller;

import com.huatech.rabbit.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author like
 * @date 2020-12-03 2:25 下午
 **/
@Controller
@RequestMapping("/message")
@ResponseBody
public class MessageProviderController {

    @Autowired
    private MessageUtils messageUtils;


    @RequestMapping(value = "/send")
    public void test() {
        messageUtils.send("leek");
    }
}