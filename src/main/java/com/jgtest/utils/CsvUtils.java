package com.jgtest.utils;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CsvUtils {

    private static final Logger LOGGER = LogManager.getLogger(CsvUtils.class);

    public static final CsvMapper csvMapper = new CsvMapper();

    public static final CsvSchema schema = CsvSchema.emptySchema()
            .withHeader()
            .withColumnReordering(false);

    static {

    }

}
