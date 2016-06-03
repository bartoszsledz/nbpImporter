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

/**
 * Manager class for {@link DataWindow}.
 */
public class DataWindowManager {

    private DefaultTableModel model;

    private ParserData parserData;
    private DataWindow dataWindow;

    public DataWindowManager(DataWindow dataWindow) {
        this.dataWindow = dataWindow;
        addButtonsListeners();
        createTableModel();
    }

    private void addButtonsListeners() {
        downloadDataButtonListener();
        saveDataButtonListener();
        clearDataButtonListener();
        searchDataInBaseButtonListener();
    }

    private void downloadDataButtonListener() {
        dataWindow.addDownloadButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    parserData = new ParserData(new DownloadData(dataWindow.getSourceUrl()).toString());
                    dataWindow.setSaveButtonOn();
                    dataWindow.setClearButtonOn();
                    refreshTable();

                    ArrayList<ExchangeRates> exchangeRates = parserData.convertToObject();
                    for (ExchangeRates e : exchangeRates) {
                        DataBaseHelper.create(e, dataWindow.getComboBox().getSelectedItem().toString());
                    }
                    showDataInTable(exchangeRates);
                    dataWindow.getTableNameField().setText(parserData.getTableName());
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
                        FileUtils.writeStringToFile(fileToSave, parserData.getListOfExchangeRates(), "UTF-8");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void searchDataInBaseButtonListener() {
        dataWindow.addSearchInBaseButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!dataWindow.getSearchFieldText().equals("")) {
                    refreshTable();
                    showDataInTable(DataBaseHelper.findByDate(dataWindow.getSearchFieldText()));
                } else
                    JOptionPane.showMessageDialog(null, "Podaj date do wyszukania w formacie: yyyy-MM-dd,\noraz wybierz odpowiednia tabele z comboboxa.");
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

    private void showDataInTable(ArrayList list) {
        try {
            ArrayList<ExchangeRates> listExchangeRates = list;
            Object[] row = new Object[3];
            for (int i = 0; i < list.size(); i++) {
                row[0] = listExchangeRates.get(i).getCurrency();
                row[1] = listExchangeRates.get(i).getCode();
                row[2] = listExchangeRates.get(i).getMidRate();
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
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