package hello;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class Simulation {
	
	private static final Logger LOG = LoggerFactory.getLogger(Simulation.class);
	private final AtomicInteger counter = new AtomicInteger(0);
	
	@Value( "${simulation.db.user:null}" )
	public String USER;
	
	@Value( "${simulation.db.pass:null}" )
	public String PASS;
	
	@Value( "${simulation.requestLatency:0}" )
	private Long requestLatency;
	
	@Value( "${simulation.simulateException:FALSE}" )
	private boolean simulateException;
	
	@Value( "${simulation.simulateError:FALSE}" )
	private boolean simulateError;
	
	@Value( "${simulation.logRuns:0}" )
	private Integer logRuns;
	
	@Value( "${simulation.simulateAdditonalProcessing:FALSE}" )
	private boolean simulateAdditonalProcessing;
	
	@Value( "${simulation.simulateRandomly:0}" )
	private int simulateRandomly;
	
	@Value( "${simulation.fileOpen:null}")
	private String fileOpen;
	
	@Value( "${simulation.numberDbCalls:0}" )
	private int numberDbCalls;

	public Long getRequestLatency() {
		return requestLatency;
	}

	public void setRequestLatency(Long requestLatency) {
		this.requestLatency = requestLatency;
	}
	
	public void simulateRequestLatency() {
		if(requestLatency > 0) {
			LOG.debug("simulateRequestLatency " + requestLatency);
			try {
				Thread.sleep(requestLatency);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				LOG.error("Interrupted Exception", e);
			}
		}
	}
	
	public void simulateExcpetion() {
		if(simulateException) {
			LOG.debug("simulateException");
			Exception se = new Exception("Simulated Exception");
			try {
				throw se;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.error("Sim Exception", e);
			}
		}
	}
	
	public void simulateError() {
		if(simulateError) {
			LOG.debug("simulateError");
			RuntimeException re = new RuntimeException("Simulated Runtime Exception");
			throw re;
		}
	}
	
	public void simulateLogLines() {
		if(logRuns > 0) {
			LOG.debug("simulateLogLines");
			for(int i = 0; i < logRuns; i++) {
				LOG.info("Simulated Log Run #" + (i+1));
			}
		}
	}
	
	public void simulateAdditonalProcessing() {
		if(simulateAdditonalProcessing) {
			LOG.info("begin simulateDummyMethod");
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				LOG.error("error: ", e);
			}
			LOG.info("end simulateDummyMethod");
		}
	}
	
	public void counterIncrement() {
		counter.incrementAndGet();
		return;
	}
	
	public int counterGetValue() {
		return counter.get();
	}
	
	public void simulateRandomly() {
		if(simulateRandomly > 0) {
			LOG.debug("simulateRandomly");
			if((counterGetValue() % simulateRandomly) == 0) {
				simulateError();
			}
		}
	}
	
	public void simulateFileAcitvity() {
		if(fileOpen != null) {	
			LOG.info("simulateFileActivity " + fileOpen);
			StringBuffer content = new StringBuffer();
			try {
				FileReader fr = new FileReader(fileOpen);
				BufferedReader br = new BufferedReader(fr);
				String s;
				while((s = br.readLine()) != null) {
					content.append(s + "\n");
				}
			}
			catch (IOException ioe){
				ioe.printStackTrace();
				LOG.error("IOError: file could not be read", ioe);
			}
		}
	}
	
	public void simulateGetDbUsers() {
		Connection c = null;
	       Statement stmt = null;
	       try {
	       Class.forName(GreetingController.JDBC_DRIVER);
	         c = DriverManager.getConnection(GreetingController.DB_URL,USER, PASS);
	         c.setAutoCommit(false);
	         stmt = c.createStatement();
	         ResultSet rs = stmt.executeQuery( "select * from UserLoginDetails" );
	         while(rs.next())  {
	        	 LOG.debug(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
	         }
	         rs.close();
	         stmt.close();
	         c.close();
	       } catch ( Exception e ) {
	         LOG.debug( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	       }
	       LOG.debug("Operation done successfully");
	}
	
	public void simulateDbRun() {
		for (int i = 0; i < numberDbCalls; i++) {
			LOG.info("simulateDbRun");
			 try {
			    simulateGetDbUsers();
			    //sleep 2 mili seconds
			    Thread.sleep(2);
			  } catch (InterruptedException e) {
			    e.printStackTrace();
		         }
		}
	}
	
	public void simulate() {
//		simulateRequestLatency();
//		simulateExcpetion();
//		simulateLogLines();
//		//simulateError();
//		simulateAdditonalProcessing();
//		simulateRandomly();
//		simulateFileAcitvity();
		simulateDbRun();
	}
	
	

}
