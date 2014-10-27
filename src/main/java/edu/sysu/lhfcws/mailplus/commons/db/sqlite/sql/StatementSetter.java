package edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 申明参数绑定方法, 典型用法:
 * 	stmt.setString(1, ...);
 *  stmt.setString(2, ...);
 *  stmt.addBatch() //必须通过addBatch, 框架中会调用 executeBatch 统一执行
 * @author wangkai
 *
 */
public interface StatementSetter {
	/**
	 * 绑定参数
	 * @param psmt
	 * @throws SQLException 
	 */
	public void bind(PreparedStatement psmt) throws SQLException;

}
