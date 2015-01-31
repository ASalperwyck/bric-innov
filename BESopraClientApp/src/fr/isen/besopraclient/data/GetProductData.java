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
import fr.isen.besopraclient.model.Product;
import android.os.AsyncTask;
import android.util.Base64;


public class GetProductData extends AsyncTask<String, Integer, List<Product>>
{
	private final static String GET_PRODUCT_SERVICE_URI = "Product/products";
	
	private HttpClient client;

	public GetProductData(){
		this.client = new DefaultHttpClient();
	}
	

	@Override
	protected void onPostExecute(List<Product> data) {
		DataManager.setProductList(data);
		super.onPostExecute(data);
	}

	@Override
	protected List<Product> doInBackground(String... params) {
		
		try {
			List<Product> products = new ArrayList<Product>();
			JSONArray productsJsonArray = getProductData();
			
			if(productsJsonArray != null){
				for(int i=0; i< productsJsonArray.length(); i++)
				{
					JSONObject productJson = productsJsonArray.getJSONObject(i);
					Product p = new Product(Integer.parseInt(productJson.getString("Id")), productJson.getString("Title"), productJson.getString("Description"), 
							Float.parseFloat(productJson.getString("Price")), Integer.parseInt(productJson.getString("CategoryId")), Integer.parseInt(productJson.getString("SubcategoryId")), productJson.getString("Barcode"), Base64.decode(productJson.getString("Image"), Base64.DEFAULT));
					products.add(p);
				}	
			}

			return products;

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

	public JSONArray getProductData() throws ClientProtocolException, IOException, JSONException
	{
		HttpGet get = new HttpGet(Config.BASE_SERVICE_URI + GET_PRODUCT_SERVICE_URI);
		
		HttpResponse response = this.client.execute(get);
		int status = response.getStatusLine().getStatusCode();

		if(status == 200) //sucess
		{
			HttpEntity e = response.getEntity();
			String data = EntityUtils.toString(e);
			JSONArray productsData = new JSONArray(data);
			System.out.println("ProductDataOK");

			return productsData;
		}
		else
		{
			return null;
		}
	}

}