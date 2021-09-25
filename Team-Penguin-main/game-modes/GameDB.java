import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

enum QuestionType { ADDSUB, PLACES };

public class GameDB
{
    static DB_Interface database;

    /* 
     * default constructor
     */
    public GameDB()
    {
        // database connection object
        try {
            database = new DB_Interface();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }


    public Question getQuestion(QuestionType qtype)
    {
        Object[] db_question = new Object[4];

        try {
            if (qtype == QuestionType.ADDSUB) {
                db_question = database.getAddSubQuestion();
            }
            else {
                db_question = database.getPlaceQuestion();
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return new Question(db_question, qtype);
    }

    public void close()
    {
        database.close();
    }
}



// access database

/// DB info: ///
// String checkDB = "SELECT * FROM Questions";
// Statement statement;
// statement = database.connect.createStatement();
// ResultSet rs = statement.executeQuery(checkDB);
// for (int i = 1; i < rs.getMetaData().getColumnCount(); ++i) {
//     System.out.println(rs.getMetaData().getColumnName(i) + "\t" + JDBCType.valueOf(rs.getMetaData().getColumnType(i)).getName());
// }

/// DROP: ///
// String drop = "DROP TABLE Questions";
// Statement statement = database.connect.createStatement();
// statement.execute(drop);

/// CREATE: ///
// String create = "CREATE TABLE Questions (" +
//     "questionID SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
//     "questionType ENUM('addSub', 'places') NOT NULL, " +
//     "leftNum SMALLINT UNSIGNED, " +
//     "operator CHAR(1), " +
//     "rightNum SMALLINT UNSIGNED, " +
//     "number SMALLINT UNSIGNED, " +
//     "answer SMALLINT UNSIGNED, " +
//     "place ENUM('1', '10', '100'))";
// Statement statement = database.connect.createStatement();
// statement.execute(create);

/// ADD QUESTIONS: ///
// database.addAddSubQuestion(5, "+", 15, 20);
// database.addAddSubQuestion(10, "+", 13, 23);
// database.addAddSubQuestion(18, "-", 3, 15);
// database.addAddSubQuestion(7, "-", 4, 3);
// database.addPlaceQuestion(312, "100", 3);
// database.addPlaceQuestion(192, "10", 9);
// database.addPlaceQuestion(21, "1", 1);
// database.addPlaceQuestion(562, "10", 6);
