package br.com.jlgregorio.rentacar.controller;

import br.com.jlgregorio.rentacar.dto.ProfessionDto;
import br.com.jlgregorio.rentacar.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/professions/v1")
public class ProfessionController {

    @Autowired
    private ProfessionService service;

    @PostMapping
    public ResponseEntity<ProfessionDto> create(@RequestBody ProfessionDto professionDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(professionDto));
    }

    @PutMapping
    public ResponseEntity<ProfessionDto> update(@RequestBody ProfessionDto professionDto){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(professionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionDto> findById(@PathVariable int id){
        ProfessionDto dto = service.findById(id);
        buildEntityLink(dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ProfessionDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<ProfessionDto> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        Page<ProfessionDto> professions = service.findAll(pageable);
        for (ProfessionDto profession : professions) {
            buildEntityLink(profession);
        }
        return new ResponseEntity(assembler.toModel(professions), HttpStatus.OK);
    }
    @GetMapping("/find")
    public ResponseEntity<PagedModel<ProfessionDto>> findByName(
            @RequestParam(value = "name", defaultValue = "%") String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<ProfessionDto> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        Page<ProfessionDto> professions = service.findByName(name, pageable);

        for (ProfessionDto profession : professions) {
            buildEntityLink(profession);
        }

        return new ResponseEntity(assembler.toModel(professions), HttpStatus.OK);
    }

    public void buildEntityLink(ProfessionDto professionDto){
        professionDto.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(this.getClass()).findById(professionDto.getId())
                ).withSelfRel()
        );
    }


}
