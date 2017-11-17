package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ArchRegress implements Runnable {
	
	@Override
	public void run(){
		 for (int i = 0; i < 10; i++) {
			 try {
		            GreetingController.POSTGRES_NUM_OPS_METRIC_COUNT += (i + 1);
			    getAllUsersFromDB();
			    //sleep 2 mili seconds
			    Thread.sleep(2);
			  } catch (InterruptedException e) {
			    e.printStackTrace();
		         }
		}
	}
	
	public void getAllUsersFromDB(){
	       Connection c = null;
	       Statement stmt = null;
	       try {
	       Class.forName(GreetingController.JDBC_DRIVER);
	         c = DriverManager.getConnection(GreetingController.DB_URL,GreetingController.USER, GreetingController.PASS);
	         c.setAutoCommit(false);
	         stmt = c.createStatement();
	         ResultSet rs = stmt.executeQuery( "select * from UserLoginDetails" );
	         while(rs.next())  {
	        	 System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
	        	 break;
	         }
	         rs.close();
	         stmt.close();
	         c.close();
	       } catch ( Exception e ) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	       }
	       System.out.println("Operation done successfully");
	  
}

}
