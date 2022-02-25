package demoPack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet("/updateStudentDataservlet")
public class updateStudentDataservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();  
       
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer studentID= request.getParameter("studID")!=null?Integer.parseInt(request.getParameter("studID").toString()):0;
		String fname=request.getParameter("fname");
		System.out.println(fname);
		String mname=request.getParameter("mname");
		String lname=request.getParameter("lname");
		String faname=request.getParameter("faname");
		String moname=request.getParameter("moname");
		String mno=request.getParameter("mno");
		String gen=request.getParameter("gen");
		String addr=request.getParameter("addr");
		
		
		
		try {
	Connection	con=DriverManager.getConnection("jdbc:mysql://localhost:3307/Avocado","root","root");
	PreparedStatement  ps=con.prepareStatement("update student3 set FirstName=?,MiddleName=?,LastName=?,FatherName=?,MotherName=?,MobileNo=?,Gender=?,Address=? where StdId=? ");
	ps.setString(1,fname); 
	ps.setString(2,mname); 
	ps.setString(3,lname); 
	ps.setString(4,faname); 
	ps.setString(5,moname); 
	ps.setString(6,mno); 
	ps.setString(7,gen); 
	ps.setString(8,addr); 
	ps.setInt(9,studentID);
	
	
		
	int	rs=ps.executeUpdate();
		if(rs!=1)
			System.out.println("Record is not deleted");
		else 
			System.out.println("record deleted ");
		
		ps.close();
		con.close();
	}
	catch(Exception e)
	{
			System.out.println(e);
			e.printStackTrace();
	}
	 String employeeJsonString = this.gson.toJson("Record updated Successfully");

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
