/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toolsinventory;
import java.io.*;
import java.util.*;
/**
 *
 * @author obana
 */
public class CSVBK {
      private static final String FILE_NAME = "SMAWNC1.csv";

    public static void updateStatus(String toolName, String newStatus) {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",", -1);

                if (firstLine) {
                    data.add(parts);
                    firstLine = false;
                    continue;
                }

                if (parts.length >= 3 &&
                    parts[0].trim().equalsIgnoreCase(toolName.trim())) {

                    parts[2] = newStatus;
                }

                data.add(parts);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {

            for (String[] row : data) {
                pw.println(String.join(",", row));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadTools(javax.swing.JTable table) {

        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) table.getModel();

        model.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",", -1);

                if (parts.length >= 3) {
                    model.addRow(new Object[]{
                        parts[0],
                        parts[1],
                        parts[2]
                    });
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
