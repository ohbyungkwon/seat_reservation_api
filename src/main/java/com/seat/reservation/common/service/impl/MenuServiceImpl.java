package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.Menu;
import com.seat.reservation.common.domain.MenuAuthority;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.dto.MenuDto;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.repository.MenuAuthorityRepository;
import com.seat.reservation.common.repository.MenuRepository;
import com.seat.reservation.common.service.MenuService;
import com.seat.reservation.common.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MenuServiceImpl extends SecurityService implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuAuthorityRepository menuAuthorityRepository;

    @Override
    public List<MenuDto.search> searchAllMenu(MenuDto.search search) {
        String menuId = search.getMenuId();
        String menuName = search.getMenuName();

        List<Menu> list = new ArrayList<>();
        if(!(StringUtils.isEmpty(menuId) && StringUtils.isEmpty(menuName))){
            list = menuRepository.findByMenuIdAndMenuName(menuId, menuName);
        } else {
            if(!StringUtils.isEmpty(menuId)){
                list = menuRepository.findByMenuId(menuId);
            } else if(!StringUtils.isEmpty(menuName)){
                list = menuRepository.findByMenuName(menuName);
            } else{
                list = menuRepository.findAll();
            }
        }

        return list.stream().map(Menu::convertMenuDtoShow).collect(Collectors.toList());
    }

    /**
     * @param key (#userId + "_menu_list")
     * @return
     */
    @Override
    @Cacheable(value = "reservation_api_cache_name", key = "#key")
    public List<MenuDto.searchAll> searchUserMenu(String key) {
        StringTokenizer st = new StringTokenizer(key, "_");
        String userId = st.nextToken();

        User user = this.getUser().orElseThrow(() -> new NotFoundUserException("사용자 정보를 찾을 수 없습니다."));
        if(!user.getUserId().equals(userId)){
            throw new BadReqException("로그인 정보가 다릅니다.");
        }

        return menuAuthorityRepository.findMenuWithUserInfo(userId).stream()
                .map(MenuAuthority::convertMenuDto)
                .collect(Collectors.toList());
    }
}
