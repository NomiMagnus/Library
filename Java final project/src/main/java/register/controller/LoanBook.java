package register.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import register.dao.BookDao;
import register.model.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class LoanBook
 */
public class LoanBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDao bookDao = new BookDao();
	static Logger logger=null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoanBook() {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		List allLoansList = new ArrayList();
		try {
			allLoansList = bookDao.getAllLoans();
			request.getSession().setAttribute("loansList",allLoansList);
		} catch (ClassNotFoundException e) {
			logger.error("Error logging: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    if (request.getSession().getAttribute("id") == null) {
	        response.sendRedirect("../../Login.jsp");
	        return;
	    }
	    
	    if(request.getParameter("extend")!=null) {
	    	if(request.getParameter("bookId")==null)
	    		return;
	    	int bookId = Integer.parseInt(request.getParameter("bookId"));
	    	extension(request, response, bookId);
	    	return;
	    }
	    
	    int userId = (int) request.getSession().getAttribute("id");
	    int bookId = Integer.parseInt(request.getParameter("idBook"));
	    Book b = new Book();

	    try {
	        b = BookDao.findBook(bookId);
	        if (b == null) {
	            logger.error("Error: Book not exist.");
	            request.getSession().setAttribute("message", "Book not exist.");
	            return;
	        }
	        if (b.getLoanUserId() == userId) {// החזרה
	            returnBook(request, response, bookId, userId);
	        } else {// השאלה
	            takeBook(request, response, bookId, userId);
	        }
	    } catch (Exception e) {
	        logger.error("Error logging: " + e);
	    }
	}

	private void returnBook(HttpServletRequest request, HttpServletResponse response, int bookId, int userId) {
	    try {
	        BookDao.returnBook(bookId, userId);
	        
	        request.getSession().setAttribute("id", userId);
	        dataChange(request, response);
	        
	        response.sendRedirect("../../BookList.jsp");

	    } catch (Exception e) {
	        logger.error("Error logging: " + e);
	    }
	}

	private void takeBook(HttpServletRequest request, HttpServletResponse response, int bookId, int userId) {
	    try {
	    	Book b = BookDao.findBook(bookId);
	    	if(!b.isLoan()) {
	    		BookDao.takeBook(bookId, userId, (String) request.getSession().getAttribute("userType"));
	    	}
	        
	        dataChange(request, response);
	        
	        response.sendRedirect("../../BookList.jsp");

	    } catch (Exception e) {
	        logger.error("Error logging: " + e);
	    }
	}
	
	private void extension(HttpServletRequest request, HttpServletResponse response, int bookId) {
		int userId=(int) request.getSession().getAttribute("id");
		String type = (String) request.getSession().getAttribute("userType");
		try {
			boolean success = bookDao.extension(bookId, userId, type);
			if(success == false) {
				request.getSession().setAttribute("message","Failed to extend.");
			}
			
			dataChange(request, response);
			response.sendRedirect("../../BookList.jsp");
		}
		catch (Exception e) {
			logger.error("Error logging: " + e);
		}
	}

	private void dataChange(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IOException {
		if(request.getSession().getAttribute("userType")==null || request.getSession().getAttribute("id") == null)
			response.sendRedirect("../../Login.jsp");
		if (request.getSession().getAttribute("userType").equals("worker")) {
			List allLoansList = bookDao.getAllUsersLoans();
			request.getSession().setAttribute("loanData", allLoansList);
		}

		List booksList = bookDao.getListOfBooks();
		request.getSession().setAttribute("bookData", booksList);

			List historyList = bookDao.getUserHistory((int) request.getSession().getAttribute("id"));
			request.getSession().setAttribute("historyData", historyList);
	}

}
