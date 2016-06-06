package presentationLayer;

import dataLayer.WebPages;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Main class responsible for the view.
 * Class contains definitions the fields, buttons etc. necessary for the proper display data.
 */
public class DataWindow {

    private final int WIDTH = 650;
    private final int HEIGHT = 530;

    private JFrame frame;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JButton downloadButton, saveButton, clearButton, searchBaseButton, saveDataBaseButton;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<WebPages> comboBox;
    private JLabel author, searchLabel;
    private JTextField pageField, tableNameField, searchField;
    private DefaultTableCellRenderer renderer;

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
        searchBaseButton = new JButton("Search");
        downloadButton = new JButton("Download");
        saveButton = new JButton("Save to file");
        saveDataBaseButton = new JButton("Save to base");
        clearButton = new JButton("Clear");
        author = new JLabel("Created by Bartosz Śledź 2016");
        searchLabel = new JLabel("np: 2016-11-06");
        renderer = new DefaultTableCellRenderer();
        comboBox = new JComboBox();
        pageField = new JTextField();
        searchField = new JTextField();
        tableNameField = new JTextField();
        model = new DefaultTableModel();
        table = new JTable(model);
    }

    private void frameSettings() {
        scrollPane.setBounds(10, 130, 625, 350);
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
        comboBox.setBounds(315, 60, 110, 25);
        frame.getContentPane().add(comboBox);
    }

    private void buttonsSettings() {
        downloadButton.setBounds(430, 60, 90, 25);
        frame.getContentPane().add(downloadButton);

        searchBaseButton.setBounds(430, 25, 90, 25);
        frame.getContentPane().add(searchBaseButton);

        saveButton.setBounds(525, 25, 110, 25);
        saveButton.setEnabled(false);
        frame.getContentPane().add(saveButton);

        clearButton.setBounds(525, 95, 110, 25);
        clearButton.setEnabled(false);
        frame.getContentPane().add(clearButton);

        saveDataBaseButton.setBounds(525, 60, 110, 25);
        saveDataBaseButton.setEnabled(false);
        frame.getContentPane().add(saveDataBaseButton);
    }

    private void tableSettings() {
        model.addColumn("Currency");
        model.addColumn("Code");
        model.addColumn("MidRate");
        model.addColumn("Date");
        model.addColumn("TableName");
        model.addColumn("TableType");
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(140);
        table.getColumnModel().getColumn(1).setPreferredWidth(87);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(160);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < model.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        table.setEnabled(false);
        scrollPane.setViewportView(table);
    }

    private void fieldsSettings() {
        pageField.setBounds(10, 25, 300, 25);
        pageField.setEditable(false);
        frame.getContentPane().add(pageField);

        tableNameField.setBounds(10, 60, 300, 25);
        tableNameField.setEditable(false);
        frame.getContentPane().add(tableNameField);

        searchField.setBounds(315, 25, 110, 25);
        searchField.setToolTipText("np: yyyy-MM-dd");
        frame.getContentPane().add(searchField);
    }

    private void labelsSettings() {
        author.setBounds(450, 480, 250, 25);
        frame.getContentPane().add(author);

        searchLabel.setBounds(315, 0, 110, 25);
        frame.getContentPane().add(searchLabel);
    }

    public void addDownloadButtonActionListener(ActionListener actionListener) {
        downloadButton.addActionListener(actionListener);
    }

    public void addSaveButtonActionListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    public void addSearchInBaseButtonActionListener(ActionListener actionListener) {
        searchBaseButton.addActionListener(actionListener);
    }

    public void addClearButtonActionListener(ActionListener actionListener) {
        clearButton.addActionListener(actionListener);
    }

    public void addSaveDataBaseButtonActionListener(ActionListener actionListener) {
        saveDataBaseButton.addActionListener(actionListener);
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

    public void setSaveDataBaseButtonOff() {
        saveDataBaseButton.setEnabled(false);
    }

    public void setSaveDataBaseButtonOn() {
        saveDataBaseButton.setEnabled(true);
    }

    public JTable getTable() {
        return table;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getTableNameField() {
        return tableNameField;
    }

    public String getSearchFieldText() {
        return searchField.getText();
    }

    public JComboBox getComboBox() {
        return comboBox;
    }

    public String getSourceUrl() {
        webPages = (WebPages) comboBox.getSelectedItem();
        return webPages.getPage();
    }
}