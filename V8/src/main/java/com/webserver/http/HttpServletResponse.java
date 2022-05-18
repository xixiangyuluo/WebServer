package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 响应对象，该类的每一个实例用于表示服务端给客户端发送的一个响应
 * HTTP协议要求一个响应的格式由三部分构成:
 * 状态行，响应头，响应正文
 */
public class HttpServletResponse {
	private Socket socket;
	//状态行相关信息
	private int statusCode = 200;
	private String statusReason = "OK";
	private File contentFile;//正文文件
	
	public HttpServletResponse(Socket socket) {
		this.socket = socket;
	}
	
	public void response() throws IOException {
		sendStatusLine();
		sendHeaders();
		sendContent();
	}
	
	private void sendStatusLine() throws IOException {
		println("HTTP/1.1" + " " + statusCode + " " + statusReason);
	}
	
	private void sendHeaders() throws IOException {
		println("Content-Type: text/html");
		println("Content-Length: " + contentFile.length());
		println("");
	}
	
	private void sendContent() throws IOException {
		try (
				FileInputStream fis = new FileInputStream(contentFile);
		) {
			OutputStream out = socket.getOutputStream();
			byte[] data = new byte[1024 * 10];//10kb
			int len;
			while ((len = fis.read(data)) != -1) {
				out.write(data, 0, len);
			}
		}
	}
	
	private void println(String line) throws IOException {
		OutputStream out = socket.getOutputStream();
		byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
		out.write(data);
		out.write(13);
		out.write(10);
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getStatusReason() {
		return statusReason;
	}
	
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	
	public File getContentFile() {
		return contentFile;
	}
	
	public void setContentFile(File contentFile) {
		this.contentFile = contentFile;
	}
}
