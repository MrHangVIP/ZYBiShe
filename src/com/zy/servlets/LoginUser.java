/**
 * 
 */
package com.zy.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zy.Daos.TokenDaoImp;
import com.zy.Daos.UserDaoImp;
import com.zy.beans.TokenBean;
import com.zy.beans.UserBean;
import com.zy.servlets.base.BaseServletFactory;
import com.zy.utils.Constant;

import net.sf.json.JSONObject;

/**
 * @author moram
 *
 */
public class LoginUser extends BaseServletFactory {

	private static final long serialVersionUID = 1L;

	@Override
	protected Map<String, String> dataModel(HttpServletRequest request, HttpServletResponse response) {
		String userPhone = request.getParameter("userPhone");
		String userPass = request.getParameter("userPass");
		String mac = request.getParameter("MAC");
		UserDaoImp usermodel = new UserDaoImp();
		UserBean userbean = usermodel.login(userPhone, userPass);
		Map<String, String> map = new HashMap<String, String>();
		if (userbean == null) {
			map.put("result", "fail");
			map.put("data", "");
		} else {
			new TokenDaoImp().deleteData(userPhone);
			JSONObject itemJson = JSONObject.fromObject(userbean);
			String token = Constant.productToken(mac);
			if (!new TokenDaoImp().insertData(new TokenBean(token, userPhone))) {
				new TokenDaoImp().insertData(new TokenBean(token, userPhone));
			}
			map.put("result", token);
			map.put("data", itemJson.toString());
		}

		return map;
	}

}
