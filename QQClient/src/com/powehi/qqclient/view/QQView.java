package com.powehi.qqclient.view;

import com.powehi.qqclient.service.MessageClientService;
import com.powehi.qqclient.service.UserClientService;
import java.io.IOException;
import java.util.Scanner;

/**
 * @auther xx
 * @data 2022/3/16
 */
public class QQView {

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    new QQView().mainMenu();
    System.out.println("退出登录。。。");

  }

  private boolean loop = true;
  private UserClientService userClientService = new UserClientService();
  private MessageClientService messageClientService = new MessageClientService();

  //显示主菜单
  public void mainMenu() throws IOException, ClassNotFoundException {
    Scanner in = new Scanner(System.in);
    while (loop) {
      System.out.println("++++++++++++++++++欢迎登陆网络通信系统++++++++++++++++++");
      System.out.println("\t\t 1 登录系统");
      System.out.println("\t\t 2 退出系统");
      String key = in.next();//接受键盘输入
      switch (key) {
        case "1":
          System.out.print("请输入用户号：");
          String userId = in.next();
          System.out.print("请输入密  码：");
          String pwd = in.next();
          //业务逻辑。。。。。。
          if (userClientService.checkUser(userId, pwd)) {
            System.out.println("++++++++++++++++++欢迎" + userId + "登陆成功++++++++++++++++++");
            while (loop) {
              System.out.println("++++++++++++++++++网络通信系统(用户id：" + userId + ")++++++++++++++++++");
              System.out.println("\t\t 1 显示在线用户列表");
              System.out.println("\t\t 2 群发消息");
              System.out.println("\t\t 3 私聊消息");
              System.out.println("\t\t 4 发送文件");
              System.out.println("\t\t 9 退出系统");
              key = in.next();
              switch (key) {
                case "1":
                  userClientService.onlineFriend(userId);
                  break;
                case "2":
                  in.nextLine();
                  System.out.println("请输入群发的信息：");
                  String content = in.nextLine();
                  messageClientService.sendMessageAll(content,userId);
                  break;
                case "3":
                  System.out.print("Ta的Id：");
                  String getter = in.next();
                  in.nextLine();
                  System.out.print("请输入发送的信息：");
                  String context = in.nextLine();
                  messageClientService.sendMessageToOne(context,userId,getter);
                  //userClientService.privateMessage(getter,userId,context);
                  break;
                case "4":
                  //in.nextLine();
                  System.out.print("输入文件的路径：");
                  in.nextLine();
                  String src = in.nextLine();
                  System.out.print("输入接收人：");
                  String get = in.nextLine();
                  messageClientService.sendFile(src,userId,get);
                  break;
                case "9":
                  System.out.println("++++++++++++++++++退出系统++++++++++++++++++");
                  userClientService.loginOut(userId);
                  loop = false;
                  break;
              }
            }
          } else {
            System.out.println("++++++++++++++++++登陆失败++++++++++++++++++");
          }
        case "2":
          loop = false;
          break;
      }
    }
  }
}
