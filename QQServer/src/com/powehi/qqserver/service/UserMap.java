package com.powehi.qqserver.service;

import com.powehi.common.User;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther xx
 * @data 2022/3/18
 */
public class UserMap {

  //可以处理并发的集合
  public static ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();
  static {
    userMap.put("赵四",new User("赵四","890890"));
    userMap.put("刘能",new User("刘能","890890"));
    userMap.put("谢广坤",new User("谢广坤","890890"));
  }
  public static User getUser(String key){
    return userMap.get(key);
  }
}
