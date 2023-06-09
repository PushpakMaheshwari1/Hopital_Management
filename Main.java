import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class HospitalException extends Exception {
    public HospitalException(String message) {
        super(message);
    }
}

abstract class Person {
    private String name;
    private int age;
    private String contactNumber;

    public Person(String name, int age, String contactNumber) {
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public abstract String getId();

    public abstract void displayDetails();
}

class Patient extends Person {
    private String patientId;
    private String medicalHistory;

    public Patient(String patientId, String name, int age, String contactNumber, String medicalHistory) {
        super(name, age, contactNumber);
        this.patientId = patientId;
        this.medicalHistory = medicalHistory;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    @Override
    public String getId() {
        return patientId;
    }

    @Override
    public void displayDetails() {
        System.out.println("Patient Details:");
        System.out.println("Patient ID: " + getPatientId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Contact Number: " + getContactNumber());
        System.out.println("Medical History: " + getMedicalHistory());
        System.out.println();
    }
}

class Doctor extends Person {
    private String doctorId;
    private String specialization;

    public Doctor(String doctorId, String name, int age, String contactNumber, String specialization) {
        super(name, age, contactNumber);
        this.doctorId = doctorId;
        this.specialization = specialization;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String getId() {
        return doctorId;
    }

    @Override
    public void displayDetails() {
        System.out.println("Doctor Details:");
        System.out.println("Doctor ID: " + getDoctorId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Contact Number: " + getContactNumber());
        System.out.println("Specialization: " + getSpecialization());
        System.out.println();
    }
}

class Appointment {
    private Patient patient;
    private Doctor doctor;
    private String date;

    public Appointment(Patient patient, Doctor doctor, String date) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment Details:\n" +
                "Patient: " + patient.getName() + " (Patient ID: " + patient.getPatientId() + ")\n" +
                "Doctor: " + doctor.getName() + " (Doctor ID: " + doctor.getDoctorId() + ")\n" +
                "Date: " + date;
    }
}

class Hospital {
    private List<Patient> patients;
    private List<Doctor> doctors;
    private List<Appointment> appointments;

    public Hospital() {
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    public void addPatient(Patient patient) throws HospitalException {
        if (patient != null) {
            if (!patients.contains(patient)) {
                patients.add(patient);
                System.out.println("Patient added successfully.");
            } else {
                throw new HospitalException("Patient with ID " + patient.getPatientId() + " already exists.");
            }
        }
    }

    public void addDoctor(Doctor doctor) throws HospitalException {
        if (doctor != null) {
            if (!doctors.contains(doctor)) {
                doctors.add(doctor);
                System.out.println("Doctor added successfully.");
            } else {
                throw new HospitalException("Doctor with ID " + doctor.getDoctorId() + " already exists.");
            }
        }
    }

    public void createAppointment(Patient patient, String doctorId, String date) throws HospitalException {
        if (patient != null) {
            // Find the doctor
            Doctor doctor = null;
            for (Doctor d : doctors) {
                if (d.getDoctorId().equals(doctorId)) {
                    doctor = d;
                    break;
                }
            }

            // Check if the doctor exists
            if (doctor == null) {
                throw new HospitalException("Doctor with ID " + doctorId + " does not exist.");
            }

            // Create the appointment
            Appointment appointment = new Appointment(patient, doctor, date);
            appointments.add(appointment);
            System.out.println("Appointment created successfully.");
        }
    }

    public void updatePatientDetails(String patientId) throws HospitalException {
        // Find the patient
        Patient patient = null;
        for (Patient p : patients) {
            if (p.getPatientId().equals(patientId)) {
                patient = p;
                break;
            }
        }

        // Check if the patient exists
        if (patient == null) {
            throw new HospitalException("Patient with ID " + patientId + " does not exist.");
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the new details for the patient:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Contact Number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Medical History: ");
        String medicalHistory = scanner.nextLine();

        patient = new Patient(patientId, name, age, contactNumber, medicalHistory);
        patients.removeIf(p -> p.getPatientId().equals(patientId));
        patients.add(patient);
        System.out.println("Patient details updated successfully.");
    }

    public void updateDoctorDetails(String doctorId) throws HospitalException {
        // Find the doctor
        Doctor doctor = null;
        for (Doctor d : doctors) {
            if (d.getDoctorId().equals(doctorId)) {
                doctor = d;
                break;
            }
        }

        // Check if the doctor exists
        if (doctor == null) {
            throw new HospitalException("Doctor with ID " + doctorId + " does not exist.");
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the new details for the doctor:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Contact Number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Specialization: ");
        String specialization = scanner.nextLine();

        doctor = new Doctor(doctorId, name, age, contactNumber, specialization);
        doctors.removeIf(d -> d.getDoctorId().equals(doctorId));
        doctors.add(doctor);
        System.out.println("Doctor details updated successfully.");
    }

    public void displayPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients available.");
        } else {
            System.out.println("Patients:");
            for (Patient patient : patients) {
                patient.displayDetails();
            }
        }
    }

    public void displayDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("No doctors available.");
        } else {
            System.out.println("Doctors:");
            for (Doctor doctor : doctors) {
                doctor.displayDetails();
            }
        }
    }

    public void displayAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
        } else {
            System.out.println("Appointments:");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
                System.out.println("---------------------------");
            }
        }
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }
}

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Hospital Management System");
            System.out.println("--------------------------");
            System.out.println("1. Add Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. Create Appointment");
            System.out.println("4. Update Patient Details");
            System.out.println("5. Update Doctor Details");
            System.out.println("6. Display Patients");
            System.out.println("7. Display Doctors");
            System.out.println("8. Display Appointments");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    try {
                        System.out.println("Adding a Patient:");
                        System.out.print("Enter the patient ID: ");
                        String patientId = scanner.nextLine();
                        System.out.print("Enter the patient's name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter the patient's age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        System.out.print("Enter the patient's contact number: ");
                        String contactNumber = scanner.nextLine();
                        System.out.print("Enter the patient's medical history: ");
                        String medicalHistory = scanner.nextLine();

                        hospital.addPatient(new Patient(patientId, name, age, contactNumber, medicalHistory));
                    } catch (HospitalException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Adding a Doctor:");
                        System.out.print("Enter the doctor ID: ");
                        String doctorId = scanner.nextLine();
                        System.out.print("Enter the doctor's name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter the doctor's age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        System.out.print("Enter the doctor's contact number: ");
                        String contactNumber = scanner.nextLine();
                        System.out.print("Enter the doctor's specialization: ");
                        String specialization = scanner.nextLine();

                        hospital.addDoctor(new Doctor(doctorId, name, age, contactNumber, specialization));
                    } catch (HospitalException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Creating an Appointment:");
                        System.out.print("Enter the patient ID: ");
                        String patientId = scanner.nextLine();

                        // Retrieve the patient with the specified ID
                        Patient selectedPatient = null;
                        for (Patient patient : hospital.getPatients()) {
                            if (patient.getPatientId().equals(patientId)) {
                                selectedPatient = patient;
                                break;
                            }
                        }

                        if (selectedPatient == null) {
                            throw new HospitalException("Patient with ID " + patientId + " does not exist.");
                        }

                        System.out.print("Enter the doctor ID: ");
                        String doctorId = scanner.nextLine();
                        System.out.print("Enter the appointment date: ");
                        String date = scanner.nextLine();

                        hospital.createAppointment(selectedPatient, doctorId.trim(), date.trim());
                    } catch (HospitalException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        System.out.println("Updating Patient Details:");
                        System.out.print("Enter the patient ID: ");
                        String patientId = scanner.nextLine();
                        hospital.updatePatientDetails(patientId.trim());
                    } catch (HospitalException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        System.out.println("Updating Doctor Details:");
                        System.out.print("Enter the doctor ID: ");
                        String doctorId = scanner.nextLine();
                        hospital.updateDoctorDetails(doctorId.trim());
                    } catch (HospitalException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    hospital.displayPatients();
                    break;
                case 7:
                    hospital.displayDoctors();
                    break;
                case 8:
                    hospital.displayAppointments();
                    break;
                case 9:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        }
    }
}
