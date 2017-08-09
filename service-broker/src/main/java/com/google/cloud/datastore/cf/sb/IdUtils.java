package com.google.cloud.datastore.cf.sb;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by saurabhgu on 5/13/17.
 */
public class IdUtils {

  private static final Random RANDOM = new SecureRandom();

  private static final char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();

  public static String getBindingId() {
    char[] suff = new char[8];
    for (int i = 0; i < suff.length; i++) {
      suff[i] = chars[RANDOM.nextInt(chars.length)];
    }

    return Long.toString(System.currentTimeMillis(), 36) + new String(suff);
  }
}
