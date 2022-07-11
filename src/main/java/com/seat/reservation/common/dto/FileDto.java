package com.seat.reservation.common.dto;

import lombok.*;

public class FileDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class create{
        private String filename;
        private String saveFilename;
        private byte[] binary;
        private String mimeType;
    }
}
