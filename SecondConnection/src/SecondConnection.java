import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.*;

public class SecondConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Check the order");
		Scanner sc = new Scanner(System.in);
		String str = sc.next();
		customerIdNum(str);
	}
		
	public static void customerIdNum(String customerid){
		String theSql = "";
		try {
			//Load the Driver Class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//step2: create the connection object
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "password");
			
			//step3: create statement object
			Statement stmt = con.createStatement();
			
			
			String sql_1 = "select demo_orders.order_id, demo_customers.cust_first_name, demo_customers.cust_last_name, demo_customers.cust_street_address1, demo_customers.cust_street_address2, demo_customers.cust_city, demo_customers.cust_state" + " from testuser.demo_customers" + " " + 
							"join testuser.demo_orders" + " " + "on demo_customers.customer_id = " + "demo_orders.customer_id" + " " +
					 		"where demo_orders.order_id = " + customerid.toString();
			
			
			String sql_2 = "select demo_orders.order_timestamp, demo_orders.order_id, demo_order_items.unit_price, demo_order_items.quantity, (demo_order_items.unit_price * demo_order_items.quantity) as Total" + " " +
							"from testuser.demo_order_items" + " " +
							"join testuser.demo_orders" + " " +
							"on demo_orders.order_id = " + "demo_order_items.order_id" + " " +
							"where demo_orders.customer_id = " + customerid.toString();
							
	
			theSql =       "select sum(demo_order_items.unit_price * demo_order_items.quantity) as GrandTotal" + " "
							+ "from testuser.demo_order_items" + " " + 
							"join testuser.demo_orders" + " " + 
							"on testuser.demo_orders.order_id = " + 
							"demo_order_items.order_id" + 
							" " + "where demo_orders.customer_id = " + customerid.toString();
			
			//step4: execute the query
			ResultSet rs = stmt.executeQuery(sql_1);

			
	
			while(rs.next())
			{
				System.out.println(rs.getString(2) + " " + rs.getString(3) + "\n" + rs.getString(4) + " " + rs.getString(5) + "\n" + rs.getString(6) + " " + rs.getString(7));
			}
			
			
			rs = stmt.executeQuery(sql_2);
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("Date" + "\t\t\t" + "OrderID" + "\t\t\t" + "UnitPrice" + "\t\t" + "Quantity" + "\t\t" + "Total");
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
			while(rs.next())
			{
				System.out.printf("%-10s %18s %20s %22s %25s", rs.getDate("order_timestamp"), rs.getString("order_id"), rs.getString("unit_price"), rs.getString("quantity"), rs.getString("Total"));
				System.out.println();// + "\t" + rs.getString(1));
			}
			
			rs = stmt.executeQuery(theSql);
			System.out.print("Grand Total");
			
			while(rs.next())
			{
				System.out.printf("\t%-10s", rs.getString("GrandTotal"));
				System.out.println();
			}
			con.close(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			System.out.println(theSql);
		}
	}
}
