package com.powehi.qqclient.service;

import com.powehi.common.Message;
import com.powehi.common.MessageType;
import com.powehi.common.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import javax.swing.DefaultListSelectionModel;

/**
 * @auther xx
 * @data 2022/3/18
 */
public class UserClientService {

  private User user = new User();
  private Socket socket = null;
  ObjectOutputStream oos = null;

  //检查用户是否存在，密码是否正确
  public boolean checkUser(String userId,String pwd) throws IOException, ClassNotFoundException {
    boolean b = false;
    this.user.setUserId(userId);
    this.user.setPassWd(pwd);
    //连接服务端
    socket = new Socket(InetAddress.getLocalHost(),9999);
    //得到ObjectOutputStream对象
    oos = new ObjectOutputStream(socket.getOutputStream());
    oos.writeObject(user);

    //读取从服务器回复的Message对象
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    Message mes = (Message)ois.readObject();

    if(mes.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCESS)){
      //和服务端通信的线程
      ClientConnectServerThread ccst = new ClientConnectServerThread(socket,userId);
      //启动客户端线程
      ccst.start();
      //为了拓展可以创建一个集合
      ManageClientConnServerThread.addClientconServerThread(user.getUserId(),ccst);
      b = true;
    }else{
      //如果登陆失败了 就不能启动服务器连接  所以关闭socket
      socket.close();
    }
    return b;
  }

  //向服务器请求在线用户
  public void onlineFriend(String userId) throws IOException, ClassNotFoundException {
    Message message = new Message();
    message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
    message.setSender(userId);
    message.setGetter("0000");
    message.setSendTime(new Date().toString());
    //发送服务器
    //获取对象对应的线程的socket的输出流
    //Socket soc = ManageClientConnServerThread.getClientconServerThread(userId).getSocket();
    oos = new ObjectOutputStream(socket.getOutputStream());
    oos.writeObject(message);
  }

  //向服务器发送退出
  public void loginOut(String userId) throws IOException {
    Message message = new Message();
    message.setMesType(MessageType.CLIENT_EXIT);
    message.setSender(userId);
    message.setGetter("0000");
    //Socket soc = ManageClientConnServerThread.getClientconServerThread(userId).getSocket();
    oos = new ObjectOutputStream(socket.getOutputStream());
    oos.writeObject(message);
  }

  /*//发送私聊消息
  public void privateMessage(String getter,String setter,String context) throws IOException {
    Message message = new Message();
    message.setMesType(MessageType.MESSAGE_COMMON_MES);
    message.setSender(setter);
    message.setGetter(getter);
    message.setContent(context);
    message.setSendTime(new Date().toString());
    *//*oos = new ObjectOutputStream(socket.getOutputStream());
    oos.writeObject(message);*//*
    ObjectOutputStream tempoos = new ObjectOutputStream(
        ManageClientConnServerThread.getClientconServerThread(user.getUserId()).getSocket()
            .getOutputStream());
    tempoos.writeObject(message);
  }*/
}
