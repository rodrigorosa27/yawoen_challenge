import com.neoway.Configuration;
import com.neoway.csv.GenericCompanyReader;
import com.neoway.persistence.model.Company;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GenericCompanyReaderTest {

    final Configuration configuration = Configuration.getInstance();

    final InputStream sampleFile = Configuration.class.getClassLoader().getResourceAsStream("q1_catalog.csv");

    @Test
    public void readStreamField() throws IOException {
        List<Company> fileCompanyList = GenericCompanyReader.readStream(sampleFile);
        Assert.assertNotNull(fileCompanyList);
        Company company = fileCompanyList.get(0);
        Assert.assertNull(company.getId());
        Assert.assertNull(company.getWebsite());
        Assert.assertNotNull(company.getName());
        Assert.assertNotNull(company.getZip());
    }

}
