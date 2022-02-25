package demoPack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.io.InputStream;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@WebServlet("/processionServlet")
public class processionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private Gson gson = new Gson();
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("hii");
		Connection con=null;
		PreparedStatement ps=null;
		JsonArray studentArray=new JsonArray();
		
	try {
		con=DriverManager.getConnection("jdbc:mysql://localhost:3307/Avocado","root","root");
	     Statement st=con.createStatement();
	    ResultSet rs=st.executeQuery("select * from student3");
	
	while(rs.next())
		{
		JsonObject studentObje=new JsonObject();
		studentObje.addProperty("id",rs.getInt(1));
		studentObje.addProperty("FirstName",rs.getString(2));
		studentObje.addProperty("MiddleName",rs.getString(3));
		studentObje.addProperty("LastName",rs.getString(4));
		studentObje.addProperty("FatherName",rs.getString(5));
		studentObje.addProperty("MotherName",rs.getString(6));
		studentObje.addProperty("MobileNo",rs.getString(7));
		studentObje.addProperty("Gender",rs.getString(8));
		studentObje.addProperty("Address",rs.getString(9));
	//	studentobje.addProperty("photo",rs.)
//		
//		 Blob blob = rs.getBlob("photo");
//		 InputStream inputStream = blob.getBinaryStream();
//         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//         byte[] buffer = new byte[4096];
//         int bytesRead = -1;
//          
//         while ((bytesRead = inputStream.read(buffer)) != -1) {
//             outputStream.write(buffer, 0, bytesRead);                  
//         }
//          
//         byte[] imageBytes = outputStream.toByteArray();
//         String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//          
//          
//         inputStream.close();
//         outputStream.close();
//		 
//         //studentObje.setBase64Image(base64Image);
		 
		studentArray.add(studentObje);
		}
		}
	catch (SQLException e) {
		
		e.printStackTrace();
	}
	        String employeeJsonString = this.gson.toJson(studentArray);

	        PrintWriter out = response.getWriter();
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        out.print(employeeJsonString);
	        out.flush();   
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
