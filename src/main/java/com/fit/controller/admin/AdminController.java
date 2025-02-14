package com.fit.controller.admin;

import com.common.base.PatternAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @AUTO 后台页面控制器
 * @Author AIM
 * @DATE 2018/10/22
 */
@Controller
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value = {"/admin", "/admin/", "/admin/index"})
    public String admin() {
        return "admin/index";
    }

    /**
     * @return 返回登陆页
     */
    @RequestMapping(value = {"/admin/login", "/admin/login.html"})
    public ModelAndView login() {
        logger.info("后台登陆页面");
        return new PatternAndView("/admin/login");
    }

    /**
     * @return 退出, 跳转到登陆页面
     */
    @RequestMapping("/admin/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/admin/login";
    }
}
