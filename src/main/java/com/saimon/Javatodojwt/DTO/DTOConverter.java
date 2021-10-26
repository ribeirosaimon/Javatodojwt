package com.saimon.Javatodojwt.DTO;

import com.saimon.Javatodojwt.model.WorkToDo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class DTOConverter {

    public Optional<WorkDTO> converterDTO(WorkToDo work) throws Exception {
        try {
            WorkDTO workDTO = new WorkDTO(work.getDatetime(), work.getHomeWork(), work.isChecked());
            return Optional.of(workDTO);
        } catch (Exception e) {
            throw new Exception("Error converter DTO");
        }
    }

    public Optional<ArrayList<WorkDTO>> convertDTOList(Collection<WorkToDo> works) throws Exception {
        ArrayList<WorkDTO> listWork = new ArrayList<>();
        try {
            for (WorkToDo work : works) {
                listWork.add(new WorkDTO(work.getDatetime(), work.getHomeWork(), work.isChecked()));
            }
            return Optional.of(listWork);
        } catch (Exception e) {
            throw new Exception("Error converter DTO list");
        }

    }
}
