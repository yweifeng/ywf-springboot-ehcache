package com.ywf.service.impl;

import com.ywf.domain.User;
import com.ywf.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    // 模拟数据库已经有数据
    public static List<User> dbList = new ArrayList<>();

    static {
        dbList.add(new User(1, "ywf"));
        dbList.add(new User(2, "ywf2"));
        dbList.add(new User(3, "ywf3"));
    }

    //使用ehcache配置的缓存名users_test
    private final String CACHE_NAME = "users_test";

    @Override
    @Cacheable(value = CACHE_NAME)
    public List<User> listUser() {
        System.out.println("listUser from db");
        return dbList;
    }

    /**
     * 查询
     *
     * @param userId
     * @return
     */
    @Override
    @Cacheable(value = CACHE_NAME, key = "'user:'+ #userId")
    public User selectUserById(Integer userId) {
        System.out.println("selectUserById from db");
        Optional<User> optional = dbList.stream().filter(p -> (Objects.equals(userId.toString(), p.getUserId().toString()))).findFirst();
        return (optional == null || !optional.isPresent()) ? null : optional.get();
    }

    /**
     * 删除
     *
     * @param userId
     */
    @Override
    @CacheEvict(value = CACHE_NAME, key = "'user:'+ #userId")
    public void delete(Integer userId) {
        System.out.println("delete from db");
        Iterator<User> it = dbList.iterator();
        while (it.hasNext()) {
            User user = (User) it.next();
            if (Objects.equals(userId.toString(), user.getUserId().toString())) {
                it.remove();
            }
        }
    }

    /**
     * 更新，先删除，下次查询再重新读取数据库
     *
     * @param user
     */
    @Override
    @CacheEvict(value = CACHE_NAME, key = "'user:'+ #user.userId")
    public void update(User user) {

        Iterator<User> it = dbList.iterator();
        while (it.hasNext()) {
            User dbUser = (User) it.next();
            if (Objects.equals(dbUser.getUserId().toString(), user.getUserId().toString())) {
                dbUser.setUserName(user.getUserName());
            }
        }
        System.out.println("update " + user.getUserId());
    }
}
