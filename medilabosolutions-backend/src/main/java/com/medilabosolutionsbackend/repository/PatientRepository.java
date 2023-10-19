package com.medilabosolutionsbackend.repository;

import com.medilabosolutionsbackend.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
