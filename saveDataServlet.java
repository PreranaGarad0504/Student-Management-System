package demoPack;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.io.File;
//import org.apache.tomcat.jni.File;

import javax.servlet.annotation.MultipartConfig;

import com.google.gson.Gson;


@WebServlet("/saveDataServlet")
@MultipartConfig(maxFileSize = 16177215)  //16mb 
public class saveDataServlet extends HttpServlet {
	HttpServletRequest request;
	private static final long serialVersionUID = 1L;
	 private Gson gson = new Gson();  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("welcome");
		FileInputStream fis=null;	 
		String firstName=request.getParameter("fname");
		//System.out.println(firstName);
		String mname=request.getParameter("mname");
		String lname=request.getParameter("lname");
		String faname=request.getParameter("faname");
		System.out.println(faname);
		String moname=request.getParameter("moname");
		String mno=request.getParameter("mno");
		String gen=request.getParameter("gen");
		String add=request.getParameter("add");
		String password=request.getParameter("password");
	
		String myloc=request.getParameter("photo");
	     
		System.out.println(myloc);
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection	con=DriverManager.getConnection("jdbc:mysql://localhost:3307/Avocado","root","root");
	PreparedStatement  ps=con.prepareStatement("insert into student3(FirstName,MiddleName,LastName,FatherName,MotherName,MobileNo,Gender,Address,password,photo) values(?,?,?,?,?,?,?,?,?,?)");
	    
	    ps.setString(1,firstName);
		ps.setString(2, mname);
		ps.setString(3, lname);
		ps.setString(4, faname);
		ps.setString(5,moname);
		ps.setString(6, mno);
		ps.setString(7, gen);
		ps.setString(8, add);
		ps.setString(9, password);
		System.out.println(add);
		
		File image= new File(myloc);
		fis=new FileInputStream(image);
		ps.setBinaryStream(10, (InputStream) fis, (int) (image.length()));

	int	rs=ps.executeUpdate();
		if(rs!=1)
			System.out.println("Record is not inserted");
		else 
			System.out.println("record inserted");
		
   	ps.close();
	con.close();
	}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		 String employeeJsonString = this.gson.toJson("Record inserted Successfully");
		 PrintWriter out = response.getWriter();
	   	 response.setContentType("application/json");
	   	 response.setCharacterEncoding("UTF-8");
	   	 out.print(employeeJsonString);
	   	 out.flush(); 
	        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
