package mars.database.helper;


public class Logger {
	private static final String TAG = "SQL";
	private static final boolean DEBUG = true;
	public static void i(String msg) {
		if (DEBUG) {
			android.util.Log.i(TAG, msg);
		}
	}

	public static void i(String remark, String msg) {
		if (DEBUG) {
			android.util.Log.i(TAG, buildMessage(remark, msg));
		}
	}

	public static void e(String msg) {
		if (DEBUG) {
			android.util.Log.e(TAG, msg);
		}
	}

	public static void e(String msg, Throwable e) {
		if (DEBUG) {
			android.util.Log.e(TAG, msg, e);
		}
	}

	public static void e(String remark, String msg, Throwable e) {
		if (DEBUG) {
			android.util.Log.e(TAG, buildMessage(remark, msg), e);
		}
	}

	protected static String buildMessage(String remark, String msg) {
		return remark + ": " + msg;
	}

	public static long startTime() {
		return System.currentTimeMillis();
	}

	public static void countTimeLog(long start, String msg) {
		long end = System.currentTimeMillis();
		if (DEBUG) {
			android.util.Log.d(TAG, msg + " count timeï¼?" + (end - start)
					+ " ms");
		}
	}

}
