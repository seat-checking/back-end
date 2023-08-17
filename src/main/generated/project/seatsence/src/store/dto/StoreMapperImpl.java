package project.seatsence.src.store.dto;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.response.StoreDetailResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-17T19:24:36+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
@Component
public class StoreMapperImpl implements StoreMapper {

    @Override
    public StoreDetailResponse toDto(Store entity) {
        if ( entity == null ) {
            return null;
        }

        StoreDetailResponse storeDetailResponse = new StoreDetailResponse();

        storeDetailResponse.setId( entity.getId() );
        storeDetailResponse.setStoreName( entity.getStoreName() );
        storeDetailResponse.setAddress( entity.getAddress() );
        storeDetailResponse.setDetailAddress( entity.getDetailAddress() );
        storeDetailResponse.setIntroduction( entity.getIntroduction() );
        storeDetailResponse.setCategory( entity.getCategory() );
        storeDetailResponse.setMainImage( entity.getMainImage() );
        storeDetailResponse.setMonOpenTime( entity.getMonOpenTime() );
        storeDetailResponse.setMonCloseTime( entity.getMonCloseTime() );
        storeDetailResponse.setTueOpenTime( entity.getTueOpenTime() );
        storeDetailResponse.setTueCloseTime( entity.getTueCloseTime() );
        storeDetailResponse.setWedOpenTime( entity.getWedOpenTime() );
        storeDetailResponse.setWedCloseTime( entity.getWedCloseTime() );
        storeDetailResponse.setThuOpenTime( entity.getThuOpenTime() );
        storeDetailResponse.setThuCloseTime( entity.getThuCloseTime() );
        storeDetailResponse.setFriOpenTime( entity.getFriOpenTime() );
        storeDetailResponse.setFriCloseTime( entity.getFriCloseTime() );
        storeDetailResponse.setSatOpenTime( entity.getSatOpenTime() );
        storeDetailResponse.setSatCloseTime( entity.getSatCloseTime() );
        storeDetailResponse.setSunOpenTime( entity.getSunOpenTime() );
        storeDetailResponse.setSunCloseTime( entity.getSunCloseTime() );

        storeDetailResponse.setDayOff( convertDayOff(entity.getDayOff()) );

        return storeDetailResponse;
    }

    @Override
    public Store toEntity(StoreDetailResponse dto) {
        if ( dto == null ) {
            return null;
        }

        Store.StoreBuilder store = Store.builder();

        store.id( dto.getId() );
        store.storeName( dto.getStoreName() );
        store.introduction( dto.getIntroduction() );
        store.address( dto.getAddress() );
        store.detailAddress( dto.getDetailAddress() );
        store.mainImage( dto.getMainImage() );
        store.category( dto.getCategory() );
        store.monOpenTime( dto.getMonOpenTime() );
        store.monCloseTime( dto.getMonCloseTime() );
        store.tueOpenTime( dto.getTueOpenTime() );
        store.tueCloseTime( dto.getTueCloseTime() );
        store.wedOpenTime( dto.getWedOpenTime() );
        store.wedCloseTime( dto.getWedCloseTime() );
        store.thuOpenTime( dto.getThuOpenTime() );
        store.thuCloseTime( dto.getThuCloseTime() );
        store.friOpenTime( dto.getFriOpenTime() );
        store.friCloseTime( dto.getFriCloseTime() );
        store.satOpenTime( dto.getSatOpenTime() );
        store.satCloseTime( dto.getSatCloseTime() );
        store.sunOpenTime( dto.getSunOpenTime() );
        store.sunCloseTime( dto.getSunCloseTime() );

        store.dayOff( convertDayOff(dto.getDayOff()) );

        return store.build();
    }
}
