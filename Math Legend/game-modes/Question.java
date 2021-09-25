public class Question
{
    // both
    int answer;

    // add/sub
    int left, right;
    String operator;

    // places
    int number;
    String place;


    // constructor
    public Question(Object[] db_question, QuestionType qtype)
    {
        if (qtype == QuestionType.ADDSUB) {
            left = (int)db_question[0];
            operator = (String)db_question[1];
            right = (int)db_question[2];
            answer = (int)db_question[3];
        }
        else {
            number = (int)db_question[0];
            place = (String)db_question[1];
            answer = (int)db_question[2];
        }
    }


    // accessors
    public int getAnswer()
    {
        return answer;
    }

    public int getLeft()
    {
        return left;
    }
    
    public int getRight()
    {
        return right;
    }

    public String getOperator()
    {
        return operator;
    }

    public int getNumber()
    {
        return number;
    }

    public String getPlace()
    {
        return place;
    }
}
