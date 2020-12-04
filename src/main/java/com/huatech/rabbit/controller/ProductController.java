package com.huatech.rabbit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author like
 * @date 2020-12-04 4:45 下午
 **/

@Controller
@RequestMapping(value = "/product")
public class ProductController {
    private Logger logger = LoggerFactory.getLogger(ProductController.class);

}