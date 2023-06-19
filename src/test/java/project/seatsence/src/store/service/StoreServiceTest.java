package project.seatsence.src.store.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.StoreMapper;
import project.seatsence.src.store.dto.response.AdminStoreResponse;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @InjectMocks private StoreService storeService;

    @Mock private StoreRepository storeRepository;

    @Mock private StoreMapper storeMapper;

    @DisplayName("findById Test")
    @Nested
    class findById {

        @Test
        @DisplayName("findById Success")
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
            adminStoreResponse.setAvgUseTime(0);

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
            assertEquals(0, findById.getAvgUseTime());
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
}
