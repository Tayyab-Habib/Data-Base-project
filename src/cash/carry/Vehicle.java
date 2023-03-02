/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author saifj
 */
public class Vehicle extends Object
{
	protected String number;
	protected String model;
	protected String colour;
	
	Vehicle(String id, String type, String last_maintainence_date, String number, String model, String coulour, String colour)
	{
		super(id, type, last_maintainence_date);
		this.number = number;
		this.model = model;
		this.colour = colour;
	}
}
