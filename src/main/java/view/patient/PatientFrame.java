package view.patient;

import model.Medicine;
import model.Patient;
import service.PatientService;
import utils.TipBox;
import view.ManageFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PatientFrame extends JFrame {
    private final JTable table;
    private List<Patient> patientList;
    private final String[] columnCount = {"姓名", "地址", "年龄", "电话", "性别", "最近一次访问时间", "总访问次数"};
    public static Patient patientSelected = null;
    private final PatientService patientService = new PatientService();
    private final JTextField textField;

    public PatientFrame() {
        setTitle("病人");
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
        scrollPane.setBounds(10, 100, 730, 332);
        contentPane.add(scrollPane);

        table = new JTable(new DefaultTableModel()) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        scrollPane.setViewportView(table);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    new PatientDetailFrame(patientList.get(table.getSelectedRow())).setVisible(true);
                }
            }
        });
        queryAll();

        JButton button = new JButton("查询所有");
        button.addActionListener(e -> queryAll());
        button.setBounds(58, 22, 93, 23);
        contentPane.add(button);

        JButton button_1 = new JButton("添加病人");
        button_1.addActionListener(e -> new PatientFormFrame(false).setVisible(true));
        button_1.setBounds(158, 22, 93, 23);
        contentPane.add(button_1);

        JButton button_2 = new JButton("更新信息");
        button_2.addActionListener(e -> {
            update();
            queryAll();
        });
        button_2.setBounds(258, 22, 93, 23);
        contentPane.add(button_2);

        JButton button_3 = new JButton("删除病人");
        button_3.addActionListener(e -> {
            remove();
            queryAll();
        });
        button_3.setBounds(358, 22, 93, 23);
        contentPane.add(button_3);

        JButton button_4 = new JButton("返回");
        button_4.addActionListener(e -> {
            new ManageFrame().setVisible(true);
            PatientFrame.this.setVisible(false);
        });
        button_4.setBounds(550, 22, 93, 23);
        contentPane.add(button_4);

        JButton button_5 = new JButton("查询病人");
        button_5.addActionListener(e -> queryLike());
        button_5.setBounds(58, 58, 93, 23);
        contentPane.add(button_5);

        textField = new JTextField();
        textField.setBounds(158, 58, 193, 23);
        contentPane.add(textField);
        textField.setColumns(10);

        TipBox tipBox = new TipBox(textField, prefix -> {
            queryLike(prefix);
            return medicineToString();
        });

        contentPane.add(tipBox);

    }

    private static Object[][] listToArray(List<Patient> patients) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[][] data = new Object[patients.size()][];
        int size = patients.size();
        for (int i = 0; i < size; i++) {
            Patient patient = patients.get(i);
            Object[] info = new Object[]{patient.getName(), patient.getAddress(), patient.getAge(), patient.getPhone(),
                    patient.getGender() == 0 ? "男" : "女",
                    patient.getLast_time() == null ? null : sdf.format(patient.getLast_time()),
                    patient.getVisit_time()};
            data[i] = info;
        }
        return data;
    }

    private void remove() {
        int i = table.getSelectedRow();
        Patient client = patientList.get(i);
        boolean s = patientService.delete(client.getId());
        if (s) {
            JOptionPane.showMessageDialog(null, "删除成功");
        } else {
            JOptionPane.showMessageDialog(null, "删除失败");
        }
    }

    private void update() {
        int i = table.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "请先选中一条信息");
        } else {
            patientSelected = patientList.get(i);
            new PatientFormFrame(true).setVisible(true);
        }
    }

    public void queryAll() {
        patientList = patientService.queryAll();
        Object[][] data = listToArray(patientList);
        setTable(data);
    }

    private void queryLike() {
        if (null != textField.getText()) {
            patientList = patientService.queryLike("name", textField.getText());
            Object[][] data = listToArray(patientList);
            setTable(data);
        } else {
            queryAll();
        }
    }

    private void setTable(Object[][] data) {
        TableColumnModel tcm = table.getColumnModel();
        table.setModel(new DefaultTableModel(data, columnCount));
        javax.swing.table.TableColumn tc;
        tc = tcm.getColumn(0);
        tc.setPreferredWidth(30);
        tc = tcm.getColumn(1);
        tc.setPreferredWidth(230);
        tc = tcm.getColumn(2);
        tc.setPreferredWidth(5);
        tc = tcm.getColumn(4);
        tc.setPreferredWidth(5);

        tc = tcm.getColumn(5);
        tc.setPreferredWidth(110);
        tc = tcm.getColumn(6);
        tc.setPreferredWidth(35);
    }

    private void queryLike(String prefix) {
        patientList = patientService.queryLike("pinyin", prefix);
        Object[][] data = listToArray(patientList);
        setTable(data);
    }

    private List<String> medicineToString() {
        List<String> s = new ArrayList<>();
        for (Patient medicine : patientList) {
            s.add(medicine.getName());
        }
        return s;
    }

}
