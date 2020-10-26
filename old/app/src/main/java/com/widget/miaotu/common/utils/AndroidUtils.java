package com.widget.miaotu.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Retrofit;

public class AndroidUtils {

//	/**
//	 * 开启activity
//	 *
//	 * @param context
//	 * @param activity
//	 * @param anime
//	 *            是否需要切换动画动画（从右边进入，左边退出）
//	 */
//	public static void gotoActivity(Context context, Class<?> activity,
//			boolean anime) {
//		Intent intent = new Intent(context, activity);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//		context.startActivity(intent);
//		if (anime) {
//			((Activity) context).overridePendingTransition(
//					R.anim.in_from_right, R.anim.out_to_left);
//		}
//	}
//
//	/**
//	 * 开启activity
//	 *
//	 * @param context
//	 * @param activity
//	 * @param anime
//	 *            是否需要切换动画动画（从右边进入，左边退出）
//	 * @param bundle
//	 */
//	public static void gotoActivity(Context context, Class<?> activity,
//			boolean anime, Bundle bundle) {
//		Intent intent = new Intent(context, activity);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//		intent.putExtra("bundle", bundle);
//		context.startActivity(intent);
//		if (anime) {
//			((Activity) context).overridePendingTransition(
//					R.anim.in_from_right, R.anim.out_to_left);
//		}
//	}

	private static SharedPreferences mShareConfig;
	// 优化Toast
	private static Toast localToast;
	public static void Toast(Context paramContext, String paramString) {
		if(localToast == null){
			localToast = Toast.makeText(paramContext.getApplicationContext(), paramString, Toast.LENGTH_SHORT);
			localToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//			LinearLayout toastView = (LinearLayout) localToast.getView();
//			ImageView imageView = new ImageView(paramContext.getApplicationContext());
//			imageView.setImageResource(R.drawable.question_mark);
//			imageView.setPadding(0,10,0,0);
//			toastView.addView(imageView, 0);
//			localToast = new Toast(paramContext.getApplicationContext());
//			LayoutInflater inflate = LayoutInflater.from(paramContext.getApplicationContext());
//			View view = inflate.inflate(R.layout.custom_toast, null);
//			textView = (TextView) view.findViewById(R.id.toast_message);
//			textView.setText(paramString);
//			localToast.setView(view);
//			localToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//			localToast.setDuration(Toast.LENGTH_SHORT);
		}else {
			localToast.setText(paramString);
		}
		localToast.show();
	}



//	public static String getDeviceId(Context paramContext) {
//		TelephonyManager tm = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
//		String deviceId = tm.getDeviceId();
//		if (deviceId == null) {
//			return "";
//		} else {
//			return deviceId;
//		}
//	}

	/**
	 * 判断有无网络..
	 *
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			Toast.makeText(context, "网络无法连接，请检查手机网络", Toast.LENGTH_LONG).show();
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 验证身份证号是否符合规则
	 * @param text 身份证号
	 * @return
	 */
	public static boolean personIdValidation(String text) {
		String regx = "[0-9]{17}[x,X]";
		String reg1 = "[0-9]{15}";
		String regex = "[0-9]{18}";
		return text.matches(regx) || text.matches(reg1) || text.matches(regex);
	}

	/**
	 * 检查字符串是null或者空
	 *
	 * @param str
	 * @return true | false
	 */
	public static Boolean isNullOrEmpty(String str) {
		if (str == null || "".equals(str) || "null".equals(str)) {
			return true;
		}
		return false;
	}

	private static long lastClickTime;

	/**
	 * 按钮点击频率控制
	 * @return
     */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if ( 0 < timeD && timeD < 3000) {       //3000毫秒内按钮无效，这样可以控制快速点击，自己调整频率
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 给EditText设置数据并且不可点
	 * @param et
	 * @param msg
	 */
	public static void setEditTextFocusable(EditText et, String msg) {
		et.setText(msg);
		et.setFocusable(false);
		et.setFocusableInTouchMode(false);
	}

	public static  void openInputMethod(final EditText editText) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) editText
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(editText, 0);
			}
		}, 200);
	}

	/**
	 * GrowingIO 给输入框埋点  设置空的点击监听
	 * @param editText
	 */
	public static void  setInPutOnClickListener(EditText editText){
		editText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}

	/**
	 * 获取清单文件中的一些配置信息
	 */
	private  static HashMap<String, String> metaDataMap = new HashMap<>();
	public static String getAppMetaData(Context ctx, String key) {
		if (ctx == null || TextUtils.isEmpty(key)) {
			return null;
		}

		if (!TextUtils.isEmpty(metaDataMap.get(key))) {
			return metaDataMap.get(key);
		}

		String resultData = null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			if (packageManager != null) {
				ApplicationInfo applicationInfo =
						packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						resultData = applicationInfo.metaData.getString(key);
					}
				}
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		if ("UMENG_CHANNEL".equals(key) && TextUtils.isEmpty(resultData)) {
			resultData = "000";
		}

		metaDataMap.put(key, resultData);
		return resultData;
	}

	/**
	 * <pre>
	 * 将字符串从右至左每三位加一逗号
	 * </pre>
	 * @param str 需要加逗号的字符串
	 * @return 数值金额加逗号
	 */
	public static String displayWithComma(String str)
	{
		if(AndroidUtils.isNullOrEmpty(str)){
			return "";
		}
		String substr1="";
		String substr2="";
		if(str.contains(".")){
			substr1 = str.substring(0,str.indexOf("."));
			substr2 = str.substring(str.indexOf("."),str.length());
		}else{
			substr1 = str;
		}
		substr1 = new StringBuffer(substr1).reverse().toString(); // 先将字符串颠倒顺序
		String str2 = "";

		int size = (substr1.length() % 3 == 0) ? (substr1.length() / 3) : (substr1.length() / 3 + 1); // 每三位取一长度

    /*
     * 比如把一段字符串分成n段,第n段可能不是三个数,有可能是一个或者两个,
     * 现将字符串分成两部分.一部分为前n-1段,第二部分为第n段.前n-1段，每一段加一",".而第n段直接取出即可
     */
		for (int i = 0; i < size - 1; i++)
		{ // 前n-1段
			str2 += substr1.substring(i * 3, i * 3 + 3) + ",";
		}

		for (int i = size - 1; i < size; i++)
		{ // 第n段
			str2 += substr1.substring(i * 3, substr1.length());
		}
		str2 = new StringBuffer(str2).reverse().toString()+substr2;
		return str2;
	}

	public static String getTime() {
		String str = Calendar.getInstance().getTime()+"";

		return str;
	}
}
