package utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Vector;

public class TipBox extends JList<String> {

    private final JTextField textField;
    private final CandidateStrategy strategy;

    public TipBox(JTextField textField, CandidateStrategy strategy) {
        this.strategy = strategy;
        this.textField = textField;
        this.textField.getDocument().addDocumentListener(new MyDocumentListener());
//        this.textField.addKeyListener(new MyKeyListener());
        Point point = calculateTipBoxPoint();
        this.setBounds(new Rectangle(point, new Dimension(textField.getWidth(), 200)));
        this.setVisible(false);
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
            TipBox.this.setVisible(false);
            if (!prefix.isEmpty()) {
                List<String> candidate = this.strategy.matchPrefix(prefix);
                if (!candidate.isEmpty()) {
                    TipBox.this.setListData(new Vector<>(candidate));
//                    TipBox.this.setVisible(true);
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
                int index = TipBox.this.getSelectedIndex();
                if (index < TipBox.this.getLastVisibleIndex()) {
                    TipBox.this.setSelectedIndex(++index);
                }
            } else if (keycode == KeyEvent.VK_UP) {
                int index = TipBox.this.getSelectedIndex();
                if (index > TipBox.this.getFirstVisibleIndex()) {
                    TipBox.this.setSelectedIndex(--index);
                }
            } else if (keycode == KeyEvent.VK_ENTER) {
                String value = TipBox.this.getSelectedValue();
                if (value != null) {
                    textField.setText(value);
                    TipBox.this.setVisible(false);
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
            TipBox.this.textChanged();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            TipBox.this.textChanged();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }

    public interface CandidateStrategy {
        List<String> matchPrefix(String prefix);
    }
}