package com.powehi.qqclient.service;

import java.util.HashMap;

/**
 * @auther xx
 * @data 2022/3/18
 */
public class ManageClientConnServerThread{
  //多个线程放到hashmap Key是用户ID,Value是线程
  private static HashMap<String,ClientConnectServerThread> hm = new HashMap<>();

  //将某个线程加入到集合
  public static void addClientconServerThread(String userId,ClientConnectServerThread clientConnectServerThread){
    hm.put(userId,clientConnectServerThread);
  }

  //通过userId得到对应的线程
  public static ClientConnectServerThread getClientconServerThread(String userId){
    return hm.get(userId);
  }

  //删除线程
  public static void stopThread(String userId){
    hm.remove(userId);
  }
}
