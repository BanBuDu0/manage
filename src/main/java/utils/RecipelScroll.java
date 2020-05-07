package utils;

import model.Recipel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class RecipelScroll extends JScrollPane {
    private final JTextField textField;
    private final CandidateStrategy strategy;
    private final JList<String> jList;
    private final JTable jTable;
    private List<Recipel> recipelList;
    private String[] columnCount = {"药物", "数量"};
    private Map<String, Double> medicineUsed;

    public RecipelScroll(JTextField textField, CandidateStrategy strategy, JTable jTable, Map<String, Double> medicineUesd) {
        jList = new JList<>();
        jList.setVisible(true);
        this.medicineUsed = medicineUesd;
        this.jTable = jTable;
        this.strategy = strategy;
        this.textField = textField;
        this.recipelList = new ArrayList<>();
        this.textField.getDocument().addDocumentListener(new MyDocumentListener());
        this.textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keycode = e.getKeyCode();
                if (keycode == KeyEvent.VK_DOWN) {
                    RecipelScroll.this.requestFocus();
                    int index = jList.getSelectedIndex();
                    if (index < jList.getLastVisibleIndex()) {
                        jList.setSelectedIndex(++index);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        Point point = calculateTipBoxPoint();
        this.setBounds(new Rectangle(point, new Dimension(textField.getWidth(), 150)));
        this.setVisible(false);
        this.setViewportView(jList);
        this.addKeyListener(new MyKeyListener());
    }

    private Point calculateTipBoxPoint() {
        int x = 0;
        int y = 0;
        Container parent = textField;
        while (parent != textField.getRootPane()) {
            x += parent.getX();
            y += parent.getY();
            parent = parent.getParent();
        }
        y += textField.getHeight();
        return new Point(x, y);
    }


    private void textChanged() {
        if (this.textField.isEnabled()) {
            String prefix = this.textField.getText();
            this.setVisible(false);
            if (!prefix.isEmpty()) {
                List<String> candidate = new ArrayList<>();
                this.recipelList = this.strategy.matchPrefix(prefix);
                for (Recipel recipel : this.recipelList) {
                    candidate.add(recipel.getName());
                }
                if (!candidate.isEmpty()) {
                    jList.setListData(new Vector<>(candidate));
                    this.setVisible(true);
                }
            }
        }
    }

    class MyKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            if (keycode == KeyEvent.VK_DOWN) {
                int index = jList.getSelectedIndex();
                if (index < jList.getLastVisibleIndex()) {
                    jList.setSelectedIndex(++index);
                }
            } else if (keycode == KeyEvent.VK_UP) {
                int index = jList.getSelectedIndex();
                if (index > jList.getFirstVisibleIndex()) {
                    jList.setSelectedIndex(--index);
                } else {
                    textField.requestFocus();
                    jList.clearSelection();
                }
            } else if (keycode == KeyEvent.VK_ENTER) {
                String value = jList.getSelectedValue();
                int index = jList.getSelectedIndex();
                if (value != null) {
                    for (Recipel i : recipelList) {
                        System.out.println(i.getName());
                    }
                    System.out.println(recipelList.get(index).getName());
                    Recipel recipel = recipelList.get(index);
                    textField.setText(value);
                    textField.requestFocus();
                    RecipelScroll.this.setVisible(false);
                    Object[][] data = medicineToArray(recipel);
                    RecipelScroll.this.jTable.setModel(new DefaultTableModel(data, columnCount));
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private Object[][] medicineToArray(Recipel recipel) {
        String content = recipel.getContent();
        String[] medicines = content.split(";");
        Object[][] data = new Object[medicines.length][];
        int j = 0;
        for (String i : medicines) {
            String[] t = i.split(",");
            Object[] info = new Object[]{t[0],
                    t[1]};
            data[j] = info;
            medicineUsed.put(t[0], Double.parseDouble(t[1]));
            ++j;
        }
        return data;
    }

    class MyDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            RecipelScroll.this.textChanged();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            RecipelScroll.this.textChanged();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }

    public interface CandidateStrategy {
        List<Recipel> matchPrefix(String prefix);
    }

}
