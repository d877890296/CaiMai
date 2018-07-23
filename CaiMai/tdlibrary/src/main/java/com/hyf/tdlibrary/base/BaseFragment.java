package com.hyf.tdlibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by LK on 2016/3/7 15:08
 */
public abstract class BaseFragment extends Fragment {
  public Context mContext;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(getLayoutResource(), container, false);
    mContext = getActivity();
    ButterKnife.bind(this, view);
    initData();
    return view;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }

  protected abstract int getLayoutResource();

  protected abstract void initData();

}
