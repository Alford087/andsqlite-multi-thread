package mars.database.base;

import mars.database.dao.StarDao;
import mars.database.helper.MarsConfig;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

	private Context context;
	public String dataBaseName = MarsConfig.DB_NAME;
	public static String baseDataBaseName = MarsConfig.DB_NAME;
	private final static int VERSION = MarsConfig.DB_VERSION;
	// create table sql.eg:
	private String[] tableSql = { StarDao.getCreateTableSql() };
	// create index sql.eg:create index key on xxx (TARGET_ID)
	private String[] indexSql = {};

	public DBhelper(Context context, CursorFactory factory) {
		super(context, baseDataBaseName, factory, VERSION);
		this.context = context;
		dataBaseName = baseDataBaseName;
	}

	public DBhelper(Context context, String dbName, CursorFactory factory) {
		super(context, dbName, factory, VERSION);
		this.context = context;
		this.dataBaseName = dbName;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (String sql : tableSql) {
			if (sql != null || !"".equals(sql))
				db.execSQL(sql);
		}
		for (String sql : indexSql) {
			if (sql != null || !"".equals(sql))
				db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		context.deleteDatabase(dataBaseName);
		this.onCreate(db);
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

}
