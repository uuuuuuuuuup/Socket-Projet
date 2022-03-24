package com.powehi.qqserver.service;

import com.powehi.common.Message;
import com.powehi.common.MessageType;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @auther xx
 * @data 2022/3/18
 * 该类对应的对象和某个客户端保持通信
 */
public class ServerConClientThread extends Thread{
  private Socket socket = null;
  private String UserId = null;//与服务端通信的用户id
  public ServerConClientThread(Socket socket, String userId) {
    this.socket = socket;
    UserId = userId;
  }

  public Socket getSocket() {
    return socket;
  }



  @Override
  public void run() {//线程处于run状态 可以发送或者接收消息
    ObjectInputStream ois = null;
    boolean loop = true;
    while (loop){
      System.out.println("服务端和客户端"+UserId+"保持通信");
      try {
        //接受客户端发来的消息
        ois = new ObjectInputStream(socket.getInputStream());
        Message mes = (Message)ois.readObject();
        //输出流
        System.out.println(mes);
        //ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        //判断接受消息的类型
        /*switch(mes.getMesType()){
          case MessageType.MESSAGE_GET_ONLINE_FRIEND://获取在线用户
            System.out.println(mes.getSender()+"获取用户列表");
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
            System.out.println(mes.getSender()+"用户退出。");
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
            System.out.println(mes.getSender()+"发消息给->"+mes.getGetter());
            //接受方的管道
            ServerConClientThread thread = ManageServerConClientThread
                .getServerConClientThread(mes.getGetter());
            ObjectOutputStream socoos = new ObjectOutputStream(thread.getSocket().getOutputStream());
            socoos.writeObject(mes);
            break;
          case MessageType.MESSAGE_SEND_ALL:
            System.out.println(mes.getSender()+"发消息给大家");
            Set<Entry<String, ServerConClientThread>> entry = ManageServerConClientThread
                .getEntry();
            Iterator<Entry<String, ServerConClientThread>> iterator = entry.iterator();
            while (iterator.hasNext()) {
              Entry<String, ServerConClientThread> next =  iterator.next();
              if(next.getKey().equals(mes.getSender())){
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
            System.out.println(mes.getSender()+"发文件给->"+mes.getGetter());
            //接受方的管道
            ServerConClientThread thread1 = ManageServerConClientThread
                .getServerConClientThread(mes.getGetter());
            ObjectOutputStream socoos1 = new ObjectOutputStream(thread1.getSocket().getOutputStream());
            socoos1.writeObject(mes);
            break;
        }*/
        OffLineMessage.isHaveOffLineMes(socket,loop);
        if(OffLineMessage.isOnLine(mes)) {
          OffLineMessage.sendMessage(mes, socket, loop);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

