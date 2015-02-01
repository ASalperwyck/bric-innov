package fr.isen.besopraclient;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import fr.isen.besopraclient.Adapter.ListForProductAdapter;
import fr.isen.besopraclient.data.DataManager;
import fr.isen.besopraclient.data.GetCategoryData;
import fr.isen.besopraclient.data.GetProductData;
import fr.isen.besopraclient.model.Category;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

//Activité correspondant à l'onglet recherche
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.checkRegistration();
		
		ImageButton b = (ImageButton) this.findViewById(R.id.imageButtonSearch);
		b.setImageResource(R.drawable.ic_search_back);
		
		Spinner mySpinnerViewCategory = (Spinner) this.findViewById(R.id.categorySpinner);
		Spinner mySpinnerViewSubCategory = (Spinner) this.findViewById(R.id.subCategorySpinner);
		ListView myProductListView = (ListView) this.findViewById(R.id.productListView);
		ArrayAdapter<Category> customAdapter = new ArrayAdapter<Category>(this,android.R.layout.simple_spinner_item, DataManager.getCategoryList());
		final ArrayAdapter<Category> customSubAdapter = new ArrayAdapter<Category>(this,android.R.layout.simple_spinner_item, DataManager.getSubCategoryOf(-1));
		final ListForProductAdapter customProductAdapter = new ListForProductAdapter(this,R.layout.row_layout,DataManager.getProductList());
		
		mySpinnerViewCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    }

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Category c = (Category) parent.getItemAtPosition(position);
				customSubAdapter.clear();
				customSubAdapter.addAll(DataManager.getSubCategoryOf(c.getId()));
				customProductAdapter.clear();
				customProductAdapter.addAll(DataManager.getProductOfCategory(c.getId()));
			}
		});
		
		mySpinnerViewSubCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    }

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Category subc = (Category) parent.getItemAtPosition(position);
				customProductAdapter.clear();
				customProductAdapter.addAll(DataManager.getProductOfSubCategory(subc.getId()));
			}
		});
		
		mySpinnerViewCategory.setAdapter(customAdapter);
		mySpinnerViewSubCategory.setAdapter(customSubAdapter);
		myProductListView.setAdapter(customProductAdapter);
		
		new GetProductData().execute();
		new GetCategoryData(customAdapter).execute();
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
	
	public void changeActivityClick(View v){
		String tag = (String)v.getTag();
		if(tag.equals("account")){
			Intent intent = new Intent(MainActivity.this, AccountActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("search")){
			
		}
		else if(tag.equals("map")){
			Intent intent = new Intent(MainActivity.this, MapActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("cart")){
			Intent intent = new Intent(MainActivity.this, CartActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("adviser")){
			Intent intent = new Intent(MainActivity.this, AdviserActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	///Partie Accès au servcice de notifications de Google
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	 
	/**
    * Substitute you own sender ID here. This is the project number you got
    * from the API Console, as described in "Getting Started."
    */
   String SENDER_ID = "10938177677";

   /**
    * Tag used on log messages.
    */
   static final String TAG = "MainActivity";
   
   TextView mDisplay;
   GoogleCloudMessaging gcm;
   AtomicInteger msgId = new AtomicInteger();
   SharedPreferences prefs;
   Context context;

   String regid;
   
   private void checkRegistration(){	
	   //Google Play part
	   context = getApplicationContext();  
       gcm = GoogleCloudMessaging.getInstance(this);
       regid = getRegistrationId(context);

       if (regid.isEmpty()) {
           registerInBackground();
       }
   }
	
	 /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }	
	
	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
	
	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the regID in your app is up to you.
	    return getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}
	
	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	private void registerInBackground() {
	    new AsyncTask<Void, Void, String>(){
	    	@Override
	    	protected String doInBackground(Void... params) {
	            String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(context);
	                }
	                regid = gcm.register(SENDER_ID);
	                msg = "Device registered, registration ID=" + regid;

	                // You should send the registration ID to your server over HTTP,
	                // so it can use GCM/HTTP or CCS to send messages to your app.
	                // The request to your server should be authenticated if your app
	                // is using accounts.
	                

	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the
	                // message using the 'from' address in the message.

	                // Persist the regID - no need to register again.
	                storeRegistrationId(context, regid);
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            return msg;
	        }

	    	 @Override
	         protected void onPostExecute(String msg) {
	    		 System.out.println(msg);
	         }

	    }.execute(null, null, null);
	}
}
