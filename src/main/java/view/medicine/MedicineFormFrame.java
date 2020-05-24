package view.medicine;

import model.Medicine;
import service.MedicineService;
import utils.PinyinUtil;
import utils.StringUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;

public class MedicineFormFrame extends JFrame {
    private MedicineService medicineService = new MedicineService();

    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2, textField_3, textField_4;

    public MedicineFormFrame(boolean isUpdate) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = 314;
        int windowsHeight = isUpdate ? 450 : 390;
        setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel(isUpdate ? "更新药物数据" : "添加新药物");
        label.setFont(new Font("", Font.PLAIN, 17));
        label.setBounds(118, 20, 150, 39);
        contentPane.add(label);

        JLabel label_1 = new JLabel("药物名称");
        label_1.setBounds(23, 71, 100, 15);
        contentPane.add(label_1);

        textField = new JTextField();
        textField.setBounds(125, 68, 155, 30);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel label_2 = new JLabel(isUpdate ? "现有药物数量 kg" : "药物数量 kg");
        label_2.setBounds(23, 128, 100, 15);
        contentPane.add(label_2);

        textField_1 = new JTextField();
        textField_1.setBounds(125, 125, 155, 30);
        contentPane.add(textField_1);
        textField_1.setColumns(10);


        JButton button = new JButton(isUpdate ? "更新" : "添加");
        button.addActionListener(e -> {
            if (isUpdate) {
                update();
            } else {
                add();
            }
        });
        button.setBounds(37, isUpdate ? 350 : 300, 93, 23);
        contentPane.add(button);

        JButton button_1 = new JButton("返回");
        button_1.addActionListener(e -> {
            MedicineFrame.medicineSelected = null;
            dispose();
        });
        button_1.setBounds(169, isUpdate ? 350 : 300, 93, 23);
        contentPane.add(button_1);

        //do update
        if (isUpdate) {
            textField.setText(MedicineFrame.medicineSelected.getName());
            textField_1.setText(String.valueOf(MedicineFrame.medicineSelected.getNum() / 1000));

            JLabel label_3 = new JLabel("新增数量 kg");
            label_3.setBounds(23, 299, 100, 15);
            contentPane.add(label_3);

            textField_2 = new JTextField();
            textField_2.setBounds(125, 299, 155, 30);
            contentPane.add(textField_2);
            textField_2.setColumns(10);
        }
        JLabel label_4 = new JLabel("进价 kg/￥");
        label_4.setBounds(23, 185, 100, 15);
        contentPane.add(label_4);

        textField_3 = new JTextField();
        textField_3.setBounds(125, 185, 155, 30);
        contentPane.add(textField_3);
        textField_3.setColumns(10);

        JLabel label_5 = new JLabel("售价 g/￥");
        label_5.setBounds(23, 242, 100, 15);
        contentPane.add(label_5);

        textField_4 = new JTextField();
        textField_4.setBounds(125, 242, 155, 30);
        contentPane.add(textField_4);
        textField_4.setColumns(10);


    }

    private void add() {
        Medicine medicine = new Medicine();
        medicine.setName(textField.getText());
        Double num = null;
        BigDecimal purchase = null, sell = null;
        try {
            if (StringUtil.isNotBlank(textField_1.getText())) {
                num = Double.parseDouble(textField_1.getText());
            }
            if (StringUtil.isNotBlank(textField_3.getText())) {
                purchase = BigDecimal.valueOf(Double.parseDouble(textField_3.getText()));
            }

            if (StringUtil.isNotBlank(textField_4.getText())) {
                sell = BigDecimal.valueOf(Double.parseDouble(textField_4.getText()));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "输入数字的数量");
        }

        if (null != num) {
            medicine.setNum(num * 1000);
            medicine.setPurchase_price(purchase);
            medicine.setSelling_price(sell);
            medicine.setPinyin(new PinyinUtil().changeToGetShortPinYin(medicine.getName()));
            boolean s = medicineService.add(medicine);
            if (s) {
                JOptionPane.showMessageDialog(null, "添加成功");
                textField.setText("");
                textField_1.setText("");
                textField_3.setText("");
                textField_4.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "添加失败");
            }
        }
    }

    private void update() {
        MedicineFrame.medicineSelected.setName(textField.getText());
        Double num_now = null, num_add = null;
        BigDecimal purchase = null, sell = null;
        boolean parse = true;
        try {
            num_now = Double.parseDouble(textField_1.getText());
            if (StringUtil.isNotBlank(textField_2.getText()))
                num_add = Double.parseDouble(textField_2.getText());

            if (StringUtil.isNotBlank(textField_3.getText())) {
                purchase = BigDecimal.valueOf(Double.parseDouble(textField_3.getText()));
            }
            if (StringUtil.isNotBlank(textField_4.getText())) {
                sell = BigDecimal.valueOf(Double.parseDouble(textField_4.getText()));
            }
        } catch (Exception e) {
            parse = false;
            JOptionPane.showMessageDialog(null, "输入数字的数量");
        }
        if (parse) {
            if(null != num_add){
                MedicineFrame.medicineSelected.setNum(((num_now * 1000) + (num_add * 1000)));
            }else{
                MedicineFrame.medicineSelected.setNum(((num_now * 1000)));
            }
            MedicineFrame.medicineSelected.setPurchase_price(purchase);
            MedicineFrame.medicineSelected.setSelling_price(sell);
            boolean s = medicineService.update(MedicineFrame.medicineSelected);
            if (s) {
                JOptionPane.showMessageDialog(null, "更新成功");
                textField.setText("");
                textField_1.setText("");
                textField_2.setText("");
                textField_3.setText("");
                textField_4.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "更新失败");
            }

        }
    }
}
