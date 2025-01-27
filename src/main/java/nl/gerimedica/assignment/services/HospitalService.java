package nl.gerimedica.assignment.services;

import lombok.extern.slf4j.Slf4j;
import nl.gerimedica.assignment.repositories.AppointmentRepository;
import nl.gerimedica.assignment.repositories.PatientRepository;
import nl.gerimedica.assignment.repositories.model.Appointment;
import nl.gerimedica.assignment.repositories.model.Patient;
import nl.gerimedica.assignment.services.model.BulkAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HospitalService {
    @Autowired
    PatientRepository patientRepo;
    @Autowired
    AppointmentRepository appointmentRepo;
    @Autowired
    HospitalMetrics metrics;

    public List<Appointment> bulkCreateAppointments(String patientName,
                                                    String ssn,
                                                    List<BulkAppointment> appointments) {
        var found = findPatientBySSN(ssn);
        if (found == null) {
            log.info("Creating new patient with SSN: {}", ssn);
            found = new Patient(patientName, ssn);
            savePatient(found);
        } else {
            log.info("Existing patient found, SSN: {}", found.ssn);
        }
        var patient = found;
        List<Appointment> createdAppointments = appointments.stream()
                .map(it -> new Appointment(it.getReason(), it.getDate(), patient))
                .collect(Collectors.toList());

        for (Appointment appt : createdAppointments) {
            appointmentRepo.save(appt);
        }

        for (Appointment appt : createdAppointments) {
            log.info("Created appointment for reason: {} [Date: {}] [Patient SSN: {}]", appt.reason, appt.date, appt.patient.ssn);
        }

        metrics.recordUsage("Bulk create appointments");

        return createdAppointments;
    }

    public Patient findPatientBySSN(String ssn) {
        List<Patient> all = patientRepo.findAll();
        for (Patient p : all) {
            if (p.ssn.equals(ssn)) {
                return p;
            }
        }
        return null;
    }

    @Transactional
    void savePatient(Patient patient) {
        patientRepo.save(patient);
    }

    public List<Appointment> getAppointmentsByReason(String reasonKeyword) {
        List<Appointment> allAppointments = appointmentRepo.findAll();
        List<Appointment> matched = new ArrayList<>();

        for (Appointment ap : allAppointments) {
            if (ap.reason.contains(reasonKeyword)) {
                matched.add(ap);
            }
        }

        List<Appointment> finalList = new ArrayList<>();
        for (Appointment ap : matched) {
            if (ap.reason.equalsIgnoreCase(reasonKeyword)) {
                finalList.add(ap);
            }
        }

        HospitalMetrics utils = new HospitalMetrics();
        utils.recordUsage("Get appointments by reason");

        return finalList;
    }

    public void deleteAppointmentsBySSN(String ssn) {
        Patient patient = findPatientBySSN(ssn);
        if (patient == null) {
            return;
        }
        List<Appointment> appointments = patient.appointments;
        appointmentRepo.deleteAll(appointments);
    }

    public Appointment findLatestAppointmentBySSN(String ssn) {
        Patient patient = findPatientBySSN(ssn);
        if (patient == null || patient.appointments == null || patient.appointments.isEmpty()) {
            return null;
        }

        Appointment latest = null;
        for (Appointment appt : patient.appointments) {
            if (latest == null) {
                latest = appt;
            } else {
                if (appt.date.compareTo(latest.date) > 0) {
                    latest = appt;
                }
            }
        }

        return latest;
    }
}
