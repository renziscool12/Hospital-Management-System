import java.util.*;

public class HospitalSystem {
    ArrayList<Patient> patients = new ArrayList<>();

    // Add Patients
    void addPatient(Patient p) {
        patients.add(p);
    }

    // Read Patients
    public ArrayList<Patient> getAllPatients() {
        return patients;
    }

    // Update Patient
    void updatePatient(int index, Patient patient) {
        if (index >= 0 && index < patients.size()) {
            Patient patientt = patients.get(index);
            patientt.setPatientName(patient.getPatientName());
            patientt.setPatientAge(patient.getPatientAge());
            patientt.setPatientGender(patient.getPatientGender());
        }
    }

    // Remove patient
    void removePatient(int index) {
        if (index >= 0 && index < patients.size()) {
            patients.remove(index);
        }
    }

    // Finds the ID of a patient
    public Patient findById(String id) {
        for (Patient p : patients) {
            if (p.getPatientId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}
