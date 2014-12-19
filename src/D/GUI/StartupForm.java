package D.GUI;

import D.GUIController;
import D.Set;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartupForm extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JRadioButton spamHamRadioButton;
    private JRadioButton maleFemaleRadioButton;
    private JButton goButton;
    private JLabel statusLabel;
    private ButtonGroup sets;
    private GUIController controller;

    public StartupForm(String title, GUIController controller) {
        super(title);
        this.controller = controller;

        sets = new ButtonGroup();
        sets.add(spamHamRadioButton);
        sets.add(maleFemaleRadioButton);
        spamHamRadioButton.setText(Set.HAM_SPAM.getDisplay());
        maleFemaleRadioButton.setText(Set.MALE_FEMALE.getDisplay());

        goButton.addActionListener(this);

        this.setContentPane(this.mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedSet = Util.getSelectedButtonText(sets);
        if (selectedSet == null) {
            statusLabel.setText("Select a set.");
        } else {
            if (selectedSet.equals(Set.HAM_SPAM.getDisplay())) controller.setSet(Set.HAM_SPAM);
            if (selectedSet.equals(Set.MALE_FEMALE.getDisplay())) controller.setSet(Set.MALE_FEMALE);
            statusLabel.setText("Training classifier using the train data.");
            goButton.setEnabled(false);
            controller.startInteractiveLearner();
        }
    }
}
