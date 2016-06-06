package businessLogic;

import dataLayer.ExchangeRates;
import org.apache.commons.io.FileUtils;
import presentationLayer.DataWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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
        saveDataBaseButtonListener();
    }

    private void downloadDataButtonListener() {
        dataWindow.addDownloadButtonActionListener(arg0 -> {
            try {
                parserData = new ParserData(new DownloadData(dataWindow.getSourceUrl()).toString());
                dataWindow.setSaveButtonOn();
                dataWindow.setClearButtonOn();
                dataWindow.setSaveDataBaseButtonOn();
                refreshTable();
                showDataInTable(parserData.convertToObject());
                parserData.setTableName();
                dataWindow.getTableNameField().setText(parserData.getTableName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            dataWindow.setTextOnTextField();
        });
    }

    private void saveDataButtonListener() {
        dataWindow.addSaveButtonActionListener(arg0 -> {
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
        });
    }

    private void saveDataBaseButtonListener() {
        dataWindow.addSaveDataBaseButtonActionListener(e -> {
            refreshTable();
            List<ExchangeRates> exchangeRates = parserData.convertToObject();
            DataBaseHelper.create(exchangeRates);
            List<ExchangeRates> exchangeRatesFromDataBase = DataBaseHelper.getData();
            showDataInTable(exchangeRatesFromDataBase);
        });
    }

    private void searchDataInBaseButtonListener() {
        dataWindow.addSearchInBaseButtonActionListener(e -> {
            refreshTable();
            if (!dataWindow.getSearchFieldText().equals("")) {
                showDataInTable(DataBaseHelper.findByDate(dataWindow.getSearchFieldText()));
            } else
                JOptionPane.showMessageDialog(null, "Podaj date do wyszukania w formacie: yyyy-MM-dd,\noraz wybierz odpowiednia tabele z comboboxa.");
        });
    }

    private void clearDataButtonListener() {
        dataWindow.addClearButtonActionListener(arg0 -> {
            refreshTable();
            dataWindow.setSaveButtonOff();
            dataWindow.setClearButtonOff();
            dataWindow.setSaveDataBaseButtonOff();
        });
    }

    private void createTableModel() {
        model = (DefaultTableModel) dataWindow.getTable().getModel();
    }

    private void showDataInTable(List list) {
        try {
            List<ExchangeRates> listExchangeRates = list;
            Object[] row = new Object[6];
            for (int i = 0; i < list.size(); i++) {
                row[0] = listExchangeRates.get(i).getCurrency();
                row[1] = listExchangeRates.get(i).getCode();
                row[2] = listExchangeRates.get(i).getMidRate();
                row[3] = listExchangeRates.get(i).getDate();
                row[4] = listExchangeRates.get(i).getTableName();
                row[5] = listExchangeRates.get(i).getTableType();
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