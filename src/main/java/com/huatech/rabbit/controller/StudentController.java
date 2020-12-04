package com.huatech.rabbit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 新增StudentController
 *
 * @author like
 * @date 2020-12-04 4:43 下午
 **/
@Controller
@RequestMapping(value = "/student")
public class StudentController {
    private Logger logger = LoggerFactory.getLogger(StudentController.class);
}