package com.example.modeling_tools.endpoint.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(imports = java.util.UUID.class)
public interface ModelingToolMapper {
    ModelingToolMapper INSTANCE = Mappers.getMapper(ModelingToolMapper.class);

}
