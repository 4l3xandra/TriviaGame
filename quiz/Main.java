package quiz;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            ErrorLog errorLog = new ErrorLog();
            QuizManager quizManager = new QuizManager();
            quizManager.addObserver(errorLog);

            Scanner scanner = new Scanner(System.in);
            Map<String, String> users = CSVUserData.loadUsers();
            Login login = new Login(users);

            boolean running = true;
            while (running) {
                System.out.println("Welcome to the Quiz Game! Good luck! :)");
                System.out.println("1. Play");
                System.out.println("2. See Scores");
                System.out.println("3. Exit");
                System.out.print("Please enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        // Registration
                        System.out.println("Enter a username to register:");
                        String username = scanner.nextLine();
                        System.out.println("Enter a password:");
                        String password = scanner.nextLine();
                        CSVUserData.saveUser(username, password);

                        // reload users data to include the new registered user
                        users = CSVUserData.loadUsers();
                        login = new Login(users);

                        // authentication
                        System.out.println("Enter your username to login:");
                        username = scanner.nextLine();
                        System.out.println("Enter your password:");
                        password = scanner.nextLine();
                        boolean isAuthenticated = login.authenticate(username, password);
                        if (!isAuthenticated) {
                            System.out.println("Invalid username or password. Exiting.");
                            return;
                        }

                        // quiz process
                        quizManager.retrieveQuestions();
                        Set<Question> askedQuestions = new HashSet<>();
                        int score = 0;
                        for (int i = 0; i < 10; i++) {
                            Question question;
                            do {
                                question = quizManager.getTrivia().getRandomQuestion();
                            } while (askedQuestions.contains(question));
                            askedQuestions.add(question);

                            System.out.println("Question " + (i + 1) + ": " + question.getQuestionText());

                            List<String> options = new ArrayList<>(question.getIncorrectAnswers());
                            options.add(question.getCorrectAnswer());
                            Collections.shuffle(options);

                            for (int j = 0; j < options.size(); j++) {
                                System.out.println((j + 1) + ": " + options.get(j));
                            }

                            System.out.println("Enter your answer:");
                            int answerIndex = scanner.nextInt() - 1;
                            scanner.nextLine(); // Consume newline

                            if (answerIndex >= 0 && answerIndex < options.size()) {
                                String answer = options.get(answerIndex);
                                if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                                    System.out.println("Correct!");
                                    score++;
                                } else {
                                    System.out.println("Wrong! The correct answer is: " + question.getCorrectAnswer());
                                }
                            } else {
                                System.out.println("Invalid answer. Please enter a number between 1 and " + options.size());
                            }
                        }

                        // save score to CSV file
                        CSVScoreData.saveScore(username, score);
                        System.out.println("Your final score is: " + score + "/10");
                        break;

                    case 2:
                        // display scores
                        CSVScoreData.displayScores();
                        break;

                    case 3:
                        // exit
                        System.out.println("Exiting the application... Goodbye!");
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                        break;
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
