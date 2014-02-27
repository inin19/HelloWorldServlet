

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.microstrategy.web.objects.WebGridHeaders;
import com.microstrategy.web.objects.WebGridRows;
import com.microstrategy.web.objects.WebHeader;
import com.microstrategy.web.objects.WebHeaders;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectInfo;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.WebReportData;
import com.microstrategy.web.objects.WebReportGrid;
import com.microstrategy.web.objects.WebReportInstance;
import com.microstrategy.web.objects.WebReportSource;
import com.microstrategy.web.objects.WebRow;
import com.microstrategy.web.objects.WebRowValue;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static WebObjectsFactory factory = null;
	private static WebIServerSession serverSession = null;

	
	@Override
	public void init() throws ServletException {
		
		System.out.println("Init");
		
		getSession();
		
		
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebObjectSource lObjectSource = factory.getObjectSource();
		
		PrintWriter out = response.getWriter();
		
		out.println("<p>doGet<p>");
		
		try {
			WebObjectInfo reportObject = lObjectSource.getObject("0A91A86D4EDB361ACB8B00BCDECFC00B",EnumDSSXMLObjectTypes.DssXmlTypeReportDefinition,true);
			
//			String a = lReportObjectInfo.getName();
//			
//			String b = serverSession.getWebVersion();
			
			
			WebReportSource reportSource=factory.getReportSource();
			WebReportInstance reportInstance=null;
			
			try{
				reportInstance=reportSource.getNewInstance(reportObject.getID());
			}catch (WebObjectsException ex){
				
			}

			reportInstance.setAsync(false);
			 
			WebReportGrid reportGrid=null;
			
			try {
				WebReportData reportData=reportInstance.getResults();
				reportGrid=reportData.getWebReportGrid();
			} catch (WebObjectsException ex){
				
			}
			
			
			
			WebGridRows gridRows=reportGrid.getGridRows();
//			WebGridHeaders columnHeaders=reportGrid.getColumnHeaders();
//			
//			for (int i=0;i<columnHeaders.size();i++) {
//			    for (int j=0;j<reportGrid.getRowTitles().size();j++) {
//		
//			    }
//			    WebHeaders headers=columnHeaders.get(i);
//			    for (int j=0;j<headers.size();j++) {
//			    	WebHeader header=headers.get(j);
//			    	out.println(header.getDisplayName());
//			    }
//			    
//			}
			
			StringBuilder str = new StringBuilder();
			
			
			for (int i=0;i<gridRows.size();i++) {
	            WebRow row=gridRows.get(i);
	            WebHeaders rowHeaders=row.getHeaderElements();
	            for (int j=0;j<rowHeaders.size();j++) {
	                WebHeader header=rowHeaders.get(j);
	                
	                
	                out.print(header.getDisplayName()+"\t");
	                
	                
	                str.append((header.getDisplayName()+"\t"));
	            }

                

	            
	            for (int j=0;j<row.size();j++) {
	                WebRowValue value=row.get(j);
	               
	                out.print(value.getValue()+"\t");
	                
	                str.append((value.getValue()+"\t"));
	            }
	            
	        }
			
            
            
            
			closeSession(serverSession);
            
			
			TimelineItem timelineItem = new TimelineItem();			
			timelineItem.setText(reportObject.getName());
			
			timelineItem.setText(str.toString());
			
			
		    // Triggers an audible tone when the timeline item is received
		    timelineItem.setNotification(new NotificationConfig().setLevel("DEFAULT"));
			
			
			
			
			
			
			
			
			
		} catch (WebObjectsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	public static void getSession() {
		  System.out.println("Connecting to MicroStrategy");
		  
		  factory = WebObjectsFactory.getInstance();
		  serverSession = factory.getIServerSession();
		  serverSession.setServerName("IN-PC");
		  serverSession.setServerPort(0);
		  serverSession.setProjectName("MicroStrategy Tutorial"); //just a project to connect to, not the cache we're going to clear (see above for projects to clear)
		  serverSession.setLogin("Administrator");
		  serverSession.setPassword("");
		  
		  try {
		   serverSession.getSessionID(); 
		  } catch (WebObjectsException ex) {
		   System.out.println("Error: " + ex.getMessage());
		  }  
	}
	
	
	public static void closeSession(WebIServerSession serverSession) {
        try {
            serverSession.closeSession();
            
        } catch (WebObjectsException ex) {
           
            return;
        }
       
    }

		 

}
