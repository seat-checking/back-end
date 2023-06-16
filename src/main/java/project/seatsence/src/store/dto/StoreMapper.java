package project.seatsence.src.store.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.seatsence.global.mapper.GenericMapper;
import project.seatsence.global.util.EnumUtils;
import project.seatsence.src.store.domain.Day;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.response.AdminStoreResponse;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreMapper extends GenericMapper<AdminStoreResponse, Store> {

    @Mapping(target = "dayOff", expression = "java(convertDayOff(entity.getDayOff()))")
    @Override
    AdminStoreResponse toDto(Store entity);

    @Mapping(target = "dayOff", expression = "java(convertDayOff(dto.getDayOff()))")
    @Override
    Store toEntity(AdminStoreResponse dto);

    default List<Day> convertDayOff(String dayOff) {
        if (dayOff == null || dayOff.isEmpty()) {
            return Collections.emptyList();
        }
        return EnumUtils.getEnumListFromString(dayOff, Day.class);
    }


    default String convertDayOff(List<Day> dayOff) {
        if (dayOff == null || dayOff.isEmpty()) {
            return null;
        }
        return EnumUtils.getStringFromEnumList(dayOff);
    }

}
