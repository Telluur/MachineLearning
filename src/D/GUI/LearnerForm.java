package D.GUI;

import D.GUIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LearnerForm extends JFrame implements ActionListener {
    private JPanel mainView;
    private JTextArea displayTextArea;
    private JTextArea displayInformationArea;
    private JButton set1Button;
    private JButton set2Button;
    private JPanel interactPanel;
    private JScrollPane displayPane;
    private JButton showInteractiveLearnerButton;
    private GUIController controller;

    public LearnerForm(String title, GUIController controller) {
        super(title);
        this.controller = controller;

        set1Button.setText(controller.getSet().getSet1());
        set2Button.setText(controller.getSet().getSet2());

        set1Button.addActionListener(this);
        set2Button.addActionListener(this);
        showInteractiveLearnerButton.addActionListener(this);

        this.setContentPane(this.mainView);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(showInteractiveLearnerButton)) {
            set1Button.setEnabled(false);
            set2Button.setEnabled(false);
            showInteractiveLearnerButton.setEnabled(false);
            displayTextArea.setText("");
            displayInformationArea.setText("Finding learning != static entry");
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    controller.showLearner();
                }
            });
        } else if (e.getSource().equals(set1Button)) {
            setBusy();
            controller.selectSet1();
        } else if (e.getSource().equals(set2Button)) {
            setBusy();
            controller.selectSet2();
        }
    }

    public void updateScreen(String text, String staticPrediction, String learningPrediction, String actualSet, boolean enableLearner) {
        displayTextArea.setText(text);
        displayInformationArea.setText("Static Prediction: " + staticPrediction + "\nLearning Prediction: " + learningPrediction + "\nActual: " + actualSet);
        set1Button.setEnabled(true);
        set2Button.setEnabled(true);
        if (enableLearner) showInteractiveLearnerButton.setEnabled(true);
    }

    public void setBusy() {
        displayInformationArea.setText("Busy predicting next message...");
        set1Button.setEnabled(false);
        set2Button.setEnabled(false);
        showInteractiveLearnerButton.setEnabled(false);
    }

    public void setDone() {
        displayInformationArea.setText("No more messages available");
        displayTextArea.setText("");
        set1Button.setEnabled(false);
        set2Button.setEnabled(false);
        showInteractiveLearnerButton.setEnabled(false);
    }
}
