import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Account {

public static void main(String args[]) throws SQLException {

      DB_Interface db = new DB_Interface();// Creates an object to connect to the server
		System.out.println(db.getStudentID("John", "Doe"));// Example as to how to use the DB_Interface file
}




//Instance variables

 private int studentID;
 private String firstName;
 private String lastName;
 private int correctAnswers;
 private int totalQuestions;
 private double percentCorrect;
 private int scores[];
 private double score;
 
 //Constructors
 public Account() {
  firstName = "Temporary";
  lastName = "Temporary";
  correctAnswers = 0;
  totalQuestions = 0;
  percentCorrect = 0.0;
 }
 
 public Account(String newFirstName, String newLastName) throws SQLException {
   DB_Interface db = new DB_Interface();
   //If account is new it creates a new account in the database
   if(db.getStudentID(newFirstName, newLastName) == -1){
   addAccount(newFirstName, newLastName);
   correctAnswers = 0;
   totalQuestions = 0;
   percentCorrect = 0.0;
   }
   
   else{
   studentID = db.getStudentID(newFirstName, newLastName);
   scores = db.getStudentResults(studentID);
   firstName = newFirstName;
   lastName = newLastName;
   }
 }
 
 
//Database Methods
public void addAccount(String first, String last) throws SQLException {
   DB_Interface db = new DB_Interface();
   db.addStudent(first, last);
}

public void addScoresDB() throws SQLException {
   DB_Interface db = new DB_Interface();
   db.addResult(this.studentID, this.score);
   
}
 
 //Accessors
 
 //Returns the students full name
 public String getName() {
  return firstName + lastName;
 }
 
 //Returns the first name of the student
 public String getFirst() {
   return firstName;
 }
 
 //Returns the last name of the student
 public String getLastName() {
   return lastName;
 }
 
 //Returns total correct answers
 public int getCorrect() {
   return correctAnswers;
 }
 
 //Returns total questions
 public int getTotalQuestions() {
   return totalQuestions;
 }
 
 //Updates and returns the students percent of correct answers
 public double getPercentCorrect() {
   return (correctAnswers * totalQuestions)*100;
 }
 
 //Returns the students ID
 public int getStudentID() {
   return studentID;
 }
 
 //Mutators
 
 //Updates the students number of correct answers and total questions
 //as well as updating the percent correct
 public void addScores(int numCorrect, int numQuestions) {
   addCorrect(numCorrect);
   addQuestions(numQuestions);
   percentCorrect = getPercentCorrect();
 }  
 
 //Updates only the number of correct answers
 public void addCorrect(int numCorrect) {
   correctAnswers = correctAnswers + numCorrect;
 }
 
 //Updates only the number of total questions
 public void addQuestions(int numQuestions) {
   totalQuestions = totalQuestions + numQuestions;
 }
 
}