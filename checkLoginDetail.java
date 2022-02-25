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

import com.google.gson.Gson;


@WebServlet("/checkLoginDetail")
public class checkLoginDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private Gson gson = new Gson();
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usrName=request.getParameter("usrName");
		String pass=request.getParameter("pass");
		
		char firstLetter=usrName.charAt(0);
		String lastName=usrName.substring(1,usrName.length());
		
		System.out.println(firstLetter+" "+lastName);
		
		
		
		//db connection
		Connection con;
		ResultSet rs=null;
		String employeeJsonString="";
		Integer count=0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/Avocado","root","root");
			Statement st=con.createStatement();
		String query="select * from student3 where FirstName like'"+firstLetter+"%' and LastName='"+lastName+"' and password='"+pass+"'";
			 rs=st.executeQuery(query);
			
			 while(rs.next())
				{
				 count=1;
				}
			 
		 employeeJsonString = this.gson.toJson(count.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

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
