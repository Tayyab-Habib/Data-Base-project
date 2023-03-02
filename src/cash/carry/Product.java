/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author saifj
 */
public class Product 
{
	protected String id;
	protected String name;
	protected String kind;
	protected String type;
	protected int price;
	protected int quantity;
	protected String expiry_date;
	protected int rag_no;
	
	Product(String id, String name, String kind, String type, int price, int quantity, String expiry_date, int rag_no)
	{
		this.id = id;
		this.name = name; 
		this.kind = kind;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
		this.expiry_date = expiry_date;
		this.rag_no = rag_no;
	}
	
}
