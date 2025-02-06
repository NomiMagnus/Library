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
import register.model.Student;
import register.model.Teacher;
import register.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class BookServlet
 */
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = null;
	private BookDao bookDao = new BookDao();
	List dataList = new ArrayList();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookServlet() {
		super();
	}

	
	public void init(ServletConfig config, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = System.getProperty("user.home")
				+ "/eclipse-workspace/Log4j_withConfFile/src/main/webapp/WEB-INF/classes/log4j.properties";
		System.out.println("####path->" + path);
		PropertyConfigurator.configure(path);
		logger = Logger.getLogger(StudentServlet.class);
		getListBooks(request, response);
	}

	private void getListBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			dataList = bookDao.getListOfBooks();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("Error logging: " + e);
		}
		request.getSession().setAttribute("bookData", dataList);
		response.sendRedirect("../../BookList.jsp");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Delete book from DB by idBook
		try {
			deleteBook(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error logging: " + e);
		}
		response.sendRedirect("../../BookList.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//add book
		Book book = new Book();
		if (request.getParameter("idBook") != null) {
			try {
				deleteBook(request, response);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("Error logging: " + e);
			}
		} else {
			String name = request.getParameter("name");
			String author = request.getParameter("author");
			String description = request.getParameter("description");
			Boolean isLoan = false;

			book.setName(name);
			book.setAuthor(author);
			book.setDescription(description);
			book.setLoan(isLoan);

			try {
				bookDao.addBook(book);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("Error logging: " + e);
			}
			
//			logger.info("Info logging: succeeded to add book "+request.getParameter("name")+".");

			getListBooks(request, response);
			request.getSession().setAttribute("bookData", dataList);
//			response.sendRedirect("../../BookList.jsp");
			return;
		}
	}


	private boolean deleteBook(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException {
		boolean success = false;
		try {
			int idBook = Integer.parseInt((String) request.getParameter("idBook"));
			success = bookDao.deleteBook(idBook);
			getListBooks(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error logging: " + e);
		}
//		logger.info("Info logging: succeed to delete book. id: "+request.getParameter("idBook")+".");
		return success;
	}
}
