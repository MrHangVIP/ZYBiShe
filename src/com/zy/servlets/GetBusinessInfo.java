package com.zy.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zy.Daos.BusinessDaoImp;
import com.zy.Daos.UserDaoImp;
import com.zy.beans.BusinessBean;
import com.zy.beans.UserBean;
import com.zy.servlets.base.BaseServletFactory;

import net.sf.json.JSONObject;

public class GetBusinessInfo extends BaseServletFactory {
	private static final long serialVersionUID = 1L;

	@Override
	protected Map<String, String> dataModel(HttpServletRequest request, HttpServletResponse response) {
		String userPhone = request.getParameter("userPhone");
		String passWord = request.getParameter("passWord");
		Map<String, String> map = new HashMap<String, String>();
			BusinessDaoImp usermodel = new BusinessDaoImp();
			BusinessBean userInfo = usermodel.login(userPhone,passWord);
			JSONObject itemJson = JSONObject.fromObject(userInfo);
			map.put("result", "success");
			map.put("data", itemJson.toString());
		return map;
	}
}
