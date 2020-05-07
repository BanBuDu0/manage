package utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Vector;

public class TipScroll extends JScrollPane {
    private final JTextField textField;
    private final CandidateStrategy strategy;
    private final JList<String> jList;

    public TipScroll(JTextField textField, CandidateStrategy strategy) {
        jList = new JList<>();
        jList.setVisible(true);
        this.strategy = strategy;
        this.textField = textField;
        this.textField.getDocument().addDocumentListener(new TipScroll.MyDocumentListener());
        this.textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keycode = e.getKeyCode();
                if (keycode == KeyEvent.VK_DOWN) {
                    TipScroll.this.requestFocus();
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
        this.addKeyListener(new TipScroll.MyKeyListener());
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
            TipScroll.this.setVisible(false);
            if (!prefix.isEmpty()) {
                List<String> candidate = this.strategy.matchPrefix(prefix);
                if (!candidate.isEmpty()) {
                    jList.setListData(new Vector<>(candidate));
                    TipScroll.this.setVisible(true);
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
                if (value != null) {
                    textField.setText(value);
                    textField.requestFocus();
                    TipScroll.this.setVisible(false);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    class MyDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            TipScroll.this.textChanged();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            TipScroll.this.textChanged();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }

    public interface CandidateStrategy {
        List<String> matchPrefix(String prefix);
    }

}
