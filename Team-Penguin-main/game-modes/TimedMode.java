import java.util.Scanner;
import java.util.InputMismatchException;

public class TimedMode
{
    static GameDB game;
    static Scanner scanner;
    static QuestionType qtype;


    static void addSubLoop()
    {
        int score = 0;
        long start = System.currentTimeMillis();
        long time;
        long total = 10;
        int response;

        while (true) {
            time = System.currentTimeMillis() - start;
            Question question = game.getQuestion(qtype);
            int answer = question.getAnswer();

            if (time / 1000 >= total) {
                System.out.println("You ran out of time!\nFinal score: " + score);
                System.exit(0);
            }

            System.out.println("Score: " + score + " | time: " + (total - time / 1000));
            System.out.print(question.getLeft() + " " + question.getOperator() + " " + question.getRight() + " = ");

            try {
                response = scanner.nextInt();
                scanner.nextLine();
            }
			catch (InputMismatchException ex) {
				System.err.println("Not a number!\n");
				continue;
			}

            // duplicated here, fix this
            if (time / 1000 >= total) {
                System.out.println("\nYou ran out of time!\nFinal score: " + score);
                System.exit(0);
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
        long start = System.currentTimeMillis();
        long time;
        long total = 10;
        int response;

        while (true) {
            time = System.currentTimeMillis() - start;
            Question question = game.getQuestion(qtype);
            int answer = question.getAnswer();

            if (time / 1000 >= total) {
                System.out.println("You ran out of time!\nFinal score: " + score);
                System.exit(0);
            }

            System.out.println("Score: " + score + " | time: " + (total - time / 1000));
            System.out.print(question.getNumber() + " in the " + question.getPlace() + "'s place is ");

            try {
                response = scanner.nextInt();
                scanner.nextLine();
            }
			catch (InputMismatchException ex) {
				System.err.println("Not a number!\n");
				continue;
			}

            // duplicated here, fix this
            if (time / 1000 >= total) {
                System.out.println("\nYou ran out of time!\nFinal score: " + score);
                System.exit(0);
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
            System.err.println("usage: java TimedMode <question type>");
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
