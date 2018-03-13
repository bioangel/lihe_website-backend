package com.zhp.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class Md5Util {
private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

public static String md5Encode(String sourceString) {
    String resultString = null;
    try {
        resultString = new String(sourceString);
        MessageDigest md = MessageDigest.getInstance("MD5");
        resultString = byte2hexString(md.digest(resultString.getBytes()));
    } catch (Exception ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
    }
    return resultString;
}

public static final String byte2hexString(byte[] bytes) {
    StringBuffer buf = new StringBuffer(bytes.length * 2);
    for (int i = 0; i < bytes.length; i++) {
        if (((int) bytes[i] & 0xff) < 0x10) {
            buf.append("0");
        }
        buf.append(Long.toString((int) bytes[i] & 0xff, 16));
    }
    return buf.toString();
}

public static void main(String[] args) throws Exception {
    System.out.println(Md5Util.md5Encode("123"));
    System.out.println(getGID("32070","32070"));
    System.out.println(getGID("4321","1234"));
    System.out.println(Md5Util.md5Encode("32070"));
}

/**
* 获取G_ID.
* getGID
* @param minId min
* @param maxId max
* @return String
*/
public static final String getGID(String  minId ,String maxId) throws Exception {
  long  min = Long.parseLong(minId);
  long  max = Long.parseLong(maxId);
  long tempLong = 0L;
  if (max < min) {
      tempLong = min;
      min = max;
      max = tempLong;
    }
  String totalStr = (md5Encode(min + "") + md5Encode(max + "")).toLowerCase();
  return md5Encode(totalStr).toLowerCase();
}

private static String encodeByMD5(String originString) {
  if (originString != null) {
      try {
          MessageDigest md5 = MessageDigest.getInstance("MD5");
          byte[] results = md5.digest(originString.getBytes());
          String result = byte2hexString(results);
          return result;
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
  return null;
}
}
