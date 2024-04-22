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
    private JButton selectCategoryButton;

    public UpdateResultsDialog(JFrame parent) {
        super(parent, "Cập nhật kết quả", true);
        setSize(800, 600);

        try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            System.out.println("Kết nối thành công tới MySQL!");
        } catch (SQLException ex) {
            System.out.println("Lỗi kết nối tới MySQL: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        roundComboBox = new JComboBox<>();
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

        matchComboBox = new JComboBox<>();
        matchComboBox.setVisible(false);

        player1Label = new JLabel();
        player2Label = new JLabel();
        resultTextField = new JTextField();
        updateButton = new JButton("Cập nhật");

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.add(roundComboBox);
        northPanel.add(matchComboBox);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(3, 3));
        centerPanel.add(player1Label);
        centerPanel.add(player2Label);
        centerPanel.add(new JLabel("Kết quả:"));
        centerPanel.add(resultTextField);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.add(updateButton);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        add(mainPanel);

        selectCategoryButton = new JButton("Select Category");
        selectCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SelectCategory().setVisible(true);
            }
        });
        roundComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateMatchComboBox();
                    matchComboBox.setVisible(true);
                    northPanel.revalidate();
                    northPanel.repaint();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMatchResult();
            }
        });
    }

    private void updateMatchComboBox() {
        matchComboBox.removeAllItems();
        String selectedRound = (String) roundComboBox.getSelectedItem();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM updateresult.matches WHERE round = ?");
            pstmt.setString(1, selectedRound);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                matchComboBox.addItem(rs.getString("MatchID"));
            }
            if (matchComboBox.getItemCount() > 0) {
                matchComboBox.setSelectedIndex(0);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu cặp đấu từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateMatchResult() {
        String selectedMatchID = (String) matchComboBox.getSelectedItem();
        String result = resultTextField.getText();
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
// Path: src/GUI/StatisticsDialog.java