package com.neoway.persistence.database.dao;

import com.neoway.persistence.model.Company;
import lombok.extern.log4j.Log4j;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import javax.ejb.LocalBean;
import java.util.ArrayList;
import java.util.List;

@Log4j
@LocalBean
public class CompanyDAO extends BasicDAO<Company, ObjectId> {

    public CompanyDAO(final Class<Company> company, final Datastore ds) {
        super((Class<Company>) company, ds);
    }

    public final List<Company> filterCompany (final String name, final String zip){
        List<Company> companyList = new ArrayList<>();
        if((name != null && !name.isEmpty()) && (zip != null && !zip.isEmpty())){
            companyList = buildQuery(name, zip).asList();
        }
        return companyList;
    }

    private Query<Company> buildQuery(final String name, final String zip) {
        Query<Company> companyQuery = this.getDatastore().createQuery(Company.class);
        if((name != null && !name.isEmpty()) && (zip != null && !zip.isEmpty())){
            companyQuery.and(
                    companyQuery.criteria("name").containsIgnoreCase(name),
                    companyQuery.criteria("zip").equal(zip)
            );
        }
        return companyQuery;
    }

}
