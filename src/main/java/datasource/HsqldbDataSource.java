package datasource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * Datasource that shuts down the hsqldb when the data source is closed. hsql is
 * normally only shutdown with a JVM hook, so this datasource is better suited
 * when you need to shutdown/restart within one JVM.
 * 
 * @see http://hsqldb.org
 */
public class HsqldbDataSource extends BasicDataSource {

	private Logger logger = Logger.getLogger(this.getClass());

	private String databaseAsString;

	public static int COUNT = 0;

	public HsqldbDataSource() {
		super();
	}

	public String getDatabaseAsString() {
		return databaseAsString;
	}

	public void setDatabaseAsString(String databaseAsString) {
		this.databaseAsString = databaseAsString;
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection x = null;
		int count = 10;
		while (x == null && count > 0) {
			try {
				x = super.getConnection();
				if (count < 10) {
					// additional output if there is trouble
					File lck = new File(url.substring(url.indexOf("file:") + 5)
							+ ".data");
					logger.warn("Connected to: " + lck.getAbsolutePath());
				}
			} catch (Exception e) {
				try {
					String url = super.getUrl();
					Thread.sleep(5000);
					logger
							.warn("Can not connect. Retry in 5 seonds .. location: "
									+ url);
					if (url.indexOf("file:") > -1) {
						// try to remove the lck file. ... that might cause
						// troubles but for debugging in netbeans this is
						// required...
						File lck = new File(url
								.substring(url.indexOf("file:") + 5)
								+ ".lck");
						logger.warn("not connected; delete file: "
								+ lck.getAbsolutePath());
						lck.delete();
					}
				} catch (InterruptedException ie) {
				}
			}
			count--;
		}
		++COUNT;
		// if(x!=null)
		// logger.warn("HSQLDB - connection: " + super.url +"  closed:" +
		// x.isClosed() + "  isReadOnly:" + x.isReadOnly()+" - "+ COUNT);
		return x;
	}

	@Override
	public Connection getConnection(String s1, String s2) throws SQLException {
		Connection x = super.getConnection(s1, s2);
		++COUNT;
		// logger.debug("HSQLDB - connection: " + super.url +"  closed:" +
		// x.isClosed() + "  isReadOnly:" + x.isReadOnly()+" - "+ COUNT);
		return x;
	}

	@Override
	public synchronized void close() throws SQLException {
		// Connection conn = getConnection();
		// Statement statement = conn.createStatement();
		// statement.executeUpdate("SHUTDOWN");
		// statement.close();
		// conn.close();
		super.close();
		Connection x = super.getConnection();
		logger.debug("HSQLDB - CLOSE CALLED !!: " + super.url + "  closed:"
				+ x.isClosed() + "  isReadOnly: " + x.isReadOnly());
	}
}