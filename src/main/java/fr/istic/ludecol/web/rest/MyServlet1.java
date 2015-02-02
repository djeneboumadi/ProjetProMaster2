package fr.istic.ludecol.web.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
 
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
 
public class MyServlet1 extends HttpServlet implements javax.servlet.Servlet {
 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		  ServletContext cntx= getServletContext();
	      // Get the absolute path of the image
		  String filename = cntx.getRealPath("/assets/images/4504839455_8ef5cfccfa.jpg");
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
 
	      BufferedImage small = bigImg.getSubimage(0, 0,200, 200);
	      
	      // Copy the contents of the file to the output stream
	       ByteArrayOutputStream baos = new ByteArrayOutputStream();
	       ImageIO.write( small, "png", baos );
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
	
 
}