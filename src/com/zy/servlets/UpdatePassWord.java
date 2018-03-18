package com.zy.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zy.Daos.UserDaoImp;
import com.zy.beans.UserBean;
import com.zy.servlets.base.BaseServletFactory;

/**
 * 修改密码
 * 
 * @author Songzhihang
 *
 */
public class UpdatePassWord extends BaseServletFactory {
	private static final long serialVersionUID = 1L;

	@Override
	protected Map<String, String> dataModel(HttpServletRequest request, HttpServletResponse response) {
		String userPhone = request.getParameter("userPhone");
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		Map<String, String> map = new HashMap<String, String>();
		if (!tokenChecked(userPhone, token)) {
			map = getJsonMap();
		} else {
			UserDaoImp usermodel = new UserDaoImp();
			boolean update = usermodel.updatePassword(userPhone, password);
			if (update) {
				map.put("result", "success");
				map.put("data", "");
			} else {
				map.put("result", "fail");
				map.put("data", "update fail");
			}
		}
		return map;
	}
}
