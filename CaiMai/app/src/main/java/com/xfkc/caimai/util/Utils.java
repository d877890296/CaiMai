/**
 * 功能：工具类
 * 类名：Utils.java
 * 日期：2013-11-26
 * 作者：lukejun
 */
package com.xfkc.caimai.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xfkc.caimai.config.Constant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

/**
 * @author lukejun
 * @ClassName: Utils
 * @Description: 工具类
 * @date 2013-11-26 下午4:36:05
 */
public class Utils {
    private static String sdPath;

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @param context
     * @return
     * @Title: isNetworkAvailable
     * @Description: 检测网络状态是否为空
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    Log.i(String.valueOf(i), info[i].getTypeName());
                    if (info[i].getState() == NetworkInfo.State.CONNECTED
                            && (info[i].getTypeName()
                            .equalsIgnoreCase("MOBILE") || info[i]
                            .getTypeName().equalsIgnoreCase("WIFI"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

//    public static void setBankCardLogoImage(String photo, ImageView img) {
//        if (photo.equals("01000000")) {
//            img.setImageResource(R.drawable.a01000000);
//        } else if (photo.equals("01020000")) {
//            img.setImageResource(R.drawable.a01020000);
//        } else if (photo.equals("01030000")) {
//            img.setImageResource(R.drawable.a01030000);
//        } else if (photo.equals("01040000")) {
//            img.setImageResource(R.drawable.a01040000);
//        } else if (photo.equals("01059999")) {
//            img.setImageResource(R.drawable.a01059999);
//        } else if (photo.equals("03010000")) {
//            img.setImageResource(R.drawable.a03010000);
//        } else if (photo.equals("03020000")) {
//            img.setImageResource(R.drawable.a03020000);
//        } else if (photo.equals("03040000")) {
//            img.setImageResource(R.drawable.a03040000);
//        } else if (photo.equals("03050001")) {
//            img.setImageResource(R.drawable.a03050001);
//        } else if (photo.equals("03060000")) {
//            img.setImageResource(R.drawable.a03060000);
//        } else if (photo.equals("03080000")) {
//            img.setImageResource(R.drawable.a03080000);
//        } else if (photo.equals("03090010")) {
//            img.setImageResource(R.drawable.a03090010);
//        } else if (photo.equals("03170000")) {
//            img.setImageResource(R.drawable.a03170000);
//        } else if (photo.equals("04100000")) {
//            img.setImageResource(R.drawable.a04100000);
//        } else if (photo.equals("63030000")) {
//            img.setImageResource(R.drawable.a63030000);
//        } else if (photo.equals("63100000")) {
//            img.setImageResource(R.drawable.a63100000);
//        } else if (photo.equals("99999999")) {
//            img.setImageResource(R.drawable.a99999999);
//        } else {
//            img.setImageResource(R.drawable.a99999999);
//        }
//    }

    /**
     * @param c
     * @return
     * @Title: calculateLength
     * @Description: 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点
     * 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     */
    public static long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    /**
     * @param numOrStr
     * @return
     * @Title: equalStr
     * @Description:不能全是相同的数字或者字母（如：000000、111111、aaaaaa） 全部相同返回true
     */
    public static boolean equalStr(String numOrStr) {
        boolean flag = true;
        char str = numOrStr.charAt(0);
        for (int i = 0; i < numOrStr.length(); i++) {
            if (str != numOrStr.charAt(i)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * @param
     * @return
     * @Title: formatNum
     * @Description: 取小数点后两位double数据
     */
    public static String format(String numString) {
        double num = Double.valueOf(numString);
        if (num != 0) {
            String result = "";
            result = new DecimalFormat("0.00").format(num);
            // result = String.format("%.2f", num);
            return result;
        } else {
            return "0.00";
        }
    }

    /**
     * @param value
     * @return
     * @Title: stringToDouble
     * @Description: string转double，不损失精度
     */
    public static double stringToDouble(String value) {

        BigDecimal big = new BigDecimal(value).setScale(2);
        return big.doubleValue();

    }

    // /**
    // * @Title: formatNum
    // * @Description:(这里用一句话描述这个方法的作用)
    // * @param value
    // * @return
    // */
    // public static String format(String value) {
    //
    // return
    // DecimalFormat.getCurrencyInstance().format(Double.parseDouble(value)).replace("￥",
    // "");
    // }

    /**
     * @param num
     * @return
     * @Title: formatNumTenBeforePoint
     * @Description: 小数点前保留10位
     */
    public static String formatNumTenBeforePoint(String num) {
        String s = num.substring(0, num.indexOf(".") - 1);
        if (s.length() > 10) {
            return s.substring(s.length() - 10, s.length() - 1) + "."
                    + num.substring(num.indexOf(".") + 1, num.length() - 1);
        } else {
            return num;
        }
    }

    /**
     * @param paramInt
     * @return
     * @Title: RandomNumber
     * @Description: 生成随机数
     */
    public static int RandomNumber(int paramInt) {
        return new Random().nextInt(paramInt);
    }

    public static String FormatTime(Date date, String form) {

        SimpleDateFormat sdf = new SimpleDateFormat(form);
        String time_formated = sdf.format(date);
        return time_formated;
    }

    /**
     * 得到SD卡的路径
     *
     * @return
     */
    public static String getSDCardAbsolutePath() {
        if ((Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED))) {
            sdPath = Environment.getExternalStorageDirectory().toString();// .getPath();
        }
        return sdPath;
    }

    /**
     * @return
     * @Title: hasSdcard
     * @Description: 判断是否存在SD卡
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * 将时间戳转化成格式化的时间<br>
     * 如果是秒级时间戳，先*1000 "yyyy-MM-dd HH:mm:ss"<br>
     *
     * @param time
     * @return
     * @throws
     * @method FormatTimeForm
     * @since v1.0
     */
    public static String timeStamp2Date(long time, String format) {
        return new SimpleDateFormat(format).format(new Date(time));
    }

    /**
     * @param format
     * @return
     * @Title: getCurrentDate
     * @Description: 获取当前日期
     */
    public static String getCurrentDate(String format) {
        return new SimpleDateFormat(format).format(Calendar.getInstance()
                .getTime());
    }

    /**
     * 屏蔽手机号 中间4位
     *
     * @param accountStr
     * @return
     */
    public static String hiddenMobile(String accountStr) {
        try {
            if (ExpresssoinValidateUtil.isMobilePhone(accountStr)) { // 手机号
                accountStr = accountStr.substring(0, 3) + "****"
                        + accountStr.substring(accountStr.length() - 4);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "--";
        }
        return accountStr;
    }

    /**
     * 屏蔽银行卡号中间数字
     *
     * @param cardNoStr
     * @return
     */
    public static String hiddenCardNo(String cardNoStr) {
        if (TextUtils.isEmpty(cardNoStr)) {
            return "--";
        }
        try {
            cardNoStr = cardNoStr.substring(0, 6) + "******"
                    + cardNoStr.substring(cardNoStr.length() - 4);
        } catch (Exception e) {
            return "--";
        }
        return cardNoStr;
    }

    public static String hiddenAccount(String userNameStr) {
        userNameStr = userNameStr.substring(0, 1)
                + userNameStr.substring(1).replaceAll(".", "*");
        return userNameStr;
    }

    public static Bitmap getPicFromBytes(byte[] bytes,
                                         BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }

    /**
     * 通过bitmap获取inputStream
     *
     * @param @param  Bitmap
     * @param @return
     * @return
     * @throws
     * @method Bitmap2IS
     * @since v1.0
     */
    public static InputStream Bitmap2IS(Bitmap bm, int num) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, num, baos);
        InputStream sbs = new ByteArrayInputStream(baos.toByteArray());

        return sbs;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            // Log.e("LocalIpAddress", ex.toString());
        }
        return null;
    }

    /**
     * 截取字符串
     *
     * @param oldValueStr 原先的值
     * @param count       截取的位数 ,为0默认不截取
     * @return
     */
    @SuppressWarnings("finally")
    public static String getInterceptString(String oldValueStr, int count) {

        String newValueStr = null;

        try {

            if (count == 0) {

                newValueStr = oldValueStr;

            } else {

                newValueStr = oldValueStr.substring(0, oldValueStr.length()
                        - count);

            }

        } catch (Exception ex) {

        } finally {

            return newValueStr;
        }
    }

    public static String formatDate(String source, String format) {
        if (TextUtils.isEmpty(source)) {
            return "";
        }
        SimpleDateFormat temp = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = temp.parse(source);
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            System.err.println(source);
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }

    public static void showToast(Context context, String s) {
        Toast.makeText(context.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    public static void debug(String message) {
        Log.e(Constant.TAG, message);
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    //检验手机号格式
    public static boolean isPhoneNumber(String prePhoneNumber) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][34578]\\d{9}";
        return prePhoneNumber.matches(telRegex);
    }

    public static byte[] transbBitmap2Byte(Bitmap shareBitmap) {
        int bytes = shareBitmap.getByteCount();

        ByteBuffer buf = ByteBuffer.allocate(bytes);
        shareBitmap.copyPixelsToBuffer(buf);

        return buf.array();
    }

    public static boolean isEmpty(String string) {
        if (string == null || string.equals("") || string.length() == 0) {
            return true;
        }
        return false;
    }
}
