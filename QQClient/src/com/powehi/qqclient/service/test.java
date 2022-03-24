package com.powehi.qqclient.service;

import com.powehi.common.Message;
import com.powehi.common.MessageType;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * @auther xx
 * @data 2022/3/24
 */
public class test {

  public static Message readFile(String qwe) throws IOException {
    //String src = "C:\\Users\\87449\\Desktop\\back.txt";
    File file = new File(qwe);
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
    message.setMesType(MessageType.MESSAGE_SEND_FILE);
    message.setContent(value);
    return message;
  }
  public static void saveFile(String src, Message mes) throws IOException {
    File file = new File(src,mes.getSendTime());
    file.createNewFile();
    FileOutputStream fos = new FileOutputStream(file);
    String content = mes.getContent();
    byte[] bytes = content.getBytes();
    fos.write(bytes);
    System.out.println("OK");
  }

  public static void main(String[] args) throws IOException {
    Scanner in = new Scanner(System.in);
    System.out.println("文件路径");
    String qwe = in.nextLine();
    Message mes = null;
    try {
      mes = readFile(qwe);
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("文件保存路径：");
    String src = in.nextLine();
    saveFile(src,mes);
  }

}
