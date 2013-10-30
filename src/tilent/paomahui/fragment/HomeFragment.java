package tilent.paomahui.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.LinearLayout.LayoutParams;

import tilent.paomahui.ConfShowActivity;
import tilent.paomahui.DatabaseHandler;
import tilent.paomahui.IndexActivity;
import tilent.paomahui.R;
import tilent.paomahui.UserFunctions;
import tilent.paomahui.LoginActivity.UserLoginTask;
import tilent.paomahui.calendar.CalendarAdapter;
import tilent.paomahui.fragment.HomeFragment;
import tilent.paomahui.view.TitleView;
import tilent.paomahui.view.TitleView.OnLeftButtonClickListener;
import tilent.paomahui.view.TitleView.OnRightButtonClickListener;


public class HomeFragment extends Fragment implements OnItemClickListener {

		private View mParent;
		private Activity fatherActivity;	
		private FragmentActivity mActivity;	
		private TitleView mTitle;
		public GregorianCalendar month, itemmonth;
		public CalendarAdapter adapter;
		public Handler handler1;   
		public Handler handler2; 
		public ArrayList<String> items; 
		public ArrayList<String> conferences; 
		public String conf;
		public Time time = new Time("GMT+8");
		
		public static HomeFragment newInstance(int index) {
			HomeFragment f = new HomeFragment();

			// Supply index input as an argument.
			Bundle args = new Bundle();
			args.putInt("index", index);
			f.setArguments(args);
			
			return f;
		}

		public int getShownIndex() {
			return getArguments().getInt("index", 0);
		}
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_home, container, false);
			Locale.setDefault( Locale.CHINA );
			month = (GregorianCalendar) GregorianCalendar.getInstance();
	        itemmonth = (GregorianCalendar) month.clone();

	        items = new ArrayList<String>();
	        adapter = new CalendarAdapter(getActivity(), month);

	        GridView gridview = (GridView) view.findViewById(R.id.gridview);
	        gridview.setAdapter(adapter);
	        handler1 = new Handler();
	        handler1.post(calendarUpdater);
	        

	        TextView title = (TextView) view.findViewById(R.id.title);
	        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

	        RelativeLayout previous = (RelativeLayout) view.findViewById(R.id.previous);

	        previous.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                setPreviousMonth();
	                refreshCalendar();
	            }
	        });

	        RelativeLayout next = (RelativeLayout) view.findViewById(R.id.next);
	        next.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                setNextMonth();
	                refreshCalendar();

	            }
	        });

	        gridview.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View v,
	                    int position, long id) {

	                ((CalendarAdapter) parent.getAdapter()).setSelected(v);
	                String selectedGridDate = CalendarAdapter.dayString
	                        .get(position);
	                String[] separatedTime = selectedGridDate.split("-");
	                String gridvalueString = separatedTime[2].replaceFirst("^0*",
	                        "");// taking last part of date. ie; 2 from 2012-12-02.
	                int gridvalue = Integer.parseInt(gridvalueString);
	                // navigate to next or previous month on clicking offdays.
	                if ((gridvalue > 10) && (position < 8)) {
	                    setPreviousMonth();
	                    refreshCalendar();
	                } else if ((gridvalue < 7) && (position > 28)) {
	                    setNextMonth();
	                    refreshCalendar();
	                }
	                ArrayList<String> dialog_items = CalendarAdapter.items;
	                for(int i = 0;i < dialog_items.size(); i ++){
	                	if(dialog_items.get(i).equals(selectedGridDate)){ 
	                		time.setToNow();
	                		int year = time.year;   
	    		            int month = time.month+1; 
	    		            ArrayList<String> titles = new ArrayList<String>(); 
	    		            ArrayList<String> dayconfs = new ArrayList<String>(); 
	    		            try {
	    						JSONObject jObjDialog = new JSONObject(conf);
	    						// Print dates of the current week
	    			            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
	    			            SimpleDateFormat df2 = new SimpleDateFormat("d",Locale.CHINA);
	    			            String itemvalue;
	    			            Date dt;
	    			            JSONArray jArrayDialog = new JSONArray();
								try {
									dt = df1.parse(selectedGridDate);
									Log.e("title", dt.toString());
									Log.e("title",  df2.format(dt).toString());
									String day = df2.format(dt).toString();
									jArrayDialog = jObjDialog.getJSONArray(day);
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								for(int j = 0;j < jArrayDialog.length(); j++){		
									JSONObject day_conf = jArrayDialog.getJSONObject(j);
									titles.add(day_conf.getString("title"));
									dayconfs.add(day_conf.toString());
								}

	    			            
	    		            } catch (JSONException e) {
	    						// TODO Auto-generated catch block
	    						e.printStackTrace();
	    					} 
	    		            final String[] titlearray = titles.toArray(new String[titles.size()]);
	    		            final String[] dayconfarray = dayconfs.toArray(new String[dayconfs.size()]);
	                		new AlertDialog.Builder(mActivity).setTitle("当天会议列表")
	                			.setItems(titlearray, new DialogInterface.OnClickListener() {
	                	             
	                	            public void onClick(DialogInterface dialoginterface, int which) {
	                	                Intent intentShow = new Intent(mActivity,ConfShowActivity.class);
	                	                intentShow.putExtra("title", titlearray[which]);
	                	                intentShow.putExtra("conf", dayconfarray[which]);
	                	                startActivity(intentShow);
	                	                // TODO Auto-generated method stub
	                	            }
	                	        }).show();
	    		           
	    		            break;
	                	}
	                }
	                //((CalendarAdapter) parent.getAdapter()).setSelected(v);

	                showToast(selectedGridDate);

	            }
	        });
			return view;
		}

		
		
		protected void setNextMonth() {
	        if (month.get(GregorianCalendar.MONTH) == month
	                .getActualMaximum(GregorianCalendar.MONTH)) {
	            month.set((month.get(GregorianCalendar.YEAR) + 1),
	                    month.getActualMinimum(GregorianCalendar.MONTH), 1);
	        } else {
	            month.set(GregorianCalendar.MONTH,
	                    month.get(GregorianCalendar.MONTH) + 1);
	        }

	    }

	    protected void setPreviousMonth() {
	        if (month.get(GregorianCalendar.MONTH) == month
	                .getActualMinimum(GregorianCalendar.MONTH)) {
	            month.set((month.get(GregorianCalendar.YEAR) - 1),
	                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
	        } else {
	            month.set(GregorianCalendar.MONTH,
	                    month.get(GregorianCalendar.MONTH) - 1);
	        }

	    }

	    protected void showToast(String string) {
	        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();

	    }

	    public void refreshCalendar() {
	        TextView title = (TextView) getActivity().findViewById(R.id.title);

	        adapter.refreshDays();
	        adapter.notifyDataSetChanged();
	        handler1.post(calendarUpdater); // generate some calendar items

	        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	    }
	    
	    public Runnable calendarUpdater = new Runnable() {

	        @Override
	        public void run() {
	        	 items.clear();
		            conf = IndexActivity.conferences;
		            time.setToNow();
		            int year = time.year;   
		            int month = time.month+1;     
		            try {
						JSONObject jObj = new JSONObject(conf);
						// Print dates of the current week
			            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
			            String itemvalue;
			            Iterator<Object> keys = jObj.keys();
			            while (keys.hasNext()) {
			            	String key = String.valueOf(keys.next());      
			                itemvalue = df.format(itemmonth.getTime());
			                itemmonth.add(GregorianCalendar.DATE, 1);
                            String sdate = year + "-" + month + "-" + key;
                            Date date;
                            String format;
							try {
								date = df.parse(sdate);
								format = df.format(date);
								Log.e("title", df.toString());
							    items.add(format);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                            

			            }

			            adapter.setItems(items);
			            adapter.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
		            
		            
		        }
		    };
	    
	    
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			mActivity = getActivity();
			mParent = getView();

			mTitle = (TitleView) mParent.findViewById(R.id.mtitle);
			mTitle.setTitle(R.string.title_activity_calender);
			mTitle.setLeftButton("", new OnLeftButtonClickListener(){

				@Override
				public void onClick(View button) {
					//mActivity.finish();
				}
				
			});
			mTitle.setRightButton("", new OnRightButtonClickListener() {

				@Override
				public void onClick(View button) {
					((IndexActivity) button.getContext()).showUnableMsg(button);
					//goHelpActivity();
				}
			});

		}

		//private void goHelpActivity() {
			//Intent intent = new Intent(mActivity, HelpActivity.class);
			//startActivity(intent);
		//}

		@Override
		public void onHiddenChanged(boolean hidden) {
			super.onHiddenChanged(hidden);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
		}
		
		
}



