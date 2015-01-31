package fr.isen.besopraclient.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.isen.besopraclient.config.Config;
import fr.isen.besopraclient.model.Category;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;


public class GetCategoryData extends AsyncTask<String, Integer, List<Category>>
{
	private final static String GET_CATEGORIES_SERVICE_URI = "Category/categories";
	
	private HttpClient client;
	private ArrayAdapter<Category> adapter;

	public GetCategoryData(ArrayAdapter<Category> adapter){
		this.client = new DefaultHttpClient();
		this.adapter = adapter;
	}

	@Override
	protected void onPostExecute(List<Category> data) {
		DataManager.setCategoryList(data);
		this.adapter.clear();
		this.adapter.addAll(DataManager.getCategoryOnly());
		this.adapter.notifyDataSetChanged();
		super.onPostExecute(data);
	}

	@Override
	protected List<Category> doInBackground(String... params) {
		try {
			JSONArray categoriesJsonArray = getCategoryData();
			List<Category> categories = new ArrayList<Category>();
			
			if(categoriesJsonArray != null){
				for(int i=0; i< categoriesJsonArray.length(); i++)
				{
					JSONObject categoryJson = categoriesJsonArray.getJSONObject(i);
					Category c = new Category(Integer.parseInt(categoryJson.getString("Id")), categoryJson.getString("Name"), categoryJson.getString("Advisers"), categoryJson.getString("Description"), categoryJson.getString("SubCategoryOf").equals("null") ? -1 : Integer.parseInt(categoryJson.getString("SubCategoryOf")));
					categories.add(c);
				}	
			}

			return categories;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public JSONArray getCategoryData() throws ClientProtocolException, IOException, JSONException
	{
		HttpGet get = new HttpGet(Config.BASE_SERVICE_URI + GET_CATEGORIES_SERVICE_URI);
		
		HttpResponse response = this.client.execute(get);
		int status = response.getStatusLine().getStatusCode();

		if(status == 200) //sucess
		{
			HttpEntity e = response.getEntity();
			String data = EntityUtils.toString(e);
			JSONArray categoriesData = new JSONArray(data);

			return categoriesData;
		}
		else
		{
			return null;
		}
	}

}