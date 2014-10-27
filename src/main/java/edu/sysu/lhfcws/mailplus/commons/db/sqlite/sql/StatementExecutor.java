package edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementExecutor<T> {
	/**
	 * 根据绑定参数并且执行 Statement, 自行处理Statement的execute操作
	 * @param psmt
	 * @param obj 提供statement所需参数
	 * @return true if statement is executed successfully 
	 * @throws SQLException 
	 */
	public boolean execute(PreparedStatement psmt, T obj) throws SQLException;

}
