package com.algaworks.algacomments.comment.service.common;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;

import java.util.UUID;

public class UUIDUtils {

    private static final TimeBasedEpochGenerator TIME_BASED_EPOCH_GENERATOR = Generators.timeBasedEpochGenerator();

    private UUIDUtils() {
    }

    public static UUID generateTimeBasedUUID() {
        return TIME_BASED_EPOCH_GENERATOR.generate();
    }
}
