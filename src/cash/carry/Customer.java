package cash.carry;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author saifj
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Customer
{
    protected String password;
    protected static String id;
    protected static int fd_ID;
    protected static int od_ID;
	
    Customer(String id, String password)
    {
        this.password = password;
        Customer.id = id;
    }
        
    public static boolean register(String ID, String name, String address, String phone, String password) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                
                PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO Customer (name, adress, id, password, phone)"+ " VALUES (?, ?, ?, ?, ?)");
                prepareStatement.setString(1, name);
                prepareStatement.setString(2, address);
                prepareStatement.setString(3, ID);
                prepareStatement.setString(4, password);
                prepareStatement.setString(5, phone);
                prepareStatement.execute();
                
                ResultSet rs = stmt.executeQuery("select * from customer");
                
                while(rs.next())
                {
                    if(rs.getString(1).equals(name) && rs.getString(2).equals(address) && rs.getString(3).equals(ID) && rs.getString(4).equals(password) && rs.getString(5).equals(phone))
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
    
    public static boolean login(String ID, String pass) throws ClassNotFoundException
    {  
        id = ID;
        boolean result = false;
        try
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                
                ResultSet rs = stmt.executeQuery("select * from customer");
                
                while(rs.next())
                {
                    if(rs.getString(3).equals(ID) && rs.getString(4).equals(pass))
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
    
    public static ArrayList view_account() throws ClassNotFoundException
    {
        String text="  Id:";
        ArrayList<String> list = new ArrayList<>();
        try
        {    

            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select * from customer");
                
                while (rs.next())
                {
                    text  += rs.getString(3)+",   Password:"+rs.getString(4)+",   Name:"+rs.getString(1)+",   Phone:"+rs.getString(5)+",   Address:"+rs.getString(2);
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
    
    public static boolean update_account(String passwd, String name, String address, String phone, String new_password) throws ClassNotFoundException
    {
        boolean result = false;
        try 
        {  
                
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401");

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from customer");
            
            while (rs.next())
            {
                if(rs.getString(3).equals(id) && rs.getString(4).equals(passwd))
                {
                    Statement stmt1 = con.createStatement();		      
                    PreparedStatement prepareStatement  = con.prepareStatement("UPDATE customer " +" "+ "SET name = ?, address = ?, phone = ?, password = ? WHERE id = ? ");
                    prepareStatement.setString(1,name);
                    prepareStatement.setString(2,address);
                    prepareStatement.setString(3, phone);
                    prepareStatement.setString(4,new_password);
                    prepareStatement.setString(5, id);
            
                    prepareStatement.execute();
                }
            }
            
            ResultSet rs1 = stmt.executeQuery("Select * from customer");
            
            while (rs1.next())
            {
                if(rs1.getString(3).equals(id) && rs1.getString(4).equals(new_password))
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
    
    public static boolean order(String date, String p0, String q0, String p1, String q1, String p2, String q2, String p3, String q3, String p4,  String q4) throws ClassNotFoundException
    {
        od_ID = 1;
        boolean result = false;
        try 
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                int price = 0;
                
                Statement stmt0 = con.createStatement();
                ResultSet rs0 = stmt0.executeQuery("Select * from demands");
                
                while (rs0.next())
                {
                    if(Integer.parseInt(rs0.getString(3)) > od_ID)
                    {
                        od_ID = Integer.parseInt(rs0.getString(1));
                    }
                }
                
                od_ID +=  1;
                
                ResultSet rs1 = stmt0.executeQuery("Select * from product");
                while(rs1.next())
                {
                    if(rs1.getString(8).equals(p0) && Integer.parseInt(rs1.getString(5)) >= Integer.parseInt(q0))
                    {
                        PreparedStatement prepareStatement  = con.prepareStatement("UPDATE product " +"SET quantity = ? WHERE id = ? ");
                        int i = Integer.parseInt(rs1.getString(5)) - Integer.parseInt(q0);
                        prepareStatement.setString(1, String.valueOf(i));
                        prepareStatement.setString(2, p0);
                        prepareStatement.execute();
                        price = price + (Integer.parseInt(q0) * Integer.parseInt(rs1.getString(4)));
                        
                        PreparedStatement prepareStatement0  = con.prepareStatement("INSERT INTO buys (product_id, customer_id)"+ " VALUES (?, ?)");
                        prepareStatement0.setString(1,p0);
                        prepareStatement0.setString(2,id);
                        prepareStatement0.execute();
                    }
                    
                    if(rs1.getString(8).equals(p1) && Integer.parseInt(rs1.getString(5)) >= Integer.parseInt(q1))
                    {
                        PreparedStatement prepareStatement  = con.prepareStatement("UPDATE product " +"SET quantity = ? WHERE id = ? ");
                        int i = Integer.parseInt(rs1.getString(5)) - Integer.parseInt(q1);
                        prepareStatement.setString(1, String.valueOf(i));
                        prepareStatement.setString(2, p1);
                        prepareStatement.execute();
                        price = price + (Integer.parseInt(q1) * Integer.parseInt(rs1.getString(4)));
                        
                        PreparedStatement prepareStatement0  = con.prepareStatement("INSERT INTO buys (product_id, customer_id)"+ " VALUES (?, ?)");
                        prepareStatement0.setString(1,p1);
                        prepareStatement0.setString(2,id);
                        prepareStatement0.execute();
                    }
                    
                    if(rs1.getString(8).equals(p2) && Integer.parseInt(rs1.getString(5)) >= Integer.parseInt(q2))
                    {
                        PreparedStatement prepareStatement  = con.prepareStatement("UPDATE product " +"SET quantity = ? WHERE id = ? ");
                        int i = Integer.parseInt(rs1.getString(5)) - Integer.parseInt(q2);
                        prepareStatement.setString(1, String.valueOf(i));
                        prepareStatement.setString(2, p2);
                        prepareStatement.execute();
                        price = price + (Integer.parseInt(q2) * Integer.parseInt(rs1.getString(4)));
                        
                        PreparedStatement prepareStatement0  = con.prepareStatement("INSERT INTO buys (product_id, customer_id)"+ " VALUES (?, ?)");
                        prepareStatement0.setString(1,p2);
                        prepareStatement0.setString(2,id);
                        prepareStatement0.execute();
                    }
                    
                    if(rs1.getString(8).equals(p3) && Integer.parseInt(rs1.getString(5)) >= Integer.parseInt(q3))
                    {
                        PreparedStatement prepareStatement  = con.prepareStatement("UPDATE product " +"SET quantity = ? WHERE id = ? ");
                        int i = Integer.parseInt(rs1.getString(5)) - Integer.parseInt(q3);
                        prepareStatement.setString(1, String.valueOf(i));
                        prepareStatement.setString(2, p3);
                        prepareStatement.execute();
                        price = price + (Integer.parseInt(q3) * Integer.parseInt(rs1.getString(4)));
                        
                        PreparedStatement prepareStatement0  = con.prepareStatement("INSERT INTO buys (product_id, customer_id)"+ " VALUES (?, ?)");
                        prepareStatement0.setString(1,p3);
                        prepareStatement0.setString(2,id);
                        prepareStatement0.execute();
                    }
                    
                    if(rs1.getString(8).equals(p4) && Integer.parseInt(rs1.getString(5)) >= Integer.parseInt(q4))
                    {
                        PreparedStatement prepareStatement  = con.prepareStatement("UPDATE product " +"SET quantity = ? WHERE id = ? ");
                        int i = Integer.parseInt(rs1.getString(5)) - Integer.parseInt(q4);
                        prepareStatement.setString(1, String.valueOf(i));
                        prepareStatement.setString(2, p4);
                        prepareStatement.execute();
                        price = price + (Integer.parseInt(q4) * Integer.parseInt(rs1.getString(4)));
                        
                        PreparedStatement prepareStatement0  = con.prepareStatement("INSERT INTO buys (product_id, customer_id)"+ " VALUES (?, ?)");
                        prepareStatement0.setString(1,p4);
                        prepareStatement0.setString(2,id);
                        prepareStatement0.execute();
                    }
                }
                if(insert_order(String.valueOf(price), date))
                {
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
    
    public static ArrayList view_orders() throws ClassNotFoundException
    {
        String text="  Id:";
        ArrayList<String> list = new ArrayList<>();
        try
        {    
            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select * from demands");
                
                while (rs.next())
                {
                    text  += rs.getString(3)+",   Customer Id:"+rs.getString(4)+",   Date:"+rs.getString(1)+",   Price:"+rs.getString(2)+",   Status:"+rs.getString(5)+ "  ";
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
    
    public static boolean cancel_order(String od_id) throws ClassNotFoundException
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
                if(rs.getString(4).equals(id) && rs.getString(3).equals(od_id))
                {
                    result = true; 
                    stmt.executeQuery("delete from demands where id =" + od_id);
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

    
    public static boolean feedback(String feed, String complain) throws ClassNotFoundException
    { 
        boolean result = false;
        try 
        {  
            fd_ID = 1;
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                
                ResultSet rs = stmt.executeQuery("Select * from feedback");
                
                while (rs.next())
                {
                    if(Integer.parseInt(rs.getString(1)) > fd_ID)
                    {
                        fd_ID = Integer.parseInt(rs.getString(1));
                    }
                }
                
                fd_ID +=  1;
                
                PreparedStatement prepareStatement  = con.prepareStatement("INSERT INTO feedback (id, feedback, is_complain, customer_id)"+ " VALUES (?, ?, ?, ?)");
                prepareStatement.setString(1,String.valueOf(fd_ID));
                prepareStatement.setString(2,feed);
                prepareStatement.setString(3,complain);
                prepareStatement.setString(4,id);
                
                prepareStatement.execute();
                
                ResultSet rs1 = stmt.executeQuery("Select * from feedback");
                
                while (rs1.next())
                {
                    if(rs1.getString(2).equals(feed))
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
    
    public static boolean delete_account(String password) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {         
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401"); 

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from customer");
            
            while (rs.next())
            {
                delete_buys();
                delete_demands();
                delete_feedback();
                stmt.executeQuery("delete from customer where id =" + id);
                result = true; 
                break;
            }
            
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean delete_buys() throws ClassNotFoundException
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
                stmt.executeQuery("delete from buys where customer_id =" + id);
                result = true; 
            }        
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean delete_demands() throws ClassNotFoundException
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
                stmt.executeQuery("delete from demands where customer_id =" + id);
                result = true; 
            }        
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean delete_feedback() throws ClassNotFoundException
    {
        boolean result = false;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401"); 

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from feedback");
            
            while (rs.next())
            {
                stmt.executeQuery("delete from feedback where customer_id =" + id);
                result = true; 
            }        
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean insert_order(String date, String price) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401"); 

            Statement stmt = con.createStatement();
                
            PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO demands (datee, price, id, customer_id, status)"+ " VALUES (?, ?, ?, ?, ?)");
            prepareStatement.setString(1, date);
            prepareStatement.setString(2, String.valueOf(price));
            prepareStatement.setString(3, String.valueOf(od_ID));
            prepareStatement.setString(4, id);
            prepareStatement.setString(5, "Not Delivered");
            prepareStatement.execute();
                
            ResultSet rs = stmt.executeQuery("Select * from demands");
                
            while(rs.next())
            {
                if(rs.getString(3).equals(od_ID))
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
}