package edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import org.sqlite.SQLiteDataSource;

/**
 *
 */
public class BaseDaoFactory {

	private static Map<String, BaseDao> dataSourceMap = new HashMap<String, BaseDao>();

	/**
	 * 根据JDBC的URL，构造相应的BaseDao
	 * 
	 * @param dbString
	 *            JDBC's URL。支持sqlite.<br>
	 *            1、sqlite格式：jdbc:sqlite:&lt;dbname&gt;
	 * @return
	 */
	public static BaseDao getDaoBaseInstance(String dbString) {
		if (!dataSourceMap.containsKey(dbString)) {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(dbString);
			dataSourceMap.put(dbString, new BaseDao(ds));
		}

		return dataSourceMap.get(dbString);
	}


}
