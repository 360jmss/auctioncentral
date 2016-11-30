package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by simon on 11/29/16.
 */
public class LoginPanel extends JPanel {

    /** The help text */
    private JLabel myIntroText;

    /** The username field */
    private JTextField myUsernameField;

    /** The button to login */
    private JButton myLoginButton;

    public LoginPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        myIntroText = new JLabel("Welcome to AuctionCentral. Please log in \n");
        myIntroText.setPreferredSize(new Dimension(150, 50));
        myIntroText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel loginGroup = new JPanel();
        myUsernameField = new JTextField(20);
        myUsernameField.setPreferredSize(new Dimension(150, 28));
        myLoginButton = new JButton("Login");
        loginGroup.add(myUsernameField, BorderLayout.WEST);
        loginGroup.add(myLoginButton, BorderLayout.EAST);
        loginGroup.setPreferredSize(new Dimension(200, 25));

        add(Box.createVerticalGlue());
        add(myIntroText);
        add(loginGroup);
        add(Box.createVerticalGlue());
    }
}
