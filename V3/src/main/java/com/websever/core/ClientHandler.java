package com.websever.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable{
	private Socket socket;
	public ClientHandler(Socket socket){
		this.socket=socket;
	}
	@Override
	public void run() {
		try {
			String line = readline();
			System.out.println("请求行:"+line);
			//请求行的相关信息
			String method;//请求方式
			String uri;//抽象路径部分
			String protocol;//协议版本
			
			String[] data = line.split("\\s");
			method = data[0];
			uri = data[1];
			protocol = data[2];
			System.out.println("method:"+method);
			System.out.println("uri:"+uri);
			System.out.println("protocol:"+protocol);
			Map<String,String> headers = new HashMap<>();
			while(!(line = readline()).isEmpty()) {
				System.out.println("消息头:" + line);
				//拆分消息头，分别得到消息头的名字和值
				data = line.split(":\\s");
				headers.put(data[0],data[1]);
			}
			System.out.println("headers:"+headers);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	public String readline()throws IOException{
		InputStream in = socket.getInputStream();
		int d;
		StringBuilder builder = new StringBuilder();
		char cur='a';//表示本次读取到的字符
		char pre='a';//表示上次   读取到的字符
		while((d = in.read())!=-1){
			cur = (char)d;
			if(pre==13 && cur==10){//上次为回车，本次为换行就停止读取
				break;
			}
			builder.append(cur);
			pre = cur;
		}
		return builder.toString().trim();
	}
}
