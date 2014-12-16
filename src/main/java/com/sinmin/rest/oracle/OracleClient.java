package com.sinmin.rest.oracle;

import com.sinmin.rest.beans.response.WordFrequencyR;
import oracle.jdbc.OracleCallableStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dimuthuupeksha on 12/11/14.
 */
public class OracleClient {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    //private static final String DB_CONNECTION = "jdbc:oracle:thin:@//localhost:1521/PDB1";
    //private static final String DB_USER = "sinmin";
    //private static final String DB_PASSWORD = "sinmin";

    private static final String DB_CONNECTION = "jdbc:oracle:thin:@//192.248.15.239:1522/corpus.sinmin.com";
    private static final String DB_USER = "sinmin";
    private static final String DB_PASSWORD = "Sinmin1234";

    private static Connection dbConnection = null;


    public static Connection getDBConnection() throws SQLException, ClassNotFoundException {

        if (dbConnection == null || dbConnection.isClosed()) {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        }

        return dbConnection;
    }

    public WordFrequencyR getWordFrequency(String word_value) throws SQLException, ClassNotFoundException {
        getDBConnection();
        String sql = "select count(sw.sentence_id) from word w, sentence_word sw where w.VAL='" + word_value + "' and sw.word_id=w.id";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setDate(0);
            resp.setCategory("all");
            resp.setFrequency(Integer.parseInt(frequency));
        }

        return resp;
    }

    public WordFrequencyR getWordFrequency(String word_value, int time) throws SQLException, ClassNotFoundException {
        getDBConnection();
        String sql = "select count(sw.sentence_id) from word w, sentence_word sw, sentence s, article a where w.VAL='" + word_value + "' and sw.word_id=w.id and s.id=sw.SENTENCE_ID and a.id = s.ARTICLE_ID and a.year=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1, time);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setCategory("all");
            resp.setDate(time);
            resp.setFrequency(Integer.parseInt(frequency));
        }
        return resp;
    }

    public WordFrequencyR getWordFrequency(String word_value, String category) throws SQLException, ClassNotFoundException {
        getDBConnection();
        String sql = "select count(sw.sentence_id) from word w, sentence_word sw, sentence s, article a where w.VAL='" + word_value + "' and sw.word_id=w.id and s.id=sw.SENTENCE_ID and a.id = s.ARTICLE_ID and a.category=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1, category);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setDate(0);
            resp.setCategory(category);
            resp.setFrequency(Integer.parseInt(frequency));
        }
        return resp;

    }

    public WordFrequencyR getWordFrequency(String word_value, int time, String category) throws SQLException, ClassNotFoundException {
        getDBConnection();
        String sql = "select count(sw.sentence_id) from word w, sentence_word sw, sentence s, article a where w.VAL='" + word_value + "' and sw.word_id=w.id and s.id=sw.SENTENCE_ID and a.id = s.ARTICLE_ID and a.category=? and a.year=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1, category);
        stmt.setInt(2, time);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setDate(time);
            resp.setCategory(category);
            resp.setFrequency(Integer.parseInt(frequency));
        }
        return resp;

    }

    public WordFrequencyR getBigramFrequency(String word1, String word2,int year, String category) throws SQLException, ClassNotFoundException{
        getDBConnection();
        String sql ="select count(sb.sentence_id) from word w1,word w2,bigram b,sentence_bigram sb,sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and b.word1=w1.id and b.word2=w2.id and sb.bigram_id=b.id and s.id = sb.sentence_id and a.id = s.article_id and a.year=? and a.category=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setString(2, category);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setDate(year);
            resp.setCategory(category);
            resp.setFrequency(Integer.parseInt(frequency));
        }
        return resp;
    }

    public WordFrequencyR getBigramFrequency(String word1, String word2,int year) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select count(sb.sentence_id) from word w1,word w2,bigram b,sentence_bigram sb,sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and b.word1=w1.id and b.word2=w2.id and sb.bigram_id=b.id and s.id = sb.sentence_id and a.id = s.article_id and a.year=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1, year);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setDate(year);
            resp.setCategory("all");
            resp.setFrequency(Integer.parseInt(frequency));
        }
        return resp;
    }

    public WordFrequencyR getBigramFrequency(String word1, String word2,String category) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select count(sb.sentence_id) from word w1,word w2,bigram b,sentence_bigram sb,sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and b.word1=w1.id and b.word2=w2.id and sb.bigram_id=b.id and s.id = sb.sentence_id and a.id = s.article_id and a.category=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1, category);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setDate(0);
            resp.setCategory(category);
            resp.setFrequency(Integer.parseInt(frequency));
        }
        return resp;
    }

    public WordFrequencyR getBigramFrequency(String word1, String word2) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select count(sb.sentence_id) from word w1,word w2,bigram b,sentence_bigram sb where w1.val='"+word1+"' and w2.val='"+word2+"' and b.word1=w1.id and b.word2=w2.id and sb.bigram_id=b.id";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setDate(0);
            resp.setCategory("all");
            resp.setFrequency(Integer.parseInt(frequency));
        }
        return resp;
    }

    public WordFrequencyR getTrigramFrequency(String word1, String word2,String word3,int year, String category)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select count(st.sentence_id) from word w1,word w2,word w3, trigram t,sentence_trigram st,sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and w3.val='"+word3+"' and t.word1=w1.id and t.word2=w2.id and t.word3=w3.id and st.trigram_id=t.id and s.id = st.sentence_id and a.id = s.article_id and a.year=? and a.category=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setString(2, category);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setDate(year);
            resp.setCategory(category);
            resp.setFrequency(Integer.parseInt(frequency));
        }
        return resp;
    }

    public WordFrequencyR getTrigramFrequency(String word1, String word2,String word3,int year)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select count(st.sentence_id) from word w1,word w2,word w3, trigram t,sentence_trigram st,sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and w3.val='"+word3+"' and t.word1=w1.id and t.word2=w2.id and t.word3=w3.id and st.trigram_id=t.id and s.id = st.sentence_id and a.id = s.article_id and a.year=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setDate(year);
            resp.setCategory("all");
            resp.setFrequency(Integer.parseInt(frequency));
        }
        return resp;
    }

    public WordFrequencyR getTrigramFrequency(String word1, String word2,String word3,String category)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select count(st.sentence_id) from word w1,word w2,word w3, trigram t,sentence_trigram st,sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and w3.val='"+word3+"' and t.word1=w1.id and t.word2=w2.id and t.word3=w3.id and st.trigram_id=t.id and s.id = st.sentence_id and a.id = s.article_id and a.category=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1, category);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setDate(0);
            resp.setCategory(category);
            resp.setFrequency(Integer.parseInt(frequency));
        }
        return resp;
    }

    public WordFrequencyR getTrigramFrequency(String word1, String word2,String word3)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select count(st.sentence_id) from word w1,word w2,word w3, trigram t,sentence_trigram st where w1.val='"+word1+"' and w2.val='"+word2+"' and w3.val='"+word3+"' and t.word1=w1.id and t.word2=w2.id and t.word3=w3.id and st.trigram_id=t.id";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        ResultSet rst = stmt.executeQuery();
        WordFrequencyR resp = new WordFrequencyR();
        while (rst.next()) {
            String frequency = rst.getString(1);
            System.out.println("Count of word " + frequency);
            resp.setDate(0);
            resp.setCategory("all");
            resp.setFrequency(Integer.parseInt(frequency));
        }
        return resp;
    }

}
