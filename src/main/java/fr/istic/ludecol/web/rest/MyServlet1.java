package fr.istic.ludecol.web.rest;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyServlet1 extends HttpServlet implements javax.servlet.Servlet {

	private Hashtable mosaicImg = new Hashtable<String, BufferedImage>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext cntx= getServletContext();
		// Get the absolute path of the image
		String url = req.getRequestURL().toString();
		
		String filename = cntx.getRealPath("/assets/images/photo-aerienne-marais-salants.jpg");
		// retrieve mimeType dynamically
		String mime = cntx.getMimeType(filename);
		if (mime == null) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		resp.setContentType(mime);
		File file = new File(filename);

		FileInputStream in = new FileInputStream(file);
		OutputStream out = resp.getOutputStream();

		BufferedImage bigImg = ImageIO.read(file);
		// The above line throws an checked IOException which must be caught.
		this.decouperImg( bigImg);
		//BufferedImage small = bigImg.getSubimage(0, 0,200, 200);

		// Copy the contents of the file to the output stream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write((RenderedImage) mosaicImg.get("part0_0"), "png", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		resp.setContentLength(imageInByte.length);
		out.write(imageInByte);
		out.close();
		in.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	protected void decouperImg(BufferedImage bigImg){
		int xA, yA;
		int height = bigImg.getHeight();
		int width = bigImg.getWidth();

		int coefHeight = 1;
		int coefWidth = 1;

		if(height < 1000 &&  height  > 500){
			coefHeight = coefHeight * 2;
		}else if (height < 2000 &&  height  > 1000){
			coefHeight = coefHeight * 3;
		}else if (height > 2000){
			coefHeight = coefHeight * 4;
		}

		if(width < 1000 &&  width  > 500){
			coefWidth = coefWidth * 2;
		}else if (width < 2000 &&  width  > 1000){
			coefWidth = coefWidth * 3;
		}else if (width > 2000){
			coefWidth = coefWidth * 4;
		}
		
		int widthCoupe = width / coefWidth;
		int heightCoupe = height / coefHeight;
		
		xA = 0;
		yA = 0;
		for(int i = 0 ; i < coefHeight ; i++){
			
			for(int j = 0 ; j < coefWidth ; j++){
				String label = "part"+i+"_"+j;
				System.out.println("Coordonnees zone : A("+ xA +","+ yA +")");
				BufferedImage small = bigImg.getSubimage(xA, yA, widthCoupe, heightCoupe);
				mosaicImg.put(label, small);
				xA += widthCoupe;
			}
			xA = 0;
			yA += heightCoupe;
		}


	} 

}