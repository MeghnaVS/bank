package com.ibm.data.resource;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//import com.sun.jersey.spi.resource.Singleton;

@Path("product")
//@Singleton
public class ProductManager {

	List<Product> products=null;
	
	
	public ProductManager() {
		products=new ArrayList<Product>();
		products.add(new Product(100,"Watch",1300.00,5));
		products.add(new Product(101,"Laptop",12000.00,15));
		products.add(new Product(102,"iPad",4300.00,5));
		products.add(new Product(103,"Mobile",45000.00,10));
	}

	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Product updateProduct(Product product) {
		System.out.println("In updateProduct()");
		for(Product p : products){
			if(p.getId()==product.getId()){
				products.remove(p);
				products.add(product);
			}
		}
		return product;
	}

	@DELETE
	@Path("{id}")
	public void deleteProduct(@PathParam("id") int pid) {
		System.out.println("In deleteProduct()");
		for(Product p : products){
			if(p.getId()==pid){
				products.remove(p);
			}
		}
	}

	
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public Product addProduct(@FormParam("id") int qid,@FormParam("name") String pname, @FormParam("price")double pprice,@FormParam("qty")int pqty){
		Product p=new Product(qid,pname,pprice,pqty);
		products.add(p);
		return p;
	}

	
	/*@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String addProduct(@FormParam("id") int qid,@FormParam("name") String pname, @FormParam("price")double pprice,@FormParam("qty")int pqty){
		Product p=new Product(qid,pname,pprice,pqty);
		products.add(p);
		return "Product Added, Total : "+products.size();
	}*/

	
	//http://localhost:8080/sample/rest/product/102
	@GET
	@Path("{id}")
	@Produces("text/xml")
	public Product getProduct(@PathParam("id") int pid){
		for(Product p : products){
			if(p.getId()==pid){
				return p;
			}
		}
		return null;
	}

	
	@GET
	@Produces("text/xml")
	public List<Product> getProducts(){
		return products;
	}
	
	@GET
	@Produces("text/json")
	public List<Product> getProductAsJSON(){
		return products;
	}
	
	/*
	@GET
	@Produces("text/html")
	public String getProducts(){
		StringBuffer buffer=new StringBuffer();
		for(Product p : products){
			buffer.append("<p>");
			buffer.append("<b>"+p.getId()+"</b>");
			buffer.append("<b>"+p.getName()+"</b>");
			buffer.append("<b>"+p.getPrice()+"</b>");
			buffer.append("<b>"+p.getQty()+"</b>");
			buffer.append("</p><br/>");
		}
		return buffer.toString();
	}*/

	
	/*
	//http://localhost:8080/sample/rest/product
	@GET
	@Produces("text/plain")
	public String getProduct(){
		return "Pen 200 10";
	}

	@GET
	@Produces("text/html")
	public String getProductAsHTML(){
		return "<p><h1>Name : Pen</h1> <h1>Price : 200</h1><h1>Qty : 10</h1></p>";
	}

	@GET
	@Produces("text/xml")
	public String getProductAsXML(){
		return "<product><name>Pen</name><price>200</price><qty>10</qty></product>";
	}

	@GET
	@Produces("text/json")
	public String getProductAsJSON(){
		return "{'name':'Pen','price':200,'qty':10}";
	}*/

}
