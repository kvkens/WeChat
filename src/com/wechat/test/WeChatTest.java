package com.wechat.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.http.client.ClientProtocolException;

import com.wechat.po.AccessToken;
import com.wechat.util.WeChatUtil;

public class WeChatTest {

	public static void main(String[] args) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
		// TODO Auto-generated method stub
		try {
			AccessToken token = WeChatUtil.getAccessToken();
			System.out.println("Token:"+ token.getToken());
			System.out.println("Expires:"+ token.getExpiresIn());
			//String path = "/Users/Kvkens/Code/Java/JavaWeb/WebContent/store/1.jpg";
			String path = "D:/精修/10.jpg";
			
			String mediId = WeChatUtil.upload(path, token.getToken(), "images");
			System.out.println(mediId);
			//l6RY0srRLoZuNx2VSv6bNmsUIosXbD9J5J3VCE_EYX6gDHFKGCAJm2LZzy-inQcO
		} catch (Exception e) {
			// TODO: handle exception
			//W4cXVB8W6Hc8u1XcSQ49VWjRS8poIDfo_Fa0bVvGxecR6rvPChaNKDe5MIojHYsx
		}
		
	}

}
