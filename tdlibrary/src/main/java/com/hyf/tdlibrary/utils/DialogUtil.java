package com.hyf.tdlibrary.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * Created by LK on 2016/3/17 9:32
 */
public class DialogUtil {

      /**
       * 获取一个dialog
       * @param context
       * @return
       */
      public static AlertDialog.Builder getDialog(Context context){
            return new AlertDialog.Builder(context);
      }

      /**
       * 获取一个加载dialog
       * @param context
       * @param message
       * @return
       */
      public static ProgressDialog getProgressDialog(Context context, String message){
            ProgressDialog dialog = new ProgressDialog(context);
            if(!TextUtils.isEmpty(message)){
                dialog.setMessage(message);
            }
            return dialog;
      }

      /**
       * 获取一个显示信息dialog
       * @param context
       * @param message
       * @param onClickListener
       * @return
       */
      public static AlertDialog.Builder getMessageDialog(Context context, String message, DialogInterface.OnClickListener onClickListener){
            AlertDialog.Builder builder = getDialog(context);
            builder.setMessage(message);
            builder.setPositiveButton("确定", onClickListener);
            return builder;
      }

      public static AlertDialog.Builder getMessageDialog(Context context, String message){
            return getMessageDialog(context, message, null);
      }

      public static AlertDialog.Builder getSelectDialog(Context context, String title, String[] arrays, DialogInterface.OnClickListener onClickListener){
            AlertDialog.Builder builder = getDialog(context);
            builder.setItems(arrays, onClickListener);
            if(!TextUtils.isEmpty(title)){
                  builder.setTitle(title);
            }
            builder.setNegativeButton("取消", null);
            return builder;
      }

      public static AlertDialog.Builder getSelectDialog(Context context, String[] arrays, DialogInterface.OnClickListener onClickListener){
            AlertDialog.Builder builder = getSelectDialog(context, null, arrays, onClickListener);
            return builder;
      }

      public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener){
            AlertDialog.Builder builder = getDialog(context);
            builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
            if(!TextUtils.isEmpty(title)){
                  builder.setTitle(title);
            }
            builder.setNegativeButton("取消", null);
            return builder;
      }

      public static AlertDialog.Builder getSingleChoiceDialog(Context context,String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener){
            return getSingleChoiceDialog(context, null, arrays, selectIndex, onClickListener);
      }
}
