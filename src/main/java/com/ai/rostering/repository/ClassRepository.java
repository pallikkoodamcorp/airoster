package com.ai.rostering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.rostering.model.Classroom;

/**
 * The interface User repository.
 *
 * @author karthickumarvp
 */
@Repository
public interface ClassRepository extends JpaRepository<Classroom, Long> {

    public Classroom findClassroomByClassId(String classId);
    
    public List<Classroom> findClassroomByPid(String pid);
}
