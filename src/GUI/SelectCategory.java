package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SelectCategory extends JFrame {
    private Connection connection;
    private JButton selectCategoryButton;
    private JComboBox<String> categoryComboBox;

    public SelectCategory() {
        // Khởi tạo các thành phần giao diện
        selectCategoryButton = new JButton("Chọn loại hình thi đấu");
        categoryComboBox = new JComboBox<>();

        // Kết nối đến cơ sở dữ liệu
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/updateresult", "root", "anhquyqt03");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Thoát khỏi ứng dụng nếu không kết nối được
        }

        // Thêm ActionListener cho button
        selectCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCategoryList();
            }
        });

        // Khởi tạo giao diện
        JPanel panel = new JPanel();
        panel.add(selectCategoryButton);
        panel.add(categoryComboBox);
        add(panel);

        // Cài đặt chung cho JFrame
        setTitle("Chọn loại hình thi đấu");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadCategoryList() {
        categoryComboBox.removeAllItems();
        try {
            String desiredRoundValue = getDesiredRoundValue();
            ResultSet rs = selectCategoryFromMatches(connection, desiredRoundValue);
            while (rs.next()) {
                categoryComboBox.addItem(rs.getString("category"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu loại hình thi đấu từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private ResultSet selectCategoryFromMatches(Connection connection, String round) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("SELECT DISTINCT category FROM updateresult.matches WHERE round = ?");
        pstmt.setString(1, round);
        return pstmt.executeQuery();
    }

    // Thay thế phương thức này bằng phương thức phù hợp để lấy giá trị vòng đấu mong muốn
    private String getDesiredRoundValue() {
        // Thay thế giá trị này bằng giá trị vòng đấu mong muốn
        return "desired_round_value";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SelectCategory();
            }
        });
    }
}
