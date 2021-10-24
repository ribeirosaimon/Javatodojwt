package com.saimon.Javatodojwt.repository;

import com.saimon.Javatodojwt.model.WorkToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkToDoRepository extends JpaRepository<WorkToDo, Long> {
    WorkToDo findByHomeWork(String work);

}
