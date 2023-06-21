package project.seatsence.src.store.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.dao.StoreWifiRepository;
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreWifi;
import project.seatsence.src.store.dto.StoreMapper;
import project.seatsence.src.store.dto.request.AdminStoreCreateRequest;
import project.seatsence.src.store.dto.request.AdminStoreUpdateRequest;
import project.seatsence.src.store.dto.response.AdminStoreResponse;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @InjectMocks private StoreService storeService;

    @Mock private StoreRepository storeRepository;

    @Mock private StoreWifiRepository storeWifiRepository;

    @Mock private StoreMapper storeMapper;

    @DisplayName("findById Test")
    @Nested
    class findById {

        @Test
        @DisplayName("findById success")
        public void success() {
            // given
            Store store1 = new Store();
            store1.setId(1L);
            store1.setName("store1");
            store1.setIntroduction("introduction1");
            store1.setLocation("location1");
            store1.setTotalFloor(1);
            store1.setCategory(Category.CAFE);
            store1.setAvgUseTime(0);
            store1.setState(BaseTimeAndStateEntity.State.ACTIVE);

            AdminStoreResponse adminStoreResponse = new AdminStoreResponse();
            adminStoreResponse.setId(1L);
            adminStoreResponse.setName("store1");
            adminStoreResponse.setIntroduction("introduction1");
            adminStoreResponse.setLocation("location1");
            adminStoreResponse.setTotalFloor(1);
            adminStoreResponse.setCategory(Category.CAFE);

            when(storeRepository.findByIdAndState(
                            any(Long.class), any(BaseTimeAndStateEntity.State.class)))
                    .thenReturn(Optional.of(store1));
            when(storeMapper.toDto(store1)).thenReturn((adminStoreResponse));

            // when
            AdminStoreResponse findById = storeService.findById(1L);

            // then
            assertEquals(1L, findById.getId());
            assertEquals("store1", findById.getName());
            assertEquals("introduction1", findById.getIntroduction());
            assertEquals("location1", findById.getLocation());
            assertEquals(1, findById.getTotalFloor());
            assertEquals(Category.CAFE, findById.getCategory());
        }

        @Test
        @DisplayName("findById fail")
        public void fail() {
            // given
            when(storeRepository.findByIdAndState(
                            any(Long.class), any(BaseTimeAndStateEntity.State.class)))
                    .thenReturn(Optional.empty());

            // when, then
            assertThrows(BaseException.class, () -> storeService.findById(1L));
        }
    }

    @Nested
    @DisplayName("save Test")
    class save {

        @Test
        @DisplayName("save success")
        public void success() {
            // given
            AdminStoreCreateRequest adminStoreCreateRequest = new AdminStoreCreateRequest();
            adminStoreCreateRequest.setWifi(Arrays.asList("wifi1", "wifi2"));
            adminStoreCreateRequest.setName("store");
            adminStoreCreateRequest.setIntroduction("introduction");
            adminStoreCreateRequest.setLocation("location");
            adminStoreCreateRequest.setTotalFloor(1);
            adminStoreCreateRequest.setCategory("음식점");

            // when
            storeService.save(adminStoreCreateRequest);

            // then
            verify(storeRepository, times(1)).save(any(Store.class));
            verify(storeWifiRepository, times(adminStoreCreateRequest.getWifi().size()))
                    .save(any(StoreWifi.class));
        }

        @Test
        @DisplayName("save fail")
        public void fail() {
            // given
            AdminStoreCreateRequest adminStoreCreateRequest = new AdminStoreCreateRequest();
            adminStoreCreateRequest.setWifi(Arrays.asList("wifi1", "wifi2"));
            adminStoreCreateRequest.setName("store");
            adminStoreCreateRequest.setIntroduction("introduction");
            adminStoreCreateRequest.setLocation("location");
            adminStoreCreateRequest.setTotalFloor(1);
            adminStoreCreateRequest.setCategory("식당");

            // when, then
            assertThrows(BaseException.class, () -> storeService.save(adminStoreCreateRequest));
        }
    }

    @Nested
    @DisplayName("update Test")
    class update {

        @Test
        @DisplayName("update success")
        public void success() {
            // given
            Store store = new Store();
            store.setId(1L);
            store.setName("store1");
            store.setIntroduction("introduction1");
            store.setLocation("location1");
            store.setTotalFloor(1);
            store.setCategory(Category.CAFE);
            store.setAvgUseTime(0);
            store.setState(BaseTimeAndStateEntity.State.ACTIVE);

            StoreWifi storeWifi1 = new StoreWifi();
            storeWifi1.setId(1L);
            storeWifi1.setStore(store);
            storeWifi1.setState(BaseTimeAndStateEntity.State.ACTIVE);
            storeWifi1.setWifi("wifi1");

            StoreWifi storeWifi2 = new StoreWifi();
            storeWifi2.setId(2L);
            storeWifi2.setStore(store);
            storeWifi2.setState(BaseTimeAndStateEntity.State.ACTIVE);
            storeWifi2.setWifi("wifi2");

            List<StoreWifi> storeWifiList = new ArrayList<>();
            storeWifiList.add(storeWifi1);
            storeWifiList.add(storeWifi2);

            store.setWifiList(storeWifiList);

            AdminStoreUpdateRequest adminStoreUpdateRequest = new AdminStoreUpdateRequest();
            adminStoreUpdateRequest.setWifi(Arrays.asList("wifi1", "wifi3"));
            adminStoreUpdateRequest.setName("update store");
            adminStoreUpdateRequest.setIntroduction("update introduction");
            adminStoreUpdateRequest.setLocation("update location");
            adminStoreUpdateRequest.setTotalFloor(1);
            adminStoreUpdateRequest.setCategory("음식점");

            when(storeRepository.findByIdAndState(
                            any(Long.class), any(BaseTimeAndStateEntity.State.class)))
                    .thenReturn(Optional.of(store));

            // when
            storeService.update(1L, adminStoreUpdateRequest);

            // then
            String[] storeWifiNames =
                    store.getWifiList().stream().map(StoreWifi::getWifi).toArray(String[]::new);

            assertEquals(store.getName(), "update store");
            assertEquals(store.getIntroduction(), "update introduction");
            assertEquals(store.getLocation(), "update location");
            assertEquals(store.getCategory(), Category.RESTAURANT);
            assertArrayEquals(storeWifiNames, new String[] {"wifi1", "wifi3"});
        }
    }

    @Nested
    @DisplayName("delete Test")
    class delete {

        @Test
        @DisplayName("delete success")
        public void success() {
            // given
            Store store = new Store();
            store.setId(1L);
            store.setName("store1");
            store.setIntroduction("introduction1");
            store.setLocation("location1");
            store.setTotalFloor(1);
            store.setCategory(Category.CAFE);
            store.setAvgUseTime(0);
            store.setState(BaseTimeAndStateEntity.State.ACTIVE);

            when(storeRepository.findByIdAndState(
                            any(Long.class), any(BaseTimeAndStateEntity.State.class)))
                    .thenReturn(Optional.of(store));

            // when
            storeService.delete(1L);

            // then
            assertEquals(store.getState(), BaseTimeAndStateEntity.State.INACTIVE);
        }
    }
}
