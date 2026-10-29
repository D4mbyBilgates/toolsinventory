/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toolsinventory;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author obana
 */
public class BorrowCSV {
    private static final String FILE_NAME = "borrow.csv";

  

    public static void addBorrow(
            String borrowerName,
            String toolName,
            String borrowDate,
            String returnDate) {

        File file = new File(FILE_NAME);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);

        try {
            Date parsedBorrowDate = sdf.parse(borrowDate);
            Date parsedReturnDate = sdf.parse(returnDate);

            if (!sdf.format(parsedBorrowDate).equals(borrowDate)
                    || !sdf.format(parsedReturnDate).equals(returnDate)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Invalid date format. Use MM/dd/yyyy.",
                        "Invalid Date",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid date format.\nUse MM/dd/yyyy format.",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try (BufferedWriter bw =
                new BufferedWriter(new FileWriter(file, true))) {

            if (file.length() == 0) {
                bw.write("BORROWER NAME,TOOL NAME,BORROW DATE,RETURN DATE");
                bw.newLine();
            }

            bw.write(
                    borrowerName + "," +
                    toolName + "," +
                    borrowDate + "," +
                    returnDate
            );

            bw.newLine();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Unable to save borrow record.\n" + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void loadBorrow(JTable table) {

        DefaultTableModel model =
                (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br =
                new BufferedReader(new FileReader(file))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length >= 4) {
                    model.addRow(new Object[]{
                        data[0],
                        data[1],
                        data[2],
                        data[3]
                    });
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Unable to load borrow records.\n" + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void updateReturnDate(
            String borrowerName,
            String toolName,
            String borrowDate,
            String newReturnDate) {

        File file = new File(FILE_NAME);
        File tempFile = new File("borrow_temp.csv");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(
                    null,
                    "borrow.csv file not found.",
                    "File Not Found",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);

        try {
            Date parsedBorrowDate = sdf.parse(borrowDate);
            Date parsedReturnDate = sdf.parse(newReturnDate);

            if (!sdf.format(parsedBorrowDate).equals(borrowDate)
                    || !sdf.format(parsedReturnDate).equals(newReturnDate)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Invalid date format. Use MM/dd/yyyy.",
                        "Invalid Date",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (parsedReturnDate.before(parsedBorrowDate)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Return date cannot be earlier than borrow date.",
                        "Invalid Date",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid date format.\nUse MM/dd/yyyy format.",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        boolean updated = false;

        try (
            BufferedReader br =
                    new BufferedReader(new FileReader(file));
            BufferedWriter bw =
                    new BufferedWriter(new FileWriter(tempFile))
        ) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                if (firstLine) {
                    bw.write(line);
                    bw.newLine();
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length >= 4
                        && data[0].trim().equalsIgnoreCase(borrowerName)
                        && data[1].trim().equalsIgnoreCase(toolName)
                        && data[2].trim().equals(borrowDate)) {

                    data[3] = newReturnDate;
                    line = String.join(",", data);
                    updated = true;
                }

                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Unable to update borrow record.\n" + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );

            if (tempFile.exists()) {
                tempFile.delete();
            }
            return;
        }

        if (!updated) {
            tempFile.delete();

            JOptionPane.showMessageDialog(
                    null,
                    "Borrow record not found.",
                    "Record Not Found",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (file.delete()) {

            if (tempFile.renameTo(file)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Return date updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                file.renameTo(tempFile);

                JOptionPane.showMessageDialog(
                        null,
                        "Unable to replace borrow.csv.",
                        "File Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } else {
            tempFile.delete();

            JOptionPane.showMessageDialog(
                    null,
                    "Unable to access borrow.csv.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

