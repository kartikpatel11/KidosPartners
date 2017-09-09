package com.example.kidospartners.utils;

import android.os.AsyncTask;

import com.example.kidospartners.interfaces.IKidosPartnersImageUploader;
import com.example.kidospartners.interfaces.IKidosPartnersRestClientWrapper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Main controller for the REST client. The request you wish to make can
 * be called by either calling the request() method and passing the type
 * of request you will be to make, or by accessing the methods directly.
 * 
 * Uses the Exception to report errors, but this is extremely basic,
 * and left alone due to the fact people should rather implement their
 * own error codes and systems. You can pass a JSONObject to the Exception
 * by calling createErrorObject().
 * 
 * @author 	Isaac Whitfield
 * @version 09/03/2014
 * 
 */
@SuppressWarnings("deprecation")
public class KidosPartnersRestClient {
	
	private static final String CONTENT_URI_PREFIX="content://";
	// The client to use for requests
	DefaultHttpClient client = new DefaultHttpClient();
	
	/**
	 * Main controller for the client, has the ability to create any of the other methods. Call with 
	 * your connection type and data, and everything is handled for you.
	 * 
	 * @param 	url			the url you wish to call
	 * @param 	method		the method you wish to call wish
	 * @param 	data		the data you want to pass to the URL, can be null
	 * @return 	JSON		the JSONObject returned from the request
	 */
	public String request(String url, String method, Map<String, Object> data) throws Exception {
		System.out.println("-----------------METHOD="+method+",URL="+url);
		if (method.matches("GET")) {
			return get(url);
		} else if (method.matches("POST")) {
			return post(url, data);
		} else if (method.matches("POSTJSON")) {
			return postJson(url,data);
		} else if (method.matches("PUT")) {
			return put(url, data);
		} else if (method.matches("DELETE")) {
			return delete(url);
		}
		throw new Exception("Error! Incorrect method provided: " + method);
	}
	
	/**
	 * Calls a GET request on a given url. Doesn't take a data object (yet), so pass all get parameters 
	 * alongside the url.
	 * 
	 * @param 	url		the url you wish to connect to
	 * @return 	JSON	the JSON response from the call		
	 */
	public String get(String url) throws Exception {
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != 200){
				
				throw new Exception("Error executing GET request! Received error code: " + response.getStatusLine().getStatusCode()+"," +response.getStatusLine().getReasonPhrase());
			}
			//return new JSONObject(readInput(response.getEntity().getContent()));
			return readInput(response.getEntity().getContent());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * Calls a POST request on a given url. Takes a data object in the form of a HashMap to POST.
	 * 
	 * @param 	url		the url you wish to connect to
	 * @param	data	the data object to post to the url
	 * @return 	JSON	the JSON response from the call		
	 */
	public String post(String url, Map<String, Object> data) throws Exception {
		HttpPost request = new HttpPost(url);
		List<NameValuePair> nameValuePairs = setParams(data);
		try {
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(request);

			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode > 300){
				throw new Exception("Error executing POST request! Received error code: " + response.getStatusLine().getStatusCode());
			}
			
			//return new JSONObject(readInput(response.getEntity().getContent()));
			return readInput(response.getEntity().getContent());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public String postJson(String url, Map<String, Object> data) throws Exception
	{
		HttpPost request = new HttpPost(url);
		JSONObject holder = new JSONObject(data);

		//passes the results to a string builder/entity
		StringEntity se = new StringEntity(holder.toString());

		System.out.println("************* SE="+holder.toString());
		try {
			request.setEntity(se);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");

			HttpResponse response = client.execute(request);

			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode > 300){
				throw new Exception("Error executing POST request! Received error code: " + response.getStatusLine().getStatusCode());
			}

			//return new JSONObject(readInput(response.getEntity().getContent()));
			return readInput(response.getEntity().getContent());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}






	/**
	 * Calls a PUT request on a given url. Takes a data object in the form of a HashMap to PUT.
	 * 
	 * @param 	url		the url you wish to connect to
	 * @param	data	the data object to post to the url
	 * @return 	JSON	the JSON response from the call		
	 */
	public String put(String url, Map<String, Object> data) throws Exception {
		HttpPut request = new HttpPut(url);
		List<NameValuePair> nameValuePairs = setParams(data);
		try {
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            request.setHeader("Content-type", "application/json");
			HttpResponse response = client.execute(request);

			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != 200){
				throw new Exception("Error executing PUT request! Received error code: " + response.getStatusLine().getStatusCode());
			}
			
			//return new JSONObject(readInput(response.getEntity().getContent()));
			return readInput(response.getEntity().getContent());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Calls a DELETE request on a given url.
	 * 
	 * @param 	url		the url you wish to connect to
	 * @return 	JSON	the JSON response from the call		
	 */
	public String delete(String url) throws Exception {
		HttpDelete request = new HttpDelete(url);
		try {
			HttpResponse response = client.execute(request);

			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != 200){
				throw new Exception("Error executing DELETE request! Received error code: " + response.getStatusLine().getStatusCode());
			}
			
			//return new JSONObject(readInput(response.getEntity().getContent()));
			return readInput(response.getEntity().getContent());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * Adds a set of cookies to the HTTPClient. Pass in a HashMap of <String, Object>
	 * to add the cookie values to the client. These cookies persist until removed.
	 * 
	 * @param cookies		a HashMap of cookies
	 * @param url			the domain URL of the cookie
	 */
	public void setCookies(HashMap<String, String> cookies, String url){
		if(cookies == null){
			return;
		}

		// Set cookies
		//client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		
		// Create cookie store
		BasicCookieStore store = (BasicCookieStore) (client.getCookieStore() == null ? new BasicCookieStore() : client.getCookieStore());

		// Add cookies to request
		for(Map.Entry<String, String> entry : cookies.entrySet()) {
			BasicClientCookie cookie = new BasicClientCookie(entry.getKey(), entry.getValue());
			if(url != null){
				cookie.setDomain(url);
			}
			cookie.setPath("/");
			store.addCookie(cookie);
		}

		// Add the cookie
		client.setCookieStore(store);
	}
	
	/**
	 * Helper method for creating an error object for better reporting. Not needed, 
	 * but has proven useful before and can be used to pass objects to the error 
	 * class Exception.
	 * 
	 * @param 	keys		a list of keys
	 * @param 	values		a list of values
	 * @return 	JSON		a JSONObject to be used as an error
	 */
	@SuppressWarnings("unused")
	private JSONObject createErrorObject(String[] keys, Object[] values){
		JSONObject json = new JSONObject();
		try {
			for(int i = 0; i < keys.length; i++){
				json.put(keys[i], values[i]);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Loops all data inside the data object and maps to a list of Name/Values.
	 * Used alongside POST and PUT requests to neaten the parameters, rather than
	 * attempting to stringify the data.
	 * 
	 * @param 	data	the data object we're going to pass
	 * @return	List	a list of Name/Value pairs
	 */
	private List<NameValuePair> setParams(Map<String, Object> data){
		List<NameValuePair> nameValuePairs = null;
		if (data != null && !data.isEmpty()) {
			nameValuePairs = new ArrayList<NameValuePair>(2);
			Iterator<String> it = data.keySet().iterator();
			while (it.hasNext()) {
				String name = it.next();
				Object value = data.get(name);
				System.out.println("name,value="+name+"==>"+value.toString());
				nameValuePairs.add(new BasicNameValuePair(name, value.toString()));
			}
		}
		return nameValuePairs;
	}

	/**
	 * Generic handler to retrieve the result of a request. Simply reads the input stream
	 * and returns the string;
	 * 
	 * @param 	is		the InputStream we're reading, usually from getEntity().getContent()
	 * @return	JSON 	the read-in JSON data as a string
	 */
	private String readInput(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String json = "", line;
		while ((line = br.readLine()) != null){
			json += line;
		}
		System.out.println("*******************responseJSON="+json);
		return json;
	}
	
	/**
	 * 
	 * @author Kartik
	 *
	 */
	private static class doRequest extends AsyncTask<Void, JSONObject, String>{
		
		private KidosPartnersRestClient rest;
		// Store context for dialogs
		private IKidosPartnersRestClientWrapper context = null;
		// Store error message
		private Exception e = null;
		// Passed in data object
		private Map<String, Object> data = null;
		// Passed in method
		private String method = "";
		// Passed in url
		private String url = "";
		
		public doRequest(IKidosPartnersRestClientWrapper context, Map<String, Object> data, String method, String url){
			this.context = context;
			this.data = data;
			this.method = method;
			this.url = url;
			rest =new KidosPartnersRestClient();
		}
		
		@Override
		protected String doInBackground(Void... arg0) {
			try {
				return rest.request(url, method, data); // Do request
			} catch (Exception e) {
				this.e = e;	
				e.printStackTrace();// Store error
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String data){
			super.onPostExecute(data);
			// Display based on error existence
			context.restCallBack(data, this.url);
		}
	}
	
	
	public static void sendRequest(IKidosPartnersRestClientWrapper context, Map<String, Object> data, String method, String url)
	{
		try {
			url=KidosPartnersConstants.NETWORK_URL+url;
			new doRequest(context, data, method, url).execute().get();
			
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Get Signature for AWS upload from Kidos Server
	 * @author Kartik
	 *
	 */
	private static class GetSignature extends AsyncTask<Void, JSONObject, String> {
		
		private KidosPartnersRestClient rest;
		
		// Passed in data object
		private Map<String, Object> data = null;
		// Passed in method
		private String method = "GET";
		// Passed in url
		private String url = "";
		// Store error message
		private Exception e = null;

		private IKidosPartnersImageUploader context = null;
		
		
		
		public GetSignature(IKidosPartnersImageUploader context2, Map<String, Object> data2,
				 String url2) 
		{
			this.context =context2;
			this.data=data2;
			
			this.url=url2;
			rest =new KidosPartnersRestClient();
		}

		@Override
		protected String doInBackground(Void... arg0) {
			try {
				return rest.request(url, method, data); // Do request
			} catch (Exception e) {
				this.e = e;	
				e.printStackTrace();// Store error
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String presignedOutput){
			super.onPostExecute(presignedOutput);
			System.out.println("********Signature--->"+presignedOutput);
			try {
				JSONObject sign=new JSONObject(presignedOutput);
				this.data.put("awsurl", sign.getString("url"));
				new UploadObject(context,sign.getString("signed_request"), this.data).execute().get();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.out.println("could not get security key due to internal issues.");
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public static void getSignature(IKidosPartnersImageUploader context, Map<String, Object> data, String url)
	{
		try {
			url=KidosPartnersConstants.NETWORK_URL+url;
			new GetSignature(context, data, url).execute().get();
			
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private static class UploadObject extends AsyncTask<Void, JSONObject, Integer> {
	
		private KidosPartnersRestClient rest;
		
		private IKidosPartnersImageUploader context = null;
		
		// Passed in data object
		private Map<String, Object> data = null;
		// Passed in method
		private String method = "";
		// Passed in url
		private String url = "";
		// Store error message
		private Exception e = null;
		
		public UploadObject(IKidosPartnersImageUploader context2, String url2, Map<String, Object> data2) {
			this.context=context2;
			this.url=url2;
			this.data=data2;
		}

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				
				return uploadObject(new URL(url), this.data);
				
			} catch (Exception e) {
				this.e = e;	
				e.printStackTrace();// Store error
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Integer response){
			super.onPostExecute(response);
			System.out.println("********Response--->"+response);
			if(response==200)
			{
				context.imageUploadCallBack((String)this.data.get("awsurl"));
			}
			
		}
	}
	
	
	public static  int uploadObject(URL url, Map<String, Object> argMap) throws IOException
	{
		HttpURLConnection connection=(HttpURLConnection) url.openConnection();
		int bufferSize = 4096;
	    byte[] buffer = new byte[bufferSize];
	    int len = 0;

		
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", (String)argMap.get("type")); // Very important ! It won't work without adding this!

		OutputStream out = connection.getOutputStream();
		
		//out.write("This text uploaded as object.");
		InputStream inputStream = (InputStream) argMap.get("data");
	    
	    while ((len = inputStream.read(buffer)) != -1) {
	        out.write(buffer, 0, bufferSize);
	      }
	     
	    out.flush();
		
		
		return connection.getResponseCode();
	}
	
	

	

}
