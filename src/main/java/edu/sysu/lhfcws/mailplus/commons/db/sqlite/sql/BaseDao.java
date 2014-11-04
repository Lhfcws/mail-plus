package edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import edu.sysu.lhfcws.mailplus.commons.db.sqlite.resultset.RemoteHostResultSetHandler;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 提供一些基础的访问数据库(Database Access Object)操作
 * @author wangkai/arber
 */
public class BaseDao {
	private final Log logger = LogFactory.getLog(BaseDao.class);
	
	protected SimpleQueryRunner queryRunner;

	public BaseDao( DataSource dataSource ){
		queryRunner = new SimpleQueryRunner(dataSource);
	}
		
	/**
	 * 执行批量sql语句
	 * @param sql sql语句
	 * @param params 二维参数数组
	 * @return 受影响的行数的数组
	 * @throws SQLException
	 */
	public int[] batchExecute(String sql, Object[][] params) throws SQLException {
		int[] affectedRows = new int[0];
		try {
			affectedRows = queryRunner.batch(sql, params);
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to batch update data",
					e);
			throw e;
		}
		return affectedRows;
	}
	
	/**
	 * 执行批量sql语句
	 * @param sql sql语句
	 * @param setter 二维参数数组
	 * @return 受影响的行数的数组
	 * @throws SQLException
	 */
	public int[] batchExecute(String sql, StatementSetter setter) throws SQLException {
		int[] affectedRows = new int[0];
		try {
			affectedRows = queryRunner.batch(sql, setter);
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to batch update data", e);
			throw e;
		}
		return affectedRows;
	}
	
	/**
	 * 执行批量sql语句,提供接口针对给定的对象单个做执行，自行处理Exception 
	 * @param sql sql语句
	 * @return 如果全部执行成功，返回true; 否则返回false.
	 * @throws SQLException
	 */
	public <T> boolean batchExecute(String sql, List<T> objList, StatementExecutor<T> stmtExecutor ) throws SQLException {
		try {
			return queryRunner.batch(sql, objList, stmtExecutor);
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to batch update data", e);
			throw e;
		}
	}	
	
	/**
	 * 执行批量sql语句
	 * 
	 * @param sql sql语句
	 * @param params 参数list
	 * @return 受影响的行数的数组
	 * @throws SQLException
	 */
	public int[] batchExecute(String sql, Collection<Object[]> params) throws SQLException {
		int[] affectedRows = new int[0];
		try {
			affectedRows = queryRunner.batch(sql, params);
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to batch update data",
					e);
			throw e;
		}
		return affectedRows;
	}


	/**
	 * 执行sql语句
	 * 
	 * @param sql sql语句
	 * @param params 参数数组
	 * @return 受影响的行数
	 * @throws SQLException
	 */
	public int batchExecute(String sql, Object... params) throws SQLException {
		int affectedRows = 0;
		try {
			if (params == null) {
				affectedRows = queryRunner.update(sql);
			} else {
				affectedRows = queryRunner.update(sql, params);
			}
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to update data", e);
			throw e;
		}
		return affectedRows;
	}
	
	/**
	 * 批量更新sql语句
	 * @param sqlList
	 * @return
	 * @throws SQLException
	 */
	public int[] batchExecute(List<String> sqlList) throws SQLException {
		int[] affectedRows = new int[0];
		try {
			affectedRows = queryRunner.batch(sqlList);
		}
		catch (SQLException e) {
				logger.debug("Error occured while attempting to update data", e);
				throw e;
		}
		return affectedRows;
	}
	

	/**
	 * 查询所有某一列的值
	 * 
	 * @param sql sql语句
	 * @param params 参数数组
	 * @return 查询结果
	 * @throws SQLException
	 */
	public <T> List<T> queryColumns(String sql, Object... params) throws SQLException {
		List<T> columns = new ArrayList<T>();
		ResultSetHandler<List<T>> handler = new ResultSetHandler<List<T>>() {
			@Override
			public List<T> handle(ResultSet rs) throws SQLException {
				List<T> lists = new ArrayList<T>();
				while (rs.next()) {
					@SuppressWarnings("unchecked")
					T t = (T) rs.getObject(1);
					lists.add(t);
				}
				return lists;
			}
		};
		try {
			if (params == null) {
				columns = queryRunner.query(sql, handler);
			} else {
				columns = queryRunner.query(sql, handler, params);
			}
			return columns;
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to findColume ", e);
			throw e;
		}
	}
	
	
	/**
	 * 查询所有某一列的值
	 * 
	 * @param sql sql语句
	 * @param params 参数数组
	 * @return 查询结果
	 * @throws SQLException
	 */
	public <T> List<T> queryColumns(String sql,final Class<T> clazz,Object... params) throws SQLException {
		List<T> columns = new ArrayList<T>();
		ResultSetHandler<List<T>> handler = new ResultSetHandler<List<T>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<T> handle(ResultSet rs) throws SQLException {
				List<T> lists = new ArrayList<T>();
				while (rs.next()) {
					T t=null ;
					if (clazz.equals(Integer.class)) {
						 t = (T) new Integer(rs.getInt(1));
					}else if (clazz.equals(Long.class)) {
						 t = (T) new Long(rs.getLong(1));
					}
					else{
						 t = (T) rs.getObject(1);
					}
					
					lists.add(t);
				}
				return lists;
			}

		};
		try {
			if (params == null) {
				columns = queryRunner.query(sql, handler);
			} else {
				columns = queryRunner.query(sql, handler, params);
			}
			return columns;
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to findColume ", e);
			throw e;
		}
	}
	
	/**
	 * 查询某一列的第一个值
	 * @param sql sql语句
	 * @param params 参数数组
	 * @return 查询结果
	 * @throws SQLException
	 */
	public <T> T queryColumn(String sql,Object... params) throws SQLException {
		ResultSetHandler<T> handler = new ResultSetHandler<T>() {
			@Override
			public T handle(ResultSet rs) throws SQLException {
				if (rs.next()) {
					@SuppressWarnings("unchecked")
					T t = (T) rs.getObject(1);
					return t;
				}
				return null;
			}

		};
		try {
			if (params == null) {
				return queryRunner.query(sql, handler);
			} else {
				return  queryRunner.query(sql, handler, params);
			}
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to findColume ", e);
			throw e;
		}
	}
	
	
	/**
	 * 查询某一列的第一个值
	 * @param sql sql语句
	 * @param params 参数数组
	 * @return 查询结果
	 * @throws SQLException
	 */
	public <T> T queryColumn(String sql,final Class<T> clazz, Object... params) throws SQLException {
		ResultSetHandler<T> handler = new ResultSetHandler<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T handle(ResultSet rs) throws SQLException {
				if (rs.next()) {
					T t=null ;
					if (clazz.equals(Integer.class)) {
						 t = (T) new Integer(rs.getInt(1));
					}else if(clazz.equals(Long.class)){
						 t = (T) new Long(rs.getLong(1));
					}else{
						 t = (T) rs.getObject(1);
					}
					return t;
				}
				return null;
			}
		};
		try {
			if (params == null) {
				return queryRunner.query(sql, handler);
			} else {
				return  queryRunner.query(sql, handler, params);
			}
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to findColume ", e);
			throw e;
		}
	}
	
	/**
	 * 根据条件获取单个对象
	 * @param sql
	 * @param handler
	 * @param params
	 * @return
	 * @throws SQLException
	 */
    public <T> T querySingleObj(String sql, ResultSetHandler<T> handler,
                                Object... params) throws SQLException {
        T t;
        try {
            t = queryRunner.query(sql, handler, params);
        } catch (SQLException e) {
            logger.debug("Error occured while attempting to find object", e);
            throw e;
        }

        return t;
    }


    /**
	 * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
	 * 
	 * @param <T>
	 * @param sql
	 * @param handler
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public <T> List<T> queryObjects(String sql, ResultSetHandler<List<T>> handler,
			Object... params) throws SQLException {
		List<T> list = new ArrayList<T>();
		try {
			if (params == null) {
				list = queryRunner.query(sql, handler);
			} else {
				list = queryRunner.query(sql, handler, params);
			}
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to query data list", e);
			throw e;
		}
		return list;
	}

	 /**
     * 插入并返回自增主键的方法
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public Integer  insertWithKey(String sql, Object... params) throws SQLException{
    	try {
    		return queryRunner.insertWithKey(sql, params);
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to insertwithkey", e);
			throw e;
		}
    	
    }
    
    /**
     * 插入并返回自增主键的方法
     * @param sql
     * @param filler
     * @return
     * @throws SQLException
     */
    public List<Integer>  insertWithKeys(String sql, StatementSetter filler) throws SQLException{
    	try {
    		return queryRunner.insertWithKey(sql, filler);
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to insertwithkey", e);
			throw e;
		}
    	
    }
    
    /**
     * 插入并返回自增主键的方法
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Integer> insertWithKey(String sql, List<Object[]> params) throws SQLException{
    	try {
    		return queryRunner.insertWithKey(sql, params);
		} catch (SQLException e) {
			logger.debug("Error occured while attempting to insertwithkey batch", e);
			throw e;
		}
    	
    }
}
