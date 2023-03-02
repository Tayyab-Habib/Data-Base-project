package cash.carry;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author saifj
 */
class Admin
{

    protected String password;
    protected static String id;
	
    Admin(int id, String password)
    {
        this.password = password;
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
                
                ResultSet rs = stmt.executeQuery("select * from owner");
                
                while(rs.next())
                {
                    if(rs.getString(5).equals(ID) && rs.getString(4).equals(pass))
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
    
    public static boolean update_account(String passwd, String name, String address, String phone, String new_password)
    {
        boolean result = false;
        try 
        {  
                
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401");

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from owner");
            
            while (rs.next())
            {
                if(rs.getString(5).equals(id) && rs.getString(4).equals(passwd))
                {
                    Statement stmt1 = con.createStatement();		      
                    PreparedStatement prepareStatement  = con.prepareStatement("UPDATE owner " +" "+ "SET name = ?, address = ?, phone = ?, password = ? WHERE id = ? ");
                    prepareStatement.setString(1,name);
                    prepareStatement.setString(2,address);
                    prepareStatement.setString(3, phone);
                    prepareStatement.setString(4,new_password);
                    prepareStatement.setString(5, id);
            
                    prepareStatement.execute();
                }
            }
            
            ResultSet rs1 = stmt.executeQuery("Select * from owner");
            
            while (rs1.next())
            {
                if(rs1.getString(5).equals(id) && rs1.getString(4).equals(new_password))
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
    
    public static boolean update_info(String phone)
    {
        boolean result = false;
        try 
        {  
                
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401");

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from cash_and_carry");
            
            while (rs.next())
            {
                Statement stmt1 = con.createStatement();		      
                PreparedStatement prepareStatement  = con.prepareStatement("UPDATE cash_and_carry SET phone = ?");
                prepareStatement.setString(1,phone);
                prepareStatement.execute();
            }
            result = true;
            con.close();  
        } 
        catch(Exception e) 
        {
            System.out.println(e);
        }
        return result;
    }
    
    public static boolean add_staff(String ID, String name, String address, String phone, String password, String salary, String designation) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                
                ResultSet rs1 = stmt.executeQuery("Select * from cash_and_carry");
                rs1.next();
                
                PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO Staff (id, name, designation, salary, cash_and_carry_adress, address, phone, password, cash_and_carry_name)"+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                prepareStatement.setString(1, ID);
                prepareStatement.setString(2, name);
                prepareStatement.setString(3, designation);
                prepareStatement.setString(4, salary);
                prepareStatement.setString(5, rs1.getString(2));
                prepareStatement.setString(6, address);
                prepareStatement.setString(7, phone);
                prepareStatement.setString(8, password);
                prepareStatement.setString(9, rs1.getString(1));
                prepareStatement.execute();
                ResultSet rs = stmt.executeQuery("select * from staff");
                
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
    
    public static boolean add_dboy(String ID, String name, String address, String phone, String password, String salary, String designation, String area, String vehicle) throws ClassNotFoundException
    {
        boolean result = false;
        try
        {  
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                
                add_staff(ID, name, address, phone, password, salary, designation);
                
                PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO Delivery_Boy (id, vehicle_id, area)"+ " VALUES (?, ?, ?)");
                prepareStatement.setString(1, ID);
                prepareStatement.setString(2, vehicle);
                prepareStatement.setString(3, area);
                prepareStatement.execute();
                
                ResultSet rs = stmt.executeQuery("select * from Delivery_Boy");
                
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
    
    public static ArrayList view_staff() throws ClassNotFoundException
    {
        String text="Delivery Boys\n  Id:";
        ArrayList<String> list = new ArrayList<>();
        try
        {    

            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select staff.id, name, address, phone, designation, salary, area, vehicle_id from staff join delivery_boy on (staff.id = delivery_boy.id)");
                
                while (rs.next())
                {
                    text  += rs.getString(1)+",   Name:"+rs.getString(2)+",   Address:"+rs.getString(2)+",   Phone:"+rs.getString(4)+",   Designation:"+rs.getString(5)+",   Salary:"+rs.getString(6)+",   Area:"+rs.getString(7)+",   Vehicle:"+rs.getString(8);
                    list.add(text);
                    text = "\nStaff\n";
                }
                
                ResultSet rs1 = stmt.executeQuery("Select id, name, address, phone, designation, salary from staff");
                
                while (rs1.next())
                {
                    text  += rs1.getString(1)+",   Name:"+rs1.getString(2)+",   Address:"+rs1.getString(2)+",   Phone:"+rs1.getString(4)+",   Designation:"+rs1.getString(5)+",   Salary:"+rs1.getString(6);
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
    
    public static boolean update_staff(String ID, String designation, String salary)
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
                Statement stmt1 = con.createStatement();		      
                PreparedStatement prepareStatement  = con.prepareStatement("UPDATE staff SET designation = ?, salary = ? WHERE id = ?");
                prepareStatement.setString(1,designation);
                prepareStatement.setString(2,salary);
                prepareStatement.setString(3,ID);
            
                prepareStatement.execute();
            }
            
            ResultSet rs1 = stmt.executeQuery("Select * from staff");
            
            while (rs1.next())
            {
                if(rs1.getString(1).equals(ID) && rs1.getString(3).equals(designation) && rs1.getString(4).equals(salary))
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
    
    public static boolean update_dboy(String ID, String designation, String salary, String area, String vehicle)
    {
        boolean result = false;
        try 
        {  
                
            Class.forName("oracle.jdbc.driver.OracleDriver");  
  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401");

            update_staff(ID, designation, salary);
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("Select * from delivey_boy");
            
            while (rs.next())
            {
                Statement stmt1 = con.createStatement();		      
                PreparedStatement prepareStatement  = con.prepareStatement("UPDATE delivey_boy " +" "+ "SET area = ?, vehicle = ? WHERE id = ?");
                prepareStatement.setString(1,area);
                prepareStatement.setString(2,vehicle);
                prepareStatement.setString(3,ID);
            
                prepareStatement.execute();
            }
            
            ResultSet rs1 = stmt.executeQuery("Select * from delivery_boy");
            
            while (rs1.next())
            {
                if(rs1.getString(1).equals(ID) && rs1.getString(2).equals(vehicle) && rs1.getString(3).equals(area))
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
    
    public static ArrayList view_recipts() throws ClassNotFoundException
    {
        String text="  Id:";
        ArrayList<String> list = new ArrayList<>();
        try
        {    

            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select * from recipt");
                
                while (rs.next())
                {
                    text  += rs.getString(1)+",   Date:"+rs.getString(3)+",   Staff Id:"+rs.getString(4)+",   Price:"+rs.getString(2);
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
                    text  += rs.getString(3)+",   Customer Id:"+rs.getString(4)+",   Date:"+rs.getString(1)+",   Price:"+rs.getString(2)+",   Status:"+rs.getString(5);
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
    
    public static ArrayList view_customers() throws ClassNotFoundException
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
                    text  += rs.getString(3)+",   Name:"+rs.getString(1)+",   Address:"+rs.getString(2)+",   Phone:"+rs.getString(5);
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
    
    public static ArrayList view_feedback() throws ClassNotFoundException
    {
        String text="  Id:";
        ArrayList<String> list = new ArrayList<>();
        try
        {    

            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb","Project","37401")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select * from feedback");
                
                while (rs.next())
                {
                    text  += rs.getString(1)+",   Customer Id:"+rs.getString(4)+",   Feedback:"+rs.getString(2)+",   Is Complain?:"+rs.getString(3);
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
}