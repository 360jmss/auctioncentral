package gui;

import model.Calendar;
import model.User;
import model.UserRepo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

/**
 * Created by simon on 11/29/16.
 */
public class LoginPanel extends JPanel {

    /** The user repo */
    private UserRepo myRepo;

    /** The help text */
    private JLabel myIntroText;

    /** The username field */
    private JTextField myUsernameField;

    /** The button to login */
    private JButton myLoginButton;

    public LoginPanel(UserRepo theRepo) {
        myRepo = theRepo;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        myIntroText = new JLabel("Welcome to AuctionCentral. Please log in \n");
        myIntroText.setPreferredSize(new Dimension(150, 50));
        myIntroText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel loginGroup = new JPanel();
        LoginListener listener = new LoginListener();
        myUsernameField = new JTextField(20);
        myUsernameField.setPreferredSize(new Dimension(150, 28));
        myUsernameField.addActionListener(listener);
        myLoginButton = new JButton("Login");
        myLoginButton.addActionListener(listener);
        loginGroup.add(myUsernameField, BorderLayout.WEST);
        loginGroup.add(myLoginButton, BorderLayout.EAST);
        loginGroup.setPreferredSize(new Dimension(200, 25));

        add(Box.createVerticalGlue());
        add(myIntroText);
        add(loginGroup);
        add(Box.createVerticalGlue());
    }

    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            User u = myRepo.loginUser(myUsernameField.getText());
            if(u != null) {
                myIntroText.setText("Welcome to AuctionCentral. Please log in \n");
            } else {
                myIntroText.setText("User does not exist. Please try again.");
            }
        }
    }
}
