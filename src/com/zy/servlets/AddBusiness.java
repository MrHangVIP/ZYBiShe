/**
 * 
 */
package com.zy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;

import com.zy.Daos.AccountDaoImp;
import com.zy.Daos.BusinessDaoImp;
import com.zy.Daos.UserDaoImp;
import com.zy.beans.BusinessBean;
import com.zy.beans.UserBean;
import com.zy.servlets.base.BaseServletFactory;

import net.sf.json.JSONObject;

/**
 * @author moram
 *
 */
public class AddBusiness extends BaseServletFactory {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userPhone = request.getParameter("userPhone");
		String userPass = request.getParameter("userPass");
		String nickName = request.getParameter("nickName");
		String address = request.getParameter("address");
		BusinessBean user = new BusinessBean();
		user.setUserPhone(userPhone);
		user.setUserPass(userPass);
		user.setAddress(address);
		user.setNickName(nickName);
		BusinessDaoImp usermodel = new BusinessDaoImp();
		boolean isExist = usermodel.userPhoneChecked(userPhone);
		String respose="";
		if (isExist) {
			respose="¸ÃÕËºÅÒÑ×¢²á£¡";
		} else {
			boolean result = usermodel.insertData(user);
			if (result) {
				BusinessBean userbean = usermodel.getUserInfo(userPhone);
				 AccountDaoImp accountDaoImp=new AccountDaoImp();
				boolean success= accountDaoImp.insertData(userbean.getBusinessId(), 0.00, 0);//Ä¬ÈÏÃ»ÓÐÇ®
				if(success){
					respose="×¢²á³É¹¦£¡";
				}else{
					usermodel.deleteData(userbean.getBusinessId());
					respose="×¢²áÊ§°Ü£¡";
				}
			} else {
				respose="×¢²áÊ§°Ü£¡";
			}
		}
		PrintWriter out = response.getWriter();
		out.println("<html>");  
	    out.println("<head>");  
	    out.println("<title>"+"½Y¹û"+"</title>"); 
	    out.print("<meta http-equiv=content-type content=text/html; charset=UTF-8>");
	    out.println("</head>");  
	    out.println("<body>");  
	    out.println("<h1>"+respose+"</h1>");  
	    out.println("</body>");  
	    out.println("</html>");  
		System.out.println("json  :" + respose);
		out.close();
	}
	
	@Override
	protected Map<String, String> dataModel(HttpServletRequest request, HttpServletResponse response) {
		
		return null;
	}

}
