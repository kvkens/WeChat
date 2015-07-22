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
import com.wechat.po.Music;
import com.wechat.po.MusicMessage;
import com.wechat.po.News;
import com.wechat.po.NewsMessage;
import com.wechat.po.TextMessage;
import com.wechat.po.Video;
import com.wechat.po.VideoMessage;
import com.wechat.po.Voice;
import com.wechat.po.VoiceMessage;

import java.util.Map;

public class MessageUtil {
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_MUSIC = "music";
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
		sb.append("2、图文消息\n");
		sb.append("3、图片消息(需要企业认证)\n");
		sb.append("4、语音消息(需要企业认证)\n");
		sb.append("5、视频消息(需要企业认证)\n");
		sb.append("6、音乐消息(需要企业认证)\n");
		sb.append("回复？调用此菜单。");
		return sb.toString();
	}
	/**
	 * error
	 * @return
	 */
	public static String errorMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("指令输入不正确，\n请输入【？】提示菜单。");
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
		image.setMediaId("abqmVqoPWW174-6nIiuAD0-Gs0RyA5i6zCUcs7j2ZrVay05Ni_DCbPfNoH8IKEeM");
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
	/**
	 * 组装xml To voice
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initVoiceMessage(String toUserName,String fromUserName){
		String message = null;
		Voice voice = new Voice();
		VoiceMessage voiceMessage = new VoiceMessage();
		voice.setMediaId("lWJx8C0VnkEh6vcMNcRIxAiEevWjFX2K83g3Yk3If7YKFAIS5Gf8PyPMTR5P-z5n");
		voiceMessage.setCreateTime(new Date().getTime());
		voiceMessage.setFromUserName(toUserName);
		voiceMessage.setToUserName(fromUserName);
		voiceMessage.setMsgType(MESSAGE_VOICE);
		voiceMessage.setVoice(voice);
		message = voiceMessageToXml(voiceMessage);
		return message;
	}
	/**
	 * voice To Message
	 * @param voiceMessage
	 * @return
	 */
	public static String voiceMessageToXml(VoiceMessage voiceMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}
	/**
	 * 组装xml To video
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initVideoMessage(String toUserName,String fromUserName){
		String message = null;
		Video video = new Video();
		VideoMessage videoMessage = new VideoMessage();
		video.setMediaId("cjwtyOwxkzJ_Vq8RCeDrtYnaj0uhgnFqFanOWcFrYtESTneRzjDK4vwmNiqSccwU");
		video.setTitle("大狗熊");
		video.setDescription("只是大狗熊测试视频");
		videoMessage.setCreateTime(new Date().getTime());
		videoMessage.setFromUserName(toUserName);
		videoMessage.setToUserName(fromUserName);
		videoMessage.setMsgType(MESSAGE_VIDEO);
		videoMessage.setVideo(video);
		message = videoMessageToXml(videoMessage);
		return message;
	}
	/**
	 * video To Message
	 * @param videoMessage
	 * @return
	 */
	public static String videoMessageToXml(VideoMessage videoMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	/**
	 * music to xml
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message = null;
		Music music = new Music();
		MusicMessage musicMessage = new MusicMessage();
		music.setTitle("一口气全念对");
		music.setDescription("哎哟不错哦专辑新歌");
		music.setThumbMediaId("abqmVqoPWW174-6nIiuAD0-Gs0RyA5i6zCUcs7j2ZrVay05Ni_DCbPfNoH8IKEeM");
		music.setMusicUrl("http://rmc.xicp.net/WeChat/lib/jay.mp3");
		music.setHQMusicUrl("http://rmc.xicp.net/WeChat/lib/jay.mp3");
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}

	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
}
