package nl.gerimedica.assignment.services;

import lombok.extern.slf4j.Slf4j;
import nl.gerimedica.assignment.repositories.AppointmentRepository;
import nl.gerimedica.assignment.repositories.PatientRepository;
import nl.gerimedica.assignment.repositories.model.AppointmentDto;
import nl.gerimedica.assignment.repositories.model.PatientDto;
import nl.gerimedica.assignment.services.model.Appointment;
import nl.gerimedica.assignment.services.model.BulkAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public List<Appointment> bulkCreateAppointments(String patientName,
                                                    String ssn,
                                                    List<BulkAppointment> appointments) {
        var found = patientRepo.findBySsn(ssn);
        if (found == null) {
            log.info("Creating new patient with SSN: {}", ssn);
            found = new PatientDto(patientName, ssn);
            patientRepo.save(found);
        } else {
            log.info("Existing patient found, SSN: {}", found.ssn);
        }

        // at the same time another request can create the same user
        // relying on DB and unique constraint
        var patient = found;
        List<AppointmentDto> createdAppointments = appointments.stream()
                .map(it -> new AppointmentDto(it.getReason(), it.getDate(), patient))
                .collect(Collectors.toList());

        for (AppointmentDto appt : createdAppointments) {
            appointmentRepo.save(appt);
        }

        for (AppointmentDto appt : createdAppointments) {
            log.info("Created appointment for reason: {} [Date: {}] [Patient SSN: {}]", appt.reason, appt.date, appt.patient.ssn);
        }

        metrics.recordUsage("Bulk create appointments");

        return to(createdAppointments);
    }

    private List<Appointment> to(List<AppointmentDto> list){
        return list.stream()
                .map(it -> new Appointment(it.id,
                        it.reason,
                        it.date,
                        it.patient.name,
                        it.patient.ssn))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByReason(String reasonKeyword) {
        List<AppointmentDto> result = appointmentRepo.findByReason(reasonKeyword);
        metrics.recordUsage("Get appointments by reason");
        return to(result);
    }

    public void deleteAppointmentsBySSN(String ssn) {
        PatientDto patient = patientRepo.findBySsn(ssn);
        if (patient == null) {
            return;
        }
        List<AppointmentDto> appointments = patient.appointments;
        appointmentRepo.deleteAll(appointments);
    }

    public Appointment findLatestAppointmentBySSN(String ssn) {
        PatientDto patient = patientRepo.findBySsn(ssn);
        if (patient == null || patient.appointments == null || patient.appointments.isEmpty()) {
            return null;
        }

        AppointmentDto latest = null;
        for (AppointmentDto appt : patient.appointments) {
            if (latest == null) {
                latest = appt;
            } else {
                if (appt.date.compareTo(latest.date) > 0) {
                    latest = appt;
                }
            }
        }

        return new Appointment(latest.id, latest.reason, latest.date, latest.patient.name, latest.patient.ssn);
    }
}
