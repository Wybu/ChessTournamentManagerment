package GUI;

import javax.swing.*;
import java.awt.*;

public class StatisticsDialog extends JDialog {
    public StatisticsDialog(JFrame parentFrame) {
        super(parentFrame, "Thống kê", true);
        setSize(400, 300);
        setLocationRelativeTo(parentFrame);

        // Tạo giao diện cửa sổ thống kê
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Thống kê");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Thêm các thành phần giao diện và logic thống kê vào đây

        setContentPane(mainPanel);
    }
}