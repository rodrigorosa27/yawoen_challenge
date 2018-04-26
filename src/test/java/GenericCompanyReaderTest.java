import com.neoway.Configuration;
import com.neoway.csv.CompanyReaderListener;
import com.neoway.csv.GenericCompanyReader;
import com.neoway.persistence.model.Company;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class GenericCompanyReaderTest implements CompanyReaderListener{

    final Configuration configuration = Configuration.getInstance();

    final InputStream sampleFile = Configuration.class.getClassLoader().getResourceAsStream("q1_catalog.csv");

    @Test
    public void readStreamField() throws IOException {
        GenericCompanyReader.readStream(sampleFile, this);
    }

    @Override
    public void processCompany(final Company newCompany) {
        Company company = newCompany;
        Assert.assertNull(company.getId());
        Assert.assertNull(company.getWebsite());
        Assert.assertNotNull(company.getName());
        Assert.assertNotNull(company.getZip());
    }
}
