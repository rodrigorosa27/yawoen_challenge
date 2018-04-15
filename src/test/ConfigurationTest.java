import com.neoway.Configuration;
import com.neoway.persistence.model.Company;
import org.junit.Assert;
import org.junit.Test;

public class ConfigurationTest {

    final Configuration configuration = Configuration.getInstance();

    @Test
    public void getDatabaseHostKey() {
        Assert.assertNotNull(configuration.getProperty("DATABASE_HOST"));
    }

    @Test
    public void getDatabasePortKey() {
        Assert.assertNotNull(configuration.getProperty("DATABASE_PORT"));
    }

    @Test
    public void getDatabaseNameKey() {
        Assert.assertNotNull(configuration.getProperty("DATABASE_NAME"));
    }

}
