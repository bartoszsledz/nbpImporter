package businessLogic;

import dataLayer.ExchangeRates;
import org.apache.commons.io.FileUtils;
import presentationLayer.DataWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DataWindowManager {

    private DefaultTableModel model;

    ParserData parserData;
    DataWindow dataWindow;

    public DataWindowManager(DataWindow dataWindow) {
        this.dataWindow = dataWindow;
        addButtonsListeners();
    }

    private void addButtonsListeners() {
        downloadDataButtonListener();
        saveDataButtonListener();
        clearDataButtonListener();
    }

    private void downloadDataButtonListener() {
        dataWindow.addDownloadButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    parserData = new ParserData(new DownloadData(dataWindow.getSourceUrl()).toString());
                    dataWindow.setSaveButtonOn();
                    dataWindow.setClearButtonOn();
                    createTableModel();
                    refreshTable();
                    showDataInTable();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                dataWindow.setTextOnTextField();
            }
        });
    }

    private void saveDataButtonListener() {
        dataWindow.addSaveButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setSelectedFile(new File("ExchangeRatesBNP.txt"));
                jFileChooser.setCurrentDirectory(new File("."));
                int userSelection = jFileChooser.showSaveDialog(dataWindow.getFrame());

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = jFileChooser.getSelectedFile();
                    try {
                        FileUtils.writeStringToFile(fileToSave, parserData.getListOfExchangeRates());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void clearDataButtonListener() {
        dataWindow.addClearButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                refreshTable();
                dataWindow.setSaveButtonOff();
                dataWindow.setClearButtonOff();
            }
        });
    }

    private void createTableModel() {
        model = (DefaultTableModel) dataWindow.getTable().getModel();
    }

    private void showDataInTable() {
        try {
            ArrayList<ExchangeRates> list = parserData.convertToObject();
            Object[] row = new Object[3];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getCurrency();
                row[1] = list.get(i).getCode();
                row[2] = list.get(i).getMidRate();
                model.addRow(row);
            }
        } catch (Exception e) {
        }
    }

    private void refreshTable() {
        if (model.getRowCount() > 0) {
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }
    }
}