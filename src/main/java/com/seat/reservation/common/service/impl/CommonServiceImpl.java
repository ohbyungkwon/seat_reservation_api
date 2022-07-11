package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.File;
import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.dto.FileDto;
import com.seat.reservation.common.dto.SearchDto;
import com.seat.reservation.common.repository.FileRepository;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.service.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {
    private static final Integer RESERVATION_INTERVAL = 30;
    private final MerchantRepository merchantRepository;
    private final FileRepository fileRepository;

    public CommonServiceImpl(MerchantRepository merchantRepository,
                             FileRepository fileRepository) {
        this.merchantRepository = merchantRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public List<SearchDto.time> getReservationAbleHours(Integer merchantRegNum) {
        Merchant merchant = merchantRepository.findByMerchantRegNum(merchantRegNum);
        LocalTime openTime = merchant.getOpenTime();
        LocalTime closeTime = merchant.getCloseTime();
        int stdHour = merchant.getReservationStdHour();

        int cnt = 0;
        int openHour = openTime.getHour();
        int closeHour = closeTime.getHour();
        List<SearchDto.time> list = new ArrayList<>();
        for(double i = openHour; i <= closeHour - stdHour; i += 0.5) {
            SearchDto.time searchDto = new SearchDto.time();
            int interval = RESERVATION_INTERVAL * cnt++;
            LocalTime newDateTime = openTime.plusMinutes(interval);
            searchDto.setStartTime(newDateTime);
            searchDto.setEndTime(newDateTime.plusHours(stdHour));
            list.add(searchDto);
        }

        return list;
    }

    @Override
    public String renameFile(String filename) {
        StringBuilder sb = new StringBuilder(filename);

        UUID uuid = UUID.randomUUID();
        sb.append(uuid);

        return sb.toString();
    }

    @Override
    public File getFile(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        Boolean isExist = fileRepository.existsByFilename(filename);
        String saveFilename = isExist ? this.renameFile(filename) : filename;
        return File.createImage(
                FileDto.create.builder()
                        .filename(filename)
                        .saveFilename(saveFilename)
                        .binary(file.getBytes())
                        .mimeType(file.getContentType())
                        .build());
    }
}
