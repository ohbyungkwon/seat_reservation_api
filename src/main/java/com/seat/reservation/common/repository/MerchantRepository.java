package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Item;
import com.seat.reservation.common.domain.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

    // 가맹점 정보 상세조회 : 업종, 지역 ,날짜 , 인원

    //List<MerchantHistory> findBySeat(Merchant merchant);

    /**
     *  SELECT 업종, 지역, 날짜 , 인원
     *  FROM MERCHANT
     *  WHERE ID = 사장님 ID
     */

    Merchant findByMerchantRegNumber(int merchantRegNumber); // 사장님 ID 별 사업자 등록번호를 가져오는 것

    // 업종, 지역 , 날짜 , 인원 수 에 맞는 값을 들고 와야 하지 않나?

    /**
     *  SELECT *
     *  FROM MERCHANT
     *  WHERE 1 = 1
     *  AND 업종 = 업종
     *  AND 지역 = 지역
     *  AND 날짜 = 날짜
     *  AND 인원 = 인원
     */

}
