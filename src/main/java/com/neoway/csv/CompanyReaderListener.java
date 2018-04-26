package com.neoway.csv;

import com.neoway.persistence.model.Company;

public interface CompanyReaderListener {

    void processCompany(Company newCompany);
}
