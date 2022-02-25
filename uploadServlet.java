package demoPack;
//Import required java libraries
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//this class takes file from userDetail.jsp And Add into our database.
@MultipartConfig(maxFileSize = 16177215) 
@WebServlet("/uploadServlet")
public class uploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpServletResponse hhttpResponse;  
	private boolean isMultipart;
	   private String filePath;
	   private int maxFileSize = 50 * 1024;
	   private int maxMemSize = 4 * 1024;
	   private File file ;

	   public void init(){
	      // Get the file location where it would be stored.
	      filePath = getServletContext().getInitParameter("file-upload"); 
	   }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		hhttpResponse=response;
		System.out.println("hii");
		String fileName="";
		 isMultipart = ServletFileUpload.isMultipartContent(request);
		 response.setContentType("text/html");
	      java.io.PrintWriter out = response.getWriter( );
	      
	      DiskFileItemFactory factory = new DiskFileItemFactory();
	      // maximum size that will be stored in memory
	      factory.setSizeThreshold(maxMemSize);
	   
	      // Location to save data that is larger than maxMemSize.
	      factory.setRepository(new File("C:\\Users\\Dell\\Desktop\\uplodedJson"));

	      //Create a new file upload handler
	      ServletFileUpload upload = new ServletFileUpload(factory);
	      
	      try{ 
	          // Parse the request to get file items.
	          List fileItems = upload.parseRequest(request);
	 	
	          // Process the uploaded file items
	          Iterator i = fileItems.iterator();
	          
	          out.println("<html>");
	          out.println("<head>");
	          out.println("<title>Servlet upload</title>");  
	          out.println("</head>");
	          out.println("<body>");
	    
	          while ( i.hasNext () ) {
	             FileItem fi = (FileItem)i.next();
	             if ( !fi.isFormField () ) {
	                // Get the uploaded file parameters
	                String fieldName = fi.getFieldName();
	                 fileName = fi.getName();
	                String contentType = fi.getContentType();
	                boolean isInMemory = fi.isInMemory();
	                long sizeInBytes = fi.getSize();
	             
	                // Write the file
	                if( fileName.lastIndexOf("\\") >= 0 ) {
	                   file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
	                } else {
	                   file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
	                }
	                fi.write( file ) ;
	                out.println("Uploaded Filename: " + fileName + "<br>");
	             }
	          }
	          
	          readJsonFile(fileName);
	          
	          
	          
	          out.println("</body>");
	          out.println("</html>");
	          } catch(Exception ex) {
	             System.out.println(ex);
	          }
		   
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	void readJsonFile(String fileName)
	{
		String filePath1=filePath+fileName;
		JSONParser jsonParser = new JSONParser();
		        
		             
		 try {
			 
			 Connection	con=DriverManager.getConnection("jdbc:mysql://localhost:3307/Avocado","root","root");
             //Parsing the contents of the JSON file
             JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filePath1));
             //Retrieving the array
             JSONArray jsonArray = (JSONArray) jsonObject.get("players_data");
             PreparedStatement ps=null;       
             for(Object object : jsonArray) {
		            	 
		                JSONObject record = (JSONObject) object;
		                 
		                
		                String firstName=(String) record.get("First_Name");
		        		String mname=(String) record.get("mname");
		        		String lname=(String) record.get("Last_Name");
		        		String faname=(String) record.get("faname");
		        		String moname=(String) record.get("moname");
		        		String mno=(String) record.get("mno");
		        		String gen=(String) record.get("gen");
		        		String add=(String) record.get("add");
		        		
		        		  ps=con.prepareStatement("insert into student3(FirstName,MiddleName,LastName,FatherName,MotherName,MobileNo,Gender,Address) values(?,?,?,?,?,?,?,?)");
		        		    ps.setString(1,firstName);
		        			ps.setString(2, mname);
		        			ps.setString(3, lname);
		        			ps.setString(4, faname);
		        			ps.setString(5,moname);
		        			ps.setString(6, mno);
		        			ps.setString(7, gen);
		        			ps.setString(8, add);
		        			int	rs=ps.executeUpdate();
		        			if(rs!=1)
		        				System.out.println("Record is not inserted");
		        			else 
		        				System.out.println("record inserted");
		        			
		        			
		             }  
		             System.out.println("Records inserted.....");
		             ps.close();
	        		 con.close();
	        		 hhttpResponse.sendRedirect("userDetail.jsp");  
		          } catch (FileNotFoundException e) {
		             e.printStackTrace();
		          } catch (IOException e) {
		             e.printStackTrace();
		          } catch (ParseException e) {
		             e.printStackTrace();
		          } catch (Exception e) {
		             // TODO Auto-generated catch block
		             e.printStackTrace();
		          }
		       
		  }      
		         
	

}
