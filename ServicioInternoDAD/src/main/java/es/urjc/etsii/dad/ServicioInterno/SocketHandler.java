package es.urjc.etsii.dad.ServicioInterno;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler{
	
	/*@Override
	public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
		System.err.println("Error en el cliente " + session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("Sesion " + session.getId() + "cerrada porque " + status.getReason());
	}*/
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Conectado a " + session.getId());
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {		
		String datos = message.getPayload();
		int separador = datos.indexOf("-");
		long idAnuncio = Long.parseLong(datos.substring(0, separador));
		String username = datos.substring(separador+1);
		System.out.println(idAnuncio);
		System.out.println(username);
		ServicioController.getVariables(idAnuncio, username);

	}
}
