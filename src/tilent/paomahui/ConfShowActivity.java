package tilent.paomahui;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import tilent.paomahui.LoginActivity.UserLoginTask;
import tilent.paomahui.view.TitleView;
import tilent.paomahui.view.TitleView.OnLeftButtonClickListener;
import tilent.paomahui.view.TitleView.OnRightButtonClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ConfShowActivity extends Activity {
	private MyASyncTask yncTask = new MyASyncTask();;
	private TitleView mTitle;
	private String params;
    private ProgressBar progress;
    private FrameLayout frameLayout;
    private Bitmap bitmap=null;
    ProgressDialog dialog=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_conf_show);
	    // Get the message from the intent
	    Intent intent = getIntent();
	    String conf = intent.getStringExtra("conf");
	    JSONObject mconf = null;
		try {
			mconf = new JSONObject(conf);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    mTitle = (TitleView)findViewById(R.id.conf_show_title);
		mTitle.setTitle(R.string.title_activity_conf_show);
		mTitle.setLeftButton("", new OnLeftButtonClickListener(){

			@Override
			public void onClick(View button) {
				//mActivity.finish();
			}
			
		});
		Button RBtn = (Button) findViewById(R.id.right_btn);
		RBtn.setBackgroundResource(0);
		mTitle.setRightButton("ÆÀÂÛ", new OnRightButtonClickListener() {

			@Override
			public void onClick(View button) {
				Intent comment = new Intent(getApplicationContext(), CommentActivity.class);
		        startActivity(comment);
			}
		});
	    // Create the text view
		TextView textTitle = (TextView) findViewById(R.id.text_title);
		
	    TextView textBody = (TextView) findViewById(R.id.text_body);
	    	try {
	    		JSONObject jfigure = new JSONObject(mconf.getString("figure"));
	    		
	    		Log.e("conf",jfigure.getString("url").toString());
	    		params = "http://hui.tilent.cn" + jfigure.getString("url").toString();
	    		textTitle.setText(mconf.getString("title").toString());
	    		textBody.setMovementMethod(new ScrollingMovementMethod());
				textBody.setText(Html.fromHtml(mconf.getString("body").toString()));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    yncTask.execute(params);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conf_show, menu);
		return true;
	}
	
	public class MyASyncTask extends AsyncTask<String, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
	        Bitmap bitmap=null;
	        try {
	            
	            URL url = new URL(params[0]);
	            HttpURLConnection con=(HttpURLConnection) url.openConnection();
	            con.setDoInput(true);
	            con.connect();
	            InputStream inputStream=con.getInputStream();
	            
	            bitmap=BitmapFactory.decodeStream(inputStream); 
	            inputStream.close();
	        } 
	         catch (MalformedURLException e) {
	                e.printStackTrace();
	            }catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } 
	        return bitmap;
	    }
		
		@Override
	    protected void onPostExecute(Bitmap Result){
	        ImageView imgView=(ImageView)findViewById(R.id.conf_image);
	        imgView.setImageBitmap(Result);
	        ProgressBar bar=(ProgressBar)findViewById(R.id.conf_progress);
	        bar.setVisibility(View.GONE);
	    }
	}

}
