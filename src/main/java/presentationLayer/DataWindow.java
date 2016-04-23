package presentationLayer;

import dataLayer.WebPages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;


public class DataWindow {

    private final int WIDTH = 650;
    private final int HEIGHT = 500;

    private JFrame frame;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JButton downloadButton, saveButton, clearButton;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<WebPages> comboBox;
    private JLabel author;
    private JTextField pageField, tableNameField;

    WebPages webPages;

    public DataWindow() {
        initialize();
    }

    private void initialize() {
        createAllComponents();
        frameSettings();
        defaultSizeFrame();
        buttonsSettings();
        comboBoxSettings();
        tableSettings();
        labelsSettings();
        fieldsSettings();
        setVisibleFrame();
    }

    private void createAllComponents() {
        frame = new JFrame("ImporterNBP");
        panel = new JPanel();
        scrollPane = new JScrollPane();
        downloadButton = new JButton("Download");
        saveButton = new JButton("Save to file");
        clearButton = new JButton("Clear");
        author = new JLabel("Created by Bartosz Śledź 2016");
        comboBox = new JComboBox();
        pageField = new JTextField();
        tableNameField = new JTextField();
        model = new DefaultTableModel();
        table = new JTable(model);
    }

    private void frameSettings() {
        scrollPane.setBounds(10, 100, 625, 350);
        frame.setContentPane(panel);
        frame.getContentPane().add(scrollPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
    }

    private void defaultSizeFrame() {
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        frame.setBounds(center.x - WIDTH / 2, center.y - HEIGHT / 2, WIDTH, HEIGHT);
    }

    private void comboBoxSettings() {
        comboBox.addItem(WebPages.TableA_EN);
        comboBox.addItem(WebPages.TableB_EN);
        comboBox.addItem(WebPages.TableA_PL);
        comboBox.addItem(WebPages.TableB_PL);
        comboBox.setBounds(315, 60, 95, 25);
        frame.getContentPane().add(comboBox);
    }

    private void buttonsSettings() {
        downloadButton.setBounds(430, 60, 100, 25);
        frame.getContentPane().add(downloadButton);

        saveButton.setBounds(535, 25, 100, 25);
        saveButton.setEnabled(false);
        frame.getContentPane().add(saveButton);

        clearButton.setBounds(535, 60, 100, 25);
        clearButton.setEnabled(false);
        frame.getContentPane().add(clearButton);
    }

    private void tableSettings() {
        model.addColumn("Currency");
        model.addColumn("Code");
        model.addColumn("MidRate");
        table.setEnabled(false);
        scrollPane.setViewportView(table);
    }

    private void fieldsSettings() {
        pageField.setBounds(10, 25, 400, 25);
        pageField.setEditable(false);
        frame.getContentPane().add(pageField);

        tableNameField.setBounds(10, 60, 300, 25);
        tableNameField.setEditable(false);
        frame.getContentPane().add(tableNameField);
    }

    private void labelsSettings() {
        author.setBounds(450, 450, 250, 25);
        frame.getContentPane().add(author);
    }

    public void addDownloadButtonActionListener(ActionListener actionListener) {
        downloadButton.addActionListener(actionListener);
    }

    public void addSaveButtonActionListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    public void addClearButtonActionListener(ActionListener actionListener) {
        clearButton.addActionListener(actionListener);
    }

    public void setTextOnTextField() {
        pageField.setText(webPages.getPage());
    }

    public void setVisibleFrame() {
        frame.setVisible(true);
    }

    public void setSaveButtonOn() {
        saveButton.setEnabled(true);
    }

    public void setSaveButtonOff() {
        saveButton.setEnabled(false);
    }

    public void setClearButtonOn() {
        clearButton.setEnabled(true);
    }

    public void setClearButtonOff() {
        clearButton.setEnabled(false);
    }

    public JTable getTable() {
        return table;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getTableNameField(){
        return tableNameField;
    }

    public String getSourceUrl() {
        webPages = (WebPages) comboBox.getSelectedItem();
        return webPages.getPage();
    }
}