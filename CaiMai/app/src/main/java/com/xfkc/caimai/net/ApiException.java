package com.xfkc.caimai.net;

/**
 * Created by LK on 2017/4/17 18:26.
 */

public class ApiException extends RuntimeException {
  private static final String TOKEN_EXPRIED = "10000";
  private String errorCode;
  private String errorMessage;


  public ApiException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  @Override
  public String getMessage() {
    return errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  /**
   * 判断是否是token失效
   *
   * @return 失效返回true, 否则返回false;
   */
  public boolean isTokenExpried() {
    return errorCode.equals(TOKEN_EXPRIED);
  }
}
