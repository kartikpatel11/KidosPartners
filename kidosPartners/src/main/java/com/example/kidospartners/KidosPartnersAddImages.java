package com.example.kidospartners;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.GridView;
import android.widget.Toast;

import com.example.kidospartners.abstracts.KidosPartnersPrePostProcessor;
import com.example.kidospartners.adpter.KidosPartnersImageGridAdapter;
import com.example.kidospartners.beans.KidosPartnersImageDetailsBean;
import com.example.kidospartners.beans.KidosPartnersImagesBean;
import com.example.kidospartners.interfaces.IKidosPartnersImageUploader;
import com.example.kidospartners.interfaces.IKidosPartnersRestClientWrapper;
import com.example.kidospartners.utils.KidosPartnersConstants;
import com.example.kidospartners.utils.KidosPartnersRestClient;
import com.example.kidospartners.utils.KidosPartnersUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class KidosPartnersAddImages extends KidosPartnersPrePostProcessor implements IKidosPartnersRestClientWrapper, IKidosPartnersImageUploader{

	private static final int REQUEST_CAMERA = 0;
	private static final int SELECT_FILE = 1;
    private String userChoosenTask;
	private Factory mFactory ;
	private KidosPartnersImagesBean imageDetails =new KidosPartnersImagesBean();
	ProgressDialog dialog ;

	private String getActivityImageURI=KidosPartnersConstants.GET_ACTIVITYIMAGE_BY_ACTIVITYID_URI;
	private String saveActivityImageURI=KidosPartnersConstants.SAVE_ACTIVITYIMAGE_BY_ACTIVITYID_URI;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_partners_add_images);

		KidosPartnerspreProcessor();

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(KidosPartnersUtil.setTitleText(this,KidosPartnersConstants.ADD_IMAGES_SCREEN_TITLE , KidosPartnersConstants.TITLE_TEXT_FONTFACE));
		
		//Populate Data
		if(getActivityID()!=null)
		{

				restRequest(KidosPartnersAddImages.this, null, KidosPartnersConstants.GET, getActivityImageURI+getActivityID());
		}		
		else
		{
			GridView imageGridView = (GridView) findViewById(R.id.img_gridview);

			KidosPartnersImageGridAdapter adapter = new KidosPartnersImageGridAdapter(this,R.layout.layout_kidos_partners_addimage_item, imageDetails.getImages());
			imageGridView.setAdapter(adapter);
		}
		
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
		//getMenuInflater().inflate(R.menu.kidos_partners_class_details, menu);
		 new MenuInflater(getApplication()).inflate(R.menu.kidos_partners_add_images, menu); 
		 return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId()==R.id.saveimages)
			saveImageDetails();
		
		return super.onOptionsItemSelected(item);
	}


	private void saveImageDetails()
	{
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		System.out.println("new Gson().toJson(imageDetails)="+new Gson().toJson(imageDetails));

		Map<String, Object> imageDetailsMap = new Gson().fromJson(new Gson().toJson(imageDetails), type);
		restRequest(KidosPartnersAddImages.this, imageDetailsMap, KidosPartnersConstants.POSTJSON, saveActivityImageURI);
	}

	public void AddImage(View v){
		
		selectImage();
		
	}


	
	private void selectImage() {
		  final CharSequence[] items = { "Take Photo", "Choose from Library",
		        "Cancel" };

		  AlertDialog.Builder builder = new AlertDialog.Builder(KidosPartnersAddImages.this);
		  builder.setTitle("Add Photo!");
		  builder.setItems(items, new DialogInterface.OnClickListener() {


			@Override
		     public void onClick(DialogInterface dialog, int item) {
		        boolean result=KidosPartnersUtil.checkPermission(KidosPartnersAddImages.this);

		        if (items[item].equals("Take Photo")) {
		           userChoosenTask="Take Photo";
		           if(result)
		              cameraIntent();

		        } else if (items[item].equals("Choose from Library")) {
		           userChoosenTask="Choose from Library";
		           if(result)
		              galleryIntent();

		        } else if (items[item].equals("Cancel")) {
		           dialog.dismiss();
		        }
		     }
		  });
		  builder.show();
		}
	
	private void cameraIntent()
	{
	  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	  startActivityForResult(intent, REQUEST_CAMERA);
	}
	
	private void galleryIntent()
	{
	  Intent intent = new Intent();
	  intent.setType("image/*");
	  intent.setAction(Intent.ACTION_GET_CONTENT);//
	  startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	  switch (requestCode) {
	     case KidosPartnersUtil.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
	        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	           if(userChoosenTask.equals("Take Photo"))
	              cameraIntent();
	           else if(userChoosenTask.equals("Choose from Library"))
	              galleryIntent();
	        } else {
	           //code for deny
	        }
	        break;
	  }
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  if (resultCode == Activity.RESULT_OK) {
		  
		  System.out.println("======================="+resultCode); 
	     if (requestCode == SELECT_FILE)
	        onSelectFromGalleryResult(data);
	     else if (requestCode == REQUEST_CAMERA)
	        onCaptureImageResult(data);
	  }
	}
	
	@SuppressWarnings("deprecation")
	private void onSelectFromGalleryResult(Intent data) {
	  Bitmap bm=null;
	  if (data != null) {
		  
		  HashMap<String,Object> argMap=new HashMap<String,Object>();
		  String fileName=data.getData().getLastPathSegment();
		  ContentResolver cr = this.getContentResolver();
		  String mime=null;
		  InputStream iStream=null;
		  
		  String extension = MimeTypeMap.getFileExtensionFromUrl(data.getData().getPath().toString());
		    if (extension != null) {
		        mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
		    }
		
		try {
			iStream = cr.openInputStream(data.getData());
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}



		  argMap.put("type", mime);
		  argMap.put("name", fileName);
		  argMap.put("data", iStream);
		  String url=KidosPartnersConstants.GET_AWS_SIGNATURE+"?activityId="+getActivityID()+"&file_name=" + fileName + "&file_type=" + mime;
		 
		  
		  //need to send filename for signature
		  imageUploadRequest(KidosPartnersAddImages.this, argMap, url);
		    
		  }
	}
	
	private void onCaptureImageResult(Intent data) {
        HashMap<String,Object> argMap=new HashMap<String,Object>();
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        long fileName = System.currentTimeMillis();
		  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		  thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
		  File destination = new File(Environment.getExternalStorageDirectory(),
		       fileName  + ".jpg");
		  FileOutputStream fo;
		  try {
		     destination.createNewFile();
		     fo = new FileOutputStream(destination);
		     fo.write(bytes.toByteArray());
		     fo.close();
		  } catch (FileNotFoundException e) {
		     e.printStackTrace();
		  } catch (IOException e) {
		     e.printStackTrace();
		  }

        InputStream iStream= null;
        try {
            iStream = new FileInputStream(destination);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        argMap.put("type", "image/jpg");
        argMap.put("name", fileName+".jpg");
        argMap.put("data", iStream);
        String url=KidosPartnersConstants.GET_AWS_SIGNATURE+"?activityId="+getActivityID()+"&file_name=" + fileName + ".jpg&file_type=image/jpg";


        //need to send filename for signature
        imageUploadRequest(KidosPartnersAddImages.this, argMap, url);

       }

	@Override
	public void restRequest(IKidosPartnersRestClientWrapper context,
			Map<String, Object> args, String method, String url) {
		KidosPartnersRestClient.sendRequest(context,args,method,url);
			
	}

	@Override
	public void restCallBack(String restOutput, String requestUrl) {

		System.out.println("In Rest Call back method of KidosPartnersAddImageActivity::requestUrl="+requestUrl);

		Gson gson = new Gson();

		if(requestUrl.contains(getActivityImageURI)) {

			imageDetails = gson.fromJson(restOutput, new TypeToken<KidosPartnersImagesBean>() {}.getType());

			GridView imageGridView = (GridView) findViewById(R.id.img_gridview);

			KidosPartnersImageGridAdapter adapter = new KidosPartnersImageGridAdapter(this, R.layout.layout_kidos_partners_addimage_item, imageDetails.getImages());
			imageGridView.setAdapter(adapter);
		}
		else if(requestUrl.contains(saveActivityImageURI))
		{
			if(restOutput!=null) {
				JsonObject response = gson.fromJson(restOutput, JsonObject.class);

				Toast.makeText(KidosPartnersAddImages.this, response.get("msg").toString(), Toast.LENGTH_SHORT).show();
				KidosPartnersAddImages.this.finish();
			}
		}
		
	}

	@Override
	public void imageUploadRequest(IKidosPartnersImageUploader context,
			Map<String, Object> args, String url) {
		
		//need to send filename for signature
		 dialog = ProgressDialog.show((Context) context, "Uploading", "Please wait...", true);

		  KidosPartnersRestClient.getSignature(KidosPartnersAddImages.this, args, url);
	  
		
	}

	@Override
	public void imageUploadCallBack(String restOutput) {
		// load image using resoutput string into imagegridview
		System.out.println("-----------------> ABOUT TO REFRESH GRIDVIEW--"+restOutput);
		KidosPartnersImageDetailsBean imgBean = new KidosPartnersImageDetailsBean();
		imgBean.setImgurl(restOutput);
		imgBean.setName("image");
		GridView imageGridView = (GridView) findViewById(R.id.img_gridview);
		KidosPartnersImageGridAdapter adapter = (KidosPartnersImageGridAdapter)imageGridView.getAdapter();
		System.out.println("----------------------> adapter="+adapter);
		adapter.add(imgBean);
		
		if(dialog!=null)
			dialog.dismiss();
		
		adapter.notifyDataSetChanged();
		
	}



}
