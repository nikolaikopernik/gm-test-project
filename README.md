# Gerimedica Assignment

Welcome to this **Gerimedica** repository. This is a **Spring Boot** project built for an **assignment**. The code, while functional, **is not production-ready** and **may contain questionable or non-ideal implementations**. Part of the challenge is to **discover**, **review**, and **improve** these elements.

---

## What to Expect

- A **simple** REST API for managing `Patients` and their `Appointments`.
- Multiple classes (controllers, services, entities, and repositories).
- **Incomplete** or **inefficient** approaches to certain tasks.

---

## Glossary

Below are the primary entities you’ll find in this codebase:

1. **Patient**
    - Represents an individual in the hospital system.
    - Fields may include:
        - `id`: auto-generated primary key
        - `name`: name of the patient
        - `ssn`: Social Security Number (used here as a unique identifier)
        - `appointments`: a list of `Appointment` objects linked to this patient

2. **Appointment**
    - Represents a scheduled appointment or event for a patient.
    - Fields may include:
        - `id`: auto-generated primary key
        - `reason`: a textual reason for the appointment (e.g., “Checkup”)
        - `date`: the date of the appointment
        - `patient`: a reference to the `Patient` who owns this appointment

---

## Goals

1. **Explore the codebase**: Familiarize yourself with the structure and logic.
2. **Identify potential issues**: Think about security, performance, maintainability, design patterns, etc.
3. **Propose and/or implement improvements**: Refactor, rewrite, or reorganize parts of the code to showcase your approach.

---
## Improvements
1. Made integration test running on random port. Test still checks nothing.
2. Added layer based structure to the project.
3. API issues are resolved:
   * added versioning
   * applied REST methodology
   * moved some responses from returning List -> Object
   * removed mix of request params and payload object
4. Covered all the endpoints with tests
5. Used micrometer for metrics
6. Some improvements of the DB code
7. Separated domain model from JPA model
8. Using exceptions instead of just ignoring incorrect input data
