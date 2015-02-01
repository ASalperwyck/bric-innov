package fr.isen.besopraclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MapActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		ImageButton b = (ImageButton) this.findViewById(R.id.imageButtonMap);
		b.setImageResource(R.drawable.ic_map_back);
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
			Intent intent = new Intent(MapActivity.this, AccountActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("search")){
			Intent intent = new Intent(MapActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("map")){

		}
		else if(tag.equals("cart")){
			Intent intent = new Intent(MapActivity.this, CartActivity.class);
			startActivity(intent);
			finish();
		}
		else if(tag.equals("adviser")){
			Intent intent = new Intent(MapActivity.this, AdviserActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
