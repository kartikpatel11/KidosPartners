package com.example.kidospartners;

import java.io.IOException;
import java.util.List;

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
import com.example.kidospartners.beans.KidosPartnersCategoryBean;
import com.example.kidospartners.utils.KidosPartnersConstants;
import com.example.kidospartners.utils.KidosPartnersUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class KidosPartnersLocationDetails extends KidosPartnersPrePostProcessor implements OnMapReadyCallback{

	
private KidosPartnersCategoryBean categoryBean;
	
	private Factory mFactory ;
	GoogleMap googleMap;
    MarkerOptions markerOptions;
    LatLng latLng;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_partners_location_details);

		mFactory= KidosPartnersUtil.getLayoutFactory(this.getApplicationContext());
		
		categoryBean=getCategoryDetails();
	
		//Set Title
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(KidosPartnersUtil.setTitleText(this,KidosPartnersConstants.LOCATION_DETAILS_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));

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
		 
		                if(location!=null && !location.equals("")){
		                    new GeocoderTask().execute(location);
		                }
		            }
		        };
		 
		        // Setting button click event listener for the find button
		        btn_find.setOnClickListener(findClickListener);
		 
	
	}

	private void setUpMapIfNeeded() {
		if (googleMap == null) {
			SupportMapFragment mapFrag = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
			mapFrag.getMapAsync(this);
		}
	}

	@Override
	public void onMapReady(GoogleMap _googleMap) {
		googleMap = _googleMap;
		//setUpMap();
	}

	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

		if(name.contains("android.support.v7.internal.view.menu.ActionMenuItemView")) {
	        LayoutInflater li = LayoutInflater.from(context);
	        View view = null;
	        try {
	            view = li.createView(name, null, attrs);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        if (view != null) {
	            if(mFactory != null) {
	                view = mFactory.onCreateView(name,context,attrs);
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

		if(item.getItemId()==R.id.saveclassdetails)
			this.finish();
		
		return super.onOptionsItemSelected(item);
	}


	// An AsyncTask class for accessing the GeoCoding Web Service
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{
 
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
 
            if(addresses==null || addresses.size()==0){
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }
 
            // Clears all the existing markers on the map
            googleMap.clear();
 
            // Adding Markers on Google Map for each matching address
            for(int i=0;i<addresses.size();i++){
 
                Address address = (Address) addresses.get(i);
 
                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());
 
                String addressText = String.format("%s, %s",
                address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                address.getCountryName());
 
                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addressText);
 
                googleMap.addMarker(markerOptions);
 
                // Locate the first location
                if(i==0)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }

	

	
}
