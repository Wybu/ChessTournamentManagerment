package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class MainFrame extends JFrame {

    private JTextArea tournamentInfo;
    private JButton managePlayersButton, updateResultsButton, pairPlayersButton, statisticsButton;

    public MainFrame() {
        super("Giao diện chính");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        tournamentInfo = new JTextArea();
        managePlayersButton = new JButton("Quản lý thông tin kì thủ");
        updateResultsButton = new JButton("Cập nhật kết quả");
        pairPlayersButton = new JButton("Xếp cặp thi đấu");
        statisticsButton = new JButton("Thống kê");

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JLabel("Thông tin về giải đấu"), BorderLayout.NORTH);
        mainPanel.add(tournamentInfo, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(managePlayersButton);
        buttonPanel.add(updateResultsButton);
        buttonPanel.add(pairPlayersButton);
        buttonPanel.add(statisticsButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        managePlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new ManagePlayersDialog(MainFrame.this).setVisible(true);
            }
        });

        updateResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateResultsDialog(MainFrame.this).setVisible(true);
            }
        });

        pairPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PairPlayersDialog(MainFrame.this).setVisible(true);
            }
        });

        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StatisticsDialog(MainFrame.this).setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}