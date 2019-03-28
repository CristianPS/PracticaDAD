package es.urjc.etsii.dad.ServicioInterno;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class SocketServer extends WebSocketServer{

	private static String mes = "";
	private static int listo = 0;
	
	public SocketServer()
	{
		super(new InetSocketAddress(7777));
	}
	
	public static String getMensaje()
	{
		return mes;
	}
	
	public static int mensajeListo()
	{
		return listo;
	}
	
	public static void setMensajeNoListo()
	{
		listo = 0;
	}
	
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		// TODO Auto-generated method stub
		System.out.println("Conexion iniciada del servidor");
		
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		// TODO Auto-generated method stub
		
		System.out.println("Mensaje recibido " + message);
		mes = message;
		listo = 1;
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}

	
}
