package com.seat.reservation.common.domain;

import com.seat.reservation.common.dto.FileDto;
import com.seat.reservation.common.dto.ReviewDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Table
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String saveFilename;

    private byte[] binary;

    private String mimeType;

    public static File createImage(FileDto.create dto){
        return File.builder()
                .filename(dto.getFilename())
                .saveFilename(dto.getSaveFilename())
                .binary(dto.getBinary())
                .mimeType(dto.getMimeType())
                .build();
    }

    public void changeFile(FileDto.create dto){
        this.filename = dto.getFilename();
        this.saveFilename = dto.getSaveFilename();
        this.mimeType = dto.getMimeType();
        this.binary = dto.getBinary();
    }
}
