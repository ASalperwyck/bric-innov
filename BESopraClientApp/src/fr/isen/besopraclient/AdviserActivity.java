package fr.isen.besopraclient;

import fr.isen.besopraclient.Adapter.ListForMeetingAdapter;
import fr.isen.besopraclient.data.CreateMeeting;
import fr.isen.besopraclient.data.DataManager;
import fr.isen.besopraclient.data.GetMeetingData;
import fr.isen.besopraclient.model.Category;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class AdviserActivity extends ActionBarActivity {
	
	public static final String PROPERTY_ACCOUNT = "account_name";
	public static final String PROPERTY_REG_ID = "registration_id";
	private ListForMeetingAdapter customMeetingAdapter;
	private String accountName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adviser);
		
		this.accountName = this.getAccountName(getApplicationContext());
		if(!this.accountName.isEmpty()){
			this.customMeetingAdapter = new ListForMeetingAdapter(this,R.layout.list_meeting_row_item,DataManager.getMeetingList());
			ListView myMeetingListView = (ListView) this.findViewById(R.id.meetingListView);
			myMeetingListView.setAdapter(this.customMeetingAdapter);
			
			new GetMeetingData(this.customMeetingAdapter, this.accountName).execute();
			
			//Spinner categories
    	    Spinner mySpinnerViewCategory = (Spinner) this.findViewById(R.id.categorySpinner);
	   		Spinner mySpinnerViewSubCategory = (Spinner) this.findViewById(R.id.subCategorySpinner);
	   		ArrayAdapter<Category> customAdapter = new ArrayAdapter<Category>(this,android.R.layout.simple_spinner_item, DataManager.getCategoryOnly());
	   		final ArrayAdapter<Category> customSubAdapter = new ArrayAdapter<Category>(this,android.R.layout.simple_spinner_item, DataManager.getSubCategoryOf(-1));
	   		
	   		mySpinnerViewCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
	
	   		    @Override
	   		    public void onNothingSelected(AdapterView<?> parentView) {
	   		    }
	
	   			@Override
	   			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	   				Category c = (Category) parent.getItemAtPosition(position);
	   				customSubAdapter.clear();
	   				customSubAdapter.addAll(DataManager.getSubCategoryOf(c.getId()));
	   			}
	   		});
	   		
	   		mySpinnerViewCategory.setAdapter(customAdapter);
	   		mySpinnerViewSubCategory.setAdapter(customSubAdapter);
		}
		
		ImageButton b = (ImageButton) this.findViewById(R.id.imageButtonAdviser);
		b.setImageResource(R.drawable.ic_adviser_back);
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
	
	private String getAccountName(Context context) {
	    final SharedPreferences prefs = getSharedPreferences(AccountActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	    String accountName = prefs.getString(PROPERTY_ACCOUNT, "");
	    if (accountName.isEmpty()) {
	        return "";
	    }	    
	    return accountName;
	}
	
	public void askMeetingClick(View v){
		if(!this.accountName.isEmpty()){
	   		Spinner mySpinnerViewSubCategory = (Spinner) this.findViewById(R.id.subCategorySpinner);
	   		final Category subCategory = (Category)mySpinnerViewSubCategory.getSelectedItem();
	   		final String name = this.accountName;
	   		if(subCategory != null){
	   			//Confirmation dialog
	   			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	   	        builder.setMessage("Etes-vous sûr de vouloir prendre un rendez-vous pour la catégorie " + subCategory.getName() + " ?")
	   	               .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
	   	                   public void onClick(DialogInterface dialog, int id) {
	   	                	   SharedPreferences sp = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	   	                	   String registrationId = sp.getString(PROPERTY_REG_ID, "");
	   	                	   new CreateMeeting(customMeetingAdapter, subCategory.getId(), name, subCategory.getName(), "", registrationId).execute();
	   	                   }
	   	               })
	   	               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	   	                   public void onClick(DialogInterface dialog, int id) {
	   	                       // User cancelled the dialog
	   	                   }
	   	               });
	   	        // Create the AlertDialog object and show it
	   	        builder.create().show();

	   		}
	   		else{
	   			
	   		}
		}
		else{
			
		}
	}
	
	public void scanProductClick(View v){
		
	}
	

	public void changeActivityClick(View v){
		String tag = (String)v.getTag();
		if(tag.equals("account")){
			Intent intent = new Intent(AdviserActivity.this, AccountActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("search")){
			Intent intent = new Intent(AdviserActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("map")){
			Intent intent = new Intent(AdviserActivity.this, MapActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("cart")){
			Intent intent = new Intent(AdviserActivity.this, CartActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("adviser")){

		}
	}
}
