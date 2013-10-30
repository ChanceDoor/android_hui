package tilent.paomahui;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.content.Context;
import android.util.Log;
 
public class UserFunctions {
     
    private JSONParser jsonParser;
    private static String IP_ADDRESS_TEST = "http://10.0.2.2:3000";
    private static String IP_ADDRESS = "http://121.199.35.34:8082";
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = IP_ADDRESS + "/users/sign_in.json";
    private static String registerURL = IP_ADDRESS + "/users.json";
    private static String confsURL1 = IP_ADDRESS + "/users/";
    private static String confsURL2 = "/conferences.json";
    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String confs_tag = "confs";
    
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password){
        // Building Parameters
    	JSONObject user = new JSONObject() ;
    	JSONObject params = new JSONObject();
    	JSONObject json = new JSONObject();
		try {
			user.put("email",email);
			user.put("password", password);
			params.put("user", user);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        json = jsonParser.postJSONFromUrl(loginURL, params);
        // return json
        return json;
        
    }
     
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String email, String password, String nickname, String qq, String phone, String location, String gender, String age, String job){
    	// Building Parameters
    	JSONObject user = new JSONObject() ;
    	JSONObject params = new JSONObject();
    	JSONObject json = new JSONObject();
		try {
			user.put("email",email);
			user.put("password", password);
			user.put("password_confirmation",password);
			user.put("name",nickname);
			user.put("qq",qq);
			user.put("phone",phone);
			user.put("location",location);
			user.put("gender",gender);
			user.put("age",age);
			user.put("job",job);
			
			params.put("user", user);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        json = jsonParser.postJSONFromUrl(registerURL, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
     
    public JSONObject getConferences(String uid){
    	JSONObject json = new JSONObject();
		String url = confsURL1+uid+confsURL2;
		JSONObject params = new JSONObject();
		try {
			params.put("test", 0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        json = jsonParser.getJSONFromUrl(url);
        // return json
        Log.e("JSON", json.toString());
        return json;
    }
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
}
