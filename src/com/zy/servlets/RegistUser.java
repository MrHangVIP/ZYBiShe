/**
 * 
 */
package com.zy.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zy.Daos.AccountDaoImp;
import com.zy.Daos.UserDaoImp;
import com.zy.beans.UserBean;
import com.zy.servlets.base.BaseServletFactory;

/**
 * @author moram
 *
 */
public class RegistUser extends BaseServletFactory {

	private static final long serialVersionUID = 1L;

	@Override
	protected Map<String, String> dataModel(HttpServletRequest request, HttpServletResponse response) {
		String userPhone = request.getParameter("userPhone");
		String userPass = request.getParameter("userPass");
		UserBean user = new UserBean();
		user.setUserPhone(userPhone);
		user.setUserPass(userPass);
		UserDaoImp usermodel = new UserDaoImp();
		boolean isExist = usermodel.userPhoneChecked(userPhone);
		Map<String, String> map = new HashMap<String, String>();
		if (isExist) {
			map.put("result", "fail");
			map.put("data", "exist");
		} else {
			boolean result = usermodel.insertData(user);
			if (result) {
				UserBean userbean = usermodel.getUserInfo(userPhone);
				 AccountDaoImp accountDaoImp=new AccountDaoImp();
				boolean success= accountDaoImp.insertData(userbean.getUserId(), 500.00, 1);//默认500元基金
				if(success){
					map.put("result", "success");
				}else{
					usermodel.deleteData(userbean.getUserId());
					map.put("result", "fail");
				}
			} else {
				map.put("result", "fail");
			}
		}
		return map;
	}

}
