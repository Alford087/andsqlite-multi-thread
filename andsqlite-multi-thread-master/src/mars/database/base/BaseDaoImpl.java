package mars.database.base;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * database »ù´¡Àà
 * 
 * @author TC
 * 
 * @param <T>
 */

public abstract class BaseDaoImpl<T> implements IBaseDao<T> {

	public static String getCreateTableSql() {
		return "";
	}

	public abstract ContentValues object2ContentValues(T t);

	public abstract T parseObject(Cursor cursor);

	@Override
	public void excuteSql(Context context, String sql) {
		try {
			DatabaseManager.getInstance(context).openDatabase().execSQL(sql);
			DatabaseManager.getInstance(context).closeDatabase();
		} catch (Exception e) {
			DatabaseManager.getInstance(context).closeDatabase();
			mars.database.helper.Logger.e("excuteSql:" + e.getMessage());
		}
	}

	@Override
	public void executeSql(Context context, String sql, Object[] args) {
		try {
			DatabaseManager.getInstance(context).openDatabase()
					.execSQL(sql, args);
			DatabaseManager.getInstance(context).closeDatabase();
		} catch (Exception e) {
			DatabaseManager.getInstance(context).closeDatabase();
			mars.database.helper.Logger.e("executeSql:" + e.getMessage());
		}
	}

	@Override
	public long saveObject(Context context, String tableName,
			ContentValues saveObject) {
		long operId = -1;
		try {
			operId = DatabaseManager.getInstance(context).openDatabase()
					.insert(tableName, null, saveObject);
			DatabaseManager.getInstance(context).closeDatabase();
		} catch (Exception e) {
			DatabaseManager.getInstance(context).closeDatabase();
			mars.database.helper.Logger.e("saveObject:" + e.getMessage());
			operId = -1;
		}
		return operId;
	}

	@Override
	public void saveObjectByTransaction(Context context, String tableName,
			ArrayList<T> dataList) {
		try {
			DatabaseManager.getInstance(context).openDatabase()
					.beginTransaction();
			for (T item : dataList) {
				DatabaseManager.getInstance(context).openDatabase()
						.insert(tableName, null, object2ContentValues(item));
			}
			DatabaseManager.getInstance(context).openDatabase()
					.setTransactionSuccessful();
		} catch (Exception e) {
			DatabaseManager.getInstance(context).openDatabase()
					.endTransaction();
			mars.database.helper.Logger.e("saveObjectByTransaction:"
					+ e.getMessage());
		}
		DatabaseManager.getInstance(context).openDatabase().endTransaction();
	}

	@Override
	public long updateObject(Context context, String tableName,
			String pageWhere, ContentValues updateObject) {
		long operId = -1;
		try {
			operId = DatabaseManager.getInstance(context).openDatabase()
					.update(tableName, updateObject, pageWhere, null);
			DatabaseManager.getInstance(context).closeDatabase();
		} catch (Exception e) {
			DatabaseManager.getInstance(context).closeDatabase();
			mars.database.helper.Logger.e("updateObject:" + e.getMessage());
			operId = -1;
		}
		return operId;
	}

	@Override
	public long delObject(Context context, String tableName, String pageWhere) {
		long operId = -1;
		try {
			operId = DatabaseManager.getInstance(context).openDatabase()
					.delete(tableName, pageWhere, null);
			DatabaseManager.getInstance(context).closeDatabase();
			return operId;
		} catch (Exception e) {
			DatabaseManager.getInstance(context).closeDatabase();
			mars.database.helper.Logger.e("delObject:" + e.getMessage());
			operId = -1;
		}
		return operId;
	}

	@Override
	public ArrayList<T> getPagingBySqlRawQuery(Context context, String sql,
			String[] args) {
		ArrayList<T> list = new ArrayList<T>();
		try {
			Cursor cursor = DatabaseManager.getInstance(context).openDatabase()
					.rawQuery(sql, args);
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				list.add(parseObject(cursor));
			}
			if (cursor != null)
				cursor.close();
			DatabaseManager.getInstance(context).closeDatabase();
			return list;
		} catch (Exception e) {
			DatabaseManager.getInstance(context).closeDatabase();
			mars.database.helper.Logger.e("getPagingBySqlRawQuery:"
					+ e.getMessage());
			return list;
		}
	}

	/**
	 * query data by page .See @android.database.sqlite.SQLiteDatabase.query;
	 */
	@Override
	public ArrayList<T> getPagingBySqlQuery(Context context, String table,
			String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		ArrayList<T> list = new ArrayList<T>();
		try {
			Cursor cursor = DatabaseManager
					.getInstance(context)
					.openDatabase()
					.query(table, columns, selection, selectionArgs, groupBy,
							having, orderBy);
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				list.add(parseObject(cursor));
			}
			if (cursor != null)
				cursor.close();
			DatabaseManager.getInstance(context).closeDatabase();
			return list;
		} catch (Exception e) {
			DatabaseManager.getInstance(context).closeDatabase();
			mars.database.helper.Logger.e("getPagingBySqlRawQuery:"
					+ e.getMessage());
			return list;
		}
	}

	@Override
	public int findCountSql(Context context, String sql, String[] args) {
		try {
			Cursor cursor = DatabaseManager.getInstance(context).openDatabase()
					.rawQuery(sql, args);
			int num = 0;
			while (cursor.moveToNext()) {
				num = cursor.getInt(0);
			}
			if (cursor != null)
				cursor.close();
			DatabaseManager.getInstance(context).closeDatabase();
			return num;
		} catch (Exception e) {
			DatabaseManager.getInstance(context).closeDatabase();
			mars.database.helper.Logger.e("findCountSql:" + e.getMessage());
			return 0;
		}
	}

}
