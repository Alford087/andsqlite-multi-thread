package mars.database.base;

import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
			throw new Exception("dataBaseName isjno value");
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

	public static synchronized DatabaseManager getInstance(Context context,
			String dataBaseName) {
		if (instance == null) {
			instance = new DatabaseManager();
			mDatabaseHelper = new DBhelper(context, dataBaseName, null);
		}
		return instance;
	}

	public synchronized SQLiteDatabase openDatabase() {
		if (mOpenCounter.incrementAndGet() == 1) {
			mDatabase = mDatabaseHelper.getWritableDatabase();
		}
		return mDatabase;
	}

	public synchronized void closeDatabase() {
		if (mOpenCounter.decrementAndGet() == 0) {
			if (mDatabase != null) {
				mDatabase.close();
			}
		}
	}
}
