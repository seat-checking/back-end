package project.seatsence.src.store.dto;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.response.AdminStoreResponse;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2023-06-19T17:03:43+0900",
        comments =
                "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)")
@Component
public class StoreMapperImpl implements StoreMapper {

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
        adminStoreResponse.setMonBusinessHours(entity.getMonBusinessHours());
        adminStoreResponse.setTueBusinessHours(entity.getTueBusinessHours());
        adminStoreResponse.setWedBusinessHours(entity.getWedBusinessHours());
        adminStoreResponse.setThuBusinessHours(entity.getThuBusinessHours());
        adminStoreResponse.setFriBusinessHours(entity.getFriBusinessHours());
        adminStoreResponse.setSatBusinessHours(entity.getSatBusinessHours());
        adminStoreResponse.setSunBusinessHours(entity.getSunBusinessHours());
        adminStoreResponse.setBreakTime(entity.getBreakTime());
        adminStoreResponse.setUseTimeLimit(entity.getUseTimeLimit());
        adminStoreResponse.setMemo(entity.getMemo());
        adminStoreResponse.setAvgUseTime(entity.getAvgUseTime());
        adminStoreResponse.setCreatedBy(entity.getCreatedBy());
        adminStoreResponse.setLastModifiedBy(entity.getLastModifiedBy());

        adminStoreResponse.setDayOff(convertDayOff(entity.getDayOff()));

        return adminStoreResponse;
    }

    @Override
    public Store toEntity(AdminStoreResponse dto) {
        if (dto == null) {
            return null;
        }

        Store store = new Store();

        store.setId(dto.getId());
        store.setName(dto.getName());
        store.setIntroduction(dto.getIntroduction());
        store.setLocation(dto.getLocation());
        store.setMainImage(dto.getMainImage());
        store.setTotalFloor(dto.getTotalFloor());
        store.setCategory(dto.getCategory());
        store.setMonBusinessHours(dto.getMonBusinessHours());
        store.setTueBusinessHours(dto.getTueBusinessHours());
        store.setWedBusinessHours(dto.getWedBusinessHours());
        store.setThuBusinessHours(dto.getThuBusinessHours());
        store.setFriBusinessHours(dto.getFriBusinessHours());
        store.setSatBusinessHours(dto.getSatBusinessHours());
        store.setSunBusinessHours(dto.getSunBusinessHours());
        store.setBreakTime(dto.getBreakTime());
        store.setUseTimeLimit(dto.getUseTimeLimit());
        store.setMemo(dto.getMemo());
        store.setAvgUseTime(dto.getAvgUseTime());

        store.setDayOff(convertDayOff(dto.getDayOff()));

        return store;
    }
}
