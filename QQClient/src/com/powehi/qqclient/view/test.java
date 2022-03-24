package com.powehi.qqclient.view;

import java.io.File;
import org.junit.Test;

/**
 * @auther xx
 * @data 2022/3/20
 */
public class test {

  public static void main(String[] args) {
    String src = "d:\\天翼云盘下载\\one";
    File file = new File(src);
    File[] files = file.listFiles();
    for (int i = 0; i < files.length; i++) {
      if(files[i].getName().length()<13){
        continue;
      }else {
        files[i].renameTo(new File(src,files[i].getName().substring(0,12)));
      }
    }
  }

}
