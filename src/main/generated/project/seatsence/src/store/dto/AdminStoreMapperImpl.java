package project.seatsence.src.store.dto;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.response.AdminStoreResponse;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2023-07-03T17:19:05+0900",
        comments =
                "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)")
@Component
public class AdminStoreMapperImpl implements AdminStoreMapper {

    @Override
    public AdminStoreResponse toDto(Store entity) {
        if (entity == null) {
            return null;
        }

        AdminStoreResponse adminStoreResponse = new AdminStoreResponse();

        adminStoreResponse.setId(entity.getId());
        adminStoreResponse.setName(entity.getName());
        adminStoreResponse.setIntroduction(entity.getIntroduction());
        adminStoreResponse.setLocation(entity.getLocation());
        adminStoreResponse.setMainImage(entity.getMainImage());
        adminStoreResponse.setTotalFloor(entity.getTotalFloor());
        adminStoreResponse.setCategory(entity.getCategory());
        adminStoreResponse.setMonOpenTime(entity.getMonOpenTime());
        adminStoreResponse.setMonCloseTime(entity.getMonCloseTime());
        adminStoreResponse.setTueOpenTime(entity.getTueOpenTime());
        adminStoreResponse.setTueCloseTime(entity.getTueCloseTime());
        adminStoreResponse.setWedOpenTime(entity.getWedOpenTime());
        adminStoreResponse.setWedCloseTime(entity.getWedCloseTime());
        adminStoreResponse.setThuOpenTime(entity.getThuOpenTime());
        adminStoreResponse.setThuCloseTime(entity.getThuCloseTime());
        adminStoreResponse.setFriOpenTime(entity.getFriOpenTime());
        adminStoreResponse.setFriCloseTime(entity.getFriCloseTime());
        adminStoreResponse.setSatOpenTime(entity.getSatOpenTime());
        adminStoreResponse.setSatCloseTime(entity.getSatCloseTime());
        adminStoreResponse.setSunOpenTime(entity.getSunOpenTime());
        adminStoreResponse.setSunCloseTime(entity.getSunCloseTime());
        adminStoreResponse.setBreakTime(entity.getBreakTime());
        adminStoreResponse.setUseTimeLimit(entity.getUseTimeLimit());
        adminStoreResponse.setMemo(entity.getMemo());
        adminStoreResponse.setAvgUseTime(entity.getAvgUseTime());
        adminStoreResponse.setCreatedBy(entity.getCreatedBy());
        adminStoreResponse.setLastModifiedBy(entity.getLastModifiedBy());
        adminStoreResponse.setCreatedAt(entity.getCreatedAt());
        adminStoreResponse.setUpdatedAt(entity.getUpdatedAt());
        adminStoreResponse.setState(entity.getState());

        adminStoreResponse.setDayOff(convertDayOff(entity.getDayOff()));
        adminStoreResponse.setWifiList(convertEntityWifiList(entity.getWifiList()));

        return adminStoreResponse;
    }

    @Override
    public Store toEntity(AdminStoreResponse dto) {
        if (dto == null) {
            return null;
        }

        Store store = new Store();

        store.setCreatedAt(dto.getCreatedAt());
        store.setUpdatedAt(dto.getUpdatedAt());
        store.setState(dto.getState());
        store.setId(dto.getId());
        store.setName(dto.getName());
        store.setIntroduction(dto.getIntroduction());
        store.setLocation(dto.getLocation());
        store.setMainImage(dto.getMainImage());
        store.setTotalFloor(dto.getTotalFloor());
        store.setCategory(dto.getCategory());
        store.setMonOpenTime(dto.getMonOpenTime());
        store.setMonCloseTime(dto.getMonCloseTime());
        store.setTueOpenTime(dto.getTueOpenTime());
        store.setTueCloseTime(dto.getTueCloseTime());
        store.setWedOpenTime(dto.getWedOpenTime());
        store.setWedCloseTime(dto.getWedCloseTime());
        store.setThuOpenTime(dto.getThuOpenTime());
        store.setThuCloseTime(dto.getThuCloseTime());
        store.setFriOpenTime(dto.getFriOpenTime());
        store.setFriCloseTime(dto.getFriCloseTime());
        store.setSatOpenTime(dto.getSatOpenTime());
        store.setSatCloseTime(dto.getSatCloseTime());
        store.setSunOpenTime(dto.getSunOpenTime());
        store.setSunCloseTime(dto.getSunCloseTime());
        store.setBreakTime(dto.getBreakTime());
        store.setUseTimeLimit(dto.getUseTimeLimit());
        store.setMemo(dto.getMemo());
        store.setAvgUseTime(dto.getAvgUseTime());

        store.setDayOff(convertDayOff(dto.getDayOff()));
        store.setWifiList(convertDtoWifiList(dto.getWifiList()));

        return store;
    }
}
