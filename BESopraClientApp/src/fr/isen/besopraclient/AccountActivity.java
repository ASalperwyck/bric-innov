package fr.isen.besopraclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;

public class AccountActivity extends ActionBarActivity {
	
	public static final String PROPERTY_ACCOUNT = "account_name";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		
		String accountName = getAccountName(getApplicationContext());
        if(!accountName.isEmpty()){
    	   EditText et = (EditText)this.findViewById(R.id.accountNameEditText);
    	   et.setText(accountName, TextView.BufferType.EDITABLE);
    	   Button b = (Button)this.findViewById(R.id.validateButton);
    	   b.setText(R.string.change);
        }
		
		ImageButton b = (ImageButton) this.findViewById(R.id.imageButtonAccount);
		b.setImageResource(R.drawable.ic_account_back);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void storeAccountName(Context context, String accountName) {
        final SharedPreferences prefs = getSharedPreferences(AccountActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_ACCOUNT, accountName);
        editor.commit();
    }	
	
	private String getAccountName(Context context) {
	    final SharedPreferences prefs = getSharedPreferences(AccountActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	    String accountName = prefs.getString(PROPERTY_ACCOUNT, "");
	    if (accountName.isEmpty()) {
	        return "";
	    }	    
	    return accountName;
	}
	
	public void validateClick(View v){
		EditText et = (EditText)this.findViewById(R.id.accountNameEditText);
		if(!et.getText().toString().isEmpty()){
			this.storeAccountName(getApplicationContext(), et.getText().toString());
			Button b = (Button)this.findViewById(R.id.validateButton);
	    	b.setText(R.string.change);
		}
	}
	
	public void changeActivityClick(View v){
		String tag = (String)v.getTag();
		if(tag.equals("account")){
			
		}
		else if(tag.equals("search")){
			Intent intent = new Intent(AccountActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("map")){
			Intent intent = new Intent(AccountActivity.this, MapActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("cart")){
			Intent intent = new Intent(AccountActivity.this, CartActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("adviser")){
			Intent intent = new Intent(AccountActivity.this, AdviserActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
