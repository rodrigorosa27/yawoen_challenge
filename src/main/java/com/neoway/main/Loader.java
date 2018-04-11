package com.neoway.main;

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

    private final InputStream SAMPLE_FILE = Configuration.class.getClassLoader().getResourceAsStream("q1_catalog.csv");

    @PostConstruct
    public void startLoader() throws IOException {

        List<Company> companyList;
        CompanyDAO companyDAO = new CompanyDAO(Company.class, morphiaService.getDatastore());

        if(configuration.getProperty("Q1_CATALOG_PATH").isEmpty()){
             companyList = GenericCompanyReader.readStream(SAMPLE_FILE);
        }else{
            InputStream is = new FileInputStream(new File(configuration.getProperty("Q1_CATALOG_PATH")));
            companyList = GenericCompanyReader.readStream(is);
        }

        for(Company company: companyList) {
            if (companyDAO.filterCompany(company.getName(), company.getZip()).size() == 0) {
                companyDAO.save(company);
            }
        }

    }

}
