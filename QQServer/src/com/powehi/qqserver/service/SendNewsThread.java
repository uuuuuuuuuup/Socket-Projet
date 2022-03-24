package com.powehi.qqserver.service;

import com.powehi.common.Message;
import com.powehi.common.MessageType;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * @auther xx
 * @data 2022/3/24
 */
public class SendNewsThread implements Runnable {

  Scanner in = new Scanner(System.in);

  @Override
  public void run() {
    while (true) {
      System.out.println("输入需要发送的新闻的内容：[输入exit退出发送新闻线程]");
      String content = in.nextLine();
      if (content.equals("exit")) {
        break;
      }
      Message message = new Message();
      message.setContent(content);
      message.setMesType(MessageType.MESSAGE_SEND_ALL);
      message.setSender("0000");
      message.setSendTime(new Date().toString());

      Set<Entry<String, ServerConClientThread>> entry = ManageServerConClientThread.getEntry();
      Iterator<Entry<String, ServerConClientThread>> iterator = entry.iterator();
      while (iterator.hasNext()) {
        Entry<String, ServerConClientThread> next = iterator.next();
        ServerConClientThread tempThread = next.getValue();
        Socket tempSocket = tempThread.getSocket();
        try {
          ObjectOutputStream tempoos = new ObjectOutputStream(
              tempSocket.getOutputStream());
          tempoos.writeObject(message);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}

