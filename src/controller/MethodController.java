package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionForward;
import action.AllAction;



@WebServlet(urlPatterns = { "*.do" }, initParams = { @WebInitParam(name = "properties", value = "method.properties") })

public class MethodController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Properties pr = new Properties();
	private AllAction action = new AllAction();
	private Class[] paramType = new Class[] { HttpServletRequest.class, HttpServletResponse.class };

	 @Override
	   public void init(ServletConfig config) throws ServletException{
		   String props = config.getInitParameter("properties");
		   FileInputStream f = null;
		   try {
			   f=new FileInputStream(config.getServletContext().getRealPath("/")+"WEB-INF/"+props);
			   pr.load(f);
			   f.close();
		   } catch(IOException e) { e.printStackTrace(); }
	   }
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("euc-kr");
			Object[] paramObjs = new Object[] {request,response};
			ActionForward forward = null;
			String command = null;
			try {
				command = request.getRequestURI().substring(request.getContextPath().length());
				String methodName = pr.getProperty(command);
				Method method = AllAction.class.getMethod(methodName, paramType);
				// invoke : 메서드 실행.
				forward = (ActionForward)method.invoke(action, paramObjs);
			} catch(NullPointerException e) {
				forward = new ActionForward();
			} catch(Exception e) {
				throw new ServletException(e);
			} if(forward != null) {
				if(forward.isRedirect()) {
					response.sendRedirect(forward.getView());
				}else {
					if(forward.getView() == null) {
						forward.setView(command.replace(".do", ".jsp"));
					} 
					RequestDispatcher disp = request.getRequestDispatcher(forward.getView());
					disp.forward(request,response);
				}
			}
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			doGet(request, response);
		}
}
