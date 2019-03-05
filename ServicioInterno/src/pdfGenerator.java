
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import com.itextpdf.kernel.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
public class pdfGenerator {
	private final static String DEST = "C:\\Users\\crist\\Downloads\\hello_world.pdf";
	public static void main(String args[]) throws IOException{
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		new pdfGenerator().createPdf(DEST);
	}
	public void createPdf(String dest) throws IOException {
	    //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        
        // Initialize document
        Document document = new Document(pdf);

        //Add paragraph to the document
        document.add(new Paragraph("Hello World!"));

        //Close document
        document.close();
	}
}