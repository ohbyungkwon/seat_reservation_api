package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Item;
import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.repository.custom.MerchantRepositorySearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchantRepository extends JpaRepository<Merchant, Integer>, MerchantRepositorySearch {

    // 가맹점 정보 상세조회 : 업종, 지역 ,날짜 , 인원

    //List<MerchantHistory> findBySeat(Merchant merchant);

    /**
     *  SELECT 업종, 지역, 날짜 , 인원
     *  FROM MERCHANT
     *  WHERE ID = 사장님 ID
     */

    Merchant findByMerchantRegNum(int merchantRegNum); // 사장님 ID 별 사업자 등록번호를 가져오는 것

    // 업종, 지역 , 날짜 , 인원 수 에 맞는 값을 들고 와야 하지 않나?



}
