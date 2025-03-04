  import java.util.*;
import java.util.concurrent.*;

class Question {
    String question;
    String[] options;
    char correctAnswer;

    public Question(String question, String[] options, char correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class Task4 {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Question> questions = new ArrayList<>();
    private static int score = 0;
    private static final int TIME_LIMIT = 10; // Time limit per question in seconds

    public static void main(String[] args) {
        initializeQuestions();
        System.out.println("Welcome to the Quiz Game! Answer quickly before time runs out.");
        startQuiz();
        displayResults();
    }

    private static void initializeQuestions() {
        questions.add(new Question("What is the capital of France?", new String[]{"A. Berlin", "B. Madrid", "C. Paris", "D. Rome"}, 'C'));
        questions.add(new Question("Which planet is known as the Red Planet?", new String[]{"A. Earth", "B. Mars", "C. Jupiter", "D. Venus"}, 'B'));
        questions.add(new Question("What is the largest ocean on Earth?", new String[]{"A. Atlantic", "B. Indian", "C. Arctic", "D. Pacific"}, 'D'));
    }

    private static void startQuiz() {
        for (Question q : questions) {
            askQuestion(q);
        }
    }

    private static void askQuestion(Question q) {
        System.out.println("\n" + q.question);
        for (String option : q.options) {
            System.out.println(option);
        }
        
        char answer = getUserAnswerWithTimer();
        
        if (answer == q.correctAnswer) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Wrong! Correct answer: " + q.correctAnswer);
        }
    }

    private static char getUserAnswerWithTimer() {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Character> future = executor.submit(() -> {
            System.out.print("Your answer: ");
            return scanner.next().toUpperCase().charAt(0);
        });

        try {
            return future.get(TIME_LIMIT, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("\nTime's up! No answer recorded.");
            return ' '; // Indicate no answer
        } catch (Exception e) {
            return ' ';
        } finally {
            executor.shutdownNow();
        }
    }

    private static void displayResults() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your final score: " + score + " out of " + questions.size());
    }
}


