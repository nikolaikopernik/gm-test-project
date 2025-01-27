package nl.gerimedica.assignment;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String ssn;
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    public List<Appointment> appointments;

    public Patient() {
    }

    public Patient(String name, String ssn) {
        this.name = name;
        this.ssn = ssn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient patient)) return false;
        return Objects.equals(ssn, patient.ssn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ssn);
    }
}
