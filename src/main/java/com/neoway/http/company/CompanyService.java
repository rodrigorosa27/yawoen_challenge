package com.neoway.http.company;

import com.neoway.csv.GenericCompanyReader;
import com.neoway.http.ResourcePath;
import com.neoway.persistence.database.MorphiaService;
import com.neoway.persistence.database.dao.CompanyDAO;
import com.neoway.persistence.model.Company;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Log4j
@Path(ResourcePath.ROOT)
public class CompanyService {

    private CompanyDAO companyDAO;

    public CompanyService(final MorphiaService service) {
        companyDAO = new CompanyDAO(Company.class, service.getDatastore());
    }

    @GET
    @Path(ResourcePath.FIND_BY)
    @Produces(MediaType.APPLICATION_JSON)
    public final Response findBy(@QueryParam("name") final String name, @QueryParam("zip") final String zip) {

        if (name == null || name.isEmpty()) {
            return Response.status(Response.Status.CONFLICT).entity("NAME value are mandatory!").build();
        }
        if (zip == null || zip.isEmpty()) {
            return Response.status(Response.Status.CONFLICT).entity("ZIP value are mandatory!").build();
        }

        Company company = companyDAO.findCompanyByNameAndZip(name, zip);
        if (company == null) {
            return Response.status(Response.Status.CONFLICT).entity("No records found for: \nNAME: " + name + "\nZIP: " + zip).build();
        }
        return Response.status(Response.Status.OK).entity(company).build();
    }

    @POST
    @Path(ResourcePath.SERVICE_COMPANY)
    @Consumes(MediaType.TEXT_PLAIN)
    public final Response create(final InputStream inputStream) {
        List<Company> incomingCompanyList;
        try {
            incomingCompanyList = GenericCompanyReader.readStream(inputStream);
            for (Company newCompany: incomingCompanyList) {
                Company company = companyDAO.findCompanyByNameAndZip(newCompany.getName(), newCompany.getZip());
                if (company == null) {
                    companyDAO.save(newCompany);
                } else {
                    company.setWebsite(newCompany.getWebsite());
                    companyDAO.save(company);
                }
            }
        } catch (IOException e) {
            final Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new WebApplicationException(rootCause, Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(rootCause.getMessage()).build());
        }
        return Response.status(Response.Status.OK).build();
    }

}
