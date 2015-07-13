package com.wechat.servlet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.wechat.po.TextMessage;
import com.wechat.util.CheckUtil;
import com.wechat.util.MessageUtil;

public class WeChatServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7987867715256981248L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String	signature = req.getParameter("signature");
		String	timestamp = req.getParameter("timestamp");
		String	nonce = req.getParameter("nonce");
		String	echostr = req.getParameter("echostr");
		System.out.println(signature);
		System.out.println(timestamp);
		System.out.println(nonce);
		System.out.println(echostr);
		System.out.println(req.getRemoteAddr());
		PrintWriter pw = resp.getWriter();
		//pw.println("This is Server");
		try {
			if(CheckUtil.checkSignuature(signature, timestamp, nonce)){
				pw.print(echostr);
			}else{
				pw.print("invalid");
			}
		} catch (Exception e) {
			// TODO: handle exception
			pw.print("invalid exception ^_^");
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		// TODO Auto-generated method stub
		try {
			Map<String,String> map = MessageUtil.xmlToMap(req);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			System.out.println("toUserName:"+toUserName+" fromUserName:"+fromUserName+" msgType:"+msgType+" content:"+content);
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}else if("2".equals(content)){
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if("3".equals(content)){
						
				}else if("?".equals(content)||"？".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());	
				}else{
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
				
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType = map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}
			out.print(message);
			System.out.println(message);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}


}
