package jdbc2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Dirty read - nieudany

public class Start2 {

	public static void main(String[] args) {

		for (int i = 0; i < 200; i++) {
			Runnable runnable1 = new MyRunnable4();
			Thread newThread1 = new Thread(runnable1);
			Runnable runnable2 = new MyRunnable5();
			Thread newThread2 = new Thread(runnable2);
			newThread1.start();
			newThread2.start();
		}
	}

}

class MyRunnable5 implements Runnable {

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
		polaczenie.setTransactionIsolation(polaczenie.TRANSACTION_READ_UNCOMMITTED);
		polaczenie.setAutoCommit(false);
		zapytania = polaczenie.createStatement();

		ResultSet blabla = zapytania.executeQuery("Select id from public.test where imie='dawid';");
		polaczenie.commit();
		System.out.println("--------------------------------");
		while (blabla.next()) {
			for (int i = 1; i <= 1; i++) {
				if (i > 1)
					System.out.print(" ");
				String columnValue = blabla.getString(i);
				System.out.print(columnValue);
			}
			System.out.println("");
		}
		System.out.println("--------------------------------");

		return zapytania;
	}
}

class MyRunnable4 implements Runnable {

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

		polaczenie.setTransactionIsolation(polaczenie.TRANSACTION_READ_UNCOMMITTED);
		polaczenie.setAutoCommit(false);
		zapytania = polaczenie.createStatement();
		String sql2 = new String("UPDATE public.test SET imie = 'dawid' WHERE id=50;");
		zapytania.execute(sql2);

		polaczenie.rollback();
		return zapytania;
	}
}
