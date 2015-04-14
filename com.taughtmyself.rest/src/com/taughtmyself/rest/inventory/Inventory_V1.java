package com.taughtmyself.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import com.taughtmyself.dao.*;
import com.taughtmyself.util.*;

@Path("/v1/inventory")
public class Inventory_V1 {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllProducts() throws Exception {
		PreparedStatement query = null;
		Connection conn = null;
		String returnedString = null;
		Response resp = null;

		try {
			conn = OracleDB.OracleDbConn().getConnection();
			query = conn.prepareStatement("select * from DEMO_PRODUCT_INFO");
			ResultSet rs = query.executeQuery();

			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			json = converter.toJSONArray(rs);
			query.close();// closing connection to DB

			returnedString = json.toString();
			resp = Response.ok(returnedString).build();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return resp;
	}
}
