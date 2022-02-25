package demoPack;

import java.io.FileWriter;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@WebServlet("/exportServlet1")
public class exportServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private Gson gson = new Gson();
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		Connection con=null;
		PreparedStatement ps=null;
		JsonArray studentArray=new JsonArray();
		
	try {
		con=DriverManager.getConnection("jdbc:mysql://localhost:3307/Avocado","root","root");
	     Statement st=con.createStatement();
	    ResultSet rs=st.executeQuery("select * from student3");
	
	  //Creating a JSONObject object
	      JSONObject jsonObject = new JSONObject();
	      //Creating a json array
	      JSONArray array = new JSONArray();
	     // ResultSet rs = RetrieveData();
	      //Inserting ResutlSet data into the json object
	      while(rs.next()) {
	         JSONObject record = new JSONObject();
	         //Inserting key-value pairs into the json object
	         record.put("StdId", rs.getInt("StdId"));
	         record.put("FirstName", rs.getString("FirstName"));
	         record.put("MiddleName", rs.getString("MiddleName"));
	         record.put("LastName", rs.getString("LastName"));
	         record.put("FatherName", rs.getString("FatherName"));
	         record.put("MotherName", rs.getString("MotherName"));
	         record.put("MobileNo", rs.getString("MobileNo"));
	         record.put("Gender", rs.getString("Gender"));
	         record.put("Address", rs.getString("Address"));
	         record.put("password", rs.getString("password"));
	      //   System.out.println("HII GOOD EVENING");
	         array.add(record);
	      
	      }
	      
	      jsonObject.put("Players_data", array);
	      FileWriter file = new FileWriter("C:\\Users\\Dell\\Desktop\\jsonFiles\\output2.json");
	         file.write(jsonObject.toJSONString());
	         file.close();
	      
	      String employeeJsonString = this.gson.toJson(studentArray);

	        PrintWriter out = response.getWriter();
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        out.print(employeeJsonString);
	        out.flush();  
	         
	      
	      
	}
	
	    	catch (SQLException e) {
	    		
	    		e.printStackTrace();
	    	}
	}
		
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
