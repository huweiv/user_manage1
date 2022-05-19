package com.huweiv.controller;

import com.huweiv.domain.Role;
import com.huweiv.domain.User;
import com.huweiv.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName RoleController
 * @Description TODO
 * @CreateTime 2022/4/10 10:29
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Resource(name = "roleService")
    private RoleService roleService;

    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView, HttpSession session) {
        List<Role> roleList = roleService.list();
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("roleList", roleList);
        modelAndView.addObject("loginUsername", user.getUsername());
        modelAndView.setViewName("/pages/role/list.jsp");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String add() {
        return "/pages/role/add.jsp";
    }

    @RequestMapping("/save")
    @ResponseBody
    public int save(Role role) {
        return roleService.save(role);
    }

    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable(value = "roleId") long roleId) {
        roleService.delete(roleId);
        return "redirect:/role/list";
    }


    @RequestMapping("/edit/{roleId}")
    public ModelAndView edit(@PathVariable(value = "roleId") long roleId, ModelAndView modelAndView) {
        Role role = roleService.findSingleRole(roleId);
        modelAndView.addObject("role", role);
        modelAndView.setViewName("/pages/role/edit.jsp");
        return modelAndView;
    }

    @RequestMapping("/update")
    @ResponseBody
    public int update(Role role, int isUpdateRoleName) {
        return roleService.update(role, isUpdateRoleName);
    }
}
