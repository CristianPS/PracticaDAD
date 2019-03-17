package es.urjc.etsii.dad.ServicioInterno;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class SocketHandler extends TextWebSocketHandler{

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private AnuncioRepository anuncioRepository;
	@Autowired
	private EmpresarioRepository empresarioRepository;

	// Image properties
    private static final int qr_image_width = 400;
    private static final int qr_image_height = 400;
    private static final String IMAGE_FORMAT = "png";

	/*@Override
	public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
		System.err.println("Error en el cliente " + session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("Sesion " + session.getId() + "cerrada porque " + status.getReason());
	}*/

	public static void generarQR(Document document, String name, String title) throws IOException, DocumentException
    {
    	// URL to be encoded
        String data = "http://www.google.com";

        // Encode URL in QR format
        BitMatrix matrix;
        Writer writer = new QRCodeWriter();
        try {

            matrix = writer.encode(data, BarcodeFormat.QR_CODE, qr_image_width, qr_image_height);

        } catch (WriterException e) {
            e.printStackTrace(System.err);
            return;
        }

        // Create buffered image to draw to
        BufferedImage image = new BufferedImage(qr_image_width,
                   qr_image_height, BufferedImage.TYPE_INT_RGB);

        // Iterate through the matrix and draw the pixels to the image
        for (int y = 0; y < qr_image_height; y++) {
            for (int x = 0; x < qr_image_width; x++) {
                int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
                image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
            }
        }

        // Write the image to a file
        FileOutputStream qrCode = new FileOutputStream(name + title + ".png");
        ImageIO.write(image, IMAGE_FORMAT, qrCode);

        qrCode.close();

        Image imagen = Image.getInstance(name + title + ".png");
        imagen.scaleAbsolute(100f, 100f);
        imagen.setAlignment(Element.ALIGN_CENTER);

        document.add(imagen);
    }

    public static void eliminarFicheros(String name, String title)
    {
    	File file = new File(name + title + ".png");
        File im = new File(name + title + ".pdf");

        if (file.delete())
            System.out.println("El fichero ha sido borrado satisfactoriamente");
        else
            System.out.println("El fichero no pudó ser borrado");

        if (im.delete())
            System.out.println("La imagen ha sido borrada satisfactoriamente");
        else
            System.out.println("La imagen no pudó ser borrada");
    }

	public static void generarPDF(String name, String title, String entName, String address, String city, String date) throws DocumentException, IOException
	{
		Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(name + title + ".pdf"));
        document.open();

        /*document.add(new Paragraph("Cachimba Premium por 10€", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD)));
        document.add(new Paragraph("Anubis", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL)));
        document.add(new Paragraph("C.C.Xanadú, Arroyomolinos (Madrid)", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL)));
        document.add(new Paragraph("Fecha de expiración: 18/09/2019", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL)));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Debes presentar este código QR en el establecimiento indicado para poder canjear la oferta", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
        */

        document.add(new Paragraph(title, new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD)));
        document.add(new Paragraph(entName, new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL)));
        document.add(new Paragraph(address + " (" + city + ")", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL)));
        document.add(new Paragraph("Fecha de expiración: " + date, new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL)));
        //document.add(new Paragraph(a.getLocal().getEntName(), new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL)));
        //document.add(new Paragraph(a.getLocal().getAddress() + ", " + a.getLocal().getCity(), new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL)));
        //document.add(new Paragraph("Fecha de expiración: " + a.getDate(), new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL)));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Debes presentar este código QR en el establecimiento indicado para poder canjear la oferta", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));


        //Generamos el codigo QR y lo añadimos al PDF

        generarQR(document, name, title);

        //eliminarFicheros();

        document.close();

	}

	public static void enviarConGMail(String name, String title) {

		try
        {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "muchafiestapaga.na@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            texto.setText("Has recibido la oferta ( " + title + " )\nMuchas gracias por confiar en nosotros\n\nDepartamento de comunicaciones de la empresa FIESTA PAGA-NA\nCorreo de contacto: muchafiestapaga.na@gmail.com");
            
            // Se compone el adjunto con la imagen
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(name + title + ".pdf")));
            adjunto.setFileName(name + title + ".pdf");

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);

            // Construimos el mensaje
            Address[] addresses = {new InternetAddress(name), new InternetAddress("ji.diazerrejon@outlook.es"), new InternetAddress("c.posada@alumnos.urjc.es")};

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("yo@yo.com"));
            //message.addRecipient(Message.RecipientType.TO, new InternetAddress("ji.diazerrejon@outlook.es"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(name));
            //message.addRecipients(Message.RecipientType.TO, addresses);
            message.setSubject("FIESTA PAGA-NA " + title);
            message.setContent(multiParte);
            //message.setText("Hey que pasa primoooo, te estoy mandando esta puta mierda desde Java xdddd");
            //message.setDataHandler(new DataHandler(new FileDataSource("C:\\Users\\sito\\Desktop\\prueba.pdf")));
            //message.setFileName("prueba.pdf");

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("muchafiestapaga.na@gmail.com", "xcqsxmzxryjivxga");
            t.sendMessage(message, message.getAllRecipients());
            
            // Cierre.
            t.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}

	public static void enviarConGMailPass(String name, String pass) {

		try
        {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "muchafiestapaga.na@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            texto.setText("Su nueva contraseña es: " + pass + "\nMuchas gracias por confiar en nosotros\n\nDepartamento de comunicaciones de la empresa FIESTA PAGA-NA\nCorreo de contacto: muchafiestapaga.na@gmail.com");

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("yo@yo.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(name));
            message.setSubject("FIESTA PAGA-NA Recuperación de contraseña");
            message.setContent(multiParte);

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("muchafiestapaga.na@gmail.com", "xcqsxmzxryjivxga");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	/*public void getVariables(long idA, String username) throws DocumentException, IOException {
		Usuario user = usuarioRepository.getByUsername(username);
		Anuncio offer = anuncioRepository.getById(idA);

		generarPDF(user.getEmail(), offer.getTitle(), offer.getLocal().getEntName(), offer.getLocal().getAddress(), offer.getLocal().getAddress(), offer.getDate());
		enviarConGMail(user.getEmail(), offer.getTitle());

		eliminarFicheros(user.getEmail(), offer.getTitle());
	}*/
	
    public static String getAlphaNumericString(int n) 
    { 
  
        // lower limit for LowerCase Letters 
        int lowerLimit = 97; 
  
        // lower limit for LowerCase Letters 
        int upperLimit = 122; 
  
        Random random = new Random(); 
  
        // Create a StringBuffer to store the result 
        StringBuffer r = new StringBuffer(n); 
  
        for (int i = 0; i < n; i++) { 
  
            // take a random value between 97 and 122 
            int nextRandomChar = lowerLimit 
                                 + (int)(random.nextFloat() 
                                         * (upperLimit - lowerLimit + 1)); 
  
            // append a character at the end of bs 
            r.append((char)nextRandomChar); 
        } 
  
        // return the resultant string 
        return r.toString(); 
    }
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Conectado a " + session.getId());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String datos = message.getPayload();
		int barraBaja = datos.indexOf("_");
		int tipo = Integer.parseInt(datos.substring(0, barraBaja));
		switch(tipo) {
			case 0:
				int separador = datos.indexOf("-");
				long idAnuncio = Long.parseLong(datos.substring(2, separador));
				String username = datos.substring(separador+1);
				System.out.println(idAnuncio);
				System.out.println(username);
				//ServicioController.getVariables(idAnuncio, username);


				Usuario user = usuarioRepository.getByUsername(username);
				Anuncio offer = anuncioRepository.getById(idAnuncio);

				generarPDF(user.getEmail(), offer.getTitle(), offer.getLocal().getEntName(), offer.getLocal().getAddress(), offer.getLocal().getAddress(), offer.getDate());
				enviarConGMail(user.getEmail(), offer.getTitle());

				eliminarFicheros(user.getEmail(), offer.getTitle());
				
				break;
				
			case 1:
				String correo = datos.substring(2);
				System.out.println(correo);
				
				//Un empresario tambien puede haber olvidado la contraseña
				
				Empresario e;
				Usuario u;
				
				if(usuarioRepository.getByUsername(correo) != null)
				{
					u = usuarioRepository.getByUsername(correo);
					System.out.println(u.getEmail());
					
					String generatedString = getAlphaNumericString(7);
				    System.out.println(generatedString);
					u.setPassword(generatedString);
					usuarioRepository.save(u);
					enviarConGMailPass(u.getEmail(),generatedString);
				}
				else
				{
					if(empresarioRepository.getByUsername(correo) != null)
					{
						e = empresarioRepository.getByUsername(correo);
						System.out.println(e.getEmail());
						
						String generatedString = getAlphaNumericString(7);
					    System.out.println(generatedString);
						e.setPassword(generatedString);
						empresarioRepository.save(e);
						enviarConGMailPass(e.getEmail(),generatedString);
					}
				}
				
				break;
				/*Usuario u = usuarioRepository.getByUsername(correo);
				System.out.println(u.getEmail());

			    String generatedString = getAlphaNumericString(7);
			    System.out.println(generatedString);
				u.setPassword(generatedString);
				usuarioRepository.save(u);
				enviarConGMailPass(u.getEmail(),generatedString);
				
				break;*/
		}
		

	}


}
