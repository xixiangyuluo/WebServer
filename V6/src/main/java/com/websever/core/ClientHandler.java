package com.websever.core;

import com.websever.http.HttpServletRequest;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {
	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			//解析请求
			HttpServletRequest request = new HttpServletRequest(socket);
			//处理请求
			String path = request.getUri();
			//发送响应
			File rootDir = new File(
					ClientHandler.class.getClassLoader().getResource(".").toURI());
			File staticDir = new File(rootDir, "static");
//			File file = new File(staticDir, "/static/myweb/index.html");
			File file = new File(staticDir, path);
			println("HTTP/1.1 200 ok");
			println("Content-Type: text/html");
			println("Content-Lenght: " + file.length());
			println("");
			FileInputStream fis = new FileInputStream(file);
			OutputStream out = socket.getOutputStream();
			byte[] data = new byte[1024 * 10];
			int len;
			while ((len = fis.read(data)) != -1) {
				out.write(data, 0, len);
			}
			System.out.println("发动响应完毕");
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private void println(String line) throws IOException {
		OutputStream out = socket.getOutputStream();
		byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
		out.write(data);
		out.write(13);
		out.write(10);
	}
}
