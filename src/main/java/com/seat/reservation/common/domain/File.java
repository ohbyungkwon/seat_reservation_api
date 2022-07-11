package com.seat.reservation.common.domain;

import com.seat.reservation.common.dto.FileDto;
import lombok.*;

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

    public static File createImage(FileDto.create create){
        return File.builder()
                .filename(create.getFilename())
                .saveFilename(create.getSaveFilename())
                .binary(create.getBinary())
                .mimeType(create.getMimeType())
                .build();
    }
}
