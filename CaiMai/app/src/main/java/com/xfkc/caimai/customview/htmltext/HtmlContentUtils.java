package com.xfkc.caimai.customview.htmltext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.orhanobut.logger.Logger;
import com.xfkc.caimai.R;

import java.util.List;

/**
 * 1.展示h5 详情
 * 3.@2018/5/23.
 */

public class HtmlContentUtils {


    static HtmlContentUtils netRequst;
    private Context context;
    private TextView textView;
    private String sample;

    // 实例化一次
    public synchronized static HtmlContentUtils getInstance() {
        if (null == netRequst) {
            netRequst = new HtmlContentUtils();
        }
        return netRequst;
    }

    public void setData(Context context , TextView textView ,String sample){
        this.context = context;
        this. textView = textView;
        this.sample =sample;
        setTextHtml();
    }


    private int getTextWidth() {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels - textView.getPaddingLeft() - textView.getPaddingRight();
    }


    /*设置详情   图文显示*/
    private void setTextHtml() {
        //设置文本超链接属性
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        //获取html内容
//        String sample = getSample();
        //设置关于图片的属性
        HtmlText.from(sample).setImageLoader(new HtmlImageLoader() {
            //解决图片显示
            @Override
            public void loadImage(String url, final Callback callback) {
                //本框架使用Glide也可以使用其他图片加载框架目的为加载网络图片
                Glide.with(context)
                        .load(url)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            //Glide异步加载图片
                            //Glide异步加载成功调用的方法
                            @Override
                            public void onResourceReady(Bitmap resource,
                                                        GlideAnimation<? super Bitmap> glideAnimation) {
                                //当图片加载框架拿到resource值时说明加载成功然后调用HtmlImageLoader接口中加载成功的方法
                                callback.onLoadComplete(resource);
                            }

                            //Glide图片请求失败时调用的方法
                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                //当框架请求失败后调用HtmlImageLoader接口中加载失败的方法
                                callback.onLoadFailed();
                            }
                        });
            }

            //设置调用时候显示的图片
            @Override
            public Drawable getDefaultDrawable() {
                return ContextCompat.getDrawable(context, R.mipmap.ic_launcher);
            }

            //设置调用失败时候显示的图片
            @Override
            public Drawable getErrorDrawable() {
                return ContextCompat.getDrawable(context, R.mipmap.ic_launcher);
            }

            @Override
            public int getMaxWidth() {
                return getTextWidth();
            }

            @Override
            public boolean fitWidth() {
                return true;
            }
        })
                .setOnTagClickListener(new OnTagClickListener() {
                    @Override
                    public void onImageClick(Context context, List<String> imageUrlList, int position) {
//                        Toast.makeText(context, "image click, position: " + position + ", url: " + imageUrlList.get(position), Toast.LENGTH_SHORT).show();
                        Logger.e("shopcontent", "image click, position: " + position + ", url: " + imageUrlList.get(position));
                    }

                    @Override
                    public void onLinkClick(Context context, String url) {
//                        Toast.makeText(context, "url click: " + url, Toast.LENGTH_SHORT).show();
                        Logger.e("shopcontent_onlinkclick", "url click: " + url);
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .into(textView);
    }
}
