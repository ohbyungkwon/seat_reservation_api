//package com.seat.reservation.common.repository;
//
//import com.seat.reservation.common.domain.Menu;
//import com.seat.reservation.common.domain.MenuAuthority;
//import com.seat.reservation.common.domain.User;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//@Transactional
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class MenuAuthorityRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private MenuRepository menuRepository;
//
//    @Autowired
//    private MenuAuthorityRepository menuAuthorityRepository;
//
//    @Test
//    @Rollback(value = false)
//    public void save(){
//        List<User> userStore = new ArrayList<>();
//        List<Menu> menuStore = new ArrayList<>();
//
//        String userId = "test";
//        for(int i = 0; i < 10;  i++){
//            User user = User.createUserSimple(userId + i);
//            userStore.add(userRepository.save(user));
//        }
//
//        String menuId = "MM00000";
//        String menuName = "TEST_NAME_";
//        for(int i = 0; i < 10;  i++){
//            Menu menu = Menu.createSimpleMenu(menuId + i, menuName + i);
//            menuStore.add(menuRepository.save(menu));
//        }
//
//        for (User user : userStore) {
//            for (Menu menu : menuStore) {
//                MenuAuthority menuAuthority = MenuAuthority.createSimpleMenuAuthority(menu, user);
//                menuAuthorityRepository.save(menuAuthority);
//            }
//        }
//    }
//
//    @Test
//    public void findMenuWithUserInfo() {
//        List<Integer> menuAuthoritySizeList = new ArrayList<>();
//        List<User> userList = userRepository.findAll();
//        for (User user : userList) {
//            List<MenuAuthority> menuAuthorityList = menuAuthorityRepository.findMenuWithUserInfo(user.getUserid());
//            menuAuthoritySizeList.add(menuAuthorityList.size());
//        }
//
//        Boolean isHaveSameMenu = Boolean.FALSE;
//        Integer compareSize = menuAuthoritySizeList.get(0);
//        for(Integer size : menuAuthoritySizeList){
//            if(Objects.equals(size, compareSize)){
//                isHaveSameMenu = Boolean.TRUE;
//            } else {
//                isHaveSameMenu = Boolean.FALSE;
//                break;
//            }
//        }
//
//        Assertions.assertThat(isHaveSameMenu).isEqualTo(Boolean.TRUE);
//    }
//}