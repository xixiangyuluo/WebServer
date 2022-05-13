package com.websever.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WerServer {
	private ServerSocket serverSocket;
	
	public WerServer() {
		try {
			System.out.println("正在启动服务器...");
			serverSocket = new ServerSocket(8088);
			System.out.println("服务器已启动！");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		try {
			System.out.println("等待客户端链接...");
			Socket socket = serverSocket.accept();
			System.out.println("一个客户端链接了！");
			ClientHandler handler = new ClientHandler(socket);
			Thread t = new Thread(handler);
			t.start();
			
			InputStream in = socket.getInputStream();
			int d;
			while ((d = in.read()) != -1) {
				System.out.print((char) d);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		WerServer server = new WerServer();
		server.start();
	}
	
}
