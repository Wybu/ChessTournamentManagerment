package GUI;

import javax.swing.*;
import java.awt.*;

public class PairPlayersDialog extends JDialog {
    public PairPlayersDialog(JFrame parentFrame) {
        super(parentFrame, "Xếp cặp thi đấu", true);
        setSize(400, 300);
        setLocationRelativeTo(parentFrame);

        // Tạo giao diện cửa sổ xếp cặp thi đấu
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Xếp cặp thi đấu");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Thêm các thành phần giao diện và logic xếp cặp thi đấu vào đây

        setContentPane(mainPanel);
    }
}