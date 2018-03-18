package com.zy.Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.zy.Daos.base.BaseDBFactor;
import com.zy.beans.AccountBean;
import com.zy.utils.DateUtil;

public class AccountDaoImp extends BaseDBFactor<AccountBean> {

	@Override
	public boolean insertData(AccountBean t) {
		Connection conn = null;
		PreparedStatement stat = null;
		int rowCount = 0;
		try {
			conn = getConn();
			String sql = "insert into t_account(userid, money,createtime,updatetime) value(?,?,?,?)";
			stat = conn.prepareStatement(sql);
			// 璁剧疆鍊�
			stat.setInt(1, t.getUserId());
			stat.setDouble(2, t.getMoney());
			stat.setString(3, DateUtil.getCurrentDate());
			stat.setString(4,DateUtil.getCurrentDate());
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
	
	public boolean insertData(int userId,double money,int type) {
		Connection conn = null;
		PreparedStatement stat = null;
		int rowCount = 0;
		try {
			conn = getConn();
			String sql="";
			if(type==1){//用户
				sql = "insert into t_account(userid, money,createtime,updatetime) value(?,?,?,?)";
			}else{
				sql = "insert into t_business_account(userid, money,createtime,updatetime) value(?,?,?,?)";
			}
			stat = conn.prepareStatement(sql);
			// 璁剧疆鍊�
			stat.setInt(1, userId);
			stat.setDouble(2,money);
			stat.setString(3, DateUtil.getCurrentDate());
			stat.setString(4,DateUtil.getCurrentDate());
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
	public List<AccountBean> getDataList(Object... obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountBean getData(Object... obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateData(Object... obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteData(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
