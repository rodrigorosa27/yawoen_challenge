import com.neoway.persistence.model.Company;
import org.junit.Assert;
import org.junit.Test;

public class CompanyTest {

    @Test
    public void testName() {
        Company company = new Company();
        company.setName("Test");
        Assert.assertEquals("Test", company.getName());
    }

    @Test
    public void testZip() {
        Company company = new Company();
        company.setZip("12345");
        Assert.assertEquals("12345", company.getZip());
    }

    @Test
    public void testWebsite() {
        Company company = new Company();
        company.setWebsite("http://www.test.org");
        Assert.assertEquals("http://www.test.org", company.getWebsite());
    }

}
