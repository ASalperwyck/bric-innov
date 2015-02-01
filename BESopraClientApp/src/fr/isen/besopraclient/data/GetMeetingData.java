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
import fr.isen.besopraclient.model.Meeting;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;


public class GetMeetingData extends AsyncTask<String, Integer, List<Meeting>>
{
	private final static String GET_MEETINGS_SERVICE_URI = "Meeting/meetingsByClient/?cl=";
	
	private HttpClient httpClient;
	private ArrayAdapter<Meeting> adapter;
	private String client;

	public GetMeetingData(ArrayAdapter<Meeting> adapter, String client){
		this.adapter = adapter;
		this.client = client;
		this.httpClient = new DefaultHttpClient();
	}

	@Override
	protected void onPostExecute(List<Meeting> data) {
		DataManager.setMeetingList(data);
		this.adapter.clear();
		this.adapter.addAll(DataManager.getMeetingList());
		this.adapter.notifyDataSetChanged();
		super.onPostExecute(data);
	}

	@Override
	protected List<Meeting> doInBackground(String... params) {
		try {
			JSONArray meetingsJsonArray = getMeetingsData();
			List<Meeting> meetings = new ArrayList<Meeting>();

			for(int i=0; i< meetingsJsonArray.length(); i++)
			{
				JSONObject meetingJson = meetingsJsonArray.getJSONObject(i);
				long startDate = meetingJson.getString("StartDate").equals("null") ? 0 : Long.parseLong(meetingJson.getString("StartDate"));
				Meeting m = new Meeting(Integer.parseInt(meetingJson.getString("Id")), Integer.parseInt(meetingJson.getString("CategoryId")), meetingJson.getString("Client"), meetingJson.getString("Adviser"), meetingJson.getString("Reason"), startDate);

				meetings.add(m);
			}

			return meetings;

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

	public JSONArray getMeetingsData() throws ClientProtocolException, IOException, JSONException
	{
		HttpGet get = new HttpGet(Config.BASE_SERVICE_URI + GET_MEETINGS_SERVICE_URI + this.client);
		
		HttpResponse response = this.httpClient.execute(get);
		int status = response.getStatusLine().getStatusCode();

		if(status == 200) //sucess
		{
			HttpEntity e = response.getEntity();
			String data = EntityUtils.toString(e);
			JSONArray meetingsData = new JSONArray(data);

			return meetingsData;
		}
		else
		{
			return null;
		}
	}

}