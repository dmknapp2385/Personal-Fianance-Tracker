
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

    private static final long serialVersionUID = 1L;
	private JTextField username;
    private JPasswordField password;
    private JPasswordField regPasswordField;
    private JPasswordField pwConfirm;
    private JTextField fNameField;
    private JTextField lNameField;
    private JTextField regUsernameField;
    private JTextField emailField;
    private JLabel error;
    
    /**
     * description:
     * 	constructor that sets up the base for
     * 	the login page. uses components such as JLabels, 
     * 	JPanels and Buttons to provide a basic structure
     * 	to the user's login page. Accounts for both an existing user's login
     * 	(persisting data) and a new user's registration. 
     */

    public Login() {
        //set layout for panel
        this.setLayout(new BorderLayout());
        this.setSize(600, 550);
        this.setLayout(new BorderLayout());
        Color color = new Color(244, 243, 239);
        this.setBackground(color);

        //create header text label
        JLabel title = new JLabel("Login or Register if you are a new user");
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

        //create dimension for textfields and buttons
        Dimension fieldDimension = new Dimension(200, 28);
        Dimension btDimension = new Dimension(125, 25);

        //create text fields and labels for login
        //username
        JLabel usernameTxt = new JLabel("Username: ");
        this.username = new JTextField();
        username.setPreferredSize(fieldDimension);
        username.setBorder(new BevelBorder(BevelBorder.LOWERED));
        centerP.add(usernameTxt);
        centerP.add(username);

        //password
        JLabel passwordTxt = new JLabel("Password: ");
        this.password = new JPasswordField();
        password.setBorder(new BevelBorder(BevelBorder.LOWERED));
        password.setPreferredSize(fieldDimension);

        //login button
        JButton usernameBtn = new JButton("Login");
        usernameBtn.setPreferredSize(btDimension);
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
        fNameField.setPreferredSize(fieldDimension);
        fNameField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        centerP.add(fName);
        centerP.add(fNameField);

        //last name
        JLabel lName = new JLabel("Last Name: ");
        this.lNameField = new JTextField();
        lNameField.setPreferredSize(fieldDimension);
        lNameField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        centerP.add(lName);
        centerP.add(lNameField);

        //username
        JLabel regUsername = new JLabel("Username: ");
        this.regUsernameField = new JTextField();
        regUsernameField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        regUsernameField.setPreferredSize(fieldDimension);
        centerP.add(regUsername);
        centerP.add(regUsernameField);

        //email
        JLabel email = new JLabel("Email: ");
        this.emailField = new JTextField();
        emailField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        emailField.setPreferredSize(fieldDimension);
        centerP.add(email);
        centerP.add(emailField);

        //password
        JLabel regPassword = new JLabel("Password: ");
        this.regPasswordField = new JPasswordField();
        regPasswordField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        regPasswordField.setPreferredSize(fieldDimension);
        centerP.add(regPassword);
        centerP.add(regPasswordField);

        //confirm password
        JLabel confirmpw = new JLabel("Confirm Password: ");
        this.pwConfirm = new JPasswordField();
        pwConfirm.setBorder(new BevelBorder(BevelBorder.LOWERED));
        pwConfirm.setPreferredSize(fieldDimension);
        centerP.add(confirmpw);
        centerP.add(pwConfirm);

        //add Register button
        JButton registerBtn = new JButton("Register");
        registerBtn.setPreferredSize(btDimension);
        registerBtn.setBorder(new BevelBorder(BevelBorder.RAISED));
        registerBtn.setActionCommand("Register");
        registerBtn.addActionListener(new ButtonActionListener());
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
    /**
     * description:
     * 	private inner listener class implemented for a 
     * 	button's functionality, when clicked. More specifically,
     * 	the login button and register button. 
     * 
     */

    private class ButtonActionListener implements ActionListener {
    	
    	/**
    	 * description:
    	 * 	invoked when action occurs; in this case,
    	 * 	when a button is clicked. 
    	 * @param: e= ActionEvent that indicates a component-defined
    	 * action has occured. 
    	 */

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Login")) {
                //reset error text if set by previous attempt
                error.setText("");

                //get login fields 
                char[] passwordChars = password.getPassword();
                String pw = new String(passwordChars);
                String inputUser = username.getText();

                //reset textfields
                password.setText("");
                username.setText("");
                try {
                    View.controller.login(inputUser, pw);
                } catch (Exception excpetion) {
                    error.setText("Invalid login");
                }

            } else {
                //reset error text
                error.setText("");

                //get input fields
                char[] regChars = regPasswordField.getPassword();
                char[] confirmChars = pwConfirm.getPassword();
                String regPw = new String(regChars);
                String confirmPw = new String(confirmChars);
                String fn = fNameField.getText();
                String ln = lNameField.getText();
                String em = emailField.getText();
                String un = regUsernameField.getText();

                if (!regPw.equals(confirmPw)) {
                    error.setText("Passwords Must Match!");
                } else if (fn.equals("") || ln.equals("") || em.equals("")
                        || un.equals("") || regPw.equals("")) {
                    error.setText("No Empty fields");
                } else {
                    try {
                        //reset fields
                        fNameField.setText("");
                        lNameField.setText("");
                        emailField.setText("");
                        regUsernameField.setText("");
                        regPasswordField.setText("");
                        pwConfirm.setText("");
                        View.controller.register(fn, ln, em, un, regPw);
                    } catch (Exception exception) {
                        error.setText("User Already Exists");
                    }
                }

            }
        }
    }
}