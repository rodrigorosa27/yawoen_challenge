package com.neoway;

import com.neoway.csv.CompanyReaderListener;
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

@Startup
@Singleton
@Log4j
public class Loader implements CompanyReaderListener {

    private MorphiaService morphiaService = new MorphiaService();

    private Configuration configuration = Configuration.getInstance();

    private final InputStream sampleFile = Configuration.class.getClassLoader().getResourceAsStream("q1_catalog.csv");

    private CompanyDAO companyDAO;

    @PostConstruct
    public void startLoader() throws IOException {

        companyDAO = new CompanyDAO(Company.class, morphiaService.getDatastore());

        if (configuration.getProperty("Q1_CATALOG_PATH").isEmpty()) {
            GenericCompanyReader.readStream(sampleFile, this);
        } else {
            InputStream is = new FileInputStream(new File(configuration.getProperty("Q1_CATALOG_PATH")));
            GenericCompanyReader.readStream(is, this);
        }
    }

    @Override
    public void processCompany(final Company newCompany) {
        if (companyDAO.findCompanyByNameAndZip(newCompany.getName(), newCompany.getZip()) == null) {
            companyDAO.save(newCompany);
        }
    }
}
