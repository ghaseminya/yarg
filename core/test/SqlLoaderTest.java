import com.haulmont.newreport.loaders.impl.SqlDataLoader;
import com.haulmont.newreport.structure.impl.Band;
import com.haulmont.newreport.structure.impl.BandOrientation;
import com.haulmont.newreport.structure.impl.DataSetImpl;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author degtyarjov
 * @version $Id$
 */

public class SqlLoaderTest {
    @Test
    public void testListParameter() throws Exception {
        TestDatabase testDatabase = new TestDatabase();
        testDatabase.setUpDatabase();

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("login", Arrays.asList("login1", "login2"));
            SqlDataLoader sqlDataLoader = new SqlDataLoader(testDatabase.getDs());
            Band rootBand = new Band("band1", null, BandOrientation.HORIZONTAL);
            rootBand.setData(Collections.<String, Object>emptyMap());

            List<Map<String, Object>> result = sqlDataLoader.loadData(
                    new DataSetImpl("", "select login, password from user where login in (${login})", "sql"), rootBand, params);
            printResult(result);
            Assert.assertEquals(2, result.size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testArrayParameter() throws Exception {
        TestDatabase testDatabase = new TestDatabase();
        testDatabase.setUpDatabase();

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("login", new String[]{"login1", "login2"});
            SqlDataLoader sqlDataLoader = new SqlDataLoader(testDatabase.getDs());
            Band rootBand = new Band("band1", null, BandOrientation.HORIZONTAL);
            rootBand.setData(Collections.<String, Object>emptyMap());

            List<Map<String, Object>> result = sqlDataLoader.loadData(
                    new DataSetImpl("", "select login, password from user where login in (${login})", "sql"), rootBand, params);
            printResult(result);
            Assert.assertEquals(2, result.size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private void printResult(List<Map<String, Object>> result) {
        for (Map<String, Object> stringObjectMap : result) {
            for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()) {
                System.out.print("(" + entry.getKey() + ":" + entry.getValue() + ") ");
            }
            System.out.println();
        }
    }
}