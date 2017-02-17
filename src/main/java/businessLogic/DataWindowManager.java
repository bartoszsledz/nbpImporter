package businessLogic;

import dataLayer.ExchangeRates;
import presentationLayer.DataWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Manager class for {@link DataWindow}.
 */
public class DataWindowManager {

    private static final String NEW_FILE = "ExchangeRatesBNP.txt";
    private DefaultTableModel model;
    private boolean dataFromWeb = false;

    private final ParserData parserData;
    private final DownloadData downloadData;
    private final DataWindow dataWindow;

    public DataWindowManager() {
        dataWindow = new DataWindow();
        downloadData = new DownloadData();
        parserData = new ParserData();
        addButtonsListeners();
        createTableModel();
    }

    private void addButtonsListeners() {
        downloadDataButtonListener();
        saveDataButtonListener();
        clearDataButtonListener();
        searchDataInBaseButtonListener();
        saveDataBaseButtonListener();
        browseDataBaseButtonListener();
    }

    private void downloadDataButtonListener() {
        dataWindow.addDownloadButtonActionListener(arg0 -> {
            final String htmlInString = downloadData.getDataFromSource(dataWindow.getSourceUrl());
            final List<ExchangeRates> exchangeRatesList = parserData.convertToObject(htmlInString);

            dataWindow.setSaveButtonOn();
            dataWindow.setClearButtonOn();
            // dataWindow.setSaveDataBaseButtonOn();
            refreshTable();
            showDataInTable(exchangeRatesList);
            parserData.setTableName();
            dataWindow.getTableNameField().setText(parserData.getTableName());
            dataFromWeb = true;
            dataWindow.setTextOnTextField();
        });
    }

    private void saveDataButtonListener() {
        dataWindow.addSaveButtonActionListener(arg0 -> {
            final JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setSelectedFile(new File(NEW_FILE));
            jFileChooser.setCurrentDirectory(new File("."));
            final int userSelection = jFileChooser.showSaveDialog(dataWindow.getFrame());

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                final File fileToSave = jFileChooser.getSelectedFile();
                if (dataFromWeb) {
                    try {
                        saveInCsvFormat(fileToSave);
                        //FileUtils.writeStringToFile(fileToSave, parserData.getListOfExchangeRates(), "UTF-8");
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        saveInCsvFormat(fileToSave);
                        //FileUtils.writeStringToFile(fileToSave, String.valueOf(DataBaseHelper.getData()), "UTF-8");
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void saveInCsvFormat(final File fileToSave) throws FileNotFoundException {
        final PrintWriter pw = new PrintWriter(fileToSave);
        final StringBuilder sb = new StringBuilder();

        final String FILE_HEADER = "Currency,Code,MidRate,Date,TableName,TableType";

        sb.append(FILE_HEADER);
        sb.append(System.lineSeparator());

        parserData.getExchangeRates().forEach(exchangeRate -> {
            sb.append(exchangeRate.getCurrency());
            sb.append(',');
            sb.append(exchangeRate.getCode());
            sb.append(',');
            sb.append(exchangeRate.getMidRate());
            sb.append(',');
            sb.append(exchangeRate.getDate());
            sb.append(',');
            sb.append(exchangeRate.getTableName());
            sb.append(',');
            sb.append(exchangeRate.getTableType());
            sb.append(System.lineSeparator());
        });

        pw.write(sb.toString());
        pw.close();
    }

    private void saveDataBaseButtonListener() {
        dataWindow.addSaveDataBaseButtonActionListener(e -> {
            refreshTable();
            final String htmlInString = downloadData.getDataFromSource(dataWindow.getSourceUrl());
            final List<ExchangeRates> exchangeRates = parserData.convertToObject(htmlInString);
            DataBaseHelper.create(exchangeRates);
            showDataInTable(DataBaseHelper.getData());
        });
    }

    private void searchDataInBaseButtonListener() {
        dataWindow.addSearchInBaseButtonActionListener(e -> {
            refreshTable();
            if (!dataWindow.getSearchFieldText().equals("")) {
                showDataInTable(DataBaseHelper.findByDate(dataWindow.getSearchFieldText()));
            } else
                JOptionPane.showMessageDialog(null, "Podaj date do wyszukania w formacie: yyyy-MM-dd.");
        });
    }

    private void browseDataBaseButtonListener() {
        dataWindow.addBrowseDataBaseButtonActionListener(e -> {
            refreshTable();
            showDataInTable(DataBaseHelper.getData());
            dataWindow.setClearButtonOn();
            dataWindow.setSaveButtonOn();
            dataFromWeb = false;
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

    private void showDataInTable(final List<ExchangeRates> listExchangeRates) {
        try {
            final Object[] row = new Object[6];
            for (final ExchangeRates listExchangeRate : listExchangeRates) {
                row[0] = listExchangeRate.getCurrency();
                row[1] = listExchangeRate.getCode();
                row[2] = listExchangeRate.getMidRate();
                row[3] = listExchangeRate.getDate();
                row[4] = listExchangeRate.getTableName();
                row[5] = listExchangeRate.getTableType();
                model.addRow(row);
            }
        } catch (final Exception e) {
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