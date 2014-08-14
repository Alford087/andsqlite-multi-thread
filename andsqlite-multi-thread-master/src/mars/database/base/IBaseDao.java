package mars.database.base;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;

public interface IBaseDao<T> {

	/* execute base sql */
	public void excuteSql(Context context, String sql);

	/* execute sql */
	public void executeSql(Context context, String sql, Object[] args);

	/* save contentValues into table */
	public long saveObject(Context context, String tableName,
			ContentValues saveObject);

	/* save object into table by transaction */
	public void saveObjectByTransaction(Context context, String tableName,
			ArrayList<T> dataList);

	/* update */
	public long updateObject(Context context, String tableName,
			String pageWhere, ContentValues updateObject);

	/* delete */
	public long delObject(Context context, String tableName, String pageWhere);

	/* query by page */
	public ArrayList<T> getPagingBySqlRawQuery(Context context, String sql,
			String[] args);

	/* query */
	public ArrayList<T> getPagingBySqlQuery(Context context, String table,
			String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy);

	/* account data */
	public int findCountSql(Context context, String sql, String[] args);

}
