package org.androidtown.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaServerSocket {
	public static void main(String[] args) {
		
		try {
			ServerSocket server = new ServerSocket(11001);
			System.out.println("서버 소켓이 만들어졌습니다. 포트 : 11001");
			
			while(true) {
				Socket aSocket = server.accept();
				System.out.println("클라이언트와 연결됨.");
				
				ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
				Object obj = instream.readObject();
				System.out.println("받은 데이터 : " + obj);

				ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
				outstream.writeObject(obj + " from Server.");
				outstream.flush();
				System.out.println("보낸 데이터 : " + obj + " from Server.");
				
				aSocket.close();
				System.out.println("소켓 닫음.");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
