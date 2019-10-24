package com.storage.app;

import java.util.UUID;

/** @author choang on 10/23/19 */
public final class Utils {
  private Utils() {}

  public static String getUUID(int length) {
    return UUID.randomUUID().toString().replace("-", "").substring(0, length);
  }
}
