package com.algosys.devi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.algosys.devi.Wallpapers.MyImageTask;
import com.algosys.devi.data.Data;




import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.QuickContact;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class OnlinePooja extends Activity {
	EditText time,date,details,address;
	ExpandableListView poojaList;
	
	Button order;
	Spinner parent;
	public int mYear;
	public int mMonth;
	 public String am_pm;
	public int mDay;
	public int mHour;
	public int mMinute;
	//String[] allPoojaNames = {"Bhogi Puja","Panchamrut Snan Puja","Panchamrut Snan Puja","Mahapuja","Alankarpuja","devipuja","Alankarpuja","Alankarpuja","Alankarpuja"};
	Data data;
    Context context;
  
    ArrayList<String> selectedPoojas = new ArrayList<String>();
    HashMap<String,String> allPoojaDetails = new HashMap<String,String>();
    ArrayList<String> allPoojaNames = new ArrayList<String>();
    HashMap <String,String> allPoojaAmount = new HashMap<String,String>();
    String[] allpoojanames;
    String[] allpoojadetails;
    String[] allpoojaamount;
    String result;
    ProgressDialog prg;
    int width,height;
    int total = 0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.pooja_details);
	    context = this;
	    Display display = getWindowManager().getDefaultDisplay();

		width = display.getWidth();
		height = display.getHeight();
	    showParentDialog();
	    data = new Data(context);
	  
	    poojaList = (ExpandableListView)findViewById(R.id.list_pujadatails);
	    order = (Button)findViewById(R.id.button_confirm);
	    
	    order.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(selectedPoojas.size() != 0){
		   showSelectedDialog();
	   }
		}
	});
	   poojaList.setOnGroupClickListener(new OnGroupClickListener() {
		
		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			// TODO Auto-generated method stub
			Log.i("log_info","group clicked");
			return false;
		}
	});
	  
	   // allpoojanames = new String[allPoojaNames.size()];allPoojaNames.toArray(allpoojanames);
	    //allpoojanames = new String[allPoojaNames.size()];allPoojaNames.toArray(allpoojanames);
	   // allpoojanames = new String[allPoojaNames.size()];allPoojaNames.toArray(allpoojanames);
	  
	   // poojaList.onRestoreInstanceState(state); 
	   
	  
		
		
	
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		total = 0;
	}

	public void showSelectedDialog(){
		final Dialog selecteddialog = new Dialog(this);
		Window window = selecteddialog.getWindow();
		window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		 selecteddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 selecteddialog.setCancelable(false);
		   total = 0;
		   // dialog.setTitle("Parent Details");
		 selecteddialog.setContentView(R.layout.selected_pooja_dialog);
			
		 LinearLayout.LayoutParams imagelayoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, height/3);
			
		
			

			//final EditText id = (EditText) dialog.findViewById(R.id.edit_parent_id);
		    ListView list = (ListView)selecteddialog.findViewById(R.id.list_selected);
			TextView totalamount =(TextView) selecteddialog.findViewById(R.id.text_total_amount);
		    LinearLayout layout = (LinearLayout)selecteddialog.findViewById(R.id.listlayout);
		    Button proceed = (Button) selecteddialog.findViewById(R.id.button_next);
		     Button cancel = (Button) selecteddialog.findViewById(R.id.button_back);
		    // layout.setLayoutParams(imagelayoutParams);
		    //allpoojanames = new String[allPoojaNames.size()];allPoojaNames.toArray(allpoojanames);
		    /*String[] selected = new String[selectedPoojas.size()];selectedPoojas.toArray(selected);
		    list.setAdapter(new );*/
		    final ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, selectedPoojas );
			list.setAdapter(adapter);
			 //image.setImageBitmap(btmArray.get(position));
			 //btm = getImage("http://1.bp.blogspot.com/-fCdkZvJUUDQ/UJQqKZSwhvI/AAAAAAAAGT4/prhh8KK4emk/s400/konark-sun-temple-orissa.jpg");
			 
			for(int i = 0;i<selectedPoojas.size();i++){
				
				total += Integer.parseInt(allPoojaAmount.get(selectedPoojas.get(i)));
				
			}
			
			totalamount.setText("Rs."+String.valueOf(total));
			
			proceed.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					selecteddialog.dismiss();
					Intent i = new Intent(OnlinePooja.this,PoojaOrder.class);
					startActivity(i);
					finish();
				}
			});

			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					selecteddialog.dismiss();
					
				}
			});


			selecteddialog.show();
	}
	
	

	public void showPoojaDetailsDialog(){
		
	}
	
	public void showParentDialog(){
		 final Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Window window = dialog.getWindow();
			window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			dialog.setCancelable(false);
		    String parentId,parentaddress,parentPhone,parentEmail;
		   // dialog.setTitle("Parent Details");
			dialog.setContentView(R.layout.parent_detail_dialog);
			
			
			
		
			
   
			//final EditText id = (EditText) dialog.findViewById(R.id.edit_parent_id);
			final ImageView image = (ImageView)dialog.findViewById(R.id.parent_image);
			final TextView name = (TextView)dialog.findViewById(R.id.parent_name);
			final EditText address = (EditText) dialog.findViewById(R.id.edit_parent_address);
			final EditText phone = (EditText) dialog.findViewById(R.id.edit_parent_phone);
			final EditText email = (EditText) dialog.findViewById(R.id.edit_parent_email);
		    Button proceed = (Button) dialog.findViewById(R.id.button_proceed);
		    Button cancel = (Button) dialog.findViewById(R.id.button_cancel);
		    if(data == null){
		    	data = new Data(context);
		    	Log.i("log_info", "data is null");
		    }
		    data.open();
		    Cursor c = data.getAllParentRecords();
		    if(c != null){
				c.moveToFirst();
				parentId = c.getString(0);
				parentaddress = c.getString(1);
				parentPhone = c.getString(2);
				parentEmail = c.getString(3);
				Log.i("log_info", "parent data is" +parentId+parentaddress+parentPhone+parentEmail);
				name.setText(parentId);
				address.setText(parentaddress);
				phone.setText(parentPhone);
				email.setText(parentEmail);
			}
			
			c.close();
		
			data.close();
			
			 //image.setImageBitmap(btmArray.get(position));
			 //btm = getImage("http://1.bp.blogspot.com/-fCdkZvJUUDQ/UJQqKZSwhvI/AAAAAAAAGT4/prhh8KK4emk/s400/konark-sun-temple-orissa.jpg");
			
			proceed.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					new PoojaTask().execute();
				}
			});

			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					finish();
				}
			});


			dialog.show();
	}
	
	public class PoojaTask extends AsyncTask<Void, Void, Boolean> {

		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			prg = ProgressDialog.show(context, "Please Wait",
					"Loading Pooja Details");
			// Log.i("uni_info", "Checking status updates...");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			/* HttpClient httpclient = new DefaultHttpClient(); 
			  HttpPost httppost = new HttpPost("http://idc.thesynapses.com/services/user_registration.php");
			  try {
				  
			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2); 
			  nameValuePairs.add(new BasicNameValuePair("message",message));
			  nameValuePairs.add(new BasicNameValuePair("count",count));
			  nameValuePairs.add(new BasicNameValuePair("startIconid",startIconId));
			 
			 
			  
			  
			  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			  HttpResponse response = httpclient.execute(httppost);
			  
			  HttpEntity entity = response.getEntity();
			  InputStream is = entity.getContent();
			  
			  BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8); 
			  StringBuilder sb = new StringBuilder();
			  
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
			 
			result ="{\"items\":[{\"PoojaName\":\"Bhogi Puja\",\"PoojaDetails\":\"Khan,Naral Outi\",\"Amount\":\"351\"},"
					+"{\"PoojaName\":\"Panchamrut Snan Puja\",\"PoojaDetails\":\"Abhishek Puja\",\"Amount\":\"251\"}," 
					+"{\"PoojaName\":\"Mahapuja\",\"PoojaDetails\":\"With Sadi Choli (6 war)\",\"Amount\":\"211\"},"
					+"{\"PoojaName\":\"Akhand Nandadip\",\"PoojaDetails\":\"With Sadi Choli (9 war)\",\"Amount\":\"451\"},"
					+"{\"PoojaName\":\"Saptashati path\",\"PoojaDetails\":\"9 days (Navratra)\",\"Amount\":\"321\"},"
					+"{\"PoojaName\":\"Naivadya\",\"PoojaDetails\":\"9 days (Navratra)\",\"Amount\":\"221\"},"
					+"{\"PoojaName\":\"Panachi Prabhaval\",\"PoojaDetails\":\"Puran Poli (Veg)\",\"Amount\":\"121\"}]}";
			return true;
		
			/*Log.i("log_info", "inbackground");
			int i;
			for(i = 0;i<image_url.length;i++){
				Bitmap btm = getImage(image_url[i]);
				btmArray.add(btm);
			}
			
			return true;
		}*/
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
			Log.i("log_info", "Refreshing view...");
            parseValues();
           
            ExpandableListAdapter ea = new MyExpandableListAdapter(
    				getApplicationContext());
            poojaList.setAdapter(ea);
           /* rAdapter = new PoojaAdapter(getApplicationContext(),
    	    		allPoojaNames.size());
    	    poojaList.setAdapter(rAdapter);*/
			//grid.setAdapter(new MyAlbumGrid(context));
				
			}
		}
	}
	public class MyExpandableListAdapter extends BaseExpandableListAdapter{
		private LayoutInflater mInflator;
		public MyExpandableListAdapter(Context context){
			mInflator = LayoutInflater.from(context);
		}
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return allPoojaDetails.get(allPoojaNames.get(groupPosition));
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = convertView;
			if(v == null){
				v= mInflator.inflate(R.layout.demo, null);
			}
			
			TextView details = (TextView)v.findViewById(R.id.text_detail_name);
		    TextView amount = (TextView)v.findViewById(R.id.text_detail_amount);
		    
		    details.setText(allPoojaDetails.get(allPoojaNames.get(groupPosition)));
		    amount.setText("Rs."+allPoojaAmount.get(allPoojaNames.get(groupPosition)));
			return v;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return allPoojaNames.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return allPoojaNames.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View v = convertView;
			if(v == null){
				v= mInflator.inflate(R.layout.pooja_detail_layout, null);
			}
			
			CheckBox cb = (CheckBox)v.findViewById(R.id.check_pooja);
			cb.setTextSize(20);
			cb.setText(allPoojaNames.get(groupPosition));
			if(isExpanded){
				cb.setChecked(true);
				//selectedPoojas.add(allPoojaNames.get(groupPosition));
			}else{
				cb.setChecked(false);
				/*if(selectedPoojas.contains(allPoojaNames.get(groupPosition))){
				selectedPoojas.remove(allPoojaNames.get(groupPosition));
				}*/
			}
			// TODO Auto-generated method stub
			return v;
		}

		@Override
		public void onGroupCollapsed(int groupPosition) {
			// TODO Auto-generated method stub
			super.onGroupCollapsed(groupPosition);
			if(selectedPoojas.contains(allPoojaNames.get(groupPosition))){
				selectedPoojas.remove(allPoojaNames.get(groupPosition));
				}
		}
		@Override
		public void onGroupExpanded(int groupPosition) {
			// TODO Auto-generated method stub
			super.onGroupExpanded(groupPosition);
			selectedPoojas.add(allPoojaNames.get(groupPosition));
		}
		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
	
	public void parseValues() {
		JSONObject jObject;
		try {
			Log.i("log_info", "Parse value");
			jObject = new JSONObject(result);
			allPoojaAmount.clear();
			allPoojaNames.clear();
			//allPoojaDetails.clear();
			
			// result =
			// "{\"VoucherId\":\"123\",\"ValidityPeriod\":\"1 Year\",\"ParentId\":\"001\",\"PArentAddress\":\"XYZ street, Pune\",\"ContactDetails\":\"98765640123\",\"EmailID\":\"xyz@gmail.com\"}";
			// String signedArray = jObject.getString("status");
			JSONArray  items = jObject.getJSONArray("items");
			Log.i("log_info", "item length is "+items.length());
			for(int i = 0 ; i < items.length(); i++){
			    JSONObject item = items.getJSONObject(i);
			String poojaname = item.getString("PoojaName");
			String poojadetail = item.getString("PoojaDetails");
			String poojaamount = item.getString("Amount");
			
			Log.i("log_info","poojaname is  "+poojaname);
			Log.i("log_info","poojadetail is  "+poojadetail);
		    Log.i("log_info","poojaamount is  "+poojaamount);
		    
		   allPoojaDetails.put(poojaname,poojadetail);
		   allPoojaNames.add(poojaname);
		   allPoojaAmount.put(poojaname,poojaamount);
		    
		    
			}
		     
			// Log.i("log_info","ParentAddress is  "+pAdd);
			// Log.i("log_info","ContactDetails is  "+pContact);
			// Log.i("log_info","EmailID is  "+pEmail);
			// editor.putString("KEYARRAY", signedArray);
			// editor.putString("UID", userId);
			// editor.commit();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("log_info", "inside catch  " + e);
		}

	}
	
	

	
}
