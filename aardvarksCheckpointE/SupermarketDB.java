package aardvarksCheckpointE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class SupermarketDB {
	
	public static final DecimalFormat df = new DecimalFormat( "#.#" );
	static Connection conn = null;
	static Statement stmt = null;
	private static Scanner scan = new Scanner(System.in);

	public static Connection createConnection() {

		String user = "DBUser";
		String pass = "DBUser";
		String name = "supermarket";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/" + name;

		// System.out.println(driver);
		// System.out.println(url);

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, pass);
			// System.out.println("Connection really is from : " +
			// conn.getClass().getName());
			// System.out.println("Connection successful!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
				// stmt.close();
				System.out.println("The connection was successfully closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void checkConnect() {
		if (conn == null) {
			conn = createConnection();
		}
		if (stmt == null) {
			try {
				stmt = conn.createStatement();
			} catch (SQLException e) {
				System.out.println("Cannot create the statement");
			}

		}
	}

	public static void printCustomers() {
		checkConnect();
		String query = "SELECT * FROM customer";
		try {
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(query);
			System.out.println(" ");
			System.out.println("ID | ArrivalTime | ServiceTime | FinishTime | ServeTime | Wait");

			while (rs.next()) {
				int id = rs.getInt("custID");
				double arrrivet = rs.getDouble("arrivalTime");
				double serviceT = rs.getDouble("serviceTime");
				double finishT = rs.getDouble("finishTime");
				double serveT = rs.getDouble("serveTime");
				double wait = rs.getDouble("wait");

				System.out.println(
						id + " | " + arrrivet + " | " + serviceT + " | " + finishT + " | " + serveT + " | " + wait);
			}
		}

		catch (SQLException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}

	}

	public static void addCustomers(Customer cust) {
		checkConnect();
		
		String queryAdd = "INSERT INTO customer (custID, arrivalTime, serviceTime, finishTime, serveTime, wait) VALUES "
				+ "('" + cust.getCustomerId() + "'," + cust.getArrivalTime() + "," + cust.getServiceTime() + ","
				+ df.format(cust.getFinishTime()) + "," + df.format(cust.getServeTime()) + "," + df.format(cust.getWait()) + ")";

		System.out.println("Customer added to database");

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(queryAdd);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}

	}

	public static void addTestData(int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime,
			int numCustomers, int percentSlower, int numFullLanes, int numSelfLanes) {
		checkConnect();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		String queryAdd = "INSERT INTO testdata (dateTime, minArrival, maxArrival, minService, maxService, numCust, numFullLanes, numSelfLanes, percentSlow) VALUES "
				+ "('" + now + "'," + minArrivalTime + "," + maxArrivalTime + "," + minServiceTime + ","
				+ maxServiceTime + "," + numCustomers + "," + percentSlower + "," + numFullLanes + "," + numSelfLanes
				+ ")";

		System.out.println("Test data added to database");

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(queryAdd);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}

	}

	public static void addResults(double fullWait, double selfWait, double fullNoUse, double selfNoUse, int satisfied, int dissatisfied) {
		checkConnect();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		String queryAdd = "INSERT INTO results (dateTime, fullAverageWait, selfAverageWait, fullNoUse, selfNoUse, satisfied, dissatisfied) VALUES "
				+ "('" + now + "'," + df.format(fullWait) + "," + df.format(selfWait) + "," + df.format(fullNoUse) + ","
				+ df.format(selfNoUse) + "," + satisfied + "," + dissatisfied + ")";

		System.out.println("Results added to database");

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(queryAdd);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}

	}

}
