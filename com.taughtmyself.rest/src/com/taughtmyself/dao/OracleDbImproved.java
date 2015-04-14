package com.taughtmyself.dao;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.taughtmyself.util.ToJSON;

public class OracleDbImproved extends OracleDB {

	/**
	 * 
	 * @param prodID
	 *            Product id
	 * @return if we're able to perform this operation, we return a 200 status
	 *         code o/w, a 500 status code.
	 * @throws Exception
	 */
	public int deleteDEMO_PRODUCT_INFO(int prodID) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = OracleDBConn();
			query = conn.prepareStatement("delete from DEMO_PRODUCT_INFO "
					+ "where PRODUCT_ID = ? ");

			query.setInt(1, prodID);
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		} finally {
			if (conn != null)
				conn.close();
		}

		return 200;
	}

	/**
	 * 
	 * @param prodID
	 *            Product id
	 * @param ProdAvail
	 *            Product availability
	 * @return If PUT request is succesful, i.e we're able to update an existing
	 *         record in DB, then we return a 200 ok msg o/w a 500 status code
	 * @throws Exception
	 */
	public int updateDEMO_PRODUCT_INFO(int prodID, int ProdAvail)
			throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = OracleDBConn();
			query = conn.prepareStatement("update DEMO_PRODUCT_INFO "
					+ "set PRODUCT_AVAIL = ? " + "where PC_PARTS_PK = ? ");

			query.setInt(1, ProdAvail);
			query.setInt(2, prodID);
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		} finally {
			if (conn != null)
				conn.close();
		}

		return 200;
	}

	/**
	 * 
	 * @param PRODUCT_ID
	 *            The product ID from Oracle Demo Database named
	 *            DEMO_PRODUCT_INFO
	 * @param PRODUCT_NAME
	 *            product name
	 * @param PRODUCT_DESCRIPTION
	 *            product description
	 * @param CATEGORY
	 *            product category
	 * @param PRODUCT_AVAIL
	 *            product availability with Y if available and N if not
	 *            available
	 * @param LIST_PRICE
	 *            price of the specified product
	 * @return If this POST request is successful, we return a 200 status code
	 *         back to the client o/w a 500 status code
	 * @throws Exception
	 */
	public int insertIntoDEMO_PRODUCT_INFO(String PRODUCT_ID,
			String PRODUCT_NAME, String PRODUCT_DESCRIPTION, String CATEGORY,
			String PRODUCT_AVAIL, int LIST_PRICE) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = OracleDBConn();
			query = conn
					.prepareStatement("insert into DEMO_PRODUCT_INFO "
							+ "(PRODUCT_NAME, PRODUCT_DESCRIPTION, CATEGORY, PRODUCT_AVAIL, LIST_PRICE) "
							+ "VALUES ( ?, ?, ?, ?, ? ) ");

			query.setString(1, PRODUCT_NAME);
			query.setString(2, PRODUCT_DESCRIPTION);
			query.setString(3, CATEGORY);
			query.setString(4, PRODUCT_AVAIL);
			query.setInt(5, LIST_PRICE);
			query.executeUpdate(); // a POST request to create a new
									// ressource/product item.

		} catch (Exception e) {
			e.printStackTrace();
			return 500; // if a error occurs, return a 500 error code to client
		} finally {
			if (conn != null)
				conn.close();
		}

		return 200;// return a 200 ok status code if POST request is successful
	}

	/**
	 * 
	 * @param prodCat
	 *            Product Category to look up in DB
	 * @return Returns all product categories as json to the the client
	 * @throws Exception
	 */
	public JSONArray ProductCategory(String prodCat) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = OracleDBConn();
			query = conn
					.prepareStatement("select PRODUCT_NAME, PRODUCT_DESCRIPTION, CATEGORY, PRODUCT_AVAIL, LIST_PRICE "
							+ "from DEMO_PRODUCT_INFO " + "where CATEGORY = ? ");

			query.setString(1, prodCat);
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); // close connection
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		} finally {
			if (conn != null)
				conn.close();
		}

		return json;
	}

	/**
	 * 
	 * @param prodCat
	 *            product category
	 * @param ProdAvail
	 *            Product availability, Y if available, N if not
	 * @return returns a JSONArray representing all product categories with
	 *         their respective availability
	 * @throws Exception
	 */
	public JSONArray ProductCatAndAvailability(String prodCat, String ProdAvail)
			throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = OracleDBConn();
			query = conn
					.prepareStatement("select PRODUCT_NAME, PRODUCT_DESCRIPTION, CATEGORY, PRODUCT_AVAIL, LIST_PRICE "
							+ "from DEMO_PRODUCT_INFO "
							+ "where CATEGORY = ?"
							+ "and PRODUCT_AVAIL = ?");

			query.setString(1, prodCat); // first ?
			// query.setBoolean(2, isAvail); //second ?
			query.setString(2, ProdAvail);
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); // close connection
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		} finally {
			if (conn != null)
				conn.close();
		}

		return json;
	}

	/**
	 * 
	 * @return returns all products from the DB in a JSONArray
	 * @throws Exception
	 */
	public JSONArray allProducts() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = OracleDBConn();
			query = conn
					.prepareStatement("select PRODUCT_NAME, PRODUCT_DESCRIPTION, CATEGORY, PRODUCT_AVAIL, LIST_PRICE "
							+ "from DEMO_PRODUCT_INFO");

			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); // close connection
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		} finally {
			if (conn != null)
				conn.close();
		}

		return json;
	}

	/**
	 * We simply try to select the data and time from the DB and if we're able
	 * to, we know we were able to successfully connect to our DB
	 * 
	 * @return Date and Time from DB server
	 * @throws Exception
	 */
	public JSONArray CheckDbConnection() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = OracleDBConn();
			query = conn
					.prepareStatement("select to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') DATETIME "
							+ "from sys.dual");

			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); // close connection
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		} finally {
			if (conn != null)
				conn.close();
		}
		return json;
	}
}
