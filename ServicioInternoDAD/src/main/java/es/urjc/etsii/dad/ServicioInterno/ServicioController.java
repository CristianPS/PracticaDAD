package es.urjc.etsii.dad.ServicioInterno;

import java.awt.image.BufferedImage;
import java.io.File;
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
import javax.annotation.PostConstruct;
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
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

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

@Controller
public class ServicioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private AnuncioRepository anuncioRepository;
	
	// Image properties
    private static final int qr_image_width = 400;
    private static final int qr_image_height = 400;
    private static final String IMAGE_FORMAT = "png";
	
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
            props.setProperty("mail.smtp.user", "sitodiaz13@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);
            
            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            texto.setText("Funciona");
            
            // Se compone el adjunto con la imagen
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(name + title + ".pdf")));
            adjunto.setFileName(name + title + ".pdf");

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);

            // Construimos el mensaje
            Address[] addresses = {new InternetAddress("c.gilsab@alumnos.urjc.es"), new InternetAddress("ji.diazerrejon@outlook.es"), new InternetAddress("c.posada@alumnos.urjc.es")};
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("yo@yo.com"));
            //message.addRecipient(Message.RecipientType.TO, new InternetAddress("ji.diazerrejon@outlook.es"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(name));
            //message.addRecipients(Message.RecipientType.TO, addresses);
            message.setSubject("Funcionaaaa!!!");
            message.setContent(multiParte);
            //message.setText("Hey que pasa primoooo, te estoy mandando esta puta mierda desde Java xdddd");
            //message.setDataHandler(new DataHandler(new FileDataSource("C:\\Users\\sito\\Desktop\\prueba.pdf")));
            //message.setFileName("prueba.pdf");

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("sitodiaz13@gmail.com", "wymwmzewhipvilld");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public void ejecutar(Socket socket) throws IOException, ClassNotFoundException, DocumentException
	{
		Socket so = socket;
		
		InputStream is = so.getInputStream();
		OutputStream os = so.getOutputStream();
		
		ObjectInputStream ois = new ObjectInputStream(is);
		
		//long idUsuario = ois.readLong();
		String email = (String) ois.readObject();
		String title = (String) ois.readObject();
		String entName = (String) ois.readObject();
		String address = (String) ois.readObject();
		String city = (String) ois.readObject();
		String date = (String) ois.readObject();
		//long idAnuncio = ois.readLong();
		
		
		/*for(Usuario u : usuarioRepository.findAll())
		{
			System.out.println(u.getUsername());
		}*/
		
		//Usuario u = usuarioRepository.getById(idUsuario);
		//Anuncio a = anuncioRepository.getById(idAnuncio);
		
		//System.out.println(u.getUsername());
		//System.out.println(u.getEmail());
		
		generarPDF(email, title, entName, address, city, date);
		enviarConGMail(email, title);
		
		eliminarFicheros(email, title);

		is.close();
		os.close();
		ois.close();
		socket.close();
	}
	
	@PostConstruct
	public void init() throws IOException, DocumentException, ClassNotFoundException
	{
		int port = 7777;
    	
    	//ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();
    	//SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(port);
		/*ServerSocket serverSocket = new ServerSocket(port);
		
		while (true) {
			Socket socket = serverSocket.accept();
			Thread t = new Thread(()->{
				try {
					ejecutar(socket);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			t.start();
		}*/
		Usuario u = usuarioRepository.getById(1);
		System.out.println(u.getUsername());
	}
}
