package com.ywf.controller;

import com.ywf.domain.User;
import com.ywf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/listUser")
    public List<User> listUser() {
        return userService.listUser();
    }

    @RequestMapping("/selectUserById")
    public User selectUserById(@RequestParam Integer userId) {
        return userService.selectUserById(userId);
    }

    @RequestMapping("/delete")
    public void delete(@RequestParam Integer userId) {
        userService.delete(userId);
    }

    @RequestMapping("/update")
    public void update(@RequestParam Integer userId, @RequestParam String userName) {
        User user = new User(userId, userName);
        userService.update(user);
    }
}
