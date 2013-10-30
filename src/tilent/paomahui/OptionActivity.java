package tilent.paomahui;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class OptionActivity extends Activity {

	public static String userinfo;
	public static String jsonconf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
		setContentView(R.layout.activity_option);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.actionbar_normal);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("选择入口");
		Intent confinfo = getIntent(); 
		jsonconf = confinfo.getStringExtra("json_conf");  
		userinfo = confinfo.getStringExtra("user");
		Button optionBtn1 = (Button)findViewById(R.id.option_btn1);
		optionBtn1.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent index = new Intent(getApplicationContext(), IndexActivity.class);
						index.putExtra("json_conf",jsonconf);
						index.putExtra("user",userinfo);
						index.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				        startActivity(index);
					}
				});
	}

	public void showUnableMsg(View view) {
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		//mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = View.inflate(this,R.layout.dialog_msg,null);
		TextView text = (TextView) layout.findViewById(R.id.message);
		text.setText("系统升级中……");
		builder = new AlertDialog.Builder(this);
		builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
		
	}
}
