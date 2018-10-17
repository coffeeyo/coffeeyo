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
				System.out.println("�� ȣ�� ����="+e);
			}
		}
		else
		{
			//��û���뿡 �غ�� ��Ʈ�ѷ��� �����Ƿ� �غ�� ���� �������� ��������.
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
			//������ �Ұ����� Ŭ����(���ڿ�)�̹Ƿ� ���� ������ Ŭ������ ������ ���´�.
			
			//prop�� ������ �ִ� ��� Ű���� Set���� ��ȯ��Ų��.
			Set set = prop.keySet();
			
			//Set���� ��ȯ�� �����͸� Iterator�� ����ؼ� ������.

			Iterator  iter = set.iterator();
			
			while(iter.hasNext()) {
				//Ű���� �ϳ��� ������
				String key = (String)iter.next();
				//Ű���� �ش��ϴ� ���� ������(���ڿ��� �� ��Ʈ�ѷ�)�� �˾Ƴ���
				String value = (String)prop.getProperty(key);
				//������ �Ұ����� Ŭ����(���ڿ�)�̹Ƿ� ���� ������ Ŭ������ ������ ���´�
				//����	�˾Ƴ� ��Ʈ�ѷ��� ���ڿ� ����
				//			���ڿ��ε� Ŭ������ new��ų�� �����Ƿ�
				//			���� ���� ������ Ŭ������ ������ �Ѵ�
				try {
					// 1) ���ڿ��� �� Ŭ�����̸��� �̿��ؼ� ���� Ŭ������ �˾Ƴ���
					Class c = Class.forName(value);
					
					// 2) �� Ŭ������ ���� �����ϵ��� new��Ų��
					Action temp = (Action)c.newInstance();
					
					//���డ���� Ŭ������ �����Լ��� ��û�� ���� �����ؾ� �ϹǷ�
					//map�� ����� ����
					map.put(key, temp);
				}
				catch (Exception e) {
					System.out.println("���� Ŭ���� ��ȯ ����="+e);
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
