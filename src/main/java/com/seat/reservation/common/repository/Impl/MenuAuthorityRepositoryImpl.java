/**
 * 사용자 단위 메뉴 권한 불필요하다 판단
 */
//package com.seat.reservation.common.repository.Impl;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.seat.reservation.common.domain.MenuAuthority;
//import com.seat.reservation.common.repository.custom.MenuAuthorityRepositoryCustom;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import static com.seat.reservation.common.domain.QMenu.menu;
//import static com.seat.reservation.common.domain.QMenuAuthority.menuAuthority;
//import static com.seat.reservation.common.domain.QUser.user;
//
//@Repository
//public class MenuAuthorityRepositoryImpl implements MenuAuthorityRepositoryCustom {
//    private final JPAQueryFactory jpaQueryFactory;
//
//    public MenuAuthorityRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
//        this.jpaQueryFactory = jpaQueryFactory;
//    }
//
//    @Override
//    public List<MenuAuthority> findMenuWithUserInfo(String userId) {
//        return jpaQueryFactory
//                .selectFrom(menuAuthority)
//                .join(menuAuthority.user, user)
//                .join(menuAuthority.menu, menu).fetchJoin()
//                .where(menuAuthority.user.userId.eq(userId))
//                .orderBy(menuAuthority.menu.menuId.asc())
//                .fetch();
//    }
//}
