package com.neoway;

import com.neoway.csv.GenericCompanyReader;
import com.neoway.persistence.database.MorphiaService;
import com.neoway.persistence.database.dao.CompanyDAO;
import com.neoway.persistence.model.Company;
import lombok.extern.log4j.Log4j;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Startup
@Singleton
@Log4j
public class Loader {

    private MorphiaService morphiaService = new MorphiaService();

    private Configuration configuration = Configuration.getInstance();

    private final InputStream sampleFile = Configuration.class.getClassLoader().getResourceAsStream("q1_catalog.csv");

    @PostConstruct
    public void startLoader() throws IOException {

        List<Company> fileCompanyList;
        CompanyDAO companyDAO = new CompanyDAO(Company.class, morphiaService.getDatastore());

        if (configuration.getProperty("Q1_CATALOG_PATH").isEmpty()) {
            fileCompanyList = GenericCompanyReader.readStream(sampleFile);
        } else {
            InputStream is = new FileInputStream(new File(configuration.getProperty("Q1_CATALOG_PATH")));
            fileCompanyList = GenericCompanyReader.readStream(is);
        }

        for (Company company: fileCompanyList) {
            if (companyDAO.findCompanyByNameAndZip(company.getName(), company.getZip()) == null) {
                companyDAO.save(company);
            }
        }

    }

}
