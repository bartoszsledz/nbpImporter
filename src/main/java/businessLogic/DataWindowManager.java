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

    private DefaultTableModel model;

    private ParserData parserData;
    private final DataWindow dataWindow;
    private boolean dataFromWeb = false;

    public DataWindowManager(final DataWindow dataWindow) {
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
        browseDataBaseButtonListener();
    }

    private void downloadDataButtonListener() {
        dataWindow.addDownloadButtonActionListener(arg0 -> {
            try {
                parserData = new ParserData(new DownloadData(dataWindow.getSourceUrl()).toString());
                dataWindow.setSaveButtonOn();
                dataWindow.setClearButtonOn();
                // dataWindow.setSaveDataBaseButtonOn();
                refreshTable();
                showDataInTable(parserData.convertToObject());
                parserData.setTableName();
                dataWindow.getTableNameField().setText(parserData.getTableName());
                dataFromWeb = true;
            } catch (final FileNotFoundException e) {
                e.printStackTrace();
            }
            dataWindow.setTextOnTextField();
        });
    }

    private void saveDataButtonListener() {
        dataWindow.addSaveButtonActionListener(arg0 -> {
            final JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setSelectedFile(new File("ExchangeRatesBNP.txt"));
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
            final List<ExchangeRates> exchangeRates = parserData.convertToObject();
            DataBaseHelper.create(exchangeRates);
            final List<ExchangeRates> exchangeRatesFromDataBase = DataBaseHelper.getData();
            showDataInTable(exchangeRatesFromDataBase);
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

    private void showDataInTable(final List list) {
        try {
            final List<ExchangeRates> listExchangeRates = list;
            final Object[] row = new Object[6];
            for (int i = 0; i < list.size(); i++) {
                row[0] = listExchangeRates.get(i).getCurrency();
                row[1] = listExchangeRates.get(i).getCode();
                row[2] = listExchangeRates.get(i).getMidRate();
                row[3] = listExchangeRates.get(i).getDate();
                row[4] = listExchangeRates.get(i).getTableName();
                row[5] = listExchangeRates.get(i).getTableType();
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