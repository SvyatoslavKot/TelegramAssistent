package ru.svyatoslavkotov.telegramassistent.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppDocument {
    private Long id;
    private String telegramFileId;
    private String docName;
    private BinaryContent binaryContent;
    private String mineType;
    private Long fileSize;
}
