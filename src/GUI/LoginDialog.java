package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/updateresult";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "anhquyqt03";

    private Connection connection;

    public LoginDialog() {
        super((JFrame) null, "Đăng nhập", true);
        setSize(300, 200);

        try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Đăng nhập");

        JPanel mainPanel = new JPanel(new GridLayout(3, 2));
        mainPanel.add(new JLabel("Tên đăng nhập:"));
        mainPanel.add(usernameField);
        mainPanel.add(new JLabel("Mật khẩu:"));
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        add(mainPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (checkLogin(username, password)) {
                    new MainFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Tên đăng nhập hoặc mật khẩu không đúng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean checkLogin(String username, String password) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM updateresult.users WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi kiểm tra thông tin đăng nhập.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginDialog().setVisible(true);
            }
        });
    }
}