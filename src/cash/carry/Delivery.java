/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author saifj
 */
public class Delivery extends Order
{
	protected String address;
	
	Delivery(String date, int price, String id, String address)
	{
		super(date, price, id);		
	}
}
