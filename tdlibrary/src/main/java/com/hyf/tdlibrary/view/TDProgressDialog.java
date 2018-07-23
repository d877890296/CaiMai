package com.hyf.tdlibrary.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyf.tdlibrary.R;

/**
 * Created by LK on 2016/4/11 10:27
 */
public class TDProgressDialog extends ProgressDialog{

	private TextView tipText;
	private ImageView loadImg;

	public TDProgressDialog(Context context) {
		super(context);
	}

	public TDProgressDialog(Context context, int theme) {
		super(context, theme);

		init(context);
	}

	private void init(Context context) {
		setContentView(R.layout.loading_dialog);
		tipText = (TextView) this.findViewById(R.id.tipTextView);
		loadImg = (ImageView) this.findViewById(R.id.load_img);

		// 加载动画
		RotateAnimation animation = new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		animation.setDuration(3000);
		animation.setRepeatCount(100);
		animation.setFillAfter(false);
		// 使用ImageView显示动画
		loadImg.startAnimation(animation);

	}

	public void setText(String msg){
		tipText.setText(msg);
	}
}
