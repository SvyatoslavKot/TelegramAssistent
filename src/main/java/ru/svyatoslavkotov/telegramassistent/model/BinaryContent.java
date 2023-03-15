package ru.svyatoslavkotov.telegramassistent.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BinaryContent {
    private Long id;
    private byte[] fileAsArrayOfBytes;
}
