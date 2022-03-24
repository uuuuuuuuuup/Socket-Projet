package com.powehi.qqserver.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @auther xx
 * @data 2022/3/18 管理和客户端通信线程的类
 */
public class ManageServerConClientThread {

  private static HashMap<String, ServerConClientThread> hm = new HashMap<>();

  public static void addServerConClientThread(String userId, ServerConClientThread thread) {
    hm.put(userId, thread);
  }

  public static ServerConClientThread getServerConClientThread(String userId) {
    return hm.get(userId);
  }

  //返回在线用户
  public static String getOnLineFriend(){
    String string = "";
    Iterator<String> iterator = hm.keySet().iterator();
    while (iterator.hasNext()) {
      String next =  iterator.next();
      string+=next+",";
    }
    return string;
  }

  //退出线程
  public static void stopThread(String userId){
    hm.remove(userId);
  }

  //返回集合
  public static HashMap<String,ServerConClientThread> getHm(){
    return hm;
  }

  //返回entryset
  public static Set<Entry<String,ServerConClientThread>> getEntry(){
    return hm.entrySet();
  }

  //返回keyset
  public static Set<String> getKeySet(){
    return hm.keySet();
  }
}
