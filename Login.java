
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class Login extends JPanel {

    private GridBagConstraints gbc = new GridBagConstraints();
    private JTextField username;
    private JPasswordField password;
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
        title.setBorder(new EmptyBorder(15,5,8,15));
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
        JLabel usernameTxt = new JLabel("Username: ");
        this.username = new JTextField();
        username.setPreferredSize(new Dimension(150, 28));
        username.setBorder(new BevelBorder(BevelBorder.LOWERED));
        centerP.add(usernameTxt);
        centerP.add(username);
        JLabel passwordTxt = new JLabel("Password: ");
        this.password = new JPasswordField();
        password.setBorder(new BevelBorder(BevelBorder.LOWERED));
        password.setPreferredSize(new Dimension(150, 28));
        JButton usernameBtn = new JButton("Login");
        usernameBtn.setPreferredSize(new Dimension(150, 28));
        usernameBtn.setBorder(new BevelBorder(BevelBorder.RAISED));
        usernameBtn.setActionCommand("Login");
        centerP.add(passwordTxt);
        centerP.add(password);
        centerP.add(usernameBtn);

        //create error textfield
        this.error = new JLabel("This is the error Text");
        error.setForeground(Color.RED);
        error.setBorder(new EmptyBorder(15, 0, 15, 15));
        centerP.add(error);

        //create Text fields and labels for user registration
        //layout components
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(usernameTxt)
                                .addComponent(passwordTxt))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(error))
                        .addComponent(usernameBtn)
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

                }
            }
        }
    }
}
