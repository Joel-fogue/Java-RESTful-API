package com.taughtmyself.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;
import com.taughtmyself.dao.*;

@Path("/v1/status/*")
public class Status_V1 {
	private static final String api_version = "00.01.00";

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle() {
		return "<p>Java Web Service</p>";
	}

	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnAPIVersion() {
		return "<p>API Verion: </p>" + api_version;
	}

	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception {
		PreparedStatement query = null;
		String st = null;
		String returnedDbStatus = null;
		Connection conn = null;

		try {
			conn = OracleDB.OracleDbConn().getConnection();
			query = conn
					.prepareStatement("Select to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') DATETIME "
							+ "from sys.dual");
			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				st = rs.getString("DATETIME");
			}
			query.close();// close db connection
			returnedDbStatus = "<p>Database Status</p> "
					+ "<p> Database Date/Time return: " + st + "</p>";

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return returnedDbStatus;
	}
}
