package uz.aknb.db.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import uz.aknb.db.dto.auth.RegisterRequest;
import uz.aknb.db.entity.EntUser;
import uz.aknb.db.entity.UserDetails;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDetails toUserDetails(EntUser user);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    void update(@MappingTarget EntUser user, RegisterRequest request);

    EntUser toEntity(RegisterRequest source);
}
