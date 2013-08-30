package com.algosys.devi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.algosys.devi.data.Data;



import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class Launcher extends Activity {
	Button submit;
	EditText name, city, email, phno;
	Context context;
	ProgressDialog prg;
	String result;
//	OnlineRegistration online;
	String mPhoneNumber;
	boolean chkForm = true;
	int dialogMessage = -1;
	static String message;
	Data data;
	SharedPreferences preferences;
	SharedPreferences.Editor prefEditor;
	boolean firstTime = true;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        preferences = getSharedPreferences("Devi_App", MODE_PRIVATE);
        prefEditor = preferences.edit();
        firstTime = preferences.getBoolean("FIRST_TIME", true);
        if(!firstTime){
        	Intent i = new Intent(Launcher.this,DashBoard.class);
        	startActivity(i);
        	finish();
        }
        setContentView(R.layout.activity_launcher);
       
        submit=(Button)findViewById(R.id.button_prev);
        name = (EditText)findViewById(R.id.editText_name);
        city = (EditText)findViewById(R.id.editText_city);
        email = (EditText)findViewById(R.id.editText_email);
        phno = (EditText)findViewById(R.id.editText_phno);
        data = new Data(context);
       // online = new OnlineRegistration();
      //activationKey = (EditText)findViewById(R.id.editText_activationKey);
        
        TelephonyManager tMgr =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getDeviceId();
   
        
        Log.i("log_info","phone imei number is " +mPhoneNumber );
       
        
        
    }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
            submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent i = new Intent(Launcher.this,DashBoard.class);
				startActivity(i);*/
				boolean phvalid = isPhoneValid(phno.getText().toString());
				boolean emailvalid = isEmailValid(email.getText().toString());
				Log.i("log_info", "email  valid " +emailvalid);
				if(name.getText().toString().equalsIgnoreCase("") || name.getText().toString() == null){
					chkForm = false;
					dialogMessage = 1;
					
				}
				else if(city.getText().toString().equalsIgnoreCase("") || city.getText().toString() == null){
					
					chkForm = false;
					dialogMessage = 2;
					
				}else if(phno.getText().toString().equalsIgnoreCase("") || phno.getText().toString() == null){
					
					
					chkForm = false;
					dialogMessage = 3;
					
				
			}
				
				else if(email.getText().toString().equalsIgnoreCase("") || email.getText().toString() == null){
					
				
						chkForm = false;
						dialogMessage = 4;
						
					
			}else if(!phvalid){
				chkForm = false;
				dialogMessage = 5;
			}else if(!emailvalid){
				chkForm = false;
				dialogMessage = 6;
			}else{
				chkForm = true;
			}
			
				 
					
				
				
               if(!chkForm){
            	 
				new OnlineRegistration().execute();
            	  
               }else{
            	   AlertDialog.Builder builder = new AlertDialog.Builder(Launcher.this);

   			    builder.setTitle("Error:");
   			    if(dialogMessage == 1){
   			    builder.setMessage("Please Enter Name");
   			    }else if(dialogMessage == 2){
   			     builder.setMessage("Please Enter City");
   			    }else if (dialogMessage == 3){
   			     builder.setMessage("Please Enter Phone Number");
   			    }
   			    else if (dialogMessage == 4){
   			     builder.setMessage("Please Enter E-mail Id");
   			    }
   			    else if (dialogMessage == 5){
   			     builder.setMessage("Please Enter valid Phone Number ");
   			    }
   			 else if (dialogMessage == 6){
   			     builder.setMessage("Please Enter valid Email-id");
   			    }
   			    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

   			        public void onClick(DialogInterface dialog, int which) {
   			            // Do nothing but close the dialog

   			            dialog.dismiss();
   			         /* Intent i = new Intent (context,DashBoard.class);
   			          startActivity(i);*/
   			        }

   			    });

   			   /* builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

   			        @Override
   			        public void onClick(DialogInterface dialog, int which) {
   			            // Do nothing
   			            dialog.dismiss();
   			        }
   			    });
   */
   			    AlertDialog alert = builder.create();
   			    alert.show();
   				
   				 
               }
				
			}
		});
	}

	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isPhoneValid(String phone) {
		boolean isValid = false;

		if (phone.length() == 10) {

			isValid = true;
		}
		return isValid;
	}

	public class OnlineRegistration extends AsyncTask<Void, Void, Boolean> {

		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			prg = ProgressDialog.show(context, "Please Wait",
					"Registering User");
			// Log.i("uni_info", "Checking status updates...");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Log.i("log_info", "inbackground");
		boolean net = hasActiveInternetConnection(context);
			// boolean net = isNetworkAvailable(context);
      	  if(net){
			
			 /* HttpClient httpclient = new DefaultHttpClient(); HttpPost
			  httppost = new
			  HttpPost("http://idc.thesynapses.com/services/user_registration.php"
			  );
			  try {
				  
				  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2); 
			  nameValuePairs.add(new BasicNameValuePair("name", name.getText().toString()));
			  nameValuePairs.add(new BasicNameValuePair("email",email.getText().toString()));
			  nameValuePairs.add(new BasicNameValuePair("city", city.getText().toString()));
			  nameValuePairs.add(new BasicNameValuePair("mobile",phno.getText().toString()));
			  nameValuePairs.add(new BasicNameValuePair("imei",mPhoneNumber));
			 
			  
			  
			  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			  HttpResponse response = httpclient.execute(httppost);
			  
			  HttpEntity entity = response.getEntity(); InputStream is =
			  entity.getContent();
			  
			  BufferedReader reader = new BufferedReader(new
			  InputStreamReader(is, "UTF-8"), 8); StringBuilder sb = new
			  StringBuilder();
			  
			 String line = null; while ((line = reader.readLine()) != null) {
			 sb.append(line + "\n");
			 } //result = sb.toString();
			  
			  //result = EntityUtils.toString(entity);
			  
			  Log.i("log_info","response is "+result); } catch
			  (UnsupportedEncodingException e) {
				  // TODO Auto-generated catch  block 
				  e.printStackTrace(); Log.i("log_info","response is "+e); }
			 catch (ClientProtocolException e) { 
			  e.printStackTrace(); 
			  Log.i("log_info","response is "+e); }
			  catch (IOException e) {
			  e.printStackTrace();
			  Log.i("log_info","response is "+e); }*/
			 
			result = "{\"Message\":\"success\",\"VoucherId\":\"123\",\"ValidityPeriod\":\"1Year\",\"ParentId\":\"001\",\"ParentName\":\"Vikram Singh\",\"PArentAddress\":\"XYZ street, Pune\",\"ContactDetails\":\"98765640123\",\"EmailID\":\"xyz@gmail.com\"}";
			return true;
      	  }else{
      	 return false;
      	 }
      	 
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			// Log.i("uni_info", "In onProgressUpdate..." + values[0]);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			prg.cancel();
			prg.dismiss();
			Log.i("log_info", "In onPostExecute..." + result);
			if (result == true) {
				// Log.i("uni_info", "Refreshing view...");

				parseValues();
				
			}else{
				showDialog();
				Toast.makeText(context,"Your Internet is not working", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void parseValues() {
		JSONObject jObject;
		try {
			Log.i("log_info", "Parse value");
			jObject = new JSONObject(result);
			// result =
			// "{\"VoucherId\":\"123\",\"ValidityPeriod\":\"1 Year\",\"ParentId\":\"001\",\"PArentAddress\":\"XYZ street, Pune\",\"ContactDetails\":\"98765640123\",\"EmailID\":\"xyz@gmail.com\"}";
			// String signedArray = jObject.getString("status");
		    message = jObject.getString("Message");
		    if(message.equals("success")){
		    	String vId = jObject.getString("VoucherId");
				String vPeriod = jObject.getString("ValidityPeriod");
				String pId = jObject.getString("ParentId");
				String pName = jObject.getString("ParentName");
				String pAdd = jObject.getString("PArentAddress");
				String pContact = jObject.getString("ContactDetails");
				String pEmail = jObject.getString("EmailID");
				
				data.open();
				data.insertParentRecord(vId, vPeriod, pId,pName, pAdd, pContact, pEmail);
				Log.i("log_info", "data inserted");
				
				Cursor c = data.getAllParentRecords();
				Log.i("log_info", "data inserted cursor is "+c.getCount());
				if(c != null){
					c.moveToFirst();
					if(c.moveToNext()){
						String contact = c.getString(0);
						Log.i("log_info", "contac is "+contact);
					}
				}
				data.close();
				// String userId = jObject.getString("user_id");
				// Log.i("log_info","VoucherId is  "+vId);
				// Log.i("log_info","VoucherPeriod is  "+vPeriod);
				// Log.i("log_info","ParentId is  "+pId);
				// Log.i("log_info","ParentAddress is  "+pAdd);
				// Log.i("log_info","ContactDetails is  "+pContact);
				// Log.i("log_info","EmailID is  "+pEmail);
				// editor.putString("KEYARRAY", signedArray);
				// editor.putString("UID", userId);
				// editor.commit();
				prefEditor.putBoolean("FIRST_TIME", false);
				prefEditor.commit();
				Intent i = new Intent(context, DashBoard.class);
				startActivity(i);
				finish();
		    }else{
		    	 AlertDialog.Builder builder = new AlertDialog.Builder(Launcher.this);

	   			    builder.setTitle("Error:");
	   			  
	   			    builder.setMessage("You are not subscribed");
	   			   
	   			    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

	   			        public void onClick(DialogInterface dialog, int which) {
	   			            // Do nothing but close the dialog

	   			            dialog.dismiss();
	   			         /* Intent i = new Intent (context,DashBoard.class);
	   			          startActivity(i);*/
	   			        }

	   			    });
                    AlertDialog alert = builder.create();
	   			    alert.show();
		    }
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("log_info", "inside catch  " + e);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_launcher, menu);
		return true;
	}
 
	
	private  boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager 
	         = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
	
	
	public  boolean hasActiveInternetConnection(Context context) {
	    if (isNetworkAvailable(context)) {
	        try {
	            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
	            urlc.setRequestProperty("User-Agent", "Test");
	            urlc.setRequestProperty("Connection", "close");
	            urlc.setConnectTimeout(1500); 
	            urlc.connect();
	            return (urlc.getResponseCode() == 200);
	        } catch (IOException e) {
	            Log.e("log_info", "Error checking internet connection", e);
	        }
	    } else {
	        Log.d("log_info", "No network available!");
	    }
	    return false;
	}
	
	public void showDialog(){
		AlertDialog.Builder dialog = new Builder(context);
		dialog.setMessage(
				"Do you want to enable network connection?").setIcon(
				R.drawable.alert).setTitle(
				"This application requires internet access").setCancelable(
				false).setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						Intent networkSettingsPage = new Intent();
						networkSettingsPage
								.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						startActivity(networkSettingsPage);
					}
				}).setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						finish();
					}
				}).create().show();
	}
}
