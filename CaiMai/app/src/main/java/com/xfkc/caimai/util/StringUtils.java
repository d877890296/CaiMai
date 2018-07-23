package com.xfkc.caimai.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
public class StringUtils {
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
//                if (s.toString().contains(".")) {
//                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
//                        s = s.toString().subSequence(0,
//                                s.toString().indexOf(".") + 3);
//                        editText.setText(s);
//                        editText.setSelection(s.length());
//                    }
//                }
//                if (s.toString().trim().substring(0).equals(".")) {
//                    s = "0" + s;
//                    editText.setText(s);
//                    editText.setSelection(2);
//                }
//                if (s.toString().startsWith("0")
//                        && s.toString().trim().length() > 1) {
//                    if (!s.toString().substring(1, 2).equals(".")) {
//                        editText.setText(s.subSequence(0, 1));
//                        editText.setSelection(1);
//                        return;
//                    }
//                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    /**
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
     * 2 -- 200
     * 2.06 -- 206
     *
     * @param amount
     * @return
     */
    public static String changeY2F(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }

    //=====================================下面方法还没有封装======================

    /**
     * 登录密码的正则表达式 6,15
     */
    public static boolean isPsValidate(String ps) {
        Pattern p = Pattern.compile("^[0-9a-zA-Z]{6,15}$");
        Matcher m = p.matcher(ps);
        return m.matches();
    }

    /**
     * 支付密码的正则表达式
     */
    public static boolean isNumValidate(String ps) {
        Pattern p = Pattern.compile("^[0-9]{6}$");
        Matcher m = p.matcher(ps);
        return m.matches();
    }

    /**
     * 汉字的正则表达式
     * 由2-6个汉字组成
     */
    public static boolean isNameValidate(String name) {
        Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5]{0,}$");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 显示卡号后四位
     */
    public static String getLast4Length(String cardNo) {
        return cardNo.substring(cardNo.length() - 4, cardNo.length());
    }


    /**
     * 显示卡号前六中间*后四位
     */
    public static String getFrist6Last4Length(String cardNo) {
        return cardNo.substring(0, 6) + "***" + getLast4Length(cardNo);
    }

    /**
     * 隐藏执照编号3-6位
     * @param licNo
     * @return
     */
    public static String getLicNo(String licNo){
        return licNo.substring(0,2)+"***"+licNo.substring(6);
    }
    /**
     * 隐藏执照全称3-6位
     * @param custName
     * @return
     */
    public static String getCustName(String custName){
        if (custName.length()<=6){
            return custName.substring(0,2)+"***";
        }
        return custName.substring(0,2)+"***"+custName.substring(6);
    }
    /**
     * 隐藏经营地址3-6位
     * @param companyAdress
     * @return
     */
    public static String getcompanyAdress(String companyAdress){
        if (companyAdress.length()<=6){
            return companyAdress.substring(0,2)+"***";
        }
        return companyAdress.substring(0,2)+"***"+companyAdress.substring(6);
    }
    /**
     * 隐藏身份证7-16位
     * @param idNum
     * @return
     */
    public static String getIdNum(String idNum){
        return idNum.substring(0,6)+"***"+idNum.substring(idNum.length()-2, idNum.length());
    }
    /**
     * 显示日期后四位 10-16
     */
    public static String getLastDate4Length(String date) {
        return date.substring(5, 10);
    }

    /**
     * 1,236.02后台返回的格式
     * 123602是我们要实现的格式
     * 订单金额为2.06
     */
    public static String formatAmount(String amount) {
//        String result = amount.replace(".", "");
//        if(result.contains(",")){
//            result = result.replace(",", "");
//        }
        String result = null;
        if (!amount.contains("元")) {//提现时
            result = amount.replace("元", "");
        }
        //目前解析的是元，变成分

        return changeY2F(result);
    }

    /**
     * @param v1
     * @param v2
     * @return
     * @Title: compareTo
     * @Description: v1数字上小于、等于或大于 v2 时，返回 -1、0 或 1。
     */

    public static int compareTo(String v1, String v2) {

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);

    }


    /**
     * 去除字符串中的空格、回车、换行符、制表符等
     * @param str
     * @return
     */
    public static String replaceSpecialStr(String str) {
        String repl = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }

}
