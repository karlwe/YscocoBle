package com.yscoco.yscocoble.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.ys.module.utils.StringUtils;
import com.yscoco.yscocoble.Constans;

public class SharePreferenceDevice {

	public static DeviceBean readShareDevice(Context context) {
		DeviceBean member = null;
		SharedPreferences preferences = context.getSharedPreferences(
				Constans.SHAREDPREFERENCES_FILENAME, Context.MODE_PRIVATE);
		String productBase64 = preferences.getString("device", "");
		if (StringUtils.isEmpty(productBase64)) {
			// Log.e("taa","出现了空值");
			return member;
		}
		Gson gson = new Gson();
		member = gson.fromJson(productBase64,DeviceBean.class);
		return member;
	}

	public static void saveShareDevice(Context context, DeviceBean m) {
		SharedPreferences preferences = context.getSharedPreferences(
				Constans.SHAREDPREFERENCES_FILENAME, Context.MODE_PRIVATE);
		Gson gson = new Gson();
			Editor editor = preferences.edit();
			editor.putString("device", gson.toJson(m));
			editor.commit();
	}

	public static void clearAll(Context context) {
		saveShareDevice(context, null);

	}
}
