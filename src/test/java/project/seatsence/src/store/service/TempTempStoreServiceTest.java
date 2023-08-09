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
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.admin.service.AdminService;
import project.seatsence.src.store.dao.StoreMemberRepository;
import project.seatsence.src.store.dao.StoreWifiRepository;
import project.seatsence.src.store.dao.TempStoreRepository;
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.domain.StoreWifi;
import project.seatsence.src.store.domain.TempStore;
import project.seatsence.src.store.dto.request.AdminStoreCreateRequest;
import project.seatsence.src.store.dto.request.AdminStoreUpdateRequest;
import project.seatsence.src.store.dto.response.AdminStoreResponse;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class TempTempStoreServiceTest {

    @InjectMocks private TempStoreService tempStoreService;

    @Mock private TempStoreRepository tempStoreRepository;

    @Mock private StoreWifiRepository storeWifiRepository;

    @Mock private StoreMemberRepository storeMemberRepository;

    @Mock private UserService userService;

    @Mock private AdminService adminService;

    @DisplayName("findById Test")
    @Nested
    class findById {

        @Test
        @DisplayName("findById success")
        public void success() {
            // given
            TempStore tempStore1 = new TempStore();
            tempStore1.setId(1L);
            tempStore1.setName("store1");
            tempStore1.setIntroduction("introduction1");
            tempStore1.setLocation("location1");
            tempStore1.setTotalFloor(1);
            tempStore1.setCategory(Category.CAFE);
            tempStore1.setAvgUseTime(0);
            tempStore1.setState(BaseTimeAndStateEntity.State.ACTIVE);

            AdminStoreResponse adminStoreResponse = new AdminStoreResponse();
            adminStoreResponse.setId(1L);
            adminStoreResponse.setName("store1");
            adminStoreResponse.setIntroduction("introduction1");
            adminStoreResponse.setLocation("location1");
            adminStoreResponse.setTotalFloor(1);
            adminStoreResponse.setCategory(Category.CAFE);

            when(tempStoreRepository.findByIdAndState(
                            any(Long.class), any(BaseTimeAndStateEntity.State.class)))
                    .thenReturn(Optional.of(tempStore1));

            // when
            TempStore tempStore = tempStoreService.findById(1L);

            // then
            assertEquals(1L, tempStore.getId());
            assertEquals("store1", tempStore.getName());
            assertEquals("introduction1", tempStore.getIntroduction());
            assertEquals("location1", tempStore.getLocation());
            assertEquals(1, tempStore.getTotalFloor());
            assertEquals(Category.CAFE, tempStore.getCategory());
        }

        @Test
        @DisplayName("findById fail")
        public void fail() {
            // given
            when(tempStoreRepository.findByIdAndState(
                            any(Long.class), any(BaseTimeAndStateEntity.State.class)))
                    .thenReturn(Optional.empty());

            // when, then
            assertThrows(BaseException.class, () -> tempStoreService.findById(1L));
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
            adminStoreCreateRequest.setAdminInfoId(1L);
            adminStoreCreateRequest.setWifi(Arrays.asList("wifi1", "wifi2"));
            adminStoreCreateRequest.setName("store");
            adminStoreCreateRequest.setIntroduction("introduction");
            adminStoreCreateRequest.setLocation("location");
            adminStoreCreateRequest.setTotalFloor(1);
            adminStoreCreateRequest.setCategory("음식점");

            AdminInfo adminInfo = new AdminInfo();
            adminInfo.setId(1L);
            adminInfo.setAdminName("admin");

            when(adminService.findAdminInfoById(any(Long.class))).thenReturn(adminInfo);
            when(userService.findUserByUserEmail(any(String.class))).thenReturn(new User());

            // when
            tempStoreService.save(adminStoreCreateRequest, "email");

            // then
            verify(tempStoreRepository, times(1)).save(any(TempStore.class));
            verify(storeWifiRepository, times(adminStoreCreateRequest.getWifi().size()))
                    .save(any(StoreWifi.class));
            verify(storeMemberRepository, times(1)).save(any(StoreMember.class));
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
            assertThrows(
                    BaseException.class,
                    () -> tempStoreService.save(adminStoreCreateRequest, "email"));
        }
    }

    @Nested
    @DisplayName("update Test")
    class update {

        @Test
        @DisplayName("update success")
        public void success() {
            // given
            TempStore tempStore = new TempStore();
            tempStore.setId(1L);
            tempStore.setName("store1");
            tempStore.setIntroduction("introduction1");
            tempStore.setLocation("location1");
            tempStore.setTotalFloor(1);
            tempStore.setCategory(Category.CAFE);
            tempStore.setAvgUseTime(0);
            tempStore.setState(BaseTimeAndStateEntity.State.ACTIVE);

            StoreWifi storeWifi1 = new StoreWifi();
            storeWifi1.setId(1L);
            storeWifi1.setTempStore(tempStore);
            storeWifi1.setState(BaseTimeAndStateEntity.State.ACTIVE);
            storeWifi1.setWifi("wifi1");

            StoreWifi storeWifi2 = new StoreWifi();
            storeWifi2.setId(2L);
            storeWifi2.setTempStore(tempStore);
            storeWifi2.setState(BaseTimeAndStateEntity.State.ACTIVE);
            storeWifi2.setWifi("wifi2");

            List<StoreWifi> storeWifiList = new ArrayList<>();
            storeWifiList.add(storeWifi1);
            storeWifiList.add(storeWifi2);

            tempStore.setWifiList(storeWifiList);

            AdminStoreUpdateRequest adminStoreUpdateRequest = new AdminStoreUpdateRequest();
            adminStoreUpdateRequest.setWifi(Arrays.asList("wifi1", "wifi3"));
            adminStoreUpdateRequest.setName("update store");
            adminStoreUpdateRequest.setIntroduction("update introduction");
            adminStoreUpdateRequest.setLocation("update location");
            adminStoreUpdateRequest.setTotalFloor(1);
            adminStoreUpdateRequest.setCategory("음식점");

            when(tempStoreRepository.findByIdAndState(
                            any(Long.class), any(BaseTimeAndStateEntity.State.class)))
                    .thenReturn(Optional.of(tempStore));

            // when
            tempStoreService.update(1L, adminStoreUpdateRequest);

            // then
            String[] storeWifiNames =
                    tempStore.getWifiList().stream().map(StoreWifi::getWifi).toArray(String[]::new);

            assertEquals(tempStore.getName(), "update store");
            assertEquals(tempStore.getIntroduction(), "update introduction");
            assertEquals(tempStore.getLocation(), "update location");
            assertEquals(tempStore.getCategory(), Category.RESTAURANT);
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
            TempStore tempStore = new TempStore();
            tempStore.setId(1L);
            tempStore.setName("store1");
            tempStore.setIntroduction("introduction1");
            tempStore.setLocation("location1");
            tempStore.setTotalFloor(1);
            tempStore.setCategory(Category.CAFE);
            tempStore.setAvgUseTime(0);
            tempStore.setState(BaseTimeAndStateEntity.State.ACTIVE);

            when(tempStoreRepository.findByIdAndState(
                            any(Long.class), any(BaseTimeAndStateEntity.State.class)))
                    .thenReturn(Optional.of(tempStore));

            // when
            tempStoreService.delete(1L);

            // then
            assertEquals(tempStore.getState(), BaseTimeAndStateEntity.State.INACTIVE);
        }
    }
}
