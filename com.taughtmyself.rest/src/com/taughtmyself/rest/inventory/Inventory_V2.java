package com.taughtmyself.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import com.taughtmyself.dao.OracleDbImproved;

@Path("/v2/inventory")
public class Inventory_V2 {

	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response response(){
		return Response.status(400).entity("Please specify a Product Category to filter on").build();
	}*/
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnProductCategory(
				@QueryParam("category") String prodCat)
				throws Exception {
		
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try {
			
			//return a error is prodCat is missing from the url string
			if(prodCat == null) {
				return Response.status(400).entity("Error: please specify Product Category for this call").build();
			}
			
			OracleDbImproved dao = new OracleDbImproved();
			
			json = dao.ProductCategory(prodCat);
			returnString = json.toString();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Sorry, but an error occured on server while processing your request :(").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	
	
	@Path("/{category}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(
				@PathParam("category") String prodCat) 
				throws Exception {
		
		String returnString = null;
		
		JSONArray json = new JSONArray();
		
		try {
			
			OracleDbImproved dao = new OracleDbImproved();
			
			json = dao.ProductCategory(prodCat);
			returnString = json.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
}