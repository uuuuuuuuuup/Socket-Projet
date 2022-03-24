package com.powehi.qqserver.service;

import com.powehi.common.Message;
import com.powehi.common.MessageType;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @auther xx
 * @data 2022/3/24
 */
public class OffLineMessage {

  private static HashSet<Message> hs = new HashSet<>();

  //添加离线消息
  public static void addMessage(Message mes) {
    hs.add(mes);
  }

  //发送消息
  public static void sendMessage(Message mes, Socket socket, boolean loop) throws IOException {
    switch (mes.getMesType()) {
      case MessageType.MESSAGE_GET_ONLINE_FRIEND://获取在线用户
        System.out.println(mes.getSender() + "获取用户列表");
        Message message1 = new Message();
        String allUserId = ManageServerConClientThread.getOnLineFriend();
        message1.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
        message1.setGetter(mes.getSender());
        message1.setContent(allUserId);
        message1.setSender("0000");
        message1.setSendTime(new Date().toString());
        ObjectOutputStream oos1 = new ObjectOutputStream(
            socket.getOutputStream());
        oos1.writeObject(message1);
        break;
      case MessageType.CLIENT_EXIT:
        System.out.println(mes.getSender() + "用户退出。");
        Message message2 = new Message();
        message2.setMesType(MessageType.CLIENT_EXIT);
        message2.setGetter(mes.getSender());
        message2.setSender("0000");
        ObjectOutputStream oos2 = new ObjectOutputStream(socket.getOutputStream());
        oos2.writeObject(message2);
        ManageServerConClientThread.stopThread(mes.getSender());
        //socket.close();
        loop = false;
        break;
      case MessageType.MESSAGE_COMMON_MES:
        System.out.println(mes.getSender() + "发消息给->" + mes.getGetter());
        //接受方的管道
        ServerConClientThread thread = ManageServerConClientThread
            .getServerConClientThread(mes.getGetter());
        ObjectOutputStream socoos = new ObjectOutputStream(thread.getSocket().getOutputStream());
        socoos.writeObject(mes);
        break;
      case MessageType.MESSAGE_SEND_ALL:
        System.out.println(mes.getSender() + "发消息给大家");
        Set<Entry<String, ServerConClientThread>> entry = ManageServerConClientThread
            .getEntry();
        Iterator<Entry<String, ServerConClientThread>> iterator = entry.iterator();
        while (iterator.hasNext()) {
          Entry<String, ServerConClientThread> next = iterator.next();
          if (next.getKey().equals(mes.getSender())) {
            continue;
          }
          ServerConClientThread tempThread = next.getValue();
          Socket tempSocket = tempThread.getSocket();
          ObjectOutputStream tempOos = new ObjectOutputStream(
              tempSocket.getOutputStream());
          tempOos.writeObject(mes);
        }
        break;
      case MessageType.MESSAGE_SEND_FILE:
        System.out.println(mes.getSender() + "发文件给->" + mes.getGetter());
        //接受方的管道
        ServerConClientThread thread1 = ManageServerConClientThread
            .getServerConClientThread(mes.getGetter());
        ObjectOutputStream socoos1 = new ObjectOutputStream(thread1.getSocket().getOutputStream());
        socoos1.writeObject(mes);
        break;
    }
  }

  //判断用户是否离线，是则保存信息
  public static boolean isOnLine(Message message) {
    Set<String> keySet = ManageServerConClientThread.getKeySet();
    //判断收信人是否在线
    if (message.getMesType().equals(MessageType.MESSAGE_COMMON_MES)) {
      if (keySet.contains(message.getGetter())) {
        return true;
      }
      addMessage(message);
      return false;
    }
    return true;
  }

  //判断该用户是否有离线消息
  public static void isHaveOffLineMes(Socket socket, boolean loop) throws IOException {
    Set<String> keySet = ManageServerConClientThread.getKeySet();
    Iterator<String> iterator = keySet.iterator();
    while (iterator.hasNext()) {
      String next = iterator.next();
      for (Message i : hs) {
        if (next.equals(i.getGetter())) {
          sendMessage(i, socket, loop);
          hs.remove(i);
        }
      }
    }
  }
}
