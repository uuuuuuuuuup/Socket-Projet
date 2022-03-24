package com.powehi.common;

/**
 * @auther xx
 * @data 2022/3/16
 */
public interface MessageType {

  //在接口中定义了一些常量
  //不同的常量，表示不同的消息类型
  String MESSAGE_LOGIN_SUCCESS = "1";//登陆成功
  String MESSAGE_LOGIN_FAIL = "2";//登陆失败
  String MESSAGE_COMMON_MES = "3";//普通信息
  String MESSAGE_GET_ONLINE_FRIEND = "4";//获取在线用户
  String MESSAGE_RET_ONLINE_FRIEND = "5";//返回在线用户
  String CLIENT_EXIT = "6";//客户端退出
  String MESSAGE_SEND_ALL = "7";//群发消息
  String MESSAGE_SEND_FILE = "8";//发送文件
}
