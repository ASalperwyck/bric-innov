package fr.isen.besopraclient.data;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import fr.isen.besopraclient.config.Config;
import fr.isen.besopraclient.model.Meeting;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;


public class CreateMeeting extends AsyncTask<String, Integer, Meeting>
{
	private final static String CREATE_MEETINGS_SERVICE_URI = "Meeting/createMeeting/?";
	
	private HttpClient httpClient;
	private int categoryId;
	private String client;
	private String reason;
	private String phone;
	private String registrationId;
	private ArrayAdapter<Meeting> adapter;

	public CreateMeeting(ArrayAdapter<Meeting> adapter, int categoryId, String client, String reason, String phone, String registrationId){
		this.client = client;
		this.categoryId = categoryId;
		this.reason = reason;
		this.phone = phone;
		this.registrationId = registrationId;
		this.adapter = adapter;
		this.httpClient = new DefaultHttpClient();
	}

	@Override
	protected void onPostExecute(Meeting data) {
		super.onPostExecute(data);
		new GetMeetingData(adapter, client).execute();
	}

	@Override
	protected Meeting doInBackground(String... params) {
		try {
			createMeeting();
			return null;

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

	public void createMeeting() throws ClientProtocolException, IOException, JSONException
	{
		HttpGet get = new HttpGet(Config.BASE_SERVICE_URI + CREATE_MEETINGS_SERVICE_URI + "id="+ this.categoryId + "&client=" + this.client + "&reason=" + this.reason + "&phone=" + this.phone + "&registrationId=" + this.registrationId);
		      
		HttpResponse response = this.httpClient.execute(get);
		int status = response.getStatusLine().getStatusCode();

		if(status == 200) //sucess
		{
			HttpEntity e = response.getEntity();
		}
	}

}