package view.medicine;

import model.Medicine;
import service.MedicineService;
import utils.TipBox;
import utils.TipScroll;
import view.ManageFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MedicineFrame extends JFrame {
    private JTable table;
    private String[] columnCount = {"药物名称", "药物数量 kg", "进价 kg/￥", "售价 g/￥"};
    public static Medicine medicineSelected = null;
    private List<Medicine> medicineList;
    private MedicineService medicineService = new MedicineService();
    private JTextField textField;

    public MedicineFrame() {
        setTitle("药物");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = 764;
        int windowsHeight = 500;
        setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(29, 100, 692, 332);
        contentPane.add(scrollPane);

        table = new JTable(new DefaultTableModel()) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scrollPane.setViewportView(table);

        queryAll();

        JButton button = new JButton("查询所有");
        button.addActionListener(e -> queryAll());
        button.setBounds(58, 22, 93, 23);
        contentPane.add(button);

        JButton button_1 = new JButton("添加药物");
        button_1.addActionListener(e -> new MedicineFormFrame(false).setVisible(true));
        button_1.setBounds(158, 22, 93, 23);
        contentPane.add(button_1);

        JButton button_2 = new JButton("更新药物");
        button_2.addActionListener(e -> {
            update();
        });
        button_2.setBounds(258, 22, 93, 23);
        contentPane.add(button_2);

        JButton button_3 = new JButton("删除药物");
        button_3.addActionListener(e -> {
            remove();
            queryAll();
        });
        button_3.setBounds(358, 22, 93, 23);
        contentPane.add(button_3);

        JButton button_4 = new JButton("返回");
        button_4.addActionListener(e -> {
            new ManageFrame().setVisible(true);
            MedicineFrame.this.setVisible(false);
        });
        button_4.setBounds(550, 22, 93, 23);
        contentPane.add(button_4);

        JButton button_5 = new JButton("查询药物");
        button_5.addActionListener(e -> queryLike());
        button_5.setBounds(58, 58, 93, 23);
        contentPane.add(button_5);

        textField = new JTextField();
        textField.setBounds(158, 58, 193, 23);

        TipBox tipBox = new TipBox(textField, prefix -> {
            queryLike(prefix);
            return medicineToString();
        });

        contentPane.add(tipBox);
        contentPane.add(textField);
        textField.setColumns(10);
        textField.addActionListener(e -> queryLike());

    }

    private static Object[][] listToArray(List<Medicine> items) {
        Object[][] data = new Object[items.size()][];
        int size = items.size();
        for (int i = 0; i < size; i++) {
            Medicine medicine = items.get(i);
            DecimalFormat df = new DecimalFormat("######0.00000");
            Object[] info = new Object[]{medicine.getName(), df.format((double) medicine.getNum() / 1000), medicine.getPurchase_price(), medicine.getSelling_price()};
            data[i] = info;
        }
        return data;
    }

    private void remove() {
        int i = table.getSelectedRow();
        Medicine item = medicineList.get(i);
        boolean s = medicineService.delete(item.getId());
        if (s) {
            JOptionPane.showMessageDialog(null, "删除成功");
        } else {
            JOptionPane.showMessageDialog(null, "删除失败");
        }
    }

    private void update() {
        int i = table.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "先选择一条记录");
        } else {
            medicineSelected = medicineList.get(i);
            new MedicineFormFrame(true).setVisible(true);
        }
    }

    public void queryAll() {
        medicineList = medicineService.queryAll();
        Object[][] data = listToArray(medicineList);
        table.setModel(new DefaultTableModel(data, columnCount));
    }

    private void queryLike() {
        if (null != textField.getText()) {
            medicineList = medicineService.queryLike("name", textField.getText());
            Object[][] data = listToArray(medicineList);
            table.setModel(new DefaultTableModel(data, columnCount));
        } else {
            queryAll();
        }
    }

    private void queryLike(String prefix) {
        medicineList = medicineService.queryLike("pinyin", prefix);
        Object[][] data = listToArray(medicineList);
        table.setModel(new DefaultTableModel(data, columnCount));
    }

    private List<String> medicineToString() {
        List<String> s = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            s.add(medicine.getName());
        }
        return s;
    }

}
