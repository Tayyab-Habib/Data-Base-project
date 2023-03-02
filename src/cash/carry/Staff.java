package cash.carry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author saifj
 */
public class Staff
{
	protected String designation;
	protected int salary;
	protected String password;
	protected static String id;
        protected static int rc_ID;
	
	Staff(String password, String designation, int salary)
	{
		this.password = password;
		this.designation = designation;
		this.salary = salary;
	}
        
        public static boolean login(String ID, String pass) throws ClassNotFoundException
    {  
        id = ID;
        boolean result = true;
        try
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                
                ResultSet rs = stmt.executeQuery("select * from staff");
                
                while(rs.next())
                {
                    System.out.println(rs.getString(1) + rs.getString(8));
                    if(rs.getString(1).equals(ID) && rs.getString(8).equals(pass))
                    {
                        result = true;
                        break;
                    }
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }  
        
    public static boolean update_account(String passwd, String name, String address, String phone, String new_password) throws ClassNotFoundException
    {
        boolean result = false;
        try 
        {  
                
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401");

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from staff");
            
            while (rs.next())
            {
                if(rs.getString(1).equals(id) && rs.getString(8).equals(passwd))
                {
                    Statement stmt1 = con.createStatement();		      
                    PreparedStatement prepareStatement  = con.prepareStatement("UPDATE staff " +" "+ "SET name = ?, address = ?, phone = ?, password = ? WHERE id = ? ");
                    prepareStatement.setString(1,name);
                    prepareStatement.setString(2,address);
                    prepareStatement.setString(3, phone);
                    prepareStatement.setString(4,new_password);
                    prepareStatement.setString(5, id);
                    prepareStatement.execute();
                }
            }
            
            ResultSet rs1 = stmt.executeQuery("Select * from staff");
            
            while (rs1.next())
            {
                if(rs1.getString(1).equals(id) && rs1.getString(8).equals(new_password))
                {
                    result = true;
                }
            }
            con.close();  
        } 
        catch(Exception e) 
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static ArrayList view_wholesellers() throws ClassNotFoundException
    {
        String text="  Id:";
        ArrayList<String> list = new ArrayList<>();
        try
        {    

            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select * from wholeseller");
                
                while (rs.next())
                {
                    text  += rs.getString(1)+",   Name:"+rs.getString(2)+",   Phone:"+rs.getString(3)+",   Address:"+rs.getString(4);
                    list.add(text);
                    text = "  ";
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return list;
        
    }
    
    public static boolean register(String ID, String name, String address, String phone) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                
                PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO wholeseller (id, name, adress, phone)"+ " VALUES (?, ?, ?, ?)");
                prepareStatement.setString(1, ID);
                prepareStatement.setString(2, name);
                prepareStatement.setString(3, address);
                prepareStatement.setString(4, phone);
                prepareStatement.execute();
                
                ResultSet rs = stmt.executeQuery("select * from wholeseller");
                
                while(rs.next())
                {
                    if(rs.getString(1).equals(ID) && rs.getString(2).equals(name))
                    {
                        result = true;
                        break;
                    }
                }
            }
        }
        
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean update_wholeseller(String ID, String name, String address, String phone) throws ClassNotFoundException
    {
        boolean result = false;
        try 
        {  
                
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401");

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from wholeseller");
            
            while (rs.next())
            {
                if(rs.getString(1).equals(ID))
                {
                    Statement stmt1 = con.createStatement();		      
                    PreparedStatement prepareStatement  = con.prepareStatement("UPDATE wholeseller SET name = ?, adress = ?, phone = ? WHERE id = ?");
                    prepareStatement.setString(1,name);
                    prepareStatement.setString(2,address);
                    prepareStatement.setString(3,phone);
                    prepareStatement.setString(4,ID);
                    prepareStatement.executeUpdate();
                }
            }
            
            ResultSet rs1 = stmt.executeQuery("Select * from wholeseller");
            
            while (rs1.next())
            {
                if(rs1.getString(1).equals(ID) && rs1.getString(2).equals(name) && rs1.getString(3).equals(phone) && rs1.getString(4).equals(address))
                {
                    result = true;
                }
            }
            con.close();  
        } 
        catch(Exception e) 
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean delete_wholeseller(String ID) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401"); 

            delete_provide(ID);
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from wholeseller");
            
            while (rs.next())
            {
                delete_provide(ID);
                stmt.executeQuery("delete from wholeseller where id =" + ID);
                result = true; 
            }        
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean delete_provide(String ID) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401"); 

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from provide");
            
            while (rs.next())
            {
                stmt.executeQuery("delete from provide where wholeseller_id =" + ID);
                result = true; 
            }        
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean update_order(String ID, String status) throws ClassNotFoundException
    {
        boolean result = false;
        try 
        {  
                
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401");

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from demands");
            
            while (rs.next())
            {
                if(rs.getString(3).equals(ID))
                {
                    Statement stmt1 = con.createStatement();		      
                    PreparedStatement prepareStatement  = con.prepareStatement("UPDATE demands SET status = ? WHERE id = ? ");
                    prepareStatement.setString(1,status);
                    prepareStatement.setString(2,ID);
            
                    prepareStatement.execute();
                }
            }
            
            ResultSet rs1 = stmt.executeQuery("Select * from demands");
            
            while (rs1.next())
            {
                if(rs1.getString(3).equals(ID) && rs1.getString(5).equals(status))
                {
                    result = true;
                }
            }
            con.close();  
        } 
        catch(Exception e) 
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean create_recipt(String date, String staff, String p0, String q0, String p1, String q1, String p2, String q2, String p3, String q3, String p4,  String q4) throws ClassNotFoundException
    {
        rc_ID = 1;
        boolean result = false;
        try 
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401");
                
            Statement stmt9 = con.createStatement();
                
                ResultSet rs9 = stmt9.executeQuery("Select * from recipt");
                
                while (rs9.next())
                {
                    if(Integer.parseInt(rs9.getString(1)) > rc_ID)
                    {
                        rc_ID = Integer.parseInt(rs9.getString(1));
                    }
                }
                
                rc_ID +=  1;
            
            int price = 0;
            
            Statement stmt0 = con.createStatement();
            ResultSet rs1 = stmt0.executeQuery("Select * from product");
                while(rs1.next())
                {
                    if(rs1.getString(8).equals(p0) && Integer.parseInt(rs1.getString(5)) >= Integer.parseInt(q0))
                    {
                        PreparedStatement prepareStatement  = con.prepareStatement("UPDATE product SET quantity = ? WHERE id = ?");
                        int i = Integer.parseInt(rs1.getString(5)) - Integer.parseInt(q0);
                        prepareStatement.setString(1, String.valueOf(i));
                        prepareStatement.setString(2, p0);
                        prepareStatement.execute();
                        price = price + (Integer.parseInt(q0) * Integer.parseInt(rs1.getString(4)));
                    }
                    
                    if(rs1.getString(8).equals(p1) && Integer.parseInt(rs1.getString(5)) >= Integer.parseInt(q1))
                    {
                        PreparedStatement prepareStatement  = con.prepareStatement("UPDATE product SET quantity = ? WHERE id = ?");
                        int i = Integer.parseInt(rs1.getString(5)) - Integer.parseInt(q1);
                        prepareStatement.setString(1, String.valueOf(i));
                        prepareStatement.setString(2, p1);
                        prepareStatement.execute();
                        price = price + (Integer.parseInt(q1) * Integer.parseInt(rs1.getString(4)));
                    }
                    
                    if(rs1.getString(8).equals(p2) && Integer.parseInt(rs1.getString(5)) >= Integer.parseInt(q2))
                    {
                        PreparedStatement prepareStatement  = con.prepareStatement("UPDATE product SET quantity = ? WHERE id = ?");
                        int i = Integer.parseInt(rs1.getString(5)) - Integer.parseInt(q2);
                        prepareStatement.setString(1, String.valueOf(i));
                        prepareStatement.setString(2, p2);
                        prepareStatement.execute();
                        price = price + (Integer.parseInt(q2) * Integer.parseInt(rs1.getString(4)));
                    }
                    
                    if(rs1.getString(8).equals(p3) && Integer.parseInt(rs1.getString(5)) >= Integer.parseInt(q3))
                    {
                        PreparedStatement prepareStatement  = con.prepareStatement("UPDATE product SET quantity = ? WHERE id = ?");
                        int i = Integer.parseInt(rs1.getString(5)) - Integer.parseInt(q3);
                        prepareStatement.setString(1, String.valueOf(i));
                        prepareStatement.setString(2, p3);
                        prepareStatement.execute();
                        price = price + (Integer.parseInt(q3) * Integer.parseInt(rs1.getString(4)));
                    }
                    
                    if(rs1.getString(8).equals(p4) && Integer.parseInt(rs1.getString(5)) >= Integer.parseInt(q4))
                    {
                        PreparedStatement prepareStatement  = con.prepareStatement("UPDATE product SET quantity = ? WHERE id = ?");
                        int i = Integer.parseInt(rs1.getString(5)) - Integer.parseInt(q4);
                        prepareStatement.setString(1, String.valueOf(i));
                        prepareStatement.setString(2, p4);
                        prepareStatement.execute();
                        price = price + (Integer.parseInt(q4) * Integer.parseInt(rs1.getString(4)));
                    }
                }
                
                PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO recipt (id, price, datee, staff_id)"+ " VALUES (?, ?, ?, ?)");
                prepareStatement.setString(1, String.valueOf(rc_ID));
                prepareStatement.setString(2, String.valueOf(price));
                prepareStatement.setString(3, date);
                prepareStatement.setString(4, staff);
                prepareStatement.execute();
                
                Statement stmt1 = con.createStatement();
                ResultSet rs = stmt1.executeQuery("select * from recipt");
                
                while(rs.next())
                {
                    if(rs.getString(1).equals(String.valueOf(rc_ID)))
                    {
                        
                        result = true;
                        break;
                    }
                }
        } 
        catch(SQLException e) 
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static ArrayList view_vehicles() throws ClassNotFoundException
    {
        String text="  Number:";
        ArrayList<String> list = new ArrayList<>();
        try
        {    

            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select * from vehicles");
                
                while (rs.next())
                {
                    text  += rs.getString(1)+",   Model:"+rs.getString(2)+",   Colour:"+rs.getString(3);
                    list.add(text);
                    text = "  ";
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return list;
        
    }
    
    public static boolean add_veh(String number, String model, String colour) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                
                Statement stmt1 = con.createStatement();
                ResultSet rs1 = stmt1.executeQuery("Select * from cash_and_carry");
                rs1.next();
                
                PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO \"PROJECT\".\"VEHICLES\" (ID, MODEL, COLOUR, CASH_AND_CARRY_ADRESS, CASH_AND_CARRY_NAME) VALUES (?, ?, ?, ?, ?)");
                prepareStatement.setString(1, number);
                prepareStatement.setString(2, model);
                prepareStatement.setString(3, colour);
                prepareStatement.setString(4, rs1.getString(2));
                prepareStatement.setString(5, rs1.getString(1));
                prepareStatement.execute();
                
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from vehicles");
                
                while(rs.next())
                {
                    if(rs.getString(1).equals(number))
                    {
                        result = true;
                        break;
                    }
                }
            }
        }
        
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean delete_vehicle(String ID) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401"); 
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from vehicles");
            
            while (rs.next())
            {
                if(rs.getString(1).equals(ID))   
                {
                    stmt.executeQuery("DELETE FROM VEHICLES WHERE ID ='" + ID + "'");
                    result = true; 
                }
            }        
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean add_product(String ID, String name, String kind, String type, String price, String qty, String expiry, String rag, String seller) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                
                PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO product (name, kind, type, price, quantity, expiry_date, rag_number, id)"+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                prepareStatement.setString(1, name);
                prepareStatement.setString(2, kind);
                prepareStatement.setString(3, type);
                prepareStatement.setString(4, price);
                prepareStatement.setString(5, qty);
                prepareStatement.setString(6, expiry);
                prepareStatement.setString(7, rag);
                prepareStatement.setString(8, ID);
                prepareStatement.execute();
                
                add_provide(ID, seller);
                add_canbe(ID);
                
                ResultSet rs = stmt.executeQuery("select * from product");
                
                while(rs.next())
                {
                    if(rs.getString(8).equals(ID))
                    {
                        result = true;
                        break;
                    }
                }
            }
        }
        
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean add_provide(String ID, String seller) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                
                PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO provide (product_ID, wholeseller_id)"+ " VALUES (?, ?)");
                prepareStatement.setString(1, ID);
                prepareStatement.setString(2, seller);
                prepareStatement.execute();
                
                ResultSet rs = stmt.executeQuery("select * from provide");
                
                while(rs.next())
                {
                    if(rs.getString(1).equals(ID) && rs.getString(2).equals(seller))
                    {
                        result = true;
                        break;
                    }
                }
            }
        }
        
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean add_canbe(String ID) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                
                Statement stmt1 = con.createStatement();
                ResultSet rs1 = stmt1.executeQuery("Select * from cash_and_carry");
                rs1.next();
                
                PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO can_be (product_ID, cash_and_carry_adress, cash_and_carry_name)"+ " VALUES (?, ?, ?)");
                prepareStatement.setString(1, ID);
                prepareStatement.setString(2, rs1.getString(2));
                prepareStatement.setString(3, rs1.getString(1));
                prepareStatement.execute();
                
                ResultSet rs = stmt.executeQuery("select * from can_be");
                
                while(rs.next())
                {
                    if(rs.getString(1).equals(ID))
                    {
                        result = true;
                        break;
                    }
                }
            }
        }
        
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean delete_product(String ID) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {         
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401"); 

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from product");
            
            while (rs.next())
            {
                if(rs.getString(8).equals(ID))
                {
                delete_buys(ID);
                delete_canbe(ID);
                delete_provide1(ID);
                stmt.executeQuery("delete from product where id =" + ID);
                result = true; 
                break;
                }
            }
            
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean delete_buys(String ID) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401"); 

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from buys");
            
            while (rs.next())
            {
                if(rs.getString(1).equals(ID))
                {
                stmt.executeQuery("delete from buys where product_id =" + ID);
                result = true; 
                break;
                }
            }        
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean delete_canbe(String ID) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401"); 

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from can_be");
            
            while (rs.next())
            {
                if(rs.getString(1).equals(ID))
                {
                stmt.executeQuery("delete from can_be where product_id =" + ID);
                result = true; 
                break;
                }
            }        
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean delete_provide1(String ID) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401"); 

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from provide");
            
            while (rs.next())
            {
                if(rs.getString(1).equals(ID))
                {
                stmt.executeQuery("delete from provide where product_id =" + ID);
                result = true; 
                break;
                }
            }        
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static ArrayList view_inventory() throws ClassNotFoundException
    {
        String text="  Id:";
        ArrayList<String> list = new ArrayList<>(12);
        try
        {    
            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select * from product");
                
                while (rs.next())
                {
                    text  += rs.getString(8)+",   Name:"+rs.getString(1)+",   Kind:"+rs.getString(2)+",   Type:"+rs.getString(3)+",   Price:"+rs.getString(4)+ ",   Quantity:"+rs.getString(5)+",   Expiry:"+rs.getString(6)+ "  ";
                    list.add(text);
                    text = "";
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return list;
        
    }
    
    public static ArrayList view_product(String name) throws ClassNotFoundException
    {
        String text="  Id:";
        ArrayList<String> list = new ArrayList<>(12);
        try
        {    
            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                
                ResultSet rs = stmt.executeQuery("Select * from product");
                
                while (rs.next())
                {
                    if(rs.getString(1).equals(name))
                    {
                        text  += rs.getString(8)+",   Name:"+rs.getString(1)+",   Kind:"+rs.getString(2)+",   Type:"+rs.getString(3)+",   Price:"+rs.getString(4)+ ",   Quantity:"+rs.getString(5)+",   Expiry:"+rs.getString(6)+ "  ";
                        list.add(text);
                        text = "";
                    }
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return list;
        
    }
    
    
    public static int count_product() throws ClassNotFoundException
    {
        int count = 0;
        try
        {    
            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
     
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select count(*) from product");
                
                rs.next();
                count = rs.getInt(1);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return count;
        
    }
    
    public static ArrayList count_types() throws ClassNotFoundException
    {
        String text="  Type:";
        ArrayList<String> list = new ArrayList<>(12);
        try
        {    
            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
     
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select count(*) from product GROUP BY type");
                
                while (rs.next())
                {
                    text  += rs.getString(1)+",   Qty:"+rs.getString(2);
                    list.add(text);
                    text = "";
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return list;
        
    }
}

