package project.seatsence.src.store.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.global.mapper.GenericMapper;
import project.seatsence.global.utils.EnumUtils;
import project.seatsence.src.store.domain.Day;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreWifi;
import project.seatsence.src.store.dto.response.AdminStoreResponse;

@Mapper(componentModel = "spring")
public interface StoreMapper extends GenericMapper<AdminStoreResponse, Store> {

    @Mapping(target = "dayOff", expression = "java(convertDayOff(entity.getDayOff()))")
    @Mapping(target = "wifiList", expression = "java(convertEntityWifiList(entity.getWifiList()))")
    @Override
    AdminStoreResponse toDto(Store entity);

    @Mapping(target = "dayOff", expression = "java(convertDayOff(dto.getDayOff()))")
    @Mapping(target = "wifiList", expression = "java(convertDtoWifiList(dto.getWifiList()))")
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

    default List<String> convertEntityWifiList(List<StoreWifi> wifiList) {
        List<String> dtoWifiList = new ArrayList<>();
        for (StoreWifi storeWifi : wifiList) {
            if (storeWifi.getState() == BaseTimeAndStateEntity.State.ACTIVE)
                dtoWifiList.add(storeWifi.getWifi());
        }
        return dtoWifiList;
    }

    default List<StoreWifi> convertDtoWifiList(List<String> wifiList) {
        return new ArrayList<>();
    }
}
