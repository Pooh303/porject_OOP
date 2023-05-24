
//Import section
import java.awt.event.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class loginHandler extends loginGUI implements ActionListener {

    private MDI mdi;
    private File logs;
    private String username = "admin";
    private String password = "1234";
    Connection conn = null;
    PreparedStatement stmt, stmt2 = null;
    ResultSet us, ps = null;

    public loginHandler() {
//        viewlogin = new loginGUI();
        CheckServer();
        init();

    }

    public void init() {
        getLoginBtn().addActionListener(this);

        KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        getLoginBtn().registerKeyboardAction(this, "login", enterKey, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void CheckServer() {
        try {
            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "");

            // Prepare the statement with parameter placeholder
            String sqluser = "SELECT Username FROM enter WHERE ID = ?"; // Assuming ID is the primary key column
            String sqlpass = "SELECT Password FROM enter WHERE ID = ?";
            stmt = conn.prepareStatement(sqluser);
            stmt2 = conn.prepareStatement(sqlpass);

            // Set the parameter value
            int id = 1; // Replace with the appropriate ID value
            stmt.setInt(1, id);
            stmt2.setInt(1, id);

            // Execute the query
            us = stmt.executeQuery();
            ps = stmt2.executeQuery();
            // Process the result set
            if (us.next() & ps.next()) {
                username = us.getString("Username");
                password = ps.getString("Password");
                // See username and password
//                System.out.println(username);
//                System.out.println(password);
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    public String getusername() {
        String temp = getTxt1().getText();
        return temp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userinput = getTxt1().getText();
        String passwordinput = getTxt2().getText();
        String sum = userinput + "\t" + passwordinput;
        boolean match = false;
        if (e.getSource().equals(getLoginBtn())) {
            CheckServer();
            if (getTxt1().getText().isEmpty() & getTxt2().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your username and password.", "Error", 0);
            } else if (getTxt1().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your username.", "Error", 0);
            } else if (getTxt2().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your password.", "Error", 0);
            } else if (username == null & password == null) {
                JOptionPane.showMessageDialog(null, "Server is offline", "Error", 0);
            } else {
                try {
                    while (!sum.equals("")) {
                        if (sum.equals(username + "\t" + password)) {
                            match = true;
                            break;
                        } else {
                            match = false;
                            break;
                        }
                    }
                    if (match) {
                        JOptionPane.showMessageDialog(null, "Login success!", "", 1);
                        MDI mi = new MDI();
//                        mi.getFr().dispose();
                        REALMAIN rm = new REALMAIN();
                        rm.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong username or password.", "Error", 0);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setBounds(800, 280, 300, 500);
            frame.setResizable(false);
            frame.add(new loginHandler());
            frame.setVisible(true);
        });
    }
}
