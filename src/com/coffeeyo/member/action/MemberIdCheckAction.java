package com.coffeeyo.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.coffeeyo.member.model.MemberDAO;
import com.coffeeyo.common.action.Action;

public class MemberIdCheckAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		MemberDAO dao = MemberDAO.getInstance();
		
		int result = dao.idCheck(id);
		//System.out.println("result:"+result);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONObject jsonobj = new JSONObject();
		
		if(result == 0)
			jsonobj.put("check", 0);
		else
			jsonobj.put("check", 1);
		
		out.print(jsonobj.toString());

		out.flush();
		out.close();
		
		return null;
	}
}
