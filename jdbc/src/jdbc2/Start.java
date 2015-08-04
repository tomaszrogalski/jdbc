package jdbc2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import org.omg.CORBA.TRANSACTION_MODE;

public class Start {

	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5433/cwiczenie2";
	static final String USER = "postgres";
	static final String PASS = "adminLWW";

	public static void main(String[] args) throws SQLException {

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

		zapytania = zadanie1(polaczenie, zapytania);

		zapytania = Zadanie2(polaczenie, zapytania);

		zapytania = Zadanie3(polaczenie, zapytania);

		zapytania = Zadanie4(polaczenie, zapytania);

		zapytania = Zadanie5(polaczenie, zapytania);
		
		zapytania = Zadanie6(polaczenie, zapytania);

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

	private static Statement Zadanie6(Connection polaczenie, Statement zapytania) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Statement Zadanie5(Connection polaczenie, Statement zapytania) {

		Savepoint savepoint = null;
		try {
			polaczenie.setAutoCommit(false);
			zapytania = polaczenie.createStatement();
			zapytania.execute("INSERT INTO public.test(id,imie,nazwisko) VALUES(2001,'imie','nazwisko');");
			savepoint = polaczenie.setSavepoint("SavePoint1");
			polaczenie.commit();
			System.err.println("Insert zrobiony");

		} catch (SQLException e) {

			try {
				polaczenie.rollback(savepoint);
				System.err.println(e.getMessage());
				System.err.println("cofniecie do savepointa");
			} catch (SQLException e1) {
				System.err.println("blad podczas robienia rolbacka");
			}
		}
		return zapytania;
	}

	private static Statement Zadanie4(Connection polaczenie, Statement zapytania) {

		try {
			polaczenie.setAutoCommit(false);
			zapytania = polaczenie.createStatement();
			zapytania.execute("INSERT INTO public.test(id,imie,nazwisko) VALUES(3001,'imie','nazwisko');");
			polaczenie.commit();
			System.err.println("Insert zrobiony");

		} catch (SQLException e) {

			try {
				polaczenie.rollback();
				System.err.println(e.getMessage());
				System.err.println("transakcja cofnieta do poczatku");

			} catch (SQLException e1) {
				System.err.println("blad podczas robienia rolbacka");
			}
		}
		return zapytania;
	}

	private static Statement Zadanie3(Connection polaczenie, Statement zapytania) throws SQLException {
		polaczenie.setAutoCommit(false);
		// W³aczenie transakcji o poziomie izolacji Read Committed - domyœlna
		// posgresql
		zapytania = polaczenie.createStatement();

		zapytania.execute("INSERT INTO public.test(id,imie,nazwisko) VALUES(5001,'imie','nazwisko');");

		polaczenie.commit();
		// koniec tansakcji
		return zapytania;
	}

	private static Statement Zadanie2(Connection polaczenie, Statement zapytania) {
		try {
			zapytania = polaczenie.createStatement();

			zapytania.execute("INSERT INTO public.test(id,imie,nazwisko) VALUES(60003,'imie','nazwisko');");

			System.out.println("Dodano!");
		} catch (SQLException e) {
			System.out.println("Nie Dodano!");

		}
		return zapytania;
	}

	private static Statement zadanie1(Connection polaczenie, Statement zapytania) throws SQLException {

		zapytania = polaczenie.createStatement();

		zapytania.execute("INSERT INTO public.test(id,imie,nazwisko) VALUES(50035,'imie','nazwisko');");

		System.out.println("Dodano!");
		return zapytania;
	}

}
