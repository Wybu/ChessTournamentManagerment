package GUI;

import javax.swing.*;
import java.awt.*;

public class ManagePlayersDialog extends JDialog {
    public ManagePlayersDialog(JFrame parentFrame) {
        super(parentFrame, "Quản lý kỳ thủ", true);
        setSize(400, 300);
        setLocationRelativeTo(parentFrame);

        // Tạo giao diện cửa sổ quản lý kỳ thủ
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Quản lý thông tin kỳ thủ");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Thêm các thành phần giao diện và logic quản lý kỳ thủ vào đây

        setContentPane(mainPanel);
    }
}