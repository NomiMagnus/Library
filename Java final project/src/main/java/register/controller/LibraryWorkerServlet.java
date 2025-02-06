package register.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import register.dao.LibraryWorkerDao;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import register.model.LibraryWorker;

/**
 * Servlet implementation class LibraryWorkerServlet
 */
@WebServlet("/LibraryWorkerServlet")
public class LibraryWorkerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger=null;
	private LibraryWorkerDao libraryWorkerDao = new LibraryWorkerDao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LibraryWorkerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	
    	String path = System.getProperty("user.home")+
    			"/eclipse-workspace/Log4j_withConfFile/src/main/webapp/WEB-INF/classes/log4j.properties";
    	   System.out.println("****path->"+path);
    		PropertyConfigurator.configure(path);
    		logger= Logger.getLogger(LibraryWorkerServlet.class.getName());
    		
    	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		logger.debug("Debug logging");
		logger.info("Info logging");
		logger.warn("Warning logging");
		logger.error("Error logging");
		logger.fatal("Fatal logging");
		
		logger.info("This is an log4j info message...");
		logger.debug("This is an log4j debug message...");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//libraryWorker Servlet code
//		String userName = request.getParameter("userName");
//		String password = request.getParameter("password");
//		String address = request.getParameter("address");
//		String phoneNum = request.getParameter("phoneNum");
//		
//		LibraryWorker libraryWorker = new LibraryWorker();
//		
//		libraryWorker.setUserName(userName);
//		libraryWorker.setPassword(password);
//		libraryWorker.setAddress(address);
//		libraryWorker.setPhoneNum(phoneNum);
//			
//		try {
//				
//			libraryWorkerDao.registerLibraryWorker(libraryWorker);
//		} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		List dataList = new ArrayList();
//		try {
//		   dataList = libraryWorkerDao.getListOfLibraryWorkers();
//		   
//		}
//		catch(ClassNotFoundException e) {
//			
//			e.printStackTrace();
//		}
//		
//        request.setAttribute("libraryWorkerData", dataList);
//		
//		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/LoginSuccess.jsp");
//		System.out.println("LoginSucess path->"+ requestDispatcher);
//		if(requestDispatcher !=null )
//            requestDispatcher.forward(request, response);
		
	
	}

}
