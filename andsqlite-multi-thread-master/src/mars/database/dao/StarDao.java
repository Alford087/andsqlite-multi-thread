package mars.database.dao;

import java.util.ArrayList;

import mars.database.base.BaseDaoImpl;
import mars.database.bean.Star;
import mars.database.standary.IStar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * StarDao ≤‚ ‘”√
 * 
 * @author TC
 * 
 */
public class StarDao extends BaseDaoImpl<Star> implements IStar {
	public final static String tableName = "Star";

	public static String getCreateTableSql() {
		return "CREATE TABLE "
				+ tableName
				+ " (id integer NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,weight integer DEFAULT 0,radius integer DEFAULT 0,name text)";
	}

	@Override
	public Star parseObject(Cursor cursor) {
		Star item = new Star();
		item.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
		item.setWeight(cursor.getInt(cursor.getColumnIndexOrThrow("weight")));
		item.setRadius(cursor.getInt(cursor.getColumnIndexOrThrow("radius")));
		item.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
		return item;
	}

	@Override
	public ContentValues object2ContentValues(Star t) {
		ContentValues cv = new ContentValues();
		cv.put("weight", t.getWeight());
		cv.put("radius", t.getRadius());
		cv.put("name", t.getName());
		return cv;
	}

	@Override
	public long saveStar(Context context, Star t) {
		return saveObject(context, tableName, object2ContentValues(t));
	}

	public void saveStarByTransaction(Context context, ArrayList<Star> dataList) {
		super.saveObjectByTransaction(context, tableName, dataList);
	}

}
