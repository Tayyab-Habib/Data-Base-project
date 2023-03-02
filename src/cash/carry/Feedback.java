/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author saifj
 */
public class Feedback
{
	protected String id;
	protected String customer_id;
	protected String feedback;
	protected boolean is_complain;
	
	Feedback(String feedback, boolean is_complain)
	{
		this.feedback = feedback;
		this.is_complain = is_complain;
	}
}