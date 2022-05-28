package com.seat.reservation.common.repository;


import com.seat.reservation.common.domain.Item;
import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Upzong;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.domain.enums.Category;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.ItemDto;
import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.UpzongDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class MerchantRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    // Testcode 작성
    // Merchant save -> findbyId -> 동적쿼리 테스트
    @Test
    @Rollback(value = false)
    public void save() {
        User user = User.createUserSimple("1111"); // user 가져오기 위해 User를 등록시키는 것
        entityManager.persist(user);

        UpzongDto.create upzongDto = UpzongDto.create.builder()
                .code("")
                .category(Category.CAFE)
                .build();
        Upzong upzong = Upzong.createUpzong(upzongDto);
        entityManager.persist(upzong);
        entityManager.flush();

        Integer merchantRegNum = 2012305047;
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ItemDto.create itemDto = ItemDto.create.builder()
                    .merchantRegNum(merchantRegNum)
                    .menuName("ramen" + i)
                    .price(8000)
                    .build();

            itemList.add(Item.createItem(itemDto));
        }

        MerchantDto.create merchantDto = MerchantDto.create.builder() // 값을 받을 생성자를 선언한다.
                .merchantRegNum(merchantRegNum)
                .repPhone("01035780801")
                .repName("YOON")
                .merchantTel("023455678")
                .merchantName("Spring")
                .upzongId(upzong.getId())
                .address("seoul")
                .zipCode("123")
                .build();
        Merchant merchant = Merchant.createMerchant(merchantDto, user, itemList);
        merchant.setUpzong(upzong);

        entityManager.persist(merchant);
    }
}