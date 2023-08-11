package project.seatsence.src.store.dto;

import java.util.Collections;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.seatsence.global.mapper.GenericMapper;
import project.seatsence.global.utils.EnumUtils;
import project.seatsence.src.store.domain.Day;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.response.StoreDetailResponse;

@Mapper(componentModel = "spring")
public interface StoreMapper extends GenericMapper<StoreDetailResponse, Store> {

    @Mapping(target = "dayOff", expression = "java(convertDayOff(entity.getDayOff()))")
    @Override
    StoreDetailResponse toDto(Store entity);

    @Mapping(target = "dayOff", expression = "java(convertDayOff(dto.getDayOff()))")
    @Override
    Store toEntity(StoreDetailResponse dto);

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
