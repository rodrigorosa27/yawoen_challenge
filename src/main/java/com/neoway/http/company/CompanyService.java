package com.neoway.http.company;

import com.neoway.csv.GenericCompanyReader;
import com.neoway.http.ResourcePath;
import com.neoway.persistence.database.MorphiaService;
import com.neoway.persistence.database.dao.CompanyDAO;
import com.neoway.persistence.model.Company;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

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

        if(name == null || name.isEmpty()) {
            return Response.status(Response.Status.CONFLICT).entity("NAME value are mandatory!").build();
        }
        if(zip == null || zip.isEmpty()) {
            return Response.status(Response.Status.CONFLICT).entity("ZIP value are mandatory!").build();
        }

        List<Company> companyList = companyDAO.filterCompany(name, zip);
        if (companyList.size() == 0) {
            return Response.status(Response.Status.CONFLICT).entity("No records found for: \nNAME: " +name +"\nZIP: " +zip).build();
        }
        return Response.status(Response.Status.OK).entity(companyList).build();
    }

    @POST
    @Path(ResourcePath.SERVICE_COMPANY)
    @Consumes(MediaType.TEXT_PLAIN)
    public final Response create(final InputStream inputStream) {
        List<Company> companyList;
        try {
            companyList = GenericCompanyReader.readStream(inputStream);
            for(Company company: companyList) {
                if (companyDAO.filterCompany(company.getName(), company.getZip()).size() == 0) {
                    companyDAO.save(company);
                }else{
                    //FIXME do update if record exists
                }
            }
        } catch (IOException e) {
            final Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new WebApplicationException(rootCause, Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(rootCause.getMessage()).build());
        }
        return Response.status(Response.Status.OK).build();
    }

}
