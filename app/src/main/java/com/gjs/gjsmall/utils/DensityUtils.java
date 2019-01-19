package com.gjs.gjsmall.utils;



import android.content.Context;
import android.util.DisplayMetrics;


public class DensityUtils
{

	public static int px2dp(Context context, int px)
	{
		// dp = px * 160 / dpi
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int            dpi     = metrics.densityDpi;
		return (int) (px * 160f / dpi + 0.5f);
	}

	public static int dp2px(Context context, int dp)
	{
		// px = dp * (dpi / 160)
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int            dpi     = metrics.densityDpi;
		return (int) (dp * (dpi / 160f) + 0.5f);
	}
}
