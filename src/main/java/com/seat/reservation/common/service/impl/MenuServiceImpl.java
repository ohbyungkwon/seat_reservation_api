package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.Menu;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.domain.enums.Role;
import com.seat.reservation.common.dto.MenuDto;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.repository.MenuRepository;
import com.seat.reservation.common.service.MenuService;
import com.seat.reservation.common.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl extends SecurityService implements MenuService {

    private final MenuRepository menuRepository;


    /**
     * @param creates
     * - 메뉴 생성
     */
    @Override
    @Transactional
    public void createMenus(List<MenuDto.create> creates) {
        List<Menu> menus = creates.stream()
                .map(x -> Menu.createMenu(x.getMenuId(), x.getMenuName(), x.getRole()))
                .collect(Collectors.toList());
        menuRepository.saveAll(menus);
    }

    /**
     * @param menuIds
     * - 메뉴 삭제
     */
    @Override
    @Transactional
    public void deleteMenus(List<String> menuIds) {
        menuRepository.deleteByMenuIdIn(menuIds);
    }

    /**
     * @param search
     * @return List<MenuDto.search>
     * - 메뉴 조회
     */
    @Override
    public List<MenuDto.search> searchMenu(MenuDto.search search) {
        String menuId = search.getMenuId();
        String menuName = search.getMenuName();
        Role role = search.getRole();

        List<Menu> list;
        if(!StringUtils.isEmpty(menuId)) {
            list = menuRepository.findByMenuIdLike(menuId);
        } else if(!StringUtils.isEmpty(menuName)) {
            list = menuRepository.findByMenuNameLike(menuName);
        } else if(role != null) {
            list = menuRepository.findByRole(role);
        } else{
            list = menuRepository.findAll();
        }

        return list.stream().map(Menu::convertMenuDtoShow).collect(Collectors.toList());
    }

    /**
     * @param key (#role + "_menu_list")
     * @return List<MenuDto.search>
     * @exception IOException
     * - 로그인시 권한에 따라 메뉴 조회(캐시)
     */
    @Override
    @Cacheable(value = "menu_cache", key = "#key")
    public List<MenuDto.search> searchUserMenu(String key) throws IOException {
        StringTokenizer st = new StringTokenizer(key, "_");
        String role = st.nextToken();

        User user = this.getUser().orElseThrow(() ->
                new NotFoundUserException("사용자 정보를 찾을 수 없습니다."));
        Role userRole = user.getRole();
        if(!userRole.getValue().equals(role)){
            throw new BadReqException("메뉴에 대한 권한이 없습니다.");
        }
        return menuRepository.findByRole(userRole).stream()
                .map(Menu::convertMenuDtoShow)
                .collect(Collectors.toList());
    }
}
