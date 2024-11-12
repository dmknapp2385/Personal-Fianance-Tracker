
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel {

    public JTextField username;
    public JPasswordField password;

    public Login() {
        this.setLayout(new BorderLayout());

        //create header text label
        JLabel title = new JLabel("Login or Register if new user");
        this.add(title, BorderLayout.NORTH);

        //center Panel
        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        this.add(centerPanel, BorderLayout.CENTER);

        //creat login panel
        JPanel login = new JPanel();
        centerPanel.add(login);

        //create text field and label
        JLabel usernameTxt = new JLabel("Username: ");
        login.add(usernameTxt);
        this.username = new JTextField();
        login.add(username);
        JLabel passwordTxt = new JLabel("Password: ");
        login.add(passwordTxt);
        this.password = new JPasswordField();
        login.add(password);
        JButton usernameBtn = new JButton("Login");
        usernameBtn.setActionCommand("Login");
        login.add(usernameBtn);
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

                }
            }
        }
    }
}
