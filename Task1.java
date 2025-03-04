import java.util.*;
import java.util.concurrent.*;
    public class Task1 {
    
        static class Question {
            String question;
            String[] options;
            String correctAnswer;
    
            public Question(String question, String[] options, String correctAnswer) {
                this.question = question;
                this.options = options;
                this.correctAnswer = correctAnswer;
            }
        }
    
        private static final List<Question> quizData = new ArrayList<>();
        private static int score = 0;
    
        public static void main(String[] args) {
            // Initialize the quiz questions and answers
            quizData.add(new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, "Paris"));
            quizData.add(new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Venus"}, "Mars"));
            // Add more questions if necessary
    
            // Start the quiz
            runQuiz();
        }
    
        public static void runQuiz() {
            Scanner scanner = new Scanner(System.in);
            ExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    
            // Loop through all questions
            for (Question question : quizData) {
                System.out.println("\nNew Question:");
                displayQuestion(question);
    
                // Start the timer for 30 seconds
                final ScheduledFuture<?> timer = executor.schedule(() -> {
                    System.out.println("\nTime's up!");
                }, 30, TimeUnit.SECONDS);
    
                // Get the user's answer within the given time
                String userAnswer = getUserAnswer(scanner);
    
                // Cancel the timer if the user answered before time's up
                timer.cancel(true);
    
                if (userAnswer != null) {
                    if (userAnswer.equalsIgnoreCase(question.correctAnswer)) {
                        score++;
                        System.out.println("Correct!");
                    } else {
                        System.out.println("Wrong! The correct answer was: " + question.correctAnswer);
                    }
                }
            }
    
            // Show final score
            System.out.println("\nYour final score is: " + score + "/" + quizData.size());
            executor.shutdown();
        }
    
        public static void displayQuestion(Question question) {
            System.out.println("Question: " + question.question);
            for (int i = 0; i < question.options.length; i++) {
                System.out.println((i + 1) + ". " + question.options[i]);
            }
        }
    
        public static String getUserAnswer(Scanner scanner) {
            long startTime = System.currentTimeMillis();
            long timeLimit = 30000; // 30 seconds time limit
    
            System.out.println("You have " + timeLimit / 1000 + " seconds to answer.");
            while (System.currentTimeMillis() - startTime < timeLimit) {
                if (scanner.hasNextLine()) {
                    String answer = scanner.nextLine();
                    return answer;
                }
            }
            return null; // Time ran out
        }
    }
    
