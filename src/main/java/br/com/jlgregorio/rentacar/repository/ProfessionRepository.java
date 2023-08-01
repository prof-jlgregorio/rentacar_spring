package br.com.jlgregorio.rentacar.repository;

import br.com.jlgregorio.rentacar.model.ProfessionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionRepository extends JpaRepository<ProfessionModel, Integer> {

    public Page<ProfessionModel> findByNameContainsIgnoreCaseOrderByName(String name, Pageable pageable);

    public Page<ProfessionModel> findAll(Pageable pageable);


}
