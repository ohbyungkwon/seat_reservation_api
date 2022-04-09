package com.seat.reservation.domain;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Table
@Entity
public class Merchant {

    @Id
    private int merchant_reg_number; // 사업자 등록 번호

    @JoinColumn
    //@OneToMany(fetch = FetchType.LAZY)
    private int user_id; // 자리 등록한 유저

    private String rep_phone; // 가맹점 사장님 번호

    private String rep_name; // 가맹점 사장님 이름

    private String merchant_tel; // 가맹점 번호

    private String merchant_name; // 가맹점 상호

    private String category; // 업종 카테고리 ex. Pc방, cafe

    private String register_flag; // 등록 및 해지여부

    private String address; // 가맹점 주소

    private String zipcode; // 가맹점 우편번호

    private Date register_date;  // 가맹점 등록일자

    private Date modify_date; // 가맹점 수정일자





}
