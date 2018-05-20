import java.util.Arrays;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {

    @Before
    public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "agnes", "a9%Mrdvs");
    }

    @After
    public void tearDown() {
        try (Connection con = DB.sql2o.open()) {
            String deleteClientQuery = "DELETE FROM client *;";
            String deleteStylistQuery = "DELETE FROM stylist *;";
            con.createQuery(deleteClientQuery).executeUpdate();
            con.createQuery(deleteStylistQuery).executeUpdate();
        }
    }

    @Test
    public void getName_StylistInstantiatesWithName_Braider() {
        Stylist testStylist = new Stylist("Breider");
        assertEquals("Breider", testStylist.getName());
    }

    @Test
    public void equals_returnsTrueIfNamesAretheSame() {
        Stylist firstStylist = new Stylist("Breider");
        Stylist secondStylist = new Stylist("Breider");
        assertTrue(firstStylist.equals(secondStylist));
    }

    @Test
    public void save_savesIntoDatabase_true() {
        Stylist myStylist = new Stylist("Breider");
        myStylist.save();
        assertTrue(Stylist.all().get(0).equals(myStylist));
    }

    @Test
    public void all_returnsAllInstancesOfStylist_true() {
        Stylist firstStylist = new Stylist("Breider");
        firstStylist.save();
        Stylist secondStylist = new Stylist("Wash and set");
        secondStylist.save();
        assertEquals(true, Stylist.all().get(0).equals(firstStylist));
        assertEquals(true, Stylist.all().get(1).equals(secondStylist));
    }

    @Test
    public void save_assignsIdToObject() {
        Stylist myStylist = new Stylist("Breider");
        myStylist.save();
        Stylist savedStylist = Stylist.all().get(0);
        assertEquals(myStylist.getId(), savedStylist.getId());
    }

    @Test
    public void getId_stylistInstantiateWithAnId_1() {
        Stylist testStylist = new Stylist("Breider");
        testStylist.save();
        assertTrue(testStylist.getId() > 0);
    }

    @Test
    public void find_returnsStylistWithSameId_secondStylist() {
        Stylist firstStylist = new Stylist("Breider");
        firstStylist.save();
        Stylist secondStylist = new Stylist("Wash and set");
        secondStylist.save();
        assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
    }

    @Test
      public void getClients_retrievesALlClientsFromDatabase_clientsList() {
        Stylist myStylist = new Stylist("Breider");
        myStylist.save();
        Client firstClient = new Client("Agnes", myStylist.getId());
        firstClient.save();
        Client secondClient = new Client("Mary", myStylist.getId());
        secondClient.save();
        Client[] clients = new Client[] { firstClient, secondClient };
        assertTrue(myStylist.getClients().containsAll(Arrays.asList(clients)));
      }
}
