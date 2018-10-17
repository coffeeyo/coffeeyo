package com.coffeeyo.common.dispatcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

@WebServlet("*.yo")
public class CoffeeyoFrontController extends HttpServlet {
	private static final long serialVersionUID = 6850728633270586873L;
	
	@SuppressWarnings("rawtypes")
	HashMap map= new HashMap();

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String servletPath = request.getServletPath();
		System.out.println("servletPath="+servletPath);
		if(map.containsKey(servletPath)) {
			Action controller = (Action)map.get(servletPath);
			
			try {
 				String view = controller.execute(request, response);
 				
 				if(!view.equals("none")) {
					RequestDispatcher rd = request.getRequestDispatcher(view);
					rd.forward(request, response);
 				}
			} catch (Exception e) {
				System.out.println("뷰 호출 에러="+e);
			}
		}
		else
		{
			//요청내용에 준비된 컨트롤러가 없으므로 준비된 에러 페이지를 보여주자.
			response.sendRedirect("/error/errorRequest.jsp");
		}
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		Properties prop = new Properties();
		FileInputStream in;
		
		try {
			in = new FileInputStream("E:\\jspWorkspace\\coffeeyo\\src\\com\\coffeeyo\\common\\dispatcher\\coffeeyoRequest.properties");
			//in = new FileInputStream("D:\\jspWorkspace\\coffeeyo\\src\\com\\coffeeyo\\common\\dispatcher\\coffeeyoRequest.properties");
			
			prop.load(in);
			//실행이 불가능한 클래스(문자열)이므로 실행 가능한 클래스로 변경해 놓는다.
			
			//prop가 가지고 있는 모든 키값을 Set으로 변환시킨다.
			Set set = prop.keySet();
			
			//Set으로 변환된 데이터를 Iterator를 사용해서 꺼낸다.

			Iterator  iter = set.iterator();
			
			while(iter.hasNext()) {
				//키값을 하나씩 꺼낸다
				String key = (String)iter.next();
				//키값에 해당하는 실제 데이터(문자열로 된 컨트롤러)를 알아낸다
				String value = (String)prop.getProperty(key);
				//실행이 불가능한 클래스(문자열)이므로 실행 가능한 클래스로 변경해 놓는다
				//문제	알아낸 컨트롤러는 문자열 형태
				//			문자열로된 클래스는 new시킬수 없으므로
				//			실제 실행 가능한 클래스로 만들어야 한다
				try {
					// 1) 문자열로 된 클래스이름을 이용해서 실제 클래스를 알아낸다
					Class c = Class.forName(value);
					
					// 2) 이 클래스를 실행 가능하도록 new시킨다
					Action temp = (Action)c.newInstance();
					
					//실행가능한 클래스를 서비스함수에 요청이 오면 실행해야 하므로
					//map에 등록해 놓자
					map.put(key, temp);
				}
				catch (Exception e) {
					System.out.println("실행 클래스 변환 에러="+e);
				}//catch
			}//while
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
