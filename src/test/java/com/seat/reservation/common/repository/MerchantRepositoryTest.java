package com.seat.reservation.common.repository;


import com.seat.reservation.common.domain.Item;
import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Upzong;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.domain.enums.Category;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.ItemDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class MerchantRepositoryTest {

    // Testcode 작성
    // Merchant save -> findbyId -> 동적쿼리 테스트

    @Test
    @Rollback(value = false)
    public void save() {

        User user = User.createUserSimple("1111"); // user 가져오기 위해

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

        Merchant merchant = Merchant.builder() // 값을 받을 생성자를 선언한다.
                .merchantRegNum(merchantRegNum)
                .user(user)
                .item(itemList)
                .repPhone("01035780801")
                .repName("YOON")
                .merchantTel("023455678")
                .merchantName("Spring")
                .registerCode(RegisterCode.REGISTER) // enum
                .address("seoul")
                .zipCode("123")
                .user(user)
                .item(itemList)
                .build();

        Upzong upzong = Upzong.builder()
                .code("A")
                .category(Category.CAFE)
                .build();

        merchant.setUpzong(upzong);
    }
}