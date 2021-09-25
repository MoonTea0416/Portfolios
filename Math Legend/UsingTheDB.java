import java.sql.SQLException;

public class UsingTheDB {
	public static void main(String[] args) throws SQLException {
			DB_Interface db = new DB_Interface();// Creates an object to connect to the server
			
			db.getStudentID("John", "Doe");// Example as to how to use the DB_Interface file
			
			db.close();// DON'T FORGET TO CLOSE THE SERVER AT THE END OF THE PROGRAM
	}
}
