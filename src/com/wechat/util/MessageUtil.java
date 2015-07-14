package com.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.wechat.po.Image;
import com.wechat.po.ImageMessage;
import com.wechat.po.News;
import com.wechat.po.NewsMessage;
import com.wechat.po.TextMessage;

import java.util.Map;

public class MessageUtil {
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	
	
	/**
	 * xml转化map
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	/**
	 * 消息转化为XML
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}
	
	/**
	 * 主菜单
	 * @return
	 */
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，目前订阅号为开发状态：\n\n");
		sb.append("1、文本消息\n");
		sb.append("2、图文消息混排\n\n");
		sb.append("回复？调用此菜单。");
		return sb.toString();
	}
	/**
	 * 1
	 * @return
	 */
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("本套课程介绍微信公众号开发，主要涉及公众号介绍");
		return sb.toString();
	}
	/**
	 * 2
	 * @return
	 */
	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("慕课网(IMOOC)是国内最大的IT技能学习平台。慕课网提供了丰富的移动端开发、php开发、web前端、android开发以及html5等视频教程资源公开课。");
		return sb.toString();
	}
	
	/**
	 * 图文消息转换xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		
		return xstream.toXML(newsMessage);
	}
	
	/**
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		News news = new News();
		news.setTitle("慕课网介绍");
		news.setDescription("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。");
		news.setPicUrl("http://www.imooc.com/static/img/index/banner2.jpg");
		news.setUrl("www.imooc.com");
		newsList.add(news);
		News news2 = new News();
		news2.setTitle("关于前端交流");
		news2.setDescription("本微信公众号主要介绍当前时尚的技术");
		news2.setUrl("www.imyy.org");
		news2.setPicUrl("http://stimgcn3.s-msn.com/msnportal/roll/2013/07/02/415de955-36a9-41b5-bcf5-9f6bfd1707bf.jpg");
		newsList.add(news2);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticleCount(newsList.size());
		newsMessage.setArticles(newsList);
		message = newsMessageToXml(newsMessage);
		return message;
	}
	/**
	 * 组装xml
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message = null;
		Image image = new Image();
		image.setMediaId("W4cXVB8W6Hc8u1XcSQ49VWjRS8poIDfo_Fa0bVvGxecR6rvPChaNKDe5MIojHYsx");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}
	/**
	 * image To Message
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	
}
