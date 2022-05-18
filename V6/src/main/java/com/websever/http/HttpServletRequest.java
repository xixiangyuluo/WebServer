package com.websever.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServletRequest {
	private Socket socket;
	//请求行相关信息
	private String method;//请求方式
	private String uri;//抽象路径
	private String protocol;//协议版本
	//消息头相关信息
	//使用HashMap存储消息头
	Map<String, String> headers = new HashMap<>();
	
	public HttpServletRequest(Socket socket) throws IOException {
		this.socket = socket;
		parseRequestLine();
		parseHeaders();
		parseContent();
	}
	
	private void parseRequestLine() throws IOException {
		String line = readLine();
		System.out.println("请求行内容:" + line);
		//请求行相关信息
		//将请求行按照空格拆分为三部分，并分别用上述三个变量保存
		String[] data = line.split("\\s");
		method = data[0];
		uri = data[1];
		protocol = data[2];
		System.out.println("method:" + method);
		System.out.println("uri:" + uri);
		System.out.println("protocol:" + protocol);
	}
	
	private void parseHeaders() throws IOException {
		while (true) {
			String line = readLine();
			if (line.isEmpty()) {
				break;
			}
			String[] data = line.split(":\\s");
			headers.put(data[0], data[1]);
			System.out.println("消息头:" + line);
		}
		System.out.println("headers:" + headers);
	}
	
	private void parseContent() {
	
	}
	
	private String readLine() throws IOException {
		InputStream in = socket.getInputStream();
		//使用StringBuilder用于拼接每一个读取到的字符，并最终组成一个字符串使用
		StringBuilder builder = new StringBuilder();
		int d;//记录每次读取到的字节
		char cur = 'a';//表示本次读取到的字符
		char pre = 'a';//表示上次读取到的字符
		while ((d = in.read()) != -1) {
			cur = (char) d;//将本次读取到的字节转换为char记录
			if (pre == 13 && cur == 10) {//判断上次读取的是否为回车符并且本次读取到的是否为换行符
				//如果连续读取到了回车+换行符，则停止本行字符串的读取工作
				break;
			}
			builder.append(cur);//将本次读取的字符拼接到字符串中
			pre = cur;//在读取下一个字符前，将本次读取的字符记作上次读取的字符
		}
		return builder.toString().trim();
	}
	
	public String getUri() {
		return uri;
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getProtocol() {
		return protocol;
	}
	
	public String getHeaders(String name) {
		return headers.get(name);
	}
	
	
}
