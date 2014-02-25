

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microstrategy.utils.Tree;
import com.microstrategy.web.objects.WebDSN;
import com.microstrategy.web.objects.WebFolder;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectInfo;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.WebSearch;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;
import com.microstrategy.webapi.EnumDSSXMLSearchDomain;
import com.microstrategy.webapi.EnumDSSXMLSearchFlags;

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
		System.out.println("doGet");
		WebObjectSource lObjectSource = factory.getObjectSource();
		try {
			WebObjectInfo lReportObjectInfo = lObjectSource.getObject("6033F89144BDDF80F1EF5280D19CECEA",EnumDSSXMLObjectTypes.DssXmlTypeReportDefinition,true);
			
			WebSearch lSearch = lObjectSource.getNewSearchObject();

			
			lSearch.setDisplayName("*");
            lSearch.setSearchFlags(lSearch.getSearchFlags()
                  + EnumDSSXMLSearchFlags.DssXmlSearchUsedByOneOf
                  + EnumDSSXMLSearchFlags.DssXmlSearchNameWildCard);
            lSearch.setAsync(false);
            lSearch.types().add(new Integer(
                  EnumDSSXMLObjectTypes.DssXmlTypeAttribute));
            lSearch.types().add(new Integer(
                  EnumDSSXMLObjectTypes.DssXmlTypeMetric));
            lSearch.setDomain(
                  EnumDSSXMLSearchDomain.DssXmlSearchDomainProject);
            lSearch.usedBy().add(lReportObjectInfo);
            lSearch.submit();

            
            WebFolder lSearchResults = lSearch.getResults();
            
            
            
            
            
            System.out.println("");
			
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
		  serverSession.setServerName("in-PC");
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
	

		 

}
