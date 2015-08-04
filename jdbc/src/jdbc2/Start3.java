package jdbc2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Non-repeatable reads - UDANE
public class Start3 {

	public static void main(String[] args) {

		// for (int i = 0; i <1; i++) {
		Runnable runnable1 = new MyRunnable1();
		Thread newThread1 = new Thread(runnable1);
		Runnable runnable2 = new MyRunnable2();
		Thread newThread2 = new Thread(runnable2);
		newThread1.start();
		newThread2.start();
		// }
	}

}

class MyRunnable1 implements Runnable {

	public static final String JDBC_DRIVER = "org.postgresql.Driver";
	public static final String DB_URL = "jdbc:postgresql://localhost:5433/cwiczenie2";
	public static final String USER = "postgres";
	public static final String PASS = "adminLWW";

	public void run() {

		////////////////////////// Sterownik /////////////////////////

		System.out.println("Sprawdzanie sterownika:");
		try {
			Class.forName("org.postgresql.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("Blad przy ladowaniu sterownika bazy!");
			System.exit(1);
		}
		System.out.println("Sterownik OK");

		////////////////////////// Otwieranie ////////////////////////

		java.sql.Connection polaczenie = null;
		try {
			polaczenie = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch (SQLException e) {
			System.out.println("Blad przy ladowaniu sterownika bazy!");
			System.exit(1);
		}
		System.out.println("Polaczenie OK");

		/////////////////////////// Dzialania ////////////////////////

		Statement zapytania = null;

		try {
			zapytania = Zadanie7(polaczenie, zapytania);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		////////////////////////// Zamykanie ////////////////////////

		System.out.println("Zamykanie polaczenia");
		try {
			zapytania.close();
			polaczenie.close();
		} catch (SQLException e) {
			System.out.println("Blad przy zamykaniu polaczenia " + e.toString());
			System.exit(4);
		}
		System.out.print("Zamkniecie OK");

		/////////////////////////////////////////////////////////////
	}

	private static Statement Zadanie7(Connection polaczenie, Statement zapytania) throws SQLException {
		// polaczenie.setTransactionIsolation(polaczenie.TRANSACTION_READ_UNCOMMITTED);
		
		//ROZNE ODCZYTY
			
		 // polaczenie.setTransactionIsolation(polaczenie.TRANSACTION_SERIALIZABLE);
		
		// A TU TAKIE DAME
		polaczenie.setAutoCommit(false);
		zapytania = polaczenie.createStatement();

		ResultSet blabla1 = zapytania.executeQuery("Select imie from public.test where id=2;");
		while (blabla1.next()) {
			for (int i = 1; i <= 1; i++) {
				if (i > 1)
					System.out.print(" ");
				System.out.println("");
				String columnValue2 = blabla1.getString(i);
				System.out.print(columnValue2);
			}
			System.out.println("");
		}
		ResultSet blabla2 = zapytania.executeQuery("Select imie from public.test where id=2;");

		while (blabla2.next()) {
			for (int i = 1; i <= 1; i++) {
				if (i > 1)
					System.out.print(" ");
				System.out.println("");
				String columnValue = blabla2.getString(i);
				System.out.print(columnValue);
			}
			System.out.println("");
		}
		polaczenie.commit();
		return zapytania;
	}
}

class MyRunnable2 implements Runnable {

	public static final String JDBC_DRIVER = "org.postgresql.Driver";
	public static final String DB_URL = "jdbc:postgresql://localhost:5433/cwiczenie2";
	public static final String USER = "postgres";
	public static final String PASS = "adminLWW";

	public void run() {

		////////////////////////// Sterownik /////////////////////////

		System.out.println("Sprawdzanie sterownika:");
		try {
			Class.forName("org.postgresql.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("Blad przy ladowaniu sterownika bazy!");
			System.exit(1);
		}
		System.out.println("Sterownik OK");

		////////////////////////// Otwieranie ////////////////////////

		java.sql.Connection polaczenie = null;
		try {
			polaczenie = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch (SQLException e) {
			System.out.println("Blad przy ladowaniu sterownika bazy!");
			System.exit(1);
		}
		System.out.println("Polaczenie OK");

		/////////////////////////// Dzialania ////////////////////////

		Statement zapytania = null;

		try {
			zapytania = Zadanie7(polaczenie, zapytania);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		////////////////////////// Zamykanie ////////////////////////

		System.out.println("Zamykanie polaczenia");
		try {
			zapytania.close();
			polaczenie.close();
		} catch (SQLException e) {
			System.out.println("Blad przy zamykaniu polaczenia " + e.toString());
			System.exit(4);
		}
		System.out.print("Zamkniecie OK");

		/////////////////////////////////////////////////////////////
	}

	private static Statement Zadanie7(Connection polaczenie, Statement zapytania) throws SQLException {

		polaczenie.setAutoCommit(false);
		zapytania = polaczenie.createStatement();
		zapytania.execute("UPDATE public.test set imie ='123415' where id=2");
		polaczenie.commit();
		return zapytania;
	}
}
