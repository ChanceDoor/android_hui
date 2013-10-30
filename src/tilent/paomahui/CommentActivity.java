package tilent.paomahui;

import tilent.paomahui.view.TitleView;
import tilent.paomahui.view.TitleView.OnLeftButtonClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class CommentActivity extends Activity {

	private TitleView mTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comment);
		
		mTitle = (TitleView)findViewById(R.id.conf_show_title);
		mTitle.setTitle(R.string.title_activity_conf_show);
		mTitle.setLeftButton("", new OnLeftButtonClickListener(){

			@Override
			public void onClick(View button) {
				//mActivity.finish();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comment, menu);
		return true;
	}
	
	public void showUnableMsg(View view) {
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		//mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = View.inflate(this,R.layout.dialog_msg,null);
		TextView text = (TextView) layout.findViewById(R.id.message);
		text.setText("评论功能维护中……");
		builder = new AlertDialog.Builder(this);
		builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
		
	}

}
