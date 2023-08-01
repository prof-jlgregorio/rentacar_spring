package br.com.jlgregorio.rentacar.service;

import br.com.jlgregorio.rentacar.dto.ProfessionDto;
import br.com.jlgregorio.rentacar.exception.ResourceNotFoundException;
import br.com.jlgregorio.rentacar.mapper.CustomModelMapper;
import br.com.jlgregorio.rentacar.model.ProfessionModel;
import br.com.jlgregorio.rentacar.repository.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfessionService {

    @Autowired
    private ProfessionRepository repository;

    public ProfessionDto create(ProfessionDto professionDto){
       ProfessionModel model = CustomModelMapper.parseObject(professionDto, ProfessionModel.class);
       return CustomModelMapper.parseObject(repository.save(model), ProfessionDto.class);
    }

    public ProfessionDto findById(int id){
        ProfessionModel found = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profissão não encontrada!"));
        return CustomModelMapper.parseObject(found, ProfessionDto.class);
    }

    public ProfessionDto update(ProfessionDto professionDto){
        ProfessionModel found = repository.findById(professionDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Profissão não encontrada!"));
        found = CustomModelMapper.parseObject(professionDto, ProfessionModel.class);
        return CustomModelMapper.parseObject(repository.save(found), ProfessionDto.class);
    }

    public void delete(int id){
        ProfessionModel found = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Profissão não encontrada!"));
        repository.delete(found);
    }

    public Page<ProfessionDto> findAll(Pageable pageable){
        var page = repository.findAll(pageable);
        return page.map(obj -> CustomModelMapper.parseObject(obj, ProfessionDto.class));
    }

    public Page<ProfessionDto> findByName(String name, Pageable pageable){
        var page = repository.findByNameContainsIgnoreCaseOrderByName(name, pageable);
        return page.map(obj -> CustomModelMapper.parseObject(obj, ProfessionDto.class));
    }


}
