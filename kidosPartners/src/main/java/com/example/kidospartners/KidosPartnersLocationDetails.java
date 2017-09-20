package com.example.kidospartners;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.example.kidospartners.beans.KidosPartnersActivityLocationBean;
import com.example.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.example.kidospartners.utils.KidosPartnersConstants;
import com.example.kidospartners.utils.KidosPartnersRestClient;
import com.example.kidospartners.utils.KidosPartnersUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class KidosPartnersLocationDetails extends KidosPartnersPrePostProcessor implements OnMapReadyCallback, IKidosPartnersRestClientWrapper {

	private Factory mFactory;
	GoogleMap googleMap;
	MarkerOptions markerOptions;
	LatLng latLng;
	KidosPartnersActivityLocationBean loc;
	Marker marker;

	private String getActivityLocationURI = KidosPartnersConstants.GET_ACTIVITYLOCATION_BY_ACTIVITYID_URI;
	private String saveActivityLocationURI = KidosPartnersConstants.SAVE_ACTIVITYLOCATION_BY_ACTIVITYID_URI;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_partners_location_details);

		mFactory = KidosPartnersUtil.getLayoutFactory(this.getApplicationContext());

		KidosPartnerspreProcessor();

		//Set Title
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(KidosPartnersUtil.setTitleText(this, KidosPartnersConstants.LOCATION_DETAILS_SCREEN_TITLE, KidosPartnersConstants.TITLE_TEXT_FONTFACE));

		//SupportMapFragment supportMapFragment = (SupportMapFragment)
		//      getSupportFragmentManager().findFragmentById(R.id.map);

		// Getting a reference to the map
		//   googleMap = supportMapFragment.getMap();

		// Getting reference to btn_find of the layout activity_main
		Button btn_find = (Button) findViewById(R.id.btn_find);

		// Defining button click event listener for the find button
		OnClickListener findClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Getting reference to EditText to get the user input location
				EditText etLocation = (EditText) findViewById(R.id.et_location);

				// Getting user input location
				String location = etLocation.getText().toString();

				if (location != null && !location.equals("")) {
					new GeocoderTask().execute(location);
				}
			}
		};

		// Setting button click event listener for the find button
		btn_find.setOnClickListener(findClickListener);
		setUpMapIfNeeded();


	}

	private void setUpMapIfNeeded() {
		if (googleMap == null) {
			SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			mapFrag.getMapAsync(this);
		}
	}

	@Override
	public void onMapReady(GoogleMap _googleMap) {
		googleMap = _googleMap;
		restRequest(KidosPartnersLocationDetails.this, null, KidosPartnersConstants.GET, getActivityLocationURI + activitySummary.getActivityId());

		//setUpMap();
	}

	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

		if (name.contains("android.support.v7.internal.view.menu.ActionMenuItemView")) {
			LayoutInflater li = LayoutInflater.from(context);
			View view = null;
			try {
				view = li.createView(name, null, attrs);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (view != null) {
				if (mFactory != null) {
					view = mFactory.onCreateView(name, context, attrs);
				}
				return view;
			}
		}
		return super.onCreateView(name, context, attrs);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		new MenuInflater(getApplication()).inflate(R.menu.kidos_partners_location_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.savelocationdetails)
			saveLocationDetails();

		return super.onOptionsItemSelected(item);
	}


	private void saveLocationDetails()
	{
		Type type = new TypeToken<Map<String, Object>>(){}.getType();

		if(marker!=null) {
			LatLng position = marker.getPosition();
			double[] locArr = new double[]{position.latitude, position.longitude};

			KidosPartnersActivityLocationBean location = new KidosPartnersActivityLocationBean(Integer.parseInt(activitySummary.getActivityId()), locArr);
			Map<String, Object> locationMap = new Gson().fromJson(new Gson().toJson(location), type);


			restRequest(KidosPartnersLocationDetails.this, locationMap, KidosPartnersConstants.POSTJSON, saveActivityLocationURI);
		}
	}

	@Override
	public void restRequest(IKidosPartnersRestClientWrapper context, Map<String, Object> args, String method, String url) {
		KidosPartnersRestClient.sendRequest(context, args, method, url);
	}

	@Override
	public void restCallBack(String restOutput, String requestUrl) {

		System.out.println("In Rest Call back method of KidosPartnersActivityLocation::requestUrl=" + requestUrl);

		Gson gson = new Gson();

		if (requestUrl.contains(getActivityLocationURI)) {
			loc = gson.fromJson(restOutput, new TypeToken<KidosPartnersActivityLocationBean>() {}.getType());

			System.out.println("LOCATION=="+loc);
			if (loc.getCoordinates() != null && loc.getCoordinates().length > 0) {
				try {
					setMarker(loc.getCoordinates());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else if (requestUrl.contains(saveActivityLocationURI)) {
			if (restOutput != null) {
				JsonObject response = gson.fromJson(restOutput, JsonObject.class);
				Toast.makeText(KidosPartnersLocationDetails.this, response.get("msg").toString(), Toast.LENGTH_SHORT).show();
				KidosPartnersLocationDetails.this.finish();


			}
		}
	}

	protected void setMarker(double[] coordinates) {

		System.out.println("In SetMarker Method::"+ Arrays.toString(coordinates));

		latLng = new LatLng(coordinates[0], coordinates[1]);

		markerOptions = new MarkerOptions();
		markerOptions.draggable(true);
		markerOptions.position(latLng);

		marker=googleMap.addMarker(markerOptions);

		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));

	}





	// An AsyncTask class for accessing the GeoCoding Web Service
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

		@Override
		protected List<Address> doInBackground(String... locationName) {
			// Creating an instance of Geocoder class
			Geocoder geocoder = new Geocoder(getBaseContext());
			List<Address> addresses = null;

			try {
				// Getting a maximum of 3 Address that matches the input text
				addresses = geocoder.getFromLocationName(locationName[0], 3);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return addresses;
		}

		@Override
		protected void onPostExecute(List<Address> addresses) {

			if (addresses == null || addresses.size() == 0) {
				Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
			}

			setMarker(addresses.get(0));

		}

		protected void setMarker(Address address) {

			googleMap.clear();
			// Creating an instance of GeoPoint, to display in Google Map
			latLng = new LatLng(address.getLatitude(), address.getLongitude());

			String addressText = String.format("%s, %s",
					address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
					address.getCountryName());

			markerOptions = new MarkerOptions();
			markerOptions.draggable(true);
			markerOptions.position(latLng);
			markerOptions.title(addressText);

			marker=googleMap.addMarker(markerOptions);

			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
		}



	}
}

