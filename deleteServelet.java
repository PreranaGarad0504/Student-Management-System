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


@WebServlet("/deleteServelet")
public class deleteServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();   
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer studentId=request.getParameter("studentId")!=null?Integer.parseInt(request.getParameter("studentId").toString()):0;
		
		try {
	Connection	con=DriverManager.getConnection("jdbc:mysql://localhost:3307/Avocado","root","root");
	PreparedStatement  ps=con.prepareStatement("delete from student3 where StdId=?");
	ps.setInt(1,studentId); 
	   
		
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
	 String employeeJsonString = this.gson.toJson("Record Inserted Successfully");

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
