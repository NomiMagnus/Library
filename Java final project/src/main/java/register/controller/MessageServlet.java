package register.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import register.dao.MessageDao;
import register.model.Message;
import register.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * Servlet implementation class MessageServlet
 */
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MessageDao messageDao = new MessageDao();
	static Logger logger=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init(ServletConfig config) throws ServletException {

		String path = System.getProperty("user.home")
				+ "/eclipse-workspace/Log4j_withConfFile/src/main/webapp/WEB-INF/classes/log4j.properties";
		System.out.println("****path->" + path);
		PropertyConfigurator.configure(path);
		logger = Logger.getLogger(LibraryWorkerServlet.class.getName());

	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		List dataList = new ArrayList();
		try {
			dataList = messageDao.getAllMessages();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("Error logging: " + e);
		}
		request.getSession().setAttribute("messageData", dataList);
		response.sendRedirect("../../ReadMessages.jsp");

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("messageId") != null) {
			deleteMessage(request);
			doGet(request, response);
		}
		else {
			createMessage(request);
			response.sendRedirect("../../SendMessage.jsp");
		}
	}
	
	private void createMessage(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		//String messageDate = request.getParameter("messageDate");

		Message message = new Message();
		if(userId!=null)
			message.setUserId(Integer.parseInt(userId));
		message.setTitle(title);
		message.setBody(body);
//		message.setMessageDate(messageDate);


		try {
			messageDao.createNewMessage(message);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("Error logging: " + e);
		}
		
		String m= "Message created!";
		request.getSession().setAttribute("message", m);
	}
	
	private void deleteMessage(HttpServletRequest request) {
		try {
			int messageId = Integer.parseInt(request.getParameter("messageId"));
			messageDao.deleteMessage(messageId);
			
		}catch (Exception e) {
			logger.error("Error logging: " + e);
			e.printStackTrace();
		}
		
		String message= "Message deleted!";
		request.getSession().setAttribute("message", message);
	}

}
