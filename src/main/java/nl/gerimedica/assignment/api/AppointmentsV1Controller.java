package nl.gerimedica.assignment.api;

import nl.gerimedica.assignment.services.HospitalMetrics;
import nl.gerimedica.assignment.api.model.AppointmentsV1Response;
import nl.gerimedica.assignment.api.model.BulkAppointmentsV1CreationRequest;
import nl.gerimedica.assignment.repositories.model.Appointment;
import nl.gerimedica.assignment.services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AppointmentsV1Controller {

    @Autowired
    HospitalService hospitalService;

    @Autowired
    HospitalMetrics metrics;

    /**
     * Example: {
     * "personName":"name",
     * "ssn": "1234"
     * "appointments": [
     *   {
     *     "reason": "Checkup",
     *     "date": "2025-02-01"
     *   },..
     *  ]
     * }
     */
    @PostMapping("/appointments/bulk")
    public AppointmentsV1Response createBulkAppointments(@RequestBody BulkAppointmentsV1CreationRequest payload) {
        metrics.recordUsage("Controller triggered bulk appointments creation");

        List<Appointment> created = hospitalService.bulkCreateAppointments(payload.getPatientName(),
                payload.getSsn(),
                payload.getAppointments());
        return new AppointmentsV1Response(created);
    }

    @GetMapping("/appointments")
    public AppointmentsV1Response getAppointmentsByReason(@RequestParam String reason) {
        List<Appointment> found = hospitalService.getAppointmentsByReason(reason);
        return new AppointmentsV1Response(found);
    }

    @DeleteMapping("/appointments")
    public String deleteAppointmentsBySSN(@RequestParam String ssn) {
        hospitalService.deleteAppointmentsBySSN(ssn);
        return "Deleted all appointments for SSN: " + ssn;
    }

    @GetMapping("/appointments/latest")
    public Appointment getLatestAppointment(@RequestParam String ssn) {
        return hospitalService.findLatestAppointmentBySSN(ssn);
    }
}
