package com.neoway.csv;

import com.neoway.persistence.model.Company;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.util.Locale;

public final class GenericCompanyReader {

    private GenericCompanyReader() {
    }

    private static final int ZIP_ALLOWED_SIZE = 5;

    public static void readStream(final InputStream is, final CompanyReaderListener listener) throws IOException {
        try (
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withDelimiter(";".charAt(0))
                        .withIgnoreHeaderCase()
                        .withTrim()
                        .withIgnoreEmptyLines());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.get("name").isEmpty() || csvRecord.get("addresszip").isEmpty() || csvRecord.get("addresszip").length() != ZIP_ALLOWED_SIZE) {
                    continue;
                }
                Company company = new Company();
                company.setName(csvRecord.get("name").toUpperCase(Locale.getDefault()));
                company.setZip(csvRecord.get("addresszip"));
                if (csvParser.getHeaderMap().containsKey("website")) {
                    company.setWebsite(csvRecord.get("website").toLowerCase(Locale.getDefault()));
                }
                listener.processCompany(company);
            }
        }
    }

}
