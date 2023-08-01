package br.com.jlgregorio.rentacar.mapper;

import org.modelmapper.ModelMapper;

public class CustomModelMapper {

    private static ModelMapper mapper = new ModelMapper();

    public static<Origin, Destination> Destination parseObject(Origin origin, Class<Destination> destination){
        return mapper.map(origin, destination);
    }

}
