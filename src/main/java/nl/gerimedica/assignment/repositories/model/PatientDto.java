package nl.gerimedica.assignment.repositories.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class PatientDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    @Column(unique = true)
    public String ssn;
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    public List<AppointmentDto> appointments;

    public PatientDto() {
    }

    public PatientDto(String name, String ssn) {
        this.name = name;
        this.ssn = ssn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientDto patient)) return false;
        return Objects.equals(ssn, patient.ssn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ssn);
    }
}
