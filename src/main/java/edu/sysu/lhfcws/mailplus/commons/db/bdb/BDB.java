package edu.sysu.lhfcws.mailplus.commons.db.bdb;


import java.io.File;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.base.Preconditions;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sun.tools.javac.util.Pair;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;

/**
 * Berkeley DB 数据库操作类
 *
 * @author lhfcws
 * @time 14-10-25.
 */
public class BDB {

    private String path;
    private Database db; // 数据源
    private Environment environment;// 环境对象
    private AtomicBoolean isRunning = new AtomicBoolean(false);// 判断是否运行

    private static HashMap<String, BDB> cacheBDB = new HashMap<String, BDB>();
    public static BDB getInstance(String path) {
        if (!cacheBDB.containsKey(path)) {
            synchronized (cacheBDB) {
                if (!cacheBDB.containsKey(path)) {
                    cacheBDB.put(path, new BDB(path));
                }
            }
        }
        return cacheBDB.get(path);
    }

    private BDB(String path) {
        this.path = path;
    }

    /**
     * 打开数据库方法
     */
    public void start() {
        if (isRunning.get()) {
            return;
        }
        /******************** 文件处理 ***********************/
        File envDir = new File(path);
        if (!envDir.exists())
        {
            envDir.mkdir();
        }

        /******************** 环境配置 ***********************/
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setTransactional(false); // 不进行事务处理
        envConfig.setAllowCreate(true); // 如果不存在则创建一个
        environment = new Environment(envDir, envConfig);// 通过路径，设置属性进行创建

        /******************* 创建适配器对象 ******************/
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setTransactional(false); // 不进行事务处理
        dbConfig.setAllowCreate(true);// 如果不存在则创建一个
        dbConfig.setSortedDuplicates(true);// 数据分类

        /******************* 打开数据库 ******************/
        db = environment.openDatabase(null, Consts.DB_NAME, dbConfig); // 使用适配器打开数据库
        isRunning.set(true); // 设定是否运行
    }

    /**
     * 关闭数据库方法
     */
    public void stop() {
        if (isRunning.get()) {
            isRunning.set(false);
            db.close();
            environment.close();
        }
    }

    /**
     * 数据库是否在运行
     * @return boolean isRunning
     */
    public boolean isRunning() {
        return isRunning.get();
    }

    /**
     * 数据存储方法 set()
     *
     * @param key
     * @param data
     */
    public void set(String key, String data) {
        Preconditions.checkArgument(isRunning());

        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        keyEntry.setData(key.getBytes());
        dataEntry.setData(data.getBytes());

        OperationStatus status = db.put(null, keyEntry, dataEntry);

        if (status != OperationStatus.SUCCESS) {
            throw new RuntimeException("Data insertion got status " + status);
        }
    }

    /**
     * Get method
     * @param aKey
     * @return String value
     */
    public String get(String aKey) {
        Preconditions.checkArgument(isRunning());

        DatabaseEntry theKey = null;
        DatabaseEntry theData = new DatabaseEntry();
        try {
            theKey = new DatabaseEntry(aKey.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        OperationStatus status = db.get(null, theKey, theData, LockMode.DEFAULT);
        if (status == OperationStatus.SUCCESS) { //根据key值，进行数据查询
            // Recreate the data String.
            byte[] retData = theData.getData();
            String foundData = new String(retData);
            System.out.println("[DEBUG] For key: '" + aKey + "' found data: '"
                    + foundData + "'.");
            return foundData;
        }

        return null;
    }

    /**
     * 查询所有可遍历数据
     * selectAll()
     */
    public HashMap<String, String> selectAll() {
        Preconditions.checkArgument(isRunning());

        Cursor cursor = null;
        cursor = db.openCursor(null, null);
        DatabaseEntry theKey = null;
        DatabaseEntry theData = null;
        theKey = new DatabaseEntry();
        theData = new DatabaseEntry();

        HashMap<String, String> result = new HashMap<String, String>();
        while (cursor.getNext(theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
            String key = new String(theKey.getData());
            String value = new String(theData.getData());
            result.put(key, value);
        }
        cursor.close();

        return result;
    }

    /**
     * Get the first k-v
     * @return
     */
    public Pair<String, String> getFirst() {
        Preconditions.checkArgument(isRunning());

        Cursor cursor = null;
        cursor = db.openCursor(null, null);
        DatabaseEntry theKey = null;
        DatabaseEntry theData = null;
        theKey = new DatabaseEntry();
        theData = new DatabaseEntry();

        if (cursor.getNext(theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
            String key = new String(theKey.getData());
            String value = new String(theData.getData());
            Pair<String, String> pair = new Pair<String, String>(key, value);
            return pair;
        }

        return null;
    }

    /**
     * 删除方法
     * delete(Here describes this method function with a few words)
     * @param key
     */
    public void delete(String key) {
        Preconditions.checkArgument(isRunning());

        DatabaseEntry keyEntry = null;
        try {
            keyEntry = new DatabaseEntry(key.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.delete(null, keyEntry);
    }

    /**
     * 删除所有key-value
     */
    public void clear() {
        HashMap<String, String> map = this.selectAll();
        for (String key : map.keySet()) {
            this.delete(key);
        }
    }
}