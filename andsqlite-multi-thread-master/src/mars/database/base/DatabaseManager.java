package mars.database.base;

import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
/**
 * database manager,it can support database object
 * 
 * @author TC
 * 
 * @param <T>
 */
public class DatabaseManager {

private AtomicInteger mOpenCounter = new AtomicInteger();

	private static DatabaseManager instance = null;

	private static DBhelper mDatabaseHelper = null;

	private SQLiteDatabase mDatabase;

	public synchronized void switchDataBase(Context context, String dataBaseName)
			throws Exception {
		if (mDatabaseHelper == null) {
			throw new Exception("DatabaseHelper is null");
		}
		if (dataBaseName == null || "".equals(dataBaseName)) {
			throw new Exception("dataBaseName is no value");
		}
		if (!mDatabaseHelper.getDataBaseName().equals(dataBaseName)) {
			mDatabaseHelper = new DBhelper(context, dataBaseName, null);
		}
	}

	public static synchronized DatabaseManager getInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseManager();
			mDatabaseHelper = new DBhelper(context, null);
		}
		return instance;
	}

	/**
	 * get DatabaseManager instance
	 * 
	 * @param context
	 * @param dataBaseName
	 * @return
	 */
	public static synchronized DatabaseManager getInstance(Context context,
			String dataBaseName) {
		if (instance == null) {
			instance = new DatabaseManager();
			mDatabaseHelper = new DBhelper(context, dataBaseName, null);
		}
		return instance;
	}

	/**
	 * get a SQLiteDatabase instance,while use
	 */
	public synchronized SQLiteDatabase openDatabase() {
		if (mOpenCounter.incrementAndGet() == 1) {
			mDatabase = mDatabaseHelper.getWritableDatabase();
		}
		return mDatabase;
	}

	/**
	 * close database，while you don't use
	 */
	public synchronized void closeDatabase() {
		if (mOpenCounter.decrementAndGet() == 0) {
			if (mDatabase != null) {
				mDatabase.close();
			}
		}
	}

	/**
	 * return current DataBase Name, Maybe he is composed of many database..
	 * 
	 * @return
	 */
	public String getCurrentDataBaseName() {
		if (mDatabaseHelper != null) {
			return mDatabaseHelper.getDataBaseName();
		}
		return "";
	}
}
