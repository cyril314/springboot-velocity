package com.aim.controller.front;

import com.common.base.PatternAndView;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @AUTO 前台页面控制器
 * @Author AIM
 * @DATE 2018/10/22
 */
@Controller
@RequestMapping("/front")
public class FrontController {

    private final Logger logger = LoggerFactory.getLogger(FrontController.class);

    @ApiOperation(value = "前台首页")
    @RequestMapping(value = {"/", "/index"})
    public String index() {
        logger.info("访问前台首页");
        return "/front/index";
    }

    /**
     * @return 返回登陆页
     */
    @RequestMapping(value = {"/login", "/login.html"})
    public ModelAndView login() {
        logger.info("前台登陆页面");
        return new PatternAndView("front/login");
    }
}
