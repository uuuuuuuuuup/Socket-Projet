package com.powehi.common;

import java.io.Serializable;

/**
 * @auther xx
 * @data 2022/3/16
 * 表示一个用户发送信息
 */
public class Message implements Serializable {

  private static final long serialVersionUID = 1L;
  private String content;//内容
  private String Sender;//发送方
  private String getter;//接收者
  private String sendTime;//发送时间
  private String mesType;//消息类型

  public String getMesType() {
    return mesType;
  }

  public void setMesType(String mesType) {
    this.mesType = mesType;
  }

  @Override
  public String toString() {
    return "Message{" +
        "content='" + content + '\'' +
        ", Sender='" + Sender + '\'' +
        ", getter='" + getter + '\'' +
        ", sendTime='" + sendTime + '\'' +
        ", mesType='" + mesType + '\'' +
        '}';
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getSender() {
    return Sender;
  }

  public void setSender(String sender) {
    Sender = sender;
  }

  public String getGetter() {
    return getter;
  }

  public void setGetter(String getter) {
    this.getter = getter;
  }

  public String getSendTime() {
    return sendTime;
  }

  public void setSendTime(String sendTime) {
    this.sendTime = sendTime;
  }
}
