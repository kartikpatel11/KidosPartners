package com.example.kidospartners.interfaces;

import java.util.Map;

public interface IKidosPartnersRestClientWrapper {

	public void restRequest(IKidosPartnersRestClientWrapper context, Map<String, Object> args, String method, String url);
	public void restCallBack(String restOutput, String requestUrl);
}
