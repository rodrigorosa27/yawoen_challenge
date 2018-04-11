package com.neoway.csv;

import com.neoway.persistence.model.Company;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenericCompanyReader {

    private static List<Company> companyList;

    public static List<Company> readStream(InputStream is) throws IOException {
        companyList = new ArrayList<>();
        try (
                Reader reader = new BufferedReader(new InputStreamReader(is));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withDelimiter(";".charAt(0))
                        .withIgnoreHeaderCase()
                        .withTrim()
                        .withIgnoreEmptyLines());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                if(csvRecord.get("name").isEmpty() || csvRecord.get("addresszip").isEmpty()){
                    continue;
                }
                Company company = new Company();
                company.setName(csvRecord.get("name").toUpperCase());
                company.setZip(csvRecord.get("addresszip"));
                if(csvParser.getHeaderMap().containsKey("website")){
                    company.setWebsite(csvRecord.get("website").toLowerCase());
                }
                companyList.add(company);
            }
        }
        return companyList;
    }

}

