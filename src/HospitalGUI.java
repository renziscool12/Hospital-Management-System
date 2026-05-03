import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HospitalGUI extends JFrame {

    HospitalSystem hospital = new HospitalSystem();
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JTextField searchField;

    public HospitalGUI() {
        setTitle("Hospital System Management");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        String[] columns = { "ID", "Name", "Age", "Gender", "Complete" };
        model.setColumnIdentifiers(columns);

        // Layout
        setLayout(new BorderLayout());

        // Table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 30, 3, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        JButton exitButton = new JButton("Exit");
        JButton searchButton = new JButton("Search");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(deleteButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(updateButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        panel.add(exitButton, gbc);
        add(panel, BorderLayout.SOUTH);

        JPanel topPanel = new JPanel();
        searchField = new JTextField(10);

        topPanel.add(new JLabel("Search ID:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        addButton.addActionListener(e -> {
            new HopsitalForm(this, "Add Patient", null).setVisible(true);
        });

        updateButton.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                Patient selectedPatient = hospital.getAllPatients().get(row);
                new HopsitalForm(this, "Update Patient", selectedPatient).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a patient to update.");
            }
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                hospital.removePatient(row);
                refreshTable();
                model.removeRow(row);
            }
        });

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        searchButton.addActionListener(e -> {
            searchPatient();
        });

        searchField.addActionListener(e -> {
            searchPatient();
        });
    }

    public void refreshTable() {
        model.setRowCount(0);
        for (Patient p : hospital.getAllPatients()) {
            model.addRow(new Object[] {
                    p.getPatientId(),
                    p.getPatientName(),
                    p.getPatientAge(),
                    p.getPatientGender(),
                    p.isCompleted() ? "Checked [/]" : "Unchecked [X]"
            });
        }
        table.clearSelection();
    }

    private void searchPatient() {
        String searchId = searchField.getText();
        Patient p = hospital.findById(searchId);

        if (p != null) {
            JOptionPane.showMessageDialog(this, p.getPatientInfo());
        } else {
            JOptionPane.showMessageDialog(this, "ID not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    public HospitalSystem getHospital() {
        return hospital;
    }

    public static void main(String[] args) {
        new HospitalGUI().setVisible(true);
    }
}
