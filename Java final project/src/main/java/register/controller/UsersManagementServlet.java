package register.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import register.model.Student;
import register.model.Teacher;
import register.model.User;
import register.dao.BookDao;
import register.dao.StudentDao;
import register.dao.TeacherDao;
import register.dao.UserDao;
import register.dao.MessageDao;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class UsersManagementServlet
 */
@WebServlet("/UsersManagementServlet")
public class UsersManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = null;
	// private StudentDao studentDao = new StudentDao();
	private UserDao userDao = new UserDao();
	private BookDao bookDao = new BookDao();
	private MessageDao messageDao = new MessageDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UsersManagementServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {

		String path = System.getProperty("user.home")
				+ "/eclipse-workspace/Log4j_withConfFile/src/main/webapp/WEB-INF/classes/log4j.properties";
		System.out.println("path->" + path);
		PropertyConfigurator.configure(path);
		logger = Logger.getLogger(StudentServlet.class.getName());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		// Delete user from DB by idUser
		try {
			deleteUser(request, response);
		} catch (ClassNotFoundException e) {
			logger.error("Error logging: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// add user to DB
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("userName") != null && request.getParameter("address") == null) {
			try {
				login(request, response);
				return;
			} catch (ClassNotFoundException e) {
				logger.error("Error logging: " + e);
				e.printStackTrace();
			}
		} else
			register(request, response);
	}

	private boolean deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, ServletException, IOException {
		boolean success = false;
		try {
			int idUser = Integer.parseInt(request.getParameter("idUser"));
			success = userDao.deleteUser(idUser);

		} catch (Exception e) {
			logger.error("Error logging: " + e);
			e.printStackTrace();
		}

//		String message= "User deleted!";
//		request.setAttribute("message", message);

		List dataList = new ArrayList();
		try {
			dataList = userDao.getListOfUsers();

		} catch (ClassNotFoundException e) {
			logger.error("Error logging: " + e);
			e.printStackTrace();
		}
		request.getSession().setAttribute("userData", dataList);

		response.sendRedirect("../../LoginSuccess.jsp");

//		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/LoginSuccess.jsp");
//		System.out.println("LoginSucess path->" + requestDispatcher);
//		if (requestDispatcher != null)
//			requestDispatcher.forward(request, response);

		return success;
	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, ServletException, IOException {
		User user;
		try {
			Object myUser[] = userDao.login(request.getParameter("userName"), request.getParameter("password"));

			if (myUser == null || myUser[0] == null) {
				int loginTryCount = 0;
				if (request.getSession().getAttribute("loginTryCount") == null)
					request.getSession().setAttribute("loginTryCount", 1);
				loginTryCount = (int) request.getSession().getAttribute("loginTryCount");
				request.getSession().setAttribute("loginTryCount", loginTryCount + 1);
				request.getSession().setAttribute("message", "User not found");

				response.sendRedirect("../../Login.jsp");

//				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Login.jsp");
//				System.out.println("Login path->" + requestDispatcher);
//				if (requestDispatcher != null)
//					requestDispatcher.forward(request, response);
			} else {
				request.getSession().setAttribute("id", myUser[1]);
				user = (User) myUser[0];
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("userType", user.getType());

				List usersList = new ArrayList();
				List booksList = new ArrayList();
				List messageList = new ArrayList();
				List allLoansList = new ArrayList();
				List historyList = new ArrayList();
				List userLoansList = new ArrayList();
				try {
					if (user.getType().equals("worker")) {
						usersList = userDao.getListOfUsers();
						request.getSession().setAttribute("userData", usersList);

						messageList = messageDao.getAllMessages();
						request.getSession().setAttribute("messageData", messageList);

						allLoansList = bookDao.getAllUsersLoans();
						request.getSession().setAttribute("loanData", allLoansList);
					}
					booksList = bookDao.getListOfBooks();
					request.getSession().setAttribute("bookData", booksList);

					if (request.getSession().getAttribute("id") != null) {
						historyList = bookDao.getUserHistory((int) request.getSession().getAttribute("id"));
						request.getSession().setAttribute("historyData", historyList);
					}

				} catch (ClassNotFoundException e) {
					logger.error("Error logging: " + e);
					e.printStackTrace();
				}

				response.sendRedirect("../../BookList.jsp");
			}
//			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/BookList.jsp");
//			System.out.println("BookList path->" + requestDispatcher);
//			if (requestDispatcher != null)
//				requestDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error logging: " + e);
		}

	}

	private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String phoneNum = request.getParameter("phoneNum");
		String type = request.getParameter("userType");

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setAddress(address);
		user.setPhoneNum(phoneNum);
		user.setType(type);

		try {
			userDao.registerUser(user);

		} catch (ClassNotFoundException e) {
			logger.error("Error logging: " + e);
			e.printStackTrace();
		}

		List dataList = new ArrayList();
		try {
			dataList = userDao.getListOfUsers();

		} catch (ClassNotFoundException e) {
			logger.error("Error logging: " + e);
			e.printStackTrace();
		}

		request.getSession().setAttribute("userData", dataList);

//		RequestDispatcher requestDispatcher = request.getRequestDispatcher("../../LoginSuccess.jsp");
		response.sendRedirect("../../LoginSuccess.jsp");
		// System.out.println("LoginSucess path->" + requestDispatcher);
//		if (requestDispatcher != null)
//			requestDispatcher.forward(request, response);

	}

}
