package project.seatsence.src.store.dto;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.response.AdminStoreResponse;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2023-08-17T19:24:36+0900",
        comments =
                "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)")
@Component
public class AdminStoreMapperImpl implements AdminStoreMapper {

    @Override
    public AdminStoreResponse toDto(Store entity) {
        if (entity == null) {
            return null;
        }

        AdminStoreResponse.AdminStoreResponseBuilder adminStoreResponse =
                AdminStoreResponse.builder();

        adminStoreResponse.id(entity.getId());
        adminStoreResponse.storeName(entity.getStoreName());
        adminStoreResponse.introduction(entity.getIntroduction());
        adminStoreResponse.address(entity.getAddress());
        adminStoreResponse.detailAddress(entity.getDetailAddress());
        adminStoreResponse.mainImage(entity.getMainImage());
        adminStoreResponse.category(entity.getCategory());
        adminStoreResponse.monOpenTime(entity.getMonOpenTime());
        adminStoreResponse.monCloseTime(entity.getMonCloseTime());
        adminStoreResponse.tueOpenTime(entity.getTueOpenTime());
        adminStoreResponse.tueCloseTime(entity.getTueCloseTime());
        adminStoreResponse.wedOpenTime(entity.getWedOpenTime());
        adminStoreResponse.wedCloseTime(entity.getWedCloseTime());
        adminStoreResponse.thuOpenTime(entity.getThuOpenTime());
        adminStoreResponse.thuCloseTime(entity.getThuCloseTime());
        adminStoreResponse.friOpenTime(entity.getFriOpenTime());
        adminStoreResponse.friCloseTime(entity.getFriCloseTime());
        adminStoreResponse.satOpenTime(entity.getSatOpenTime());
        adminStoreResponse.satCloseTime(entity.getSatCloseTime());
        adminStoreResponse.sunOpenTime(entity.getSunOpenTime());
        adminStoreResponse.sunCloseTime(entity.getSunCloseTime());
        adminStoreResponse.breakTime(entity.getBreakTime());
        adminStoreResponse.useTimeLimit(entity.getUseTimeLimit());
        adminStoreResponse.avgUseTime(entity.getAvgUseTime());
        adminStoreResponse.createdBy(entity.getCreatedBy());
        adminStoreResponse.lastModifiedBy(entity.getLastModifiedBy());
        adminStoreResponse.createdAt(entity.getCreatedAt());
        adminStoreResponse.updatedAt(entity.getUpdatedAt());
        adminStoreResponse.state(entity.getState());

        adminStoreResponse.dayOff(convertDayOff(entity.getDayOff()));
        adminStoreResponse.wifiList(convertEntityWifiList(entity.getWifiList()));

        return adminStoreResponse.build();
    }

    @Override
    public Store toEntity(AdminStoreResponse dto) {
        if (dto == null) {
            return null;
        }

        Store.StoreBuilder store = Store.builder();

        store.id(dto.getId());
        store.storeName(dto.getStoreName());
        store.introduction(dto.getIntroduction());
        store.address(dto.getAddress());
        store.detailAddress(dto.getDetailAddress());
        store.mainImage(dto.getMainImage());
        store.category(dto.getCategory());
        store.monOpenTime(dto.getMonOpenTime());
        store.monCloseTime(dto.getMonCloseTime());
        store.tueOpenTime(dto.getTueOpenTime());
        store.tueCloseTime(dto.getTueCloseTime());
        store.wedOpenTime(dto.getWedOpenTime());
        store.wedCloseTime(dto.getWedCloseTime());
        store.thuOpenTime(dto.getThuOpenTime());
        store.thuCloseTime(dto.getThuCloseTime());
        store.friOpenTime(dto.getFriOpenTime());
        store.friCloseTime(dto.getFriCloseTime());
        store.satOpenTime(dto.getSatOpenTime());
        store.satCloseTime(dto.getSatCloseTime());
        store.sunOpenTime(dto.getSunOpenTime());
        store.sunCloseTime(dto.getSunCloseTime());
        store.breakTime(dto.getBreakTime());
        store.useTimeLimit(dto.getUseTimeLimit());
        store.avgUseTime(dto.getAvgUseTime());

        store.dayOff(convertDayOff(dto.getDayOff()));
        store.wifiList(convertDtoWifiList(dto.getWifiList()));

        return store.build();
    }
}
