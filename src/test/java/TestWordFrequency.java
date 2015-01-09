import com.sinmin.rest.beans.response.FrequentWordR;
import com.sinmin.rest.beans.response.WordFrequencyR;
import com.sinmin.rest.beans.response.WordR;
import com.sinmin.rest.oracle.OracleClient;
import org.h2.tools.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class TestWordFrequency {
    Connection conn;
    Server server;
    @Before
    public void before() throws ClassNotFoundException, SQLException{
        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:mem:test");

        String schemaPath = this.getClass().getResource("oracle_schema.sql").getPath();
        String indexesPath = this.getClass().getResource("oracle_indexes.sql").getPath();

        String sentenceDataPath = this.getClass().getResource("oracle_sentence.sql").getPath();
        String articleDataPath = this.getClass().getResource("oracle_article.sql").getPath();

        String wordDataPath = this.getClass().getResource("oracle_word.sql").getPath();
        String bigramDataPath = this.getClass().getResource("oracle_bigram.sql").getPath();
        String trigramDataPath = this.getClass().getResource("oracle_trigram.sql").getPath();

        String wordSentenceDataPath = this.getClass().getResource("oracle_sentence_word.sql").getPath();
        String bigramSentenceDataPath = this.getClass().getResource("oracle_sentence_bigram.sql").getPath();
        String trigramSentenceDataPath = this.getClass().getResource("oracle_sentence_trigram.sql").getPath();

        conn.createStatement().execute("RUNSCRIPT FROM '"+schemaPath+"'");
        conn.createStatement().execute("RUNSCRIPT FROM '"+sentenceDataPath+"'");
        conn.createStatement().execute("RUNSCRIPT FROM '"+articleDataPath+"'");
        conn.createStatement().execute("RUNSCRIPT FROM '"+wordDataPath+"'");
        conn.createStatement().execute("RUNSCRIPT FROM '"+bigramDataPath+"'");
        conn.createStatement().execute("RUNSCRIPT FROM '"+trigramDataPath+"'");
        conn.createStatement().execute("RUNSCRIPT FROM '"+wordSentenceDataPath+"'");
        conn.createStatement().execute("RUNSCRIPT FROM '"+bigramSentenceDataPath+"'");
        conn.createStatement().execute("RUNSCRIPT FROM '"+trigramSentenceDataPath+"'");
        conn.createStatement().execute("RUNSCRIPT FROM '"+indexesPath+"'");
        OracleClient.setDbConnection(conn);
        // start a TCP server
        // (either before or after opening the database)
        server = Server.createTcpServer().start();
    }

    @After
    public void after() throws SQLException {
        OracleClient.setDbConnection(null);
        conn.close();
        server.stop();
    }

    @Test
    public void testWordFrequency() throws Exception {
        OracleClient client = new OracleClient();
        WordFrequencyR resp =client.getWordFrequency("කාර්යාල", 2012, "NEWS");
        assertEquals(resp.getFrequency(),1);
        resp =client.getWordFrequency("කාර්යාල", "NEWS");
        assertEquals(resp.getFrequency(),1);
        resp =client.getWordFrequency("කාර්යාල", 2012);
        assertEquals(resp.getFrequency(),1);
    }


}