package project.seatsence.src.store.dto;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.response.StoreDetailResponse;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2023-07-03T17:19:06+0900",
        comments =
                "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)")
@Component
public class StoreMapperImpl implements StoreMapper {

    @Override
    public StoreDetailResponse toDto(Store entity) {
        if (entity == null) {
            return null;
        }

        StoreDetailResponse storeDetailResponse = new StoreDetailResponse();

        storeDetailResponse.setId(entity.getId());
        storeDetailResponse.setName(entity.getName());
        storeDetailResponse.setLocation(entity.getLocation());
        storeDetailResponse.setIntroduction(entity.getIntroduction());
        storeDetailResponse.setCategory(entity.getCategory());
        storeDetailResponse.setMainImage(entity.getMainImage());
        storeDetailResponse.setMonOpenTime(entity.getMonOpenTime());
        storeDetailResponse.setMonCloseTime(entity.getMonCloseTime());
        storeDetailResponse.setTueOpenTime(entity.getTueOpenTime());
        storeDetailResponse.setTueCloseTime(entity.getTueCloseTime());
        storeDetailResponse.setWedOpenTime(entity.getWedOpenTime());
        storeDetailResponse.setThuOpenTime(entity.getThuOpenTime());
        storeDetailResponse.setThuCloseTime(entity.getThuCloseTime());
        storeDetailResponse.setFriOpenTime(entity.getFriOpenTime());
        storeDetailResponse.setFriCloseTime(entity.getFriCloseTime());
        storeDetailResponse.setSatOpenTime(entity.getSatOpenTime());
        storeDetailResponse.setSatCloseTime(entity.getSatCloseTime());
        storeDetailResponse.setSunOpenTime(entity.getSunOpenTime());
        storeDetailResponse.setSunCloseTime(entity.getSunCloseTime());

        storeDetailResponse.setDayOff(convertDayOff(entity.getDayOff()));

        return storeDetailResponse;
    }

    @Override
    public Store toEntity(StoreDetailResponse dto) {
        if (dto == null) {
            return null;
        }

        Store store = new Store();

        store.setId(dto.getId());
        store.setName(dto.getName());
        store.setIntroduction(dto.getIntroduction());
        store.setLocation(dto.getLocation());
        store.setMainImage(dto.getMainImage());
        store.setCategory(dto.getCategory());
        store.setMonOpenTime(dto.getMonOpenTime());
        store.setMonCloseTime(dto.getMonCloseTime());
        store.setTueOpenTime(dto.getTueOpenTime());
        store.setTueCloseTime(dto.getTueCloseTime());
        store.setWedOpenTime(dto.getWedOpenTime());
        store.setThuOpenTime(dto.getThuOpenTime());
        store.setThuCloseTime(dto.getThuCloseTime());
        store.setFriOpenTime(dto.getFriOpenTime());
        store.setFriCloseTime(dto.getFriCloseTime());
        store.setSatOpenTime(dto.getSatOpenTime());
        store.setSatCloseTime(dto.getSatCloseTime());
        store.setSunOpenTime(dto.getSunOpenTime());
        store.setSunCloseTime(dto.getSunCloseTime());

        store.setDayOff(convertDayOff(dto.getDayOff()));

        return store;
    }
}
