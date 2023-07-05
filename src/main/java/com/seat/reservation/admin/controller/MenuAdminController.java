package com.seat.reservation.admin.controller;

import com.seat.reservation.common.dto.MenuDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.service.MenuService;
import com.seat.reservation.common.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MenuAdminController extends SecurityService {

    private final MenuService menuService;


    /**
     * @param creates
     * @return ResponseEntity<ResponseComDto>
     * - 메뉴 등록
     */
    @PostMapping("/menu")
    public ResponseEntity<ResponseComDto> createMenus(@RequestBody List<MenuDto.create> creates){
        menuService.createMenus(creates);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                .resultMsg("메뉴 등록이 완료되었습니다.")
                .resultObj(creates)
                .build());
    }

    /**
     * @param menuIds
     * @return ResponseEntity<ResponseComDto>
     * - 메뉴 삭제
     */
    @DeleteMapping("/menu")
    public ResponseEntity<ResponseComDto> deleteMenus(@RequestBody List<String> menuIds){
        menuService.deleteMenus(menuIds);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                        .resultMsg("메뉴 삭제가 완료되었습니다.")
                        .resultObj(null)
                        .build());
    }

    /**
     * @param search
     * @return ResponseEntity<ResponseComDto>
     * - 메뉴 조회
     */
    @GetMapping("/menu")
    public ResponseEntity<ResponseComDto> searchMenus(@RequestBody MenuDto.search search){
        List<MenuDto.search> menus = menuService.searchMenu(search);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                        .resultMsg("조회 완료되었습니다.")
                        .resultObj(menus)
                        .build());
    }
}
