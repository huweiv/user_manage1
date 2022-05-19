package com.huweiv.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.huweiv.domain.Role;
import com.huweiv.domain.User;
import com.huweiv.service.RoleService;
import com.huweiv.service.UserService;
import com.huweiv.domain.KaptchaImageVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName UserController
 * @Description TODO
 * @CreateTime 2022/4/10 10:28
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "roleService")
    private RoleService roleService;
    @Resource(name = "kaptchaProducer")
    DefaultKaptcha kaptchaProducer;

    @RequestMapping("/index")
    public String index() {
        return "/index.jsp";
    }

    @RequestMapping("/kaptcha")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/png");
        String code = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(code);
        KaptchaImageVO kaptchaImageVO = new KaptchaImageVO(code, 2 * 60);
        session.setAttribute("kaptcha", kaptchaImageVO);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/login")
    public String login() {
        return "/pages/user/login.jsp";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public int doLogin(User user, String kaptchaCode, String remember, HttpSession session, HttpServletResponse response) {
        int flag = 0;
        Boolean is_login = (Boolean) session.getAttribute("is_login");
        if (is_login != null)
            return flag;
        KaptchaImageVO kaptcha = (KaptchaImageVO) session.getAttribute("kaptcha");
        if (kaptcha.isExpired()) {
            flag = 1;
            return flag;
        }
        if (!kaptcha.getCode().equals(kaptchaCode)) {
            flag = 2;
            return flag;
        }
        User userInfo = userService.login(user);
        if (userInfo != null) {
            if ("1".equals(remember)) {
                Cookie c_username = new Cookie("username", user.getUsername());
                Cookie c_password = new Cookie("password", user.getPassword());
                c_username.setMaxAge(60 * 60 * 24 * 7);
                c_password.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(c_username);
                response.addCookie(c_password);
            }
            session.setAttribute("user", userInfo);
            session.setAttribute("is_login", true);
            flag = 3;
        } else {
            flag = 4;
        }
        return flag;
    }

    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView, HttpSession session) {
        List<User> userList = userService.list();
        modelAndView.addObject("userList", userList);
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("loginUsername", user.getUsername());
        modelAndView.setViewName("/pages/user/list.jsp");
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add(ModelAndView modelAndView) {
        List<Role> roleList = roleService.list();
        modelAndView.addObject("roleList", roleList);
        modelAndView.setViewName("/pages/user/add.jsp");
        return modelAndView;
    }

    @RequestMapping("/save")
    @ResponseBody
    public int save(String username, String editPwd1, String editPwd2, String email, String phoneNum, Long[] roleIds) {
        int flag = 0;
        if (editPwd1 == null || editPwd1.length() == 0 || !editPwd1.equals(editPwd2))
            return flag;
        if (phoneNum.length() != 11) {
            flag = 1;
            return flag;
        }
        if (!email.contains("@")) {
            flag = 2;
            return flag;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(editPwd1);
        user.setPhoneNum(phoneNum);
        user.setEmail(email);
        return userService.save(user, roleIds);
    }

    @RequestMapping("/delete/{userId}")
    public String delete(@PathVariable(value = "userId") long userId) {
        userService.delete(userId);
        return "redirect:/user/list";
    }

    @RequestMapping("/quit")
    public String quit(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    @RequestMapping("/edit/{userId}")
    public ModelAndView edit(@PathVariable(value = "userId") long userId, ModelAndView modelAndView) {
        User user = userService.findSingleUser(userId);
        List<Role> roleList = roleService.list();
        modelAndView.addObject("user", user);
        modelAndView.addObject("roleList", roleList);
        modelAndView.setViewName("/pages/user/edit.jsp");
        return modelAndView;
    }

    @RequestMapping("/update")
    @ResponseBody
    public int update(User user, int isUpdateUsername, Long[] roleIds) {
        int flag = 0;
        if (user.getPhoneNum().length() != 11) {
            return flag;
        }
        if (!user.getEmail().contains("@")) {
            flag = 1;
            return flag;
        }
        return userService.update(user, roleIds, isUpdateUsername);
    }

    @RequestMapping("/editPwd")
    public ModelAndView editPwd(ModelAndView modelAndView, HttpSession session) {
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("username", user.getUsername());
        modelAndView.setViewName("/pages/user/editPwd.jsp");
        return modelAndView;
    }

    @RequestMapping("/doEditPwd")
    @ResponseBody
    public int doEditPwd(String username, String password, String editPwd1, String editPwd2) {
        int flag = 0;
        if (editPwd1 == null || editPwd1.length() == 0 || !editPwd1.equals(editPwd2))
            return flag;
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userService.editPwd(user, editPwd1);
    }

    @RequestMapping("/forgetPwd")
    public String forgetPwf() {
        return "/pages/user/forgetPwd.jsp";
    }

    @RequestMapping("/doForgetPwd")
    @ResponseBody
    public int doForgetPwd(String username, String phoneNum, String editPwd1, String editPwd2, String kaptchaCode, HttpSession session) {
        int flag = 0;
        KaptchaImageVO kaptcha = (KaptchaImageVO) session.getAttribute("kaptcha");
        if (kaptcha.isExpired())
            return flag;
        if (!kaptcha.getCode().equals(kaptchaCode)) {
            flag = 1;
            return flag;
        }
        if (editPwd1 == null || editPwd1.length() == 0 || !editPwd1.equals(editPwd2)) {
            flag = 2;
            return flag;
        }
        User user = new User();
        user.setUsername(username);
        user.setPhoneNum(phoneNum);
        user.setPassword(editPwd1);
        flag = userService.forgetPwd(user);
        return flag;
    }

    @RequestMapping("/register")
    public String register() {
        return "/pages/user/register.jsp";
    }

    @RequestMapping("/doRegister")
    @ResponseBody
    public int doRegister(String username, String email, String phoneNum, String pwd1, String pwd2, String kaptchaCode, HttpSession session) {
        int flag = 0;
        KaptchaImageVO kaptcha = (KaptchaImageVO) session.getAttribute("kaptcha");
        if (!kaptcha.getCode().equals(kaptchaCode))
            return flag;
        if (kaptcha.isExpired()) {
            flag = 1;
            return flag;
        }
        if (!email.contains("@")) {
            flag = 2;
            return flag;
        }
        if (phoneNum.length() != 11) {
            flag = 3;
            return flag;
        }
        if (pwd1 == null || !pwd1.equals(pwd2)) {
            flag = 4;
            return flag;
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNum(phoneNum);
        user.setPassword(pwd1);
        return userService.register(user);
    }
}
