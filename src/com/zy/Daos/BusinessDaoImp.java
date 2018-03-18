package com.zy.Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.zy.Daos.base.BaseDBFactor;
import com.zy.beans.BusinessBean;
import com.zy.beans.UserBean;
import com.zy.utils.DateUtil;

public class BusinessDaoImp extends BaseDBFactor<BusinessBean> {

	@Override
	public boolean insertData(BusinessBean t) {
		Connection conn = null;
		PreparedStatement stat = null;
		int rowCount = 0;
		try {
			conn = getConn();
			String sql = "insert into t_business(userphone, password,createtime,nickname,updatetime,address) value(?,?,?,?,?,?)";
			stat = conn.prepareStatement(sql);
			// 璁剧疆鍊�
			stat.setString(1, t.getUserPhone());
			stat.setString(2, t.getUserPass());
			stat.setString(3, DateUtil.getCurrentDate());
			stat.setString(4, t.getNickName());
			stat.setString(5,  DateUtil.getCurrentDate());
			stat.setString(6, t.getAddress());
			// 鎵ц
			rowCount = stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(stat, conn);
		}
		if (rowCount > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<BusinessBean> getDataList(Object... obj) {
		int userid = Integer.parseInt(obj[0].toString());
		Connection conn = null;
		List<BusinessBean> userList = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select * from t_business where businessId = ?";
			userList = (List<BusinessBean>) qr.query(conn, sql, new BeanHandler(BusinessBean.class), userid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return userList;
	}

	@Override
	public BusinessBean getData(Object... obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateData(Object... obj) {
		Connection conn = null;
		PreparedStatement stat = null;
		int rowCount = 0;
		try {
			conn = getConn();
			String sql = "update t_token set token = ? and createtime = ? where userphone = ? ";
			stat = conn.prepareStatement(sql);
			// 璁剧疆鍊�
			stat.setString(1, (String) obj[0]);
			stat.setString(2, (String) obj[1]);
			stat.setLong(3, System.currentTimeMillis());
			// 鎵ц
			rowCount = stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(stat, conn);
		}
		if (rowCount > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteData(int id) {
		Connection conn = null;
		PreparedStatement stat = null;
		int rowCount = 0;
		try {
			conn = getConn();
			String sql = "delete t_business where businessid = ? ";
			stat = conn.prepareStatement(sql);
			// 璁剧疆鍊�
			stat.setInt(1, id);
			// 鎵ц
			rowCount = stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(stat, conn);
		}
		if (rowCount > 0) {
			return true;
		}
		return false;
	}
	
	public BusinessBean login(String userPhone, String userPass) {
		Connection conn = null;
		BusinessBean userbean = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select a.* , b.money from t_business as a "
					+ "inner join t_business_account "
					+ "as b where a.bussinessid = b.userid "
					+ "and a.userphone = ? and a.password = ?";
			userbean = (BusinessBean) qr.query(conn, sql, new BeanHandler<BusinessBean>(BusinessBean.class), userPhone, userPass);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return userbean;

	}

	public BusinessBean getUserInfo(String userPhone) {
		Connection conn = null;
		BusinessBean userbean = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select * from t_business where userphone = ?";
			System.out.println("sql  :" + sql);
			userbean = (BusinessBean) qr.query(conn, sql, new BeanHandler<BusinessBean>(BusinessBean.class), userPhone);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return userbean;

	}
	
	public BusinessBean getUserInfo(int userId) {
		Connection conn = null;
		BusinessBean userbean = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select a.* , b.money from t_business as a "
					+ "left join t_business_account "
					+ "as b where a.businessid = b.userid "
					+ "and a.businessid = ?";
			System.out.println("sql  :" + sql);
			userbean = (BusinessBean) qr.query(conn, sql, new BeanHandler<BusinessBean>(BusinessBean.class), userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return userbean;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean userPhoneChecked(String userPhone) {
		Connection conn = null;
		UserBean userList = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select * from t_business where userphone = ?";
			userList = (UserBean) qr.query(conn, sql, new BeanHandler(UserBean.class), userPhone);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		if (userList == null) {
			return false;
		}
		return true;
	}

}
