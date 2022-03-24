package com.powehi.qqclient.service;

import com.powehi.common.Message;
import com.powehi.common.MessageType;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.invoke.StringConcatFactory;
import java.net.Socket;
import java.util.Date;

/**
 * @auther xx
 * @data 2022/3/23
 * 该对象提供和消息相关的服务方法
 */
public class MessageClientService {

  //私聊消息
  public void sendMessageToOne(String content,String sender,String getter) throws IOException {
    Message message = new Message();
    message.setSendTime(new Date().toString());
    message.setSender(sender);
    message.setGetter(getter);
    message.setMesType(MessageType.MESSAGE_COMMON_MES);
    message.setContent(content);
    System.out.println(sender+" 对 "+getter+" 说 "+content);
    Socket socket = ManageClientConnServerThread.getClientconServerThread(sender).getSocket();
    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
    oos.writeObject(message);
  }

  //群发消息
  public void sendMessageAll(String context,String sender) throws IOException {
    Message message = new Message();
    message.setSendTime(new Date().toString());
    message.setSender(sender);
    message.setMesType(MessageType.MESSAGE_SEND_ALL);
    message.setContent(context);
    System.out.println(sender+" 对 大家 说 "+context);
    Socket socket = ManageClientConnServerThread.getClientconServerThread(sender).getSocket();
    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
    oos.writeObject(message);
  }

  //发送文件
  public void sendFile(String src,String sender,String getter) throws IOException {
    File file = new File(src);
    FileInputStream fis = new FileInputStream(file);
    BufferedInputStream bis = new BufferedInputStream(fis);
    byte[] buf = new byte[10240];
    int length = 0;
    String value = "";
    while ((length = bis.read(buf))!=-1){
      value+=new String(buf,0,length);
    }
    Message message = new Message();
    message.setSendTime(file.getName());
    message.setSender(sender);
    message.setGetter(getter);
    message.setMesType(MessageType.MESSAGE_SEND_FILE);
    message.setContent(value);
    System.out.println(sender+" 给 "+getter+"发送了一个文件");
    Socket socket = ManageClientConnServerThread.getClientconServerThread(sender).getSocket();
    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
    oos.writeObject(message);
  }

}
