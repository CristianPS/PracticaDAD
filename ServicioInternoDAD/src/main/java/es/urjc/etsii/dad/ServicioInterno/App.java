package es.urjc.etsii.dad.ServicioInterno;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Hello world!
 *
 */
public class App 
{	
    public static void main( String[] args ) throws IOException
    {
    	int port = 7777;
    	
    	//ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();
    	//SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(port);
		ServerSocket serverSocket = new ServerSocket(port);
		
		while (true) {
			Socket socket = serverSocket.accept();
			Thread t = new Thread(new Servicios(socket));
			t.start();
		}
    }
}
