
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class Login extends JPanel {

    private JTextField username;
    private JPasswordField password;
    private JPasswordField regPasswordField;
    private JPasswordField pwConfirm;
    private JTextField fNameField;
    private JTextField lNameField;
    private JTextField regUsernameField;
    private JTextField emailField;
    private JLabel error;

    public Login() {
        //set layout for panel
        this.setLayout(new BorderLayout());
        this.setSize(600, 550);
        this.setLayout(new BorderLayout());
        Color color = new Color(217, 214, 176);
        this.setBackground(color);

        //create header text label
        JLabel title = new JLabel("Login or Register if  you are a new user");
        title.setFont(new Font("Calibri", Font.BOLD, 20));
        title.setBorder(new EmptyBorder(15, 5, 8, 15));
        this.add(title, BorderLayout.NORTH);

        //Create new panel and add to center
        JPanel centerP = new JPanel();
        centerP.setBackground(color);
        this.add(centerP, BorderLayout.CENTER);
        GroupLayout layout = new GroupLayout(centerP);
        centerP.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        //create text fields and labels for login
        //username
        JLabel usernameTxt = new JLabel("Username: ");
        this.username = new JTextField();
        username.setPreferredSize(new Dimension(150, 28));
        username.setBorder(new BevelBorder(BevelBorder.LOWERED));
        centerP.add(usernameTxt);
        centerP.add(username);

        //password
        JLabel passwordTxt = new JLabel("Password: ");
        this.password = new JPasswordField();
        password.setBorder(new BevelBorder(BevelBorder.LOWERED));
        password.setPreferredSize(new Dimension(150, 28));

        //login button
        JButton usernameBtn = new JButton("Login");
        usernameBtn.setPreferredSize(new Dimension(150, 28));
        usernameBtn.setBorder(new BevelBorder(BevelBorder.RAISED));
        usernameBtn.setActionCommand("Login");
        centerP.add(passwordTxt);
        centerP.add(password);
        centerP.add(usernameBtn);

        //create error textfield
        this.error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBorder(new EmptyBorder(15, 0, 15, 15));
        centerP.add(error);

        //create Register Label
        JLabel register = new JLabel("Register");
        register.setForeground(Color.BLUE);
        register.setBorder(new EmptyBorder(100, 0, 15, 15));
        centerP.add(register);

        //create Text fields and labels for user registration
        //firstName
        JLabel fName = new JLabel("First Name: ");
        this.fNameField = new JTextField();
        fNameField.setPreferredSize(new Dimension(150, 28));
        fNameField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        centerP.add(fName);
        centerP.add(fNameField);

        //last name
        JLabel lName = new JLabel("Last Name: ");
        this.lNameField = new JTextField();
        lNameField.setPreferredSize(new Dimension(150, 28));
        lNameField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        centerP.add(lName);
        centerP.add(lNameField);

        //username
        JLabel regUsername = new JLabel("Username: ");
        this.regUsernameField = new JTextField();
        regUsernameField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        regUsernameField.setPreferredSize(new Dimension(150, 28));
        centerP.add(regUsername);
        centerP.add(regUsernameField);

        //email
        JLabel email = new JLabel("Email: ");
        this.emailField = new JTextField();
        emailField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        emailField.setPreferredSize(new Dimension(150, 28));
        centerP.add(email);
        centerP.add(emailField);

        //password
        JLabel regPassword = new JLabel("Password: ");
        this.regPasswordField = new JPasswordField();
        regPasswordField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        regPasswordField.setPreferredSize(new Dimension(150, 28));
        centerP.add(regPassword);
        centerP.add(regPasswordField);

        //confirm password
        JLabel confirmpw = new JLabel("Confirm Password: ");
        this.pwConfirm = new JPasswordField();
        pwConfirm.setBorder(new BevelBorder(BevelBorder.LOWERED));
        pwConfirm.setPreferredSize(new Dimension(150, 28));
        centerP.add(confirmpw);
        centerP.add(pwConfirm);

        //add Register button
        JButton registerBtn = new JButton("Register");
        registerBtn.setPreferredSize(new Dimension(150, 28));
        registerBtn.setBorder(new BevelBorder(BevelBorder.RAISED));
        registerBtn.setActionCommand("Register");
        centerP.add(registerBtn);

        //layout components
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(usernameTxt)
                                .addComponent(passwordTxt)
                                .addComponent(fName)
                                .addComponent(lName)
                                .addComponent(regUsername)
                                .addComponent(email)
                                .addComponent(regPassword)
                                .addComponent(confirmpw))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(regUsernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(error)
                                .addComponent(register)
                                .addComponent(emailField)
                                .addComponent(regPasswordField)
                                .addComponent(pwConfirm))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(usernameBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(registerBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(usernameTxt)
                                .addComponent(username))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
                                .addComponent(passwordTxt)
                                .addComponent(password)
                                .addComponent(usernameBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(error)
                        .addComponent(register)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(fName)
                                .addComponent(fNameField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lName)
                                .addComponent(lNameField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(regUsername)
                                .addComponent(regUsernameField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(email)
                                .addComponent(emailField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(regPassword)
                                .addComponent(regPasswordField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
                                .addComponent(confirmpw)
                                .addComponent(pwConfirm)
                                .addComponent(registerBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        usernameBtn.addActionListener(new ButtonActionListener());
    }

    private class ButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Login")) {
                char[] passwordChars = password.getPassword();
                String pw = new String(passwordChars);
                try {
                    View.controller.login(username.getText(), pw);
                } catch (Exception excpetion) {
                    error.setText("Invalid login");
                }
            } else {
                char[] regChars = regPasswordField.getPassword();
                char[] confirmChars = pwConfirm.getPassword();
                String regPw = new String(regChars);
                String confirmPw = new String(confirmChars);
                if (!regPw.equals(confirmPw)) {
                    error.setText("Passwords Must Match!");
                } else {
                    try {
                        //call controller register method here.
                    } catch (Exception exception) {
                    }
                }

            }
        }
    }
}
