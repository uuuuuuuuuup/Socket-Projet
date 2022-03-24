package com.powehi.common;

import java.io.Serializable;
import java.util.Objects;

/**
 * @auther xx
 * @data 2022/3/16
 * 表示一个用户
 */
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  private String userId;
  private String passWd;

  public User(String userId, String passWd) {
    this.userId = userId;
    this.passWd = passWd;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassWd() {
    return passWd;
  }

  public void setPassWd(String passWd) {
    this.passWd = passWd;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(userId, user.userId) &&
        Objects.equals(passWd, user.passWd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, passWd);
  }
}
