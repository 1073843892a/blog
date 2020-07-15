package com.lvlei.blog.web.admin;

import com.lvlei.blog.po.User;
import com.lvlei.blog.service.UserService;
import com.lvlei.blog.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class loginController {
    @Autowired
    UserService userService;
    //请求过后自动调用这个方法
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checckUser(username, Md5Util.code(password));
        if (user != null) {
            user.setPassword(null);//不返回前端的password
            session.setAttribute("user",user);//返回user
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message","用户名或者密码错误");
            return "redirect:/admin";//重定向到这个controller，如果直接返回一个页面，再次返回再请求会导致路径错误
        }
    }

    @GetMapping("/logout")
    public  String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}