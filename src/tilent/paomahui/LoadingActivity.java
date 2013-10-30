package tilent.paomahui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class LoadingActivity extends Activity {
	int MSG_INIT_OK =1;
    int MSG_INIT_INFO = 2;
    int MSG_INIT_TIMEOUT = 9;
    
    boolean isTimeout = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		initSystem();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loading, menu);
		return true;
	}
	
	private void initSystem(){
        initThread();
        mHandler.postDelayed(timeOutTask,60000);
    }

    public Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == MSG_INIT_TIMEOUT){
                if(mHandler != null && timeOutTask != null){
                    mHandler.removeCallbacks(timeOutTask);
                }

            }else if(msg.what == MSG_INIT_OK){
                if(mHandler != null && timeOutTask !=null){
                  mHandler.removeCallbacks(timeOutTask);
                }
            startActivity(new Intent(getApplication(),LoginActivity.class));
                LoadingActivity.this.finish();
            }else if(msg.what == MSG_INIT_INFO){
                String info = msg.obj.toString();
            }
        }
    };

    Runnable timeOutTask = new Runnable() {
        @Override
        public void run() {
            isTimeout = true;
            Message msg = Message.obtain();
            msg.what = MSG_INIT_TIMEOUT;
            mHandler.sendMessage(msg);
        }
    };

    private void initThread(){
        new Thread(){
            public void run(){
                try{
                    if(!isTimeout){
                        Thread.sleep(1000);//TODO 1
                    }

                    if(!isTimeout){
                        Thread.sleep(2000);//TODO 2
                    }

                    if(!isTimeout){
                        Thread.sleep(3000);//TODO 2
                    }
                    if(!isTimeout){
                        Message msg2 = Message.obtain();
                        msg2.what = MSG_INIT_OK;
                        mHandler.sendMessage(msg2);
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
