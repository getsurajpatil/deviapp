package com.algosys.devi;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PoojaOrder extends Activity {
	EditText name;
	EditText myDate;
	EditText address;
	EditText mobile;
	Spinner payment;
	Button order;
	String[] paymentOptions = {"Payment Option 1","Payment Option 1","Payment Option 3"};
	Context context;
	public int mYear;
	public int mMonth;
	public int mDay;
	boolean chkForm = true;
	int dialogMessage = -1;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.onlinepooja);
	    context = this;
	   
	   
	   name = (EditText)findViewById(R.id.edit_order_name);
	   myDate = (EditText)findViewById(R.id.edit_date);
	  
	   address = (EditText)findViewById(R.id.edit_address);
	   mobile =  (EditText)findViewById(R.id.edit_order_mobile);
	   payment = (Spinner)findViewById(R.id.spinner_payment);
	   order = (Button)findViewById(R.id.button_confirm);
	   ArrayAdapter<String> a1 = new ArrayAdapter<String>(
				getApplicationContext(),
				android.R.layout.simple_spinner_item, paymentOptions);
		a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		payment.setAdapter(a1);
		
		 Date currentDate = new Date(System.currentTimeMillis());
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
			
	    // TODO Auto-generated method stub
			updateDisplay();
			myDate.setOnClickListener(new OnClickListener() {
				
				

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDialog(2);
				}
			});
			
			order.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					boolean phvalid = isPhoneValid(mobile.getText().toString());
					boolean dateValid = isDateValid(myDate.getText().toString());
					if(name.getText().toString().equalsIgnoreCase("") || name.getText().toString() == null){
						chkForm = false;
						dialogMessage = 1;
						
					}else if(mobile.getText().toString().equalsIgnoreCase("") || mobile.getText().toString() == null){
						
						chkForm = false;
						dialogMessage = 2;
						
					}else if(address.getText().toString().equalsIgnoreCase("") || address.getText().toString() == null){
						
						
						chkForm = false;
						dialogMessage = 3;
						
					
				}else if(!phvalid){
					chkForm = false;
					dialogMessage = 4;
				}else if(!dateValid){
					chkForm = false;
					dialogMessage = 5;
				}else{
					chkForm = true;
				}
					
					if(chkForm){
						Toast.makeText(context,"ordered successfully", Toast.LENGTH_SHORT).show();
					}else{
						AlertDialog.Builder builder = new AlertDialog.Builder(PoojaOrder.this);

		   			    builder.setTitle("Error:");
		   			    if(dialogMessage == 1){
		   			    builder.setMessage("Please Enter Name");
		   			    }else if(dialogMessage == 2){
		   			     builder.setMessage("Please Enter Mobile");
		   			    }else if (dialogMessage == 3){
		   			     builder.setMessage("Please Enter Address");
		   			    }
		   			   
		   			    else if (dialogMessage == 4){
		   			     builder.setMessage("Please Enter valid Phone Number ");
		   			    }
		   			 else if (dialogMessage == 5){
		   			     builder.setMessage("Date cannot be earlier than today");
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
	
	
	public static boolean isPhoneValid(String phone) {
		boolean isValid = false;

		if (phone.length() == 10) {

			isValid = true;
		}
		return isValid;
	}
	
	public boolean isDateValid(String date) {
		boolean isValid = false;
		Calendar calendar = Calendar.getInstance();
		Calendar calendar1 = Calendar.getInstance();
		long deviceTime = calendar.getTimeInMillis();
		calendar1.set(mYear, mMonth, mDay);
		long enteredDate = calendar1.getTimeInMillis();
		if(enteredDate > deviceTime){
			isValid = true;
		}
		return isValid;
	}
	
	private void updateDisplay() {
		myDate.setText(new StringBuilder()

		.append(mDay).append("/").append(mMonth + 1).append("/").append(mYear)
				.append(" "));

		
	  
	}

protected Dialog onCreateDialog(int id) {
	switch (id) {
	
	case 2:
		DatePickerDialog dateDialog = new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
				mDay);
		

		return dateDialog;
	}
	return null;
}
/*protected void onPrepareDialog(int id, Dialog dialog) {
	switch (id) {
	case DATE_DIALOG_ID:
		((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
		break;
	}
}*/

private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		mYear = year;
		mMonth = monthOfYear;
		mDay = dayOfMonth;
		updateDisplay();
	}
};


}
