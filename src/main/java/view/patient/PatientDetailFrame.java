package view.patient;

import model.Medicine;
import model.Patient;
import model.Prescription;
import model.Recipel;
import service.MedicineService;
import service.PatientService;
import service.PrescriptionService;
import service.RecipelService;
import utils.PinyinUtil;
import utils.RecipelScroll;
import utils.StringUtil;
import utils.TipScroll;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PatientDetailFrame extends JFrame {
    private final JTable table, table2;
    private final PrescriptionService prescriptionService = new PrescriptionService();
    private final PatientService patientService = new PatientService();
    private final String[] columnCount = {"开方时间"}, columnCount2 = {"药物", "数量/g"};
    private List<Prescription> patientList = new ArrayList<>();
    private final JTextArea jtHistory, jtSign, jtTongue, jtPulse_left, jtPulse_right, jtDiagnosis;
    private Prescription prescriptionSelected = null;
    private final Patient patient;
    private final JTextField textField, textField2, textField_1;
    private List<Medicine> medicineList;
    private List<Recipel> recipelList;
    private final MedicineService medicineService = new MedicineService();
    private final Map<String, Double> medicineUsed = new HashMap<>();
    private final RecipelService recipelService = new RecipelService();


    public PatientDetailFrame(Patient patient) {
        setTitle("处方");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowsWidth = 1030;
        int windowsHeight = 430;
        setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        this.patient = patient;
        JLabel jName = new JLabel("");
        String nameString = "姓名： " + patient.getName();
        jName.setText(nameString);
        jName.setBounds(10, 15, 93, 23);
        contentPane.add(jName);

        JLabel jAddress = new JLabel("");
        String addressString = "地址： " + (patient.getAddress() == null ? "" : patient.getAddress());
        jAddress.setText(addressString);
        jAddress.setBounds(110, 15, 300, 23);
        contentPane.add(jAddress);

        JLabel jAge = new JLabel("");
        String ageString = "年龄： " + patient.getAge();
        jAge.setText(ageString);
        jAge.setBounds(400, 15, 100, 23);
        contentPane.add(jAge);

        JLabel jGender = new JLabel("");
        String genderString = "性别： " + (patient.getGender() == 0 ? "男" : "女");
        jGender.setText(genderString);
        jGender.setBounds(500, 15, 70, 23);
        contentPane.add(jGender);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 45, 115, 250);

        table = new JTable(new DefaultTableModel()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scrollPane.setViewportView(table);
        contentPane.add(scrollPane);

        //右边
        JLabel jHistory = new JLabel("既往病史");
        jHistory.setBounds(130, 45, 60, 20);
        contentPane.add(jHistory);

        jtHistory = new JTextArea();
        jtHistory.setLineWrap(true);
        JScrollPane jsHistory = new JScrollPane(jtHistory);
        jsHistory.setBounds(200, 45, 350, 40);
        contentPane.add(jsHistory);


        JLabel jSign = new JLabel("体征");
        jSign.setBounds(130, 100, 60, 20);
        contentPane.add(jSign);

        jtSign = new JTextArea();
        jtSign.setLineWrap(true);
        JScrollPane jsSign = new JScrollPane(jtSign);
        jsSign.setBounds(200, 100, 350, 40);
        contentPane.add(jsSign);

        JLabel jTongue = new JLabel("舌象");
        jTongue.setBounds(130, 165, 60, 20);
        contentPane.add(jTongue);

        jtTongue = new JTextArea();
        jtTongue.setLineWrap(true);
        JScrollPane jsTongue = new JScrollPane(jtTongue);
        jsTongue.setBounds(200, 165, 350, 40);
        contentPane.add(jsTongue);

        JLabel jPulse_left = new JLabel("脉象左");
        jPulse_left.setBounds(130, 220, 60, 20);
        contentPane.add(jPulse_left);

        jtPulse_left = new JTextArea();
        jtPulse_left.setLineWrap(true);
        JScrollPane jsPulse_left = new JScrollPane(jtPulse_left);
        jsPulse_left.setBounds(200, 220, 120, 40);
        contentPane.add(jsPulse_left);

        JLabel jPulse_right = new JLabel("脉象右");
        jPulse_right.setBounds(360, 220, 60, 20);
        contentPane.add(jPulse_right);

        jtPulse_right = new JTextArea();
        jtPulse_right.setLineWrap(true);
        JScrollPane jsPulse_right = new JScrollPane(jtPulse_right);
        jsPulse_right.setBounds(430, 220, 120, 40);
        contentPane.add(jsPulse_right);


        JLabel jDiagnosis = new JLabel("诊断");
        jDiagnosis.setBounds(130, 275, 60, 20);
        contentPane.add(jDiagnosis);

        jtDiagnosis = new JTextArea();
        jtDiagnosis.setLineWrap(true);
        JScrollPane jsDiagnosis = new JScrollPane(jtDiagnosis);
        jsDiagnosis.setBounds(200, 275, 350, 40);
        contentPane.add(jsDiagnosis);


        JLabel jPrescribe1 = new JLabel("添加药物");
        jPrescribe1.setBounds(580, 45, 60, 23);
        contentPane.add(jPrescribe1);

        textField = new JTextField();
        textField.setBounds(640, 45, 140, 23);

        TipScroll tipBox = new TipScroll(textField, prefix -> {
            queryLike(prefix);
            return medicineToString();
        });
        contentPane.add(tipBox);
        contentPane.add(textField);
        textField.setColumns(10);
        textField2 = new JTextField();
        textField2.setBounds(800, 45, 70, 23);
        contentPane.add(textField2);


        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(580, 80, 200, 240);
        table2 = new JTable(new DefaultTableModel()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scrollPane2.setViewportView(table2);
        contentPane.add(scrollPane2);

        Object[][] data = medicineToArray();
        table2.setModel(new DefaultTableModel(data, columnCount2));

        JLabel jPrescribe = new JLabel("处方");
        jPrescribe.setBounds(800, 80, 50, 23);
        contentPane.add(jPrescribe);

        textField_1 = new JTextField();
        textField_1.setBounds(850, 80, 140, 23);

        RecipelScroll tipBox1 = new RecipelScroll(textField_1, prefix -> {
            queryLikeRecipel(prefix);
            return recipelList;
        }, table2, medicineUsed);
        contentPane.add(tipBox1);
        contentPane.add(textField_1);
        textField_1.setColumns(10);


        JTextField addRecipelField = new JTextField();
        addRecipelField.setBounds(800, 170, 160, 23);
        contentPane.add(addRecipelField);

        JButton addRecipelButton = new JButton("添加当前处方");
        addRecipelButton.addActionListener(e -> {
            if (StringUtil.isNotBlank(addRecipelField.getText())) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String i : medicineUsed.keySet()) {
                    String temp = i + "," + medicineUsed.get(i) + ";";
                    stringBuilder.append(temp);
                }
                Recipel recipel = new Recipel();
                recipel.setContent(stringBuilder.toString());
                recipel.setName(addRecipelField.getText());
                recipel.setPinyin(new PinyinUtil().changeToGetShortPinYin(addRecipelField.getText()));
                boolean s = recipelService.add(recipel);
                if (s) {
                    JOptionPane.showMessageDialog(null, "添加处方成功");
                } else {
                    JOptionPane.showMessageDialog(null, "添加处方失败");
                }
            } else {
                JOptionPane.showMessageDialog(null, "输入处方名");
            }
        });
        addRecipelButton.setBounds(800, 130, 160, 23);
        contentPane.add(addRecipelButton);

        JLabel measure = new JLabel("克");
        measure.setBounds(875, 45, 20, 20);
        contentPane.add(measure);

        JButton jButton = new JButton("添加");
        jButton.addActionListener(e -> addMedicine());
        jButton.setBounds(900, 45, 60, 23);
        contentPane.add(jButton);

        JButton button1 = new JButton("添加处方");
        button1.addActionListener(e -> addPrescribe());
        button1.setBounds(670, 350, 115, 23);
        contentPane.add(button1);

        JButton button = new JButton("添加新处方");
        button.addActionListener(e -> {
            table.clearSelection();
            clearRightContent();
            button1.setEnabled(true);
            jtHistory.setEditable(true);
            jtSign.setEditable(true);
            jtTongue.setEditable(true);
            jtPulse_left.setEditable(true);
            jtPulse_right.setEditable(true);
            jtDiagnosis.setEditable(true);
            textField.setEditable(true);
            textField2.setEditable(true);
            textField_1.setEditable(true);
            jButton.setEnabled(true);
        });
        button.setBounds(10, 338, 115, 23);
        contentPane.add(button);

        JButton deleteButton = new JButton("删除处方");
        deleteButton.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                prescriptionService.delete(patientList.get(table.getSelectedRow()).getId());
                queryByID(patient.getId());
                clearRightContent();
            } else {
                JOptionPane.showMessageDialog(null, "先选择一条记录");
            }

        });
        deleteButton.setBounds(10, 305, 115, 23);
        contentPane.add(deleteButton);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    prescriptionSelected = patientList.get(table.getSelectedRow());
                    setRightContent(prescriptionSelected);
                    button1.setEnabled(false);
                    jButton.setEnabled(false);
                }
            }
        });

        queryByID(patient.getId());
    }

    public void queryByID(int id) {
        patientList = prescriptionService.queryByID(id);
        Object[][] data = listToArray(patientList);
        setTable(data);
    }

    private static Object[][] listToArray(List<Prescription> prescriptions) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Object[][] data = new Object[prescriptions.size()][];
        int size = prescriptions.size();
        for (int i = 0; i < size; i++) {
            Prescription prescription = prescriptions.get(i);
            Object[] info = new Object[]{
                    prescription.getCreate_time() == null ? null : sdf.format(prescription.getCreate_time())};
            data[i] = info;
        }
        return data;
    }

    private void setTable(Object[][] data) {
        TableColumnModel tcm = table.getColumnModel();
        table.setModel(new DefaultTableModel(data, columnCount));
        javax.swing.table.TableColumn tc;
        tc = tcm.getColumn(0);
        tc.setPreferredWidth(150);
    }

    private void setRightContent(Prescription prescription) {
        jtHistory.setText(prescription.getHistory());
        jtSign.setText(prescription.getSign());
        jtTongue.setText(prescription.getTongue());
        jtPulse_left.setText(prescription.getPulse_left());
        jtPulse_right.setText(prescription.getPulse_right());
        jtDiagnosis.setText(prescription.getDiagnosis());
        String pair = prescription.getPrescribe();
//        Map<String, Double> medicines = new HashMap<>();
        if (StringUtil.isNotBlank(pair)) {
            String[] used = pair.split(";");
            for (String i : used) {
                String[] t = i.split(",");
                medicineUsed.put(t[0], Double.parseDouble(t[1]));
            }
        } else {
            medicineUsed.clear();
        }
        Object[][] data = medicineToArray();
        table2.setModel(new DefaultTableModel(data, columnCount2));


        jtHistory.setEditable(false);
        jtSign.setEditable(false);
        jtTongue.setEditable(false);
        jtPulse_left.setEditable(false);
        jtPulse_right.setEditable(false);
        jtDiagnosis.setEditable(false);
        textField.setEditable(false);
        textField2.setEditable(false);
        textField_1.setEditable(false);
    }

    private void clearRightContent() {
        jtHistory.setText(null);
        jtSign.setText(null);
        jtTongue.setText(null);
        jtPulse_left.setText(null);
        jtPulse_right.setText(null);
        jtDiagnosis.setText(null);
        table2.setModel(new DefaultTableModel(null, columnCount2));
        medicineUsed.clear();
        textField.setText(null);
        textField2.setText(null);
        textField_1.setText(null);
    }

    private void addPrescribe() {
        Prescription prescription = new Prescription();
        prescription.setCreate_time(new Date());
        prescription.setSign(jtSign.getText());
        prescription.setPatient_id(patient.getId());
        StringBuilder stringBuilder = new StringBuilder();
        for (String i : medicineUsed.keySet()) {
            String temp = i + "," + medicineUsed.get(i) + ";";
            stringBuilder.append(temp);
        }
        prescription.setPrescribe(stringBuilder.toString());
        prescription.setDiagnosis(jtDiagnosis.getText());
        prescription.setPulse_right(jtPulse_right.getText());
        prescription.setPulse_left(jtPulse_left.getText());
        prescription.setTongue(jtTongue.getText());
        prescription.setHistory(jtHistory.getText());
        boolean s = prescriptionService.add(prescription);
        if (s) {
            JOptionPane.showMessageDialog(null, "添加成功");
            queryByID(patient.getId());
            patient.setLast_time(new Date());
            patient.setVisit_time(patient.getVisit_time() + 1);
            patientService.update(patient);

            for (String m : medicineUsed.keySet()) {
                Medicine medicine = medicineService.queryByName(m);
                double num = medicine.getNum() - medicineUsed.get(m);
                medicine.setNum(num);
                medicineService.update(medicine);
            }
            clearRightContent();
        } else {
            JOptionPane.showMessageDialog(null, "添加失败");
        }
    }

    private void queryLike(String prefix) {
        medicineList = medicineService.queryLike("pinyin", prefix);
    }

    private void queryLikeRecipel(String prefix) {
        recipelList = recipelService.queryLike("pinyin", prefix);
    }

    private List<String> medicineToString() {
        List<String> s = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            s.add(medicine.getName());
        }
        return s;
    }

    private void addMedicine() {
        if (StringUtil.isNotBlank(textField.getText()) && StringUtil.isNotBlank(textField2.getText())) {
            double num = -1;
            try {
                num = Double.parseDouble(textField2.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "输入数字的数量");
            }
            if (num != -1) {
                medicineUsed.put(textField.getText(), Double.parseDouble(textField2.getText()));
            }
        }
        Object[][] data = medicineToArray();
        table2.setModel(new DefaultTableModel(data, columnCount2));
    }

    private Object[][] medicineToArray() {
        Object[][] data = new Object[medicineUsed.size()][];
        int j = 0;
        for (String i : medicineUsed.keySet()) {
            Object[] info = new Object[]{i,
                    medicineUsed.get(i)};
            data[j] = info;
            ++j;
        }
        return data;
    }
}
