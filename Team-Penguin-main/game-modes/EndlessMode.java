import java.util.Scanner;
import java.util.InputMismatchException;

public class EndlessMode
{
    static GameDB game;
    static Scanner scanner;
    static QuestionType qtype;


    static void addSubLoop()
    {
        int score = 0;
        int response;

        while (true) {
            Question question = game.getQuestion(qtype);
            int answer = question.getAnswer();
            System.out.println("Score: " + score);
            System.out.print(question.getLeft() + " " + question.getOperator() + " " + question.getRight() + " = ");

            try {
                response = scanner.nextInt();
                scanner.nextLine();
            }
			catch (InputMismatchException ex) {
				System.err.println("Not a number!\n");
				continue;
			}

            if (response == answer) {
                System.out.println("Correct!\n");
                ++score;
            }
            else {
                System.out.println("Not quite! The correct answer was " + answer + ".\n");
            }
        }
    }


    static void placesLoop()
    {
        int score = 0;
        int response;

        while (true) {
            Question question = game.getQuestion(qtype);
            int answer = question.getAnswer();
            System.out.println("Score: " + score);
            System.out.print(question.getNumber() + " in the " + question.getPlace() + "'s place is ");

            try {
                response = scanner.nextInt();
                scanner.nextLine();
            }
			catch (InputMismatchException ex) {
				System.err.println("Not a number!\n");
				continue;
			}

            if (response == answer) {
                System.out.println("Correct!\n");
                ++score;
            }
            else {
                System.out.println("Not quite! The correct answer was " + answer + ".\n");
            }
        }
    }


    public static void main(String[] args)
    {
        if (args.length != 1) {
            System.err.println("usage: java EndlessMode <question type>");
            System.exit(1);
        }

        qtype = null;

        if (args[0].toLowerCase().equals("addsub")) {
            qtype = QuestionType.ADDSUB;
        }
        else if (args[0].toLowerCase().equals("places")) {
            qtype = QuestionType.PLACES;
        }
        else {
            System.err.println("question type must be 'addsub' or 'places'");
            System.exit(1);
        }

        game = new GameDB();
        scanner = new Scanner(System.in);

        if (qtype == QuestionType.ADDSUB) {
            addSubLoop();
        }
        else {
            placesLoop();
        }
    }
}
