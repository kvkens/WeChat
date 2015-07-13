package com.wechat.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.wechat.po.AccessToken;
import com.wechat.util.WeChatUtil;

public class WeChatTest {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		AccessToken token = WeChatUtil.getAccessToken();
		System.out.println("Token:"+ token.getToken());
		System.out.println("Expires:"+ token.getExpiresIn());
	}

}
