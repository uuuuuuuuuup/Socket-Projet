package com.powehi.qqserver.service;

import com.powehi.common.Message;
import com.powehi.common.MessageType;
import com.powehi.common.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @auther xx
 * @data 2022/3/18 服务器，监听端口9999，等待客户端连接，保持连接
 */
public class QQServer {

  private ServerSocket serverSocket = null;

  public QQServer() throws IOException {
    //端口可以写在配置文件
    System.out.println("服务器在9999监听..");
    serverSocket = new ServerSocket(9999);
    new Thread(new SendNewsThread()).start();
    try {
      while (true) {
        //当和某个客户端建立连接后，会继续监听
        Socket accept = serverSocket.accept();
        //读取对象输入流
        ObjectInputStream ois = new ObjectInputStream(accept.getInputStream());
        //得到输出流
        ObjectOutputStream oos = new ObjectOutputStream(accept.getOutputStream());
        //读取对象
        User user = (User) ois.readObject();
        //创建Message对象
        Message message = new Message();
        //进行数据库验证  这里有HaspMap代替
        if (ManageServerConClientThread.getServerConClientThread(user.getUserId()) == null &&
            user.equals(UserMap.getUser(user.getUserId()))) {//登陆成功
          //回复客户端
          message.setMesType(MessageType.MESSAGE_LOGIN_SUCCESS);
          //将message回复客户端
          oos.writeObject(message);
          //创建线程与客户端保持通信
          ServerConClientThread serverConClientThread = new ServerConClientThread(accept,
              user.getUserId());
          serverConClientThread.start();
          //放入集合
          ManageServerConClientThread
              .addServerConClientThread(user.getUserId(), serverConClientThread);
        } else {//登陆失败
          if(ManageServerConClientThread.getServerConClientThread(user.getUserId())!=null){
            System.out.println("用户已经登陆了");
          }else {
            System.out.println("用户ID=" + user.getUserId() + "密码=" + user.getPassWd() + "登录失败");
          }
          message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
          oos.writeObject(message);
          //关闭socket
          accept.close();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      //如果退出while关闭serverSocket
      serverSocket.close();
    }
  }
}
