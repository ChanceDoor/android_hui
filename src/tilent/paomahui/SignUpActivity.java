package tilent.paomahui;

import java.lang.reflect.Array;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class SignUpActivity extends Activity {
	
	private UserSignUpTask mAuthTask = null;
	private String mEmail;
	private String mPassword;
	private String mPasswordConfirm;
	private String mNickname;
	private String mQQ;
	private String mPhone;
	private String mLocation;
	private String mGender;
	private String mAge;
	private String mJob;
    private String errorMessage;
	// UI references.
	EditText mEmailView;
	EditText mPasswordView;
	EditText mPasswordConfirmView;
	EditText mNicknameView;
	EditText mQQView;
	EditText mPhoneView;
	EditText mLocationView;
	EditText mAgeView;
	EditText mJobView;
	View mSignUpFormView;
	View mSignUpStatusView;
	TextView mSignUpStatusMessageView;
	Button commitBtn;
	Intent intent;
    TextView title;
    TextView mSignUpMessageView;
    RadioGroup radiogroup;  
    RadioButton radio1,radio2;  
    
    private static String KEY_ERROR = "errors";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
		setContentView(R.layout.activity_sign_up);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.actionbar_normal);
		title = (TextView) findViewById(R.id.title);
		title.setText(R.string.title_activity_sign_up);

		mEmailView = (EditText) findViewById(R.id.email2);
		mPasswordView = (EditText) findViewById(R.id.password2);
		mPasswordConfirmView = (EditText) findViewById(R.id.passwordConfirm);
		mNicknameView = (EditText) findViewById(R.id.nickname);
		mQQView = (EditText) findViewById(R.id.qq);
		mPhoneView = (EditText) findViewById(R.id.phone);
		mLocationView = (EditText) findViewById(R.id.location);
		mAgeView = (EditText) findViewById(R.id.age);
		mJobView = (EditText) findViewById(R.id.job);
		radiogroup=(RadioGroup)findViewById(R.id.radiogroup1);  
        radio1=(RadioButton)findViewById(R.id.radiobutton1);  
        radio2=(RadioButton)findViewById(R.id.radiobutton2); 
        radiogroup.check(R.id.radiobutton1);
		mSignUpFormView = findViewById(R.id.sign_up_form);
		mSignUpStatusView = findViewById(R.id.sign_up_status);
		mSignUpStatusMessageView = (TextView) findViewById(R.id.sign_up_status_message);
		mSignUpMessageView = (TextView) findViewById(R.id.sign_up_error_message);
		
		findViewById(R.id.sign_up_commit_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptSignUp();
					}
				}); 
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptSignUp() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);
		mPasswordConfirmView.setError(null);
		mNicknameView.setError(null);
		mQQView.setError(null);
		mPhoneView.setError(null);
		mAgeView.setError(null);
		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mPasswordConfirm = mPasswordConfirmView.getText().toString();
		mNickname = mNicknameView.getText().toString();
		mQQ = mQQView.getText().toString();
		mPhone = mPhoneView.getText().toString();
		mAge = mAgeView.getText().toString();
		
		boolean cancel = false;
		View focusView = null;

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}
		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 8 || mPassword.length() > 32) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for password confirm.
		if (TextUtils.isEmpty(mPasswordConfirm)) {
			mPasswordConfirmView.setError(getString(R.string.error_field_required));
			focusView = mPasswordConfirmView;
			cancel = true;
		} else if (!mPassword.equals(mPasswordConfirm)) {
			mPasswordConfirmView.setError(getString(R.string.error_password_confirm));
			focusView = mPasswordConfirmView;
			cancel = true;
		}
		
		// Check for nickname.
				if (TextUtils.isEmpty(mNickname)) {
					mNicknameView.setError(getString(R.string.error_field_required));
					focusView = mNicknameView;
					cancel = true;
				} else if (mNickname.contains(" ")) {
					mNicknameView.setError(getString(R.string.error_invalid_nickname));
					focusView = mNicknameView;
					cancel = true;
				}
				
		// Check for QQ.
				if(TextUtils.isEmpty(mQQ)){					
				}else if (!TextUtils.isDigitsOnly(mQQ)) {
					mQQView.setError(getString(R.string.error_invalid_qq));
					focusView = mQQView;
					cancel = true;
				} 
				
		// Check for Phone.
				if(TextUtils.isEmpty(mPhone)){					
				}else if ((!TextUtils.isDigitsOnly(mPhone))||(!mPhone.subSequence(0, 1).equals("1"))||mPhone.length()!=11) {
					mPhoneView.setError(getString(R.string.error_invalid_phone));
					focusView = mPhoneView;
					cancel = true;
				}		
				
		// Check for age.
				if(TextUtils.isEmpty(mAge)){					
				}else if ((!TextUtils.isDigitsOnly(mAge))||Integer.parseInt(mAge)>150||Integer.parseInt(mAge)<0) {
					mAgeView.setError(getString(R.string.error_invalid_age));
					focusView = mAgeView;
					cancel = true;
				}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mSignUpStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserSignUpTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mSignUpStatusView.setVisibility(View.VISIBLE);
			mSignUpStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mSignUpStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mSignUpFormView.setVisibility(View.VISIBLE);
			mSignUpFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mSignUpFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mSignUpStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	} 

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserSignUpTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();
            String nickname = mNicknameView.getText().toString();
            String qq = mQQView.getText().toString();
            String phone = mPhoneView.getText().toString();
            String location = mLocationView.getText().toString();
            String gender = null;
            int id = radiogroup.getCheckedRadioButtonId ();
    		switch (id) {
    			case R.id.radiobutton1:
    				gender = "男";
    				break;
    			case R.id.radiobutton2:
    				gender = "女";
    				break;
    		}
            String age = mAgeView.getText().toString();
            String job = mJobView.getText().toString();
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.registerUser(email, password, nickname, qq, phone, location, gender, age, job);
			try {
				errorMessage = json.getString(KEY_ERROR);
				if (errorMessage.equals("0") ) {
                        finish();
                    }else{
                    	errorMessage = "";
                    	Iterator<?> keys = json.getJSONObject(KEY_ERROR).keys();

        		        while( keys.hasNext() ){
        		            String key = (String)keys.next();
        		            if( json.getJSONObject(KEY_ERROR).get(key) instanceof JSONArray ){
        		            	JSONArray ja = new JSONArray();
        		            	ja = (JSONArray) json.getJSONObject(KEY_ERROR).get(key);
        		            	errorMessage = errorMessage + ja.getString(0) + ";";
        		            }
        		        }
                    	return false;
                        // Error in login
                        //loginErrorMsg.setText("用户名或密码错误");
                    }
				
			} catch (JSONException e) {
				e.printStackTrace();
			}


			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				mSignUpMessageView.setText(errorMessage);
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
	}
