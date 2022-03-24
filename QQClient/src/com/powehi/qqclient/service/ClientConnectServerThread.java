package com.powehi.qqclient.service;

import com.powehi.common.Message;
import com.powehi.common.MessageType;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @auther xx
 * @data 2022/3/18
 */
public class ClientConnectServerThread extends Thread {

  private Socket socket = null;
  private String userId = "";
  private ObjectInputStream ois = null;
  public ClientConnectServerThread(Socket soc, String name) throws IOException {
    userId = name;
    socket = soc;
  }

  public Socket getSocket() {
    return socket;
  }


  //保存文件
  public void saveFile(String src,Message mes) throws IOException {
    File file = new File(src,mes.getSendTime());
    file.createNewFile();
    FileOutputStream fos = new FileOutputStream(file);
    String content = mes.getContent();
    byte[] bytes = content.getBytes();
    fos.write(bytes);
    System.out.println("OK");
  }

  @Override
  public void run() {
    boolean loop = true;
    //因为Thread需要在后台和服务器通信，因此我们while循环
    while (loop) {
      try {
        if (socket == null) {
          loop = false;
        }
        ois = new ObjectInputStream(socket.getInputStream());
        //ObjectInputStream ois = new ObjectInputStream(ManageClientConnServerThread.getClientconServerThread(userId).socket.getInputStream());
        //ois = new ObjectInputStream(socket.getInputStream());
        Message mes = (Message) ois.readObject();
        //如果读取是返回在线用户列表的信息
        if (userId.equals(mes.getGetter())) {
          /*if (mes.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
            String[] split = mes.getContent().split(",");
            for (int i = 0; i < split.length; i++) {
              System.out.println("用户:" + split[i]);
            }
          }*/
          switch (mes.getMesType()) {
            case MessageType.MESSAGE_RET_ONLINE_FRIEND:
              String[] split = mes.getContent().split(",");
              for (int i = 0; i < split.length; i++) {
                System.out.println("用户:" + split[i]);
              }
              break;
            case MessageType.CLIENT_EXIT:
              ManageClientConnServerThread.stopThread(userId);
              System.exit(0);
              break;
            case MessageType.MESSAGE_COMMON_MES:
              System.out.println(mes.getSender()+" 对你说 "+mes.getSendTime().substring(8,19)+"说：\n"+mes.getContent());
              break;
            case MessageType.MESSAGE_SEND_FILE:
              System.out.println(mes.getSender()+"给你发送了个文件请输入文件的保存路径:");
              Scanner in = new Scanner(System.in);
              String src = in.nextLine();
              saveFile(src,mes);
              break;
          }
        }
        if (mes.getMesType().equals(MessageType.MESSAGE_SEND_ALL)){
          System.out.println(mes.getSender()+" 对 大家 说 "+mes.getSendTime().substring(8,19)+"说：\n"+mes.getContent());
        }
      } catch (EOFException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
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
