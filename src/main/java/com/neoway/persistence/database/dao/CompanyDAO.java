package com.neoway.persistence.database.dao;

import com.neoway.persistence.model.Company;
import lombok.extern.log4j.Log4j;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import java.util.List;

@Log4j
public class CompanyDAO extends BasicDAO<Company, ObjectId> {

    public CompanyDAO(final Class<Company> company, final Datastore ds) {
        super((Class<Company>) company, ds);
    }

    public final Company findCompanyByNameAndZip(final String name, final String zip) {
        if (name != null && !name.isEmpty() && zip != null && !zip.isEmpty()) {
            Query<Company> companyQuery = this.getDatastore().createQuery(Company.class);
                companyQuery.and(
                        companyQuery.criteria("name").containsIgnoreCase(name),
                        companyQuery.criteria("zip").equal(zip)
                );
                return companyQuery.get();
            }
        return null;
    }

    public final List<Company> findAll() {
        Query<Company> companyQuery = this.getDatastore().createQuery(Company.class);
        return companyQuery.asList();
    }

}
