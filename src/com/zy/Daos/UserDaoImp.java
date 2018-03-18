package com.zy.Daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.zy.Daos.base.BaseDBFactor;
import com.zy.beans.UserBean;
import com.zy.utils.DateUtil;

/**
 * 鐢ㄦ埛鐩稿叧鏁版嵁鎿嶄綔鐨勫疄鐜扮被
 * 
 * @author moram
 *
 */
public class UserDaoImp extends BaseDBFactor<UserBean> {

	@Override
	public boolean insertData(UserBean user) {
		Connection conn = null;
		PreparedStatement stat = null;
		int rowCount = 0;
		try {
			conn = getConn();
			String sql = "insert into t_user(userphone, userpass,createtime,nickname) value(?,?,?,?)";
			stat = conn.prepareStatement(sql);
			// 璁剧疆鍊�
			stat.setString(1, user.getUserPhone());
			stat.setString(2, user.getUserPass());
			stat.setString(3, DateUtil.getCurrentDate());
			stat.setString(4, user.getUserPhone());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<UserBean> getDataList(Object... obj) {
		int userid = Integer.parseInt(obj[0].toString());
		Connection conn = null;
		List<UserBean> userList = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select * from t_user where userId = ?";
			userList = (List<UserBean>) qr.query(conn, sql, new BeanHandler(UserBean.class), userid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return userList;
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
			String sql = "delete t_user where userid = ? ";
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

	public boolean updateData(UserBean user) {
		Connection conn = null;
		PreparedStatement stat = null;
		int rowCount = 0;
		try {
			conn = getConn();
			String sql = "update t_user set nickname = ? ,city = ? , birthday = ? , lastupdatetime = ? where userphone = ? ";
			stat = conn.prepareStatement(sql);
			// 璁剧疆鍊�
			stat.setString(1, user.getNickName());
			stat.setString(2, user.getCity());
			stat.setString(3, user.getBirthday());
			stat.setLong(4, System.currentTimeMillis());
			stat.setString(5, user.getUserPhone());
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

	public boolean updateHeadUrl(String userPhone, String headUrl) {
		Connection conn = null;
		PreparedStatement stat = null;
		int rowCount = 0;
		try {
			conn = getConn();
			String sql = "update t_user set headurl= ? , lastupdatetime = ? where userphone = ? ";
			stat = conn.prepareStatement(sql);
			// 璁剧疆鍊�
			stat.setString(1, headUrl);
			stat.setLong(2, System.currentTimeMillis());
			stat.setString(3, userPhone);
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

	/**
	 * 淇敼瀵嗙爜
	 * 
	 * @param userPhone
	 * @param password
	 * @return
	 */
	public boolean updatePassword(String userPhone, String password) {
		Connection conn = null;
		PreparedStatement stat = null;
		int rowCount = 0;
		try {
			conn = getConn();
			String sql = "update t_user set userpass= ? , lastupdatetime = ? where userPhone = ? ";
			stat = conn.prepareStatement(sql);
			// 璁剧疆鍊�
			stat.setString(1, password);
			stat.setLong(2, System.currentTimeMillis());
			stat.setString(3, userPhone);
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

	public UserBean login(String userPhone, String userPass) {
		Connection conn = null;
		UserBean userbean = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select a.* , b.money from t_user as a "
					+ "inner join t_account "
					+ "as b where a.userid = b.userid "
					+ "and a.userphone = ? and a.password = ?";
			userbean = (UserBean) qr.query(conn, sql, new BeanHandler<UserBean>(UserBean.class), userPhone, userPass);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return userbean;

	}

	public UserBean getUserInfo(int UserId) {
		Connection conn = null;
		UserBean userbean = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select a.* , b.money from t_user as a "
					+ "inner join t_account "
					+ "as b where a.userid = b.userid "
					+ "and a.userid = ?";
			userbean = (UserBean) qr.query(conn, sql, new BeanHandler<UserBean>(UserBean.class), UserId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return userbean;

	}
	
	public UserBean getUserInfo(String userPhone) {
		Connection conn = null;
		UserBean userbean = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select * from t_user where a.userphone = ?";
			userbean = (UserBean) qr.query(conn, sql, new BeanHandler<UserBean>(UserBean.class), userPhone);
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
			String sql = "select * from t_user where userphone = ?";
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

	@Override
	public UserBean getData(Object... obj) {
		return null;
	}

}
