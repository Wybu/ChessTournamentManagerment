package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateResultsDialog extends JDialog {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/updateresult";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "anhquyqt03";

    private Connection connection;
    private JComboBox<String> roundComboBox;
    private JComboBox<String> matchComboBox;
    private JLabel player1Label, player2Label;
    private JTextField resultTextField;
    private JButton updateButton;

    public UpdateResultsDialog(JFrame parent) {
        super(parent, "Cập nhật kết quả", true);
        setSize(800, 600);

        // Kết nối tới MySQL
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            System.out.println("Kết nối thành công tới MySQL!");
        } catch (SQLException ex) {
            System.out.println("Lỗi kết nối tới MySQL: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose(); // Đóng dialog nếu không thể kết nối tới cơ sở dữ liệu
            return;
        }

        // Tạo combobox chọn vòng đấu
        roundComboBox = new JComboBox<>();
        // Lấy dữ liệu vòng đấu từ database và thêm vào combobox
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM updateresult.round");
            while (rs.next()) {
                roundComboBox.addItem(rs.getString("RoundName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu vòng đấu từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Tạo combobox chọn cặp đấu
        matchComboBox = new JComboBox<>();
        // Lấy dữ liệu cặp đấu từ database và thêm vào combobox

        // Tạo label hiển thị thông tin kì thủ
        player1Label = new JLabel();
        player2Label = new JLabel();

        // Tạo textfield nhập kết quả
        resultTextField = new JTextField();

        // Tạo button cập nhật
        updateButton = new JButton("Cập nhật");

        // Thêm các component vào dialog
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.add(roundComboBox);
        northPanel.add(matchComboBox);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(3, 2));
        centerPanel.add(player1Label);
        centerPanel.add(player2Label);
        centerPanel.add(new JLabel("Kết quả:"));
        centerPanel.add(resultTextField);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.add(updateButton);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        add(mainPanel);

        roundComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateMatchComboBox();
                }
            }
        });

        // Thêm listener cho combobox chọn cặp đấu
        matchComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Lấy thông tin 2 kì thủ và hiển thị lên label
                    updatePlayersInfo();
                }
            }
        });

        // Thêm listener cho button cập nhật
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cập nhật kết quả trận đấu
                updateMatchResult();
            }
        });
    }

    // Lấy dữ liệu cặp đấu từ database và thêm vào combobox matchComboBox
    private void updateMatchComboBox() {
        // Xóa dữ liệu cũ trong combobox

        matchComboBox.removeAllItems();
        // Lấy dữ liệu cặp đấu từ database và thêm vào combobox
        String selectedRound = (String) roundComboBox.getSelectedItem();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM updateresult.matches WHERE round = ?");
            pstmt.setString(1, selectedRound);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                matchComboBox.addItem(rs.getString("MatchID"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu cặp đấu từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lấy thông tin 2 kì thủ và hiển thị lên label
     private void updatePlayersInfo() {
        String selectedMatchID = (String) matchComboBox.getSelectedItem();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM updateresult.matches WHERE MatchID = ?");
            pstmt.setString(1, selectedMatchID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String player1ID = rs.getString("Player1");
                String player2ID = rs.getString("Player2");
                // Lấy thông tin của 2 kì thủ từ database và hiển thị lên label
                player1Label.setText("Player 1 ID: " + player1ID);
                player2Label.setText("Player 2 ID: " + player2ID);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin kì thủ từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Cập nhật kết quả trận đấu
    private void updateMatchResult() {
        String selectedMatchID = (String) matchComboBox.getSelectedItem();
        String result = resultTextField.getText();
        // Thực hiện cập nhật kết quả vào cơ sở dữ liệu
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE updateresult.playedmatch SET matchScore = ? WHERE MatchID = ?");
            pstmt.setString(1, result);
            pstmt.setString(2, selectedMatchID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật kết quả thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Không thể cập nhật kết quả.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật kết quả vào cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                UpdateResultsDialog dialog = new UpdateResultsDialog(frame);
                dialog.setVisible(true);
            }
        });
    }
}
