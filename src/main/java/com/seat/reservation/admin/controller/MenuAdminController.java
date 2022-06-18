package com.seat.reservation.admin.controller;


/* 메뉴 등록, 수정 기능 (등록은 단일 혹은 엑셀로 일괄 등록) */

import com.seat.reservation.common.domain.Item;
import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.dto.ItemDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.repository.ItemRepository;
import com.seat.reservation.common.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MenuAdminController {

    private final ItemRepository itemRepository;
    private final MerchantRepository merchantRepository;

    @PostMapping("/item")
    public ResponseEntity<ResponseComDto> createMenu(@RequestBody List<ItemDto.create> creates){

        Merchant merchant =  merchantRepository.findByMerchantRegNum(creates.get(0).getMerchantRegNum());

        if(merchant == null){
            System.out.println("존재하지 않는 가맹점입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseComDto.builder()
                            .resultMsg("존재하지 않는 가맹점입니다.")
                            .resultObj(creates).build());
        }

        creates.forEach((createItem)-> {
            Item item = Item.createItem(merchant, createItem.getMenuName(), createItem.getPrice());

            itemRepository.save(item);
        });

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                .resultMsg("메뉴 등록이 완료되었습니다.")
                .resultObj(creates).build());
    }

    @DeleteMapping("/item")
    public ResponseEntity<ResponseComDto> deleteMenu(@RequestBody List<ItemDto.delete> deletes){

        Merchant merchant =  merchantRepository.findByMerchantRegNum(deletes.get(0).getMerchantRegNum());

        if(merchant == null){
            System.out.println("존재하지 않는 가맹점입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseComDto.builder()
                            .resultMsg("존재하지 않는 가맹점입니다.")
                            .resultObj(deletes).build());
        }

        deletes.forEach((deleteItem) -> {
            itemRepository.deleteByMerchantAndAndMenuName(merchant, deleteItem.getMenuName());
        });

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                        .resultMsg("메뉴 삭제가 완료되었습니다.")
                        .resultObj(deletes).build());
    }
}
