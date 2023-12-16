import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Enumeration;

class QuizQuestion {
    String question;
    List<String> options;
    int correctOption;

    QuizQuestion(String question, List<String> options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }
}

public class task_4_QuizApp extends JFrame {
    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;
    private JLabel questionLabel;
    private ButtonGroup optionsButtonGroup;
    private JLabel timerLabel;
    private static JButton submitButton;

    public task_4_QuizApp() {
        quizQuestions = List.of(
                new QuizQuestion("What is the capital of France?", List.of("Berlin", "Madrid", "Paris", "Rome"), 3),
                new QuizQuestion("Which planet is known as the Red Planet?", List.of("Earth", "Mars", "Venus", "Jupiter"), 2),
                new QuizQuestion("Which is longest river in the world?", List.of("Nile", "Amazon", "Yellow River", "Congo River"), 1)
            
                // Add more questions...
        );

        currentQuestionIndex = 0;
        score = 0;
        timer = new Timer();
    }

    private void displayQuestion() {
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
        questionLabel.setText(currentQuestion.question);
    
        optionsButtonGroup.clearSelection();
    
        Enumeration<AbstractButton> enumeration = optionsButtonGroup.getElements();
        for (String option : currentQuestion.options) {
            JRadioButton optionRadioButton = (JRadioButton) enumeration.nextElement();
            optionRadioButton.setText(option);
            optionRadioButton.setEnabled(true);
        }
    
        startTimer();
    }
    
    private void startTimer() {
        final int[] seconds = {15}; // Set your desired timer duration
        timerLabel.setText("Time remaining: " + seconds + " seconds");
    
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (seconds[0] <= 0) {
                    stopTimer();
                    submitAnswer(0); // Auto-submit if time runs out (option 0)
                }
                timerLabel.setText("Time remaining: " + seconds[0] + " seconds");
                seconds[0]--;
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();
    }

    private void submitAnswer(int selectedOption) {
        stopTimer();
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
        if (selectedOption == currentQuestion.correctOption) {
            score++;
        }

        currentQuestionIndex++;
        if (currentQuestionIndex < quizQuestions.size()) {
            displayQuestion();
        } else {
            showResult();
        }
    }

    private void showResult() {
        questionLabel.setText("Quiz completed! \nYour score:" + score + "/" + quizQuestions.size());
    
        // Hide and disable all radio buttons
        Enumeration<AbstractButton> enumeration = optionsButtonGroup.getElements();
        while (enumeration.hasMoreElements()) {
            AbstractButton radioButton = enumeration.nextElement();
            radioButton.setVisible(false);
            radioButton.setEnabled(false);
        }
    
        timerLabel.setText(""); 
    
        submitButton.setVisible(false);
    
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            task_4_QuizApp quizApp = new task_4_QuizApp();
            quizApp.setTitle("Quiz Application");
            quizApp.setSize(400, 300);
            quizApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Create a panel to hold the components with BoxLayout
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    
            quizApp.questionLabel = new JLabel();
            quizApp.optionsButtonGroup = new ButtonGroup();
            quizApp.timerLabel = new JLabel();
    
            submitButton = new JButton("Submit");
    
            centerPanel.add(Box.createVerticalGlue());
            centerPanel.add(quizApp.timerLabel);
            centerPanel.add(Box.createVerticalStrut(10));
            centerPanel.add(quizApp.questionLabel);
            centerPanel.add(Box.createVerticalStrut(10));
    
            for (int i = 0; i < 4; i++) {
                JRadioButton optionRadioButton = new JRadioButton();
                optionRadioButton.setEnabled(false);
                quizApp.optionsButtonGroup.add(optionRadioButton);
                centerPanel.add(optionRadioButton);
            }
    
            centerPanel.add(Box.createVerticalStrut(10));
            centerPanel.add(submitButton);
    
            centerPanel.add(Box.createVerticalGlue());
    
            JPanel horizontalCenterPanel = new JPanel();
            horizontalCenterPanel.setLayout(new BoxLayout(horizontalCenterPanel, BoxLayout.X_AXIS));
            horizontalCenterPanel.add(Box.createHorizontalGlue());
            horizontalCenterPanel.add(centerPanel);
            horizontalCenterPanel.add(Box.createHorizontalGlue());
    
            quizApp.add(horizontalCenterPanel);
    
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Enumeration<AbstractButton> enumeration = quizApp.optionsButtonGroup.getElements();
                    int selectedOption = 0;
                    while (enumeration.hasMoreElements()) {
                        JRadioButton radioButton = (JRadioButton) enumeration.nextElement();
                        if (radioButton.isSelected()) {
                            selectedOption++;
                            break;
                        }
                        selectedOption++;
                    }
                    quizApp.submitAnswer(selectedOption);
                }
            });
    
            quizApp.displayQuestion();
    
            quizApp.setLocationRelativeTo(null);
            quizApp.setResizable(false);
            quizApp.setVisible(true);
        });
    }
}