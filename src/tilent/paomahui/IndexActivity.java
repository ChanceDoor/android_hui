package tilent.paomahui;

import java.text.SimpleDateFormat;
import java.util.Date;

import tilent.paomahui.R;
import tilent.paomahui.LoginActivity.UserLoginTask;
import tilent.paomahui.R.id;
import tilent.paomahui.R.layout;
import tilent.paomahui.fragment.FragmentIndicator;
import tilent.paomahui.fragment.FragmentIndicator.OnIndicateListener;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

public class IndexActivity extends FragmentActivity {
	// Values for email and password at the time of the login attempt.

	public static Fragment[] mFragments;
	public static String conferences;
	public static String user_info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index);
		setFragmentIndicator(0);
		Intent confinfo = getIntent(); 
		conferences = confinfo.getStringExtra("json_conf");  
		user_info = confinfo.getStringExtra("user");  
	}

	/**
	 * 初始化fragment
	 */
	private void setFragmentIndicator(int whichIsDefault) {
		mFragments = new Fragment[4];
		mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_home);
		mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_list);
		mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_search);
		mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_user);
		getSupportFragmentManager().beginTransaction().hide(mFragments[0])
				.hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();

		FragmentIndicator mIndicator = (FragmentIndicator) findViewById(R.id.indicator);
		FragmentIndicator.setIndicator(whichIsDefault);
		mIndicator.setOnIndicateListener(new OnIndicateListener() {
			@Override
			public void onIndicate(View v, int which) {
				getSupportFragmentManager().beginTransaction()
						.hide(mFragments[0]).hide(mFragments[1])
						.hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();
			}
		});
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	public void showUnableMsg(View view) {
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		//mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = View.inflate(this,R.layout.dialog_msg,null);
		TextView text = (TextView) layout.findViewById(R.id.message);
		text.setText("功能更新中……");
		builder = new AlertDialog.Builder(this);
		builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
		
	}
	
}