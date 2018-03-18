package com.zy.Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.zy.Daos.base.BaseDBFactor;
import com.zy.beans.TicketBean;
import com.zy.utils.DateUtil;

/**
 * 鐢ㄦ埛鐩稿叧鏁版嵁鎿嶄綔鐨勫疄鐜扮被
 * 
 * @author moram
 *
 */
public class TicketsDaoImp extends BaseDBFactor<TicketBean> {

	@Override
	public boolean insertData(TicketBean t) {
		Connection conn = null;
		PreparedStatement stat = null;
		int rowCount = 0;
		try {
			conn = getConn();
			String sql = "insert into t_ticket(businessid,typeid,title, biref,createtime,address,indexpicurl,price,duration) value"
					+ "(?,?,?,?,?,?,?,?,?)";
			stat = conn.prepareStatement(sql);
			// 璁剧疆鍊�
			stat.setInt(1, t.getBusinessId());
			stat.setInt(2, t.getTypeId());
			stat.setString(3, t.getTitle());
			stat.setString(4, t.getBiref());
			stat.setString(5, DateUtil.getCurrentDate());
			stat.setString(6, t.getAddress());
			stat.setString(7, t.getIndexpicurl());
			stat.setDouble(8, t.getPrice());
			stat.setString(9, t.getDuration());
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
	public List<TicketBean> getDataList(Object... obj) {
		Connection conn = null;
		List<TicketBean> ticketBeans = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select * from t_ticket";
			ticketBeans = (List<TicketBean>) qr.query(conn, sql, new BeanHandler(TicketBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return ticketBeans;
	}

	/**
	 * 根据类型获取
	 */
	public List<TicketBean> getDataList(int typeId, long time) {
		Connection conn = null;
		List<TicketBean> ticketBeans = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			if (time == 0) {
				time = System.currentTimeMillis();
			}
			String sql = "select * from t_ticket where typeid= ? and finishtimestmp<= ? group by ticketId desc";
			ticketBeans = (List<TicketBean>) qr.query(conn, sql, new BeanHandler(TicketBean.class), typeId, time);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return ticketBeans;
	}

	/**
	 * 根据类型获取
	 */
	public List<TicketBean> getFavorDataList(int userId) {
		Connection conn = null;
		List<TicketBean> ticketBeans = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select a.* from t_ticket as a " + "inner join t_favor "
					+ "as b where a.ticketId = b.ticketId " + "and b.userId = ? group by b.id desc";
			ticketBeans = (List<TicketBean>) qr.query(conn, sql, new BeanHandler(TicketBean.class), userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return ticketBeans;
	}

	/**
	 * 根据类型获取
	 */
	public List<TicketBean> getDataList(int typeId) {
		Connection conn = null;
		List<TicketBean> ticketBeans = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select * from t_ticket where typeid= ? group by ticketId desc ";
			ticketBeans = (List<TicketBean>) qr.query(conn, sql, new BeanHandler(TicketBean.class), typeId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return ticketBeans;
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
			String sql = "delete t_ticket where ticketid = ? ";
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

	public TicketBean getTicketData(int ticketId) {
		Connection conn = null;
		TicketBean TicketBean = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select * form t_ticket where ticketId= ?";
			TicketBean = (TicketBean) qr.query(conn, sql, new BeanHandler<TicketBean>(TicketBean.class), ticketId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, conn);
		}
		return TicketBean;

	}

	@Override
	public TicketBean getData(Object... obj) {
		int ticketId=(int)obj[0];
		Connection conn = null;
		TicketBean TicketBean = null;
		try {
			conn = getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "select * form t_ticket where ticketId= ?";
			TicketBean = (TicketBean) qr.query(conn, sql, new BeanHandler<TicketBean>(TicketBean.class), ticketId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {			
			closeConn(null, conn);
		}
		return TicketBean;
	}

}
