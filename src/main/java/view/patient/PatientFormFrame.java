package view.patient;


import model.Patient;
import service.PatientService;
import utils.PinyinUtil;
import utils.StringUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PatientFormFrame extends JFrame {
    private final PatientService patientService = new PatientService();

    private final JTextField textField;
    private final JTextField textField_1;
    private final JTextField textField_2;
    private final JTextField textField_5;

    private boolean isMan = true;

    public PatientFormFrame(boolean isUpdate) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = 400;
        int windowsHeight = 550;
        setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel(isUpdate ? "更新信息" : "添加病人");
        label.setFont(new Font("", Font.PLAIN, 17));
        label.setBounds(118, 20, 78, 39);
        contentPane.add(label);

        JLabel label_1 = new JLabel("姓名");
        label_1.setBounds(23, 71, 40, 15);
        contentPane.add(label_1);

        textField = new JTextField();
        textField.setBounds(87, 68, 250, 21);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel label_2 = new JLabel("地址");
        label_2.setBounds(23, 128, 40, 15);
        contentPane.add(label_2);

        textField_1 = new JTextField();
        textField_1.setBounds(87, 125, 250, 21);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        JLabel label_3 = new JLabel("电话");
        label_3.setBounds(23, 185, 40, 15);
        contentPane.add(label_3);

        textField_2 = new JTextField();
        textField_2.setBounds(87, 182, 250, 21);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        JLabel label_5 = new JLabel("性别");
        label_5.setBounds(23,  242, 50, 15);
        contentPane.add(label_5);

        ButtonGroup g = new ButtonGroup();

        JRadioButton b1 = new JRadioButton("男");
        b1.setBounds(87, 239, 54, 21);
        b1.addActionListener(arg0 -> isMan = true);
        contentPane.add(b1);

        JRadioButton b2 = new JRadioButton("女");
        b2.setBounds(151, 239, 100, 21);
        b2.addActionListener(arg0 -> isMan = false);
        contentPane.add(b2);
        g.add(b1);
        g.add(b2);

        JLabel label_6 = new JLabel("年龄");
        label_6.setBounds(23, 299, 32, 15);
        contentPane.add(label_6);

        textField_5 = new JTextField();
        textField_5.setBounds(87, 296, 250, 21);
        contentPane.add(textField_5);
        textField_5.setColumns(10);

        JButton button = new JButton(isUpdate ? "更新" : "添加");
        button.addActionListener(e -> {
            if (isUpdate) {
                update();
            } else {
                add();
            }
        });
        button.setBounds(37, 353, 93, 23);
        contentPane.add(button);

        JButton button_1 = new JButton("返回");
        button_1.addActionListener(e -> {
            PatientFrame.patientSelected = null;
            dispose();
        });
        button_1.setBounds(169, 353, 93, 23);
        contentPane.add(button_1);

        //do update
        if (isUpdate) {
            textField.setText(PatientFrame.patientSelected.getName());
            textField_1.setText(PatientFrame.patientSelected.getAddress());
            textField_2.setText(PatientFrame.patientSelected.getPhone());
            if (PatientFrame.patientSelected.getGender() == 0) {
                b1.doClick();
            } else {
                b2.doClick();
            }
            textField_5.setText(String.valueOf(PatientFrame.patientSelected.getAge()));
        } else {
            b1.doClick();
        }
    }

    private void add() {
        Patient patient = new Patient();
        if (StringUtil.isNotBlank(textField.getText())) {
            patient.setName(textField.getText());
            patient.setPinyin(new PinyinUtil().changeToGetShortPinYin(textField.getText()));
        }
        if (StringUtil.isNotBlank(textField_1.getText())) {
            patient.setAddress(textField_1.getText());
        }
        if (StringUtil.isNotBlank(textField_2.getText())) {
            patient.setPhone(textField_2.getText());
        }
        patient.setGender(isMan ? 0 : 1);
        int age;
        if (StringUtil.isNotBlank(textField_5.getText())) {
            try {
                age = Integer.parseInt(textField_5.getText());
                patient.setAge(age);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "输入数字的年龄");
            }
        }
        boolean s = patientService.add(patient);
        if (s) {
            JOptionPane.showMessageDialog(null, "添加成功");
            textField.setText("");
            textField_1.setText("");
            textField_2.setText("");
            textField_5.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "添加失败");
        }

    }

    private void update() {
        if (StringUtil.isNotBlank(textField.getText())) {
            PatientFrame.patientSelected.setName(textField.getText());
        }
        if (StringUtil.isNotBlank(textField_1.getText())) {
            PatientFrame.patientSelected.setAddress(textField_1.getText());
        }
        if (StringUtil.isNotBlank(textField_2.getText())) {
            PatientFrame.patientSelected.setPhone(textField_2.getText());
        }
        PatientFrame.patientSelected.setGender(isMan ? 0 : 1);
        if (StringUtil.isNotBlank(textField_5.getText())) {
            int age;
            try {
                age = Integer.parseInt(textField_5.getText());
                PatientFrame.patientSelected.setAge(age);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "输入数字的年龄");
            }

        }

        boolean s = patientService.update(PatientFrame.patientSelected);
        if (s) {
            JOptionPane.showMessageDialog(null, "更新成功");
            textField.setText("");
            textField_1.setText("");
            textField_2.setText("");
            textField_5.setText("");
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "更新失败");
        }


    }

}
