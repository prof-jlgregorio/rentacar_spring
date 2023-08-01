package br.com.jlgregorio.rentacar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Relation(itemRelation = "profession", collectionRelation = "professions")
public class ProfessionDto extends RepresentationModel {

    private int id;
    private String name;


}
