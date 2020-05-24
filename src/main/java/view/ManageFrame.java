package view;

import view.medicine.MedicineFrame;
import view.patient.PatientFrame;
import view.prescription.PrescriptionFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class ManageFrame extends JFrame {


    public ManageFrame() {
        setVisible(true);
        setTitle("Manage");
        setResizable(false);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = 450;
        int windowsHeight = 350;
        setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        JLabel jl3 = new JLabel(new ImageIcon("img/title.bmp"));
        jl3.setBounds(20, 20, 150, 150);
        contentPane.add(jl3);


        JLabel label = new JLabel("浙江省缙云县中医药研究所");
        label.setFont(new Font("", Font.PLAIN, 17));
        label.setBounds(118, 20, 300, 39);
        contentPane.add(label);

        JButton button = new JButton("病人管理");
        button.addActionListener(e -> {
            new PatientFrame().setVisible(true);
            ManageFrame.this.setVisible(false);
        });
        button.setBounds(150, 85, 100, 44);
        contentPane.add(button);

        JButton button1 = new JButton("仓库管理");
        button1.addActionListener(e -> {
            new MedicineFrame().setVisible(true);
            ManageFrame.this.setVisible(false);
        });
        button1.setBounds(150, 150, 100, 44);
        contentPane.add(button1);

        JButton button2 = new JButton("药方管理");
        button2.addActionListener(e -> {
            new PrescriptionFrame().setVisible(true);
            ManageFrame.this.setVisible(false);
        });
        button2.setBounds(150, 215, 100, 44);
        contentPane.add(button2);
    }
}
