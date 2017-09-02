package com.example.kidospartners.interfaces;

import java.util.Map;

public interface IKidosPartnersImageUploader {

	public void imageUploadRequest(IKidosPartnersImageUploader context, Map<String, Object> args,  String url);
	public void imageUploadCallBack(String restOutput);
}
