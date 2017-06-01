package model.dao;

public class Teste {

	public static final String HOST = "localhost";
    public static final String DB = "spotynet";
    public static final String URL = "jdbc:postgresql://" + HOST + "/" + DB;
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String POSTGRESQLDRIVER = "org.postgresql.Driver";
	
	public static void main(String[] args) {
		
		System.out.println(URL);
	}
	
}
