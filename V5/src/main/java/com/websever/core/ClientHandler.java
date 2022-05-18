package com.websever.core;

import com.websever.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 该任务负责与指定的客户端进行HTTP交互
 */
public class ClientHandler implements Runnable{
	private Socket socket;
	
	public ClientHandler(Socket socket){
		this.socket = socket;
	}
	@Override
	public void run() {
		try {
			//解析请求
			HttpServletRequest request=new HttpServletRequest(socket);
			//
			
			//发送响应
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
