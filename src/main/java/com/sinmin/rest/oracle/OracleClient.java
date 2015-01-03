package com.sinmin.rest.oracle;

import com.sinmin.rest.CorpusDBClient;
import com.sinmin.rest.beans.request.WordPosition;
import com.sinmin.rest.beans.response.*;
import oracle.jdbc.OracleCallableStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dimuthuupeksha on 12/11/14.
 */
public class OracleClient implements CorpusDBClient{

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
        rst.close();
        stmt.close();
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
        rst.close();
        stmt.close();
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
        rst.close();
        stmt.close();
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
        rst.close();
        stmt.close();
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
        rst.close();
        stmt.close();
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
        rst.close();
        stmt.close();
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
        rst.close();
        stmt.close();
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
        rst.close();
        stmt.close();
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
        rst.close();
        stmt.close();
        return resp;
    }

    public WordFrequencyR getTrigramFrequency(String word1, String word2,String word3,int year)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select count(st.sentence_id) from word w1,word w2,word w3, trigram t,sentence_trigram st,sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and w3.val='"+word3+"' and t.word1=w1.id and t.word2=w2.id and t.word3=w3.id and st.trigram_id=t.id and s.id = st.sentence_id and a.id = s.article_id and a.year=?";
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
        rst.close();
        stmt.close();
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
        rst.close();
        stmt.close();
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
        rst.close();
        stmt.close();
        return resp;
    } 
    /////////////////////Frequent Words///////////////
    public FrequentWordR getFrequentWords(int year, String category,int amount) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w2.val,tot from(select sw.word_id,count(*) as tot from sentence_word sw, sentence s, article a where s.id=sw.sentence_id and a.id = s.article_id and a.year=? and a.category=? group by sw.word_id order by count(*) desc) res, word w2 where w2.id = res.word_id and rownum <=?";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setString(2, category);
        stmt.setInt(3,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            String value = rst.getString(1);
            int frequency = rst.getInt(2);
            System.out.println("Count of word " + value +" is "+frequency);

            val1[i].setValue(value);
            val1[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory(category);
        resp.setTime(year);
        resp.setValue1(val1);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordR getFrequentWords(int year,int amount) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w2.val,tot from(select sw.word_id,count(*) as tot from sentence_word sw, sentence s, article a where s.id=sw.sentence_id and a.id = s.article_id and a.year=? group by sw.word_id order by count(*) desc) res, word w2 where w2.id = res.word_id and rownum <=?";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1, year);
        stmt.setInt(2,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            String value = rst.getString(1);
            int frequency = rst.getInt(2);
            System.out.println("Count of word " + value +" is "+frequency);

            val1[i].setValue(value);
            val1[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory("all");
        resp.setTime(year);
        resp.setValue1(val1);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordR getFrequentWords(String category,int amount) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w2.val,tot from(select sw.word_id,count(*) as tot from sentence_word sw, sentence s, article a where s.id=sw.sentence_id and a.id = s.article_id and a.category=? group by sw.word_id order by count(*) desc) res, word w2 where w2.id = res.word_id and rownum <=?";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1, category);
        stmt.setInt(2,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            String value = rst.getString(1);
            int frequency = rst.getInt(2);
            System.out.println("Count of word " + value +" is "+frequency);

            val1[i].setValue(value);
            val1[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory(category);
        resp.setTime(0);
        resp.setValue1(val1);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordR getFrequentWords(int amount) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w2.val,tot from(select sw.word_id,count(*) as tot from sentence_word sw group by sw.word_id order by count(*) desc) res, word w2 where w2.id = res.word_id and rownum <=?";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            String value = rst.getString(1);
            int frequency = rst.getInt(2);
            System.out.println("Count of word " + value +" is "+frequency);

            val1[i].setValue(value);
            val1[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory("all");
        resp.setTime(0);
        resp.setValue1(val1);
        rst.close();
        stmt.close();
        return resp;
    }
/////////////////////////////Frequent Bigrams //////////////////////////////

    public FrequentWordR getFrequentBigrams(int year, String category,int amount) throws SQLException, ClassNotFoundException{

        getDBConnection();
        String sql ="select w1.val,w2.val,res.tot from(select sb.bigram_id,count(*) as tot from sentence_bigram sb, sentence s, article a where s.id=sb.sentence_id and a.id = s.article_id and a.year=? and a.category=? group by sb.bigram_id order by count(*) desc) res, bigram b, word w1, word w2 where b.id=res.bigram_id and w1.id=b.word1 and w2.id=b.word2 and rownum <=?";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setString(2, category);
        stmt.setInt(3,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            String value1 = rst.getString(1);
            String value2 = rst.getString(2);
            int frequency = rst.getInt(3);
            System.out.println("Count of bigram " + value1 +" "+value2+" is "+frequency);

            val1[i].setValue(value1);
            val1[i].setFrequency(frequency);
            val2[i].setValue(value2);
            val2[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory(category);
        resp.setTime(year);
        resp.setValue1(val1);
        resp.setValue2(val2);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordR getFrequentBigrams(int year,int amount) throws SQLException, ClassNotFoundException{

        getDBConnection();
        String sql ="select w1.val,w2.val,res.tot from(select sb.bigram_id,count(*) as tot from sentence_bigram sb, sentence s, article a where s.id=sb.sentence_id and a.id = s.article_id and a.year=? group by sb.bigram_id order by count(*) desc) res, bigram b, word w1, word w2 where b.id=res.bigram_id and w1.id=b.word1 and w2.id=b.word2 and rownum <=?";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1, year);
        stmt.setInt(2,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            String value1 = rst.getString(1);
            String value2 = rst.getString(2);
            int frequency = rst.getInt(3);
            System.out.println("Count of bigram " + value1 +" "+value2+" is "+frequency);

            val1[i].setValue(value1);
            val1[i].setFrequency(frequency);
            val2[i].setValue(value2);
            val2[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory("all");
        resp.setTime(year);
        resp.setValue1(val1);
        resp.setValue2(val2);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordR getFrequentBigrams(String category,int amount) throws SQLException, ClassNotFoundException{

        getDBConnection();
        String sql ="select w1.val,w2.val,res.tot from(select sb.bigram_id,count(*) as tot from sentence_bigram sb, sentence s, article a where s.id=sb.sentence_id and a.id = s.article_id and a.category=? group by sb.bigram_id order by count(*) desc) res, bigram b, word w1, word w2 where b.id=res.bigram_id and w1.id=b.word1 and w2.id=b.word2 and rownum <=?";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1, category);
        stmt.setInt(2,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            String value1 = rst.getString(1);
            String value2 = rst.getString(2);
            int frequency = rst.getInt(3);
            System.out.println("Count of bigram " + value1 +" "+value2+" is "+frequency);

            val1[i].setValue(value1);
            val1[i].setFrequency(frequency);
            val2[i].setValue(value2);
            val2[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory(category);
        resp.setTime(0);
        resp.setValue1(val1);
        resp.setValue2(val2);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordR getFrequentBigrams(int amount) throws SQLException, ClassNotFoundException{

        getDBConnection();
        String sql ="select w1.val,w2.val,res.tot from(select sb.bigram_id,count(*) as tot from sentence_bigram sb  group by sb.bigram_id order by count(*) desc) res, bigram b, word w1, word w2 where b.id=res.bigram_id and w1.id=b.word1 and w2.id=b.word2 and rownum <=?";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            String value1 = rst.getString(1);
            String value2 = rst.getString(2);
            int frequency = rst.getInt(3);
            System.out.println("Count of bigram " + value1 +" "+value2+" is "+frequency);

            val1[i].setValue(value1);
            val1[i].setFrequency(frequency);
            val2[i].setValue(value2);
            val2[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory("all");
        resp.setTime(0);
        resp.setValue1(val1);
        resp.setValue2(val2);
        rst.close();
        stmt.close();
        return resp;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public FrequentWordR getFrequentTrigrams(int year, String category,int amount) throws SQLException, ClassNotFoundException{

        getDBConnection();
        String sql ="select w1.val,w2.val,w3.val,res.tot from(select st.trigram_id,count(*) as tot from sentence_trigram st, sentence s, article a where s.id=st.sentence_id and a.id = s.article_id and a.year=? and a.category=? group by st.trigram_id order by count(*) desc) res, trigram t, word w1, word w2, word w3 where t.id=res.trigram_id and w1.id=t.word1 and w2.id=t.word2 and w3.id=t.word3 and rownum <=?  ";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setString(2, category);
        stmt.setInt(3,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            String value1 = rst.getString(1);
            String value2 = rst.getString(2);
            String value3 = rst.getString(3);
            int frequency = rst.getInt(4);
            System.out.println("Count of trigram " + value1 +" "+value2+" "+value3+" is "+frequency);

            val1[i].setValue(value1);
            val1[i].setFrequency(frequency);
            val2[i].setValue(value2);
            val2[i].setFrequency(frequency);
            val3[i].setValue(value3);
            val3[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory(category);
        resp.setTime(year);
        resp.setValue1(val1);
        resp.setValue2(val2);
        resp.setValue3(val3);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordR getFrequentTrigrams(int year,int amount) throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql ="select w1.val,w2.val,w3.val,res.tot from(select st.trigram_id,count(*) as tot from sentence_trigram st, sentence s, article a where s.id=st.sentence_id and a.id = s.article_id and a.year=? group by st.trigram_id order by count(*) desc) res, trigram t, word w1, word w2, word w3 where t.id=res.trigram_id and w1.id=t.word1 and w2.id=t.word2 and w3.id=t.word3 and rownum <=?  ";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setInt(2,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            String value1 = rst.getString(1);
            String value2 = rst.getString(2);
            String value3 = rst.getString(3);
            int frequency = rst.getInt(4);
            System.out.println("Count of trigram " + value1 +" "+value2+" "+value3+" is "+frequency);

            val1[i].setValue(value1);
            val1[i].setFrequency(frequency);
            val2[i].setValue(value2);
            val2[i].setFrequency(frequency);
            val3[i].setValue(value3);
            val3[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory("all");
        resp.setTime(year);
        resp.setValue1(val1);
        resp.setValue2(val2);
        resp.setValue3(val3);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordR getFrequentTrigrams(String category,int amount) throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql ="select w1.val,w2.val,w3.val,res.tot from(select st.trigram_id,count(*) as tot from sentence_trigram st, sentence s, article a where s.id=st.sentence_id and a.id = s.article_id and a.category=? group by st.trigram_id order by count(*) desc) res, trigram t, word w1, word w2, word w3 where t.id=res.trigram_id and w1.id=t.word1 and w2.id=t.word2 and w3.id=t.word3 and rownum <=?  ";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1, category);
        stmt.setInt(2,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            String value1 = rst.getString(1);
            String value2 = rst.getString(2);
            String value3 = rst.getString(3);
            int frequency = rst.getInt(4);
            System.out.println("Count of trigram " + value1 +" "+value2+" "+value3+" is "+frequency);

            val1[i].setValue(value1);
            val1[i].setFrequency(frequency);
            val2[i].setValue(value2);
            val2[i].setFrequency(frequency);
            val3[i].setValue(value3);
            val3[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory(category);
        resp.setTime(0);
        resp.setValue1(val1);
        resp.setValue2(val2);
        resp.setValue3(val3);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordR getFrequentTrigrams(int amount) throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql ="select w1.val,w2.val,w3.val,res.tot from(select st.trigram_id,count(*) as tot from sentence_trigram st group by st.trigram_id order by count(*) desc) res, trigram t, word w1, word w2, word w3 where t.id=res.trigram_id and w1.id=t.word1 and w2.id=t.word2 and w3.id=t.word3 and rownum <=?  ";

        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,amount);

        ResultSet rst = stmt.executeQuery();
        FrequentWordR resp = new FrequentWordR();
        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        int i=0;
        while (rst.next()) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            String value1 = rst.getString(1);
            String value2 = rst.getString(2);
            String value3 = rst.getString(3);
            int frequency = rst.getInt(4);
            System.out.println("Count of trigram " + value1 +" "+value2+" "+value3+" is "+frequency);

            val1[i].setValue(value1);
            val1[i].setFrequency(frequency);
            val2[i].setValue(value2);
            val2[i].setFrequency(frequency);
            val3[i].setValue(value3);
            val3[i].setFrequency(frequency);
            i++;
        }
        resp.setCategory("all");
        resp.setTime(0);
        resp.setValue1(val1);
        resp.setValue2(val2);
        resp.setValue3(val3);
        rst.close();
        stmt.close();
        return resp;
    }


    /////////////////////Latest Articles for word ////////////////////////

    public ArticlesForWordR getLatestArticlesForWord(String word,int year, String category,int amount) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id,sw.position, w.val,a.year,a.month,a.day from word w, sentence_word sw, sentence s, article a where w.val='"+word+"' and sw.word_id=w.id and s.id=sw.sentence_id and a.id = s.article_id and a.year=? and a.category=? order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setString(2,category);
        stmt.setInt(3,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory(category);
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForWord(String word,int year,int amount) throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id,sw.position, w.val,a.year,a.month,a.day from word w, sentence_word sw, sentence s, article a where w.val='"+word+"' and sw.word_id=w.id and s.id=sw.sentence_id and a.id = s.article_id and a.year=? order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setInt(2,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory("all");
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForWord(String word,String category,int amount)throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id,sw.position, w.val,a.year,a.month,a.day from word w, sentence_word sw, sentence s, article a where w.val='"+word+"' and sw.word_id=w.id and s.id=sw.sentence_id and a.id = s.article_id and a.category=? order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1,category);
        stmt.setInt(2,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory(category);
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForWord(String word,int amount)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id,sw.position, w.val,a.year,a.month,a.day from word w, sentence_word sw, sentence s, article a where w.val='"+word+"' and sw.word_id=w.id and s.id=sw.sentence_id and a.id = s.article_id order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory("all");
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }


    //////////////// latest articles for bigrams

    public ArticlesForWordR getLatestArticlesForBigram(String word1, String word2,int year, String category,int amount)throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id ,a.year,a.month,a.day from word w1, word w2, sentence_word sw1, sentence_word sw2, sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"'  and sw1.word_id=w1.id and sw2.word_id=w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position=sw1.position+1 and s.id=sw1.sentence_id and a.id = s.article_id and a.year=? and a.category=? order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setString(2,category);
        stmt.setInt(3,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory(category);
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForBigram(String word1, String word2,int year,int amount)throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id ,a.year,a.month,a.day from word w1, word w2, sentence_word sw1, sentence_word sw2, sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"'  and sw1.word_id=w1.id and sw2.word_id=w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position=sw1.position+1 and s.id=sw1.sentence_id and a.id = s.article_id and a.year=? order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1, year);
        stmt.setInt(2,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory("all");
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForBigram(String word1,String word2,String category,int amount)throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id ,a.year,a.month,a.day from word w1, word w2, sentence_word sw1, sentence_word sw2, sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"'  and sw1.word_id=w1.id and sw2.word_id=w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position=sw1.position+1 and s.id=sw1.sentence_id and a.id = s.article_id and a.category=? order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);

        stmt.setString(1,category);
        stmt.setInt(2,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory(category);
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForBigram(String word1,String word2,int amount)throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id ,a.year,a.month,a.day from word w1, word w2, sentence_word sw1, sentence_word sw2, sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"'  and sw1.word_id=w1.id and sw2.word_id=w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position=sw1.position+1 and s.id=sw1.sentence_id and a.id = s.article_id order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);

        stmt.setInt(1,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory("all");
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }


    ///////////// Latest articles in trigrams /////////////

    public ArticlesForWordR getLatestArticlesForTrigram(String word1, String word2,String word3, int year, String category,int amount)throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id ,a.year,a.month,a.day from word w1, word w2,word w3, sentence_word sw1, sentence_word sw2,sentence_word sw3, sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and w3.val='"+word3+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and sw3.word_id=w3.id and sw1.sentence_id=sw2.sentence_id and sw1.sentence_id=sw3.sentence_id and sw2.position=sw1.position+1 and sw3.position=sw1.position+2  and s.id=sw1.sentence_id and a.id = s.article_id and a.year=? and a.category=? order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setString(2,category);
        stmt.setInt(3,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory(category);
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForTrigram(String word1, String word2,String word3,int year,int amount)throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id ,a.year,a.month,a.day from word w1, word w2,word w3, sentence_word sw1, sentence_word sw2,sentence_word sw3, sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and w3.val='"+word3+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and sw3.word_id=w3.id and sw1.sentence_id=sw2.sentence_id and sw1.sentence_id=sw3.sentence_id and sw2.position=sw1.position+1 and sw3.position=sw1.position+2  and s.id=sw1.sentence_id and a.id = s.article_id and a.year=? order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1, year);
        stmt.setInt(2,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory("all");
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForTrigram(String word1,String word2,String word3,String category,int amount)throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id ,a.year,a.month,a.day from word w1, word w2,word w3, sentence_word sw1, sentence_word sw2,sentence_word sw3, sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and w3.val='"+word3+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and sw3.word_id=w3.id and sw1.sentence_id=sw2.sentence_id and sw1.sentence_id=sw3.sentence_id and sw2.position=sw1.position+1 and sw3.position=sw1.position+2  and s.id=sw1.sentence_id and a.id = s.article_id and a.category=? order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1,category);
        stmt.setInt(2,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory(category);
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForTrigram(String word1,String word2, String word3,int amount)throws SQLException,ClassNotFoundException{

        getDBConnection();
        String sql = "select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id ,a.year,a.month,a.day from word w1, word w2,word w3, sentence_word sw1, sentence_word sw2,sentence_word sw3, sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and w3.val='"+word3+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and sw3.word_id=w3.id and sw1.sentence_id=sw2.sentence_id and sw1.sentence_id=sw3.sentence_id and sw2.position=sw1.position+1 and sw3.position=sw1.position+2  and s.id=sw1.sentence_id and a.id = s.article_id order by a.year desc,a.month desc,a.day desc) where rownum<=?) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,amount);
        ResultSet rst = stmt.executeQuery();
        int sentenceId = 0;
        int articleId =0;
        ArticleR article = new ArticleR();
        List<ArticleR> articleList = new ArrayList<>();
        String sentence = "";
        while(rst.next()){
            int newSentenceId = rst.getInt(2);
            String newWord = rst.getString(5);

            if(newSentenceId!=sentenceId){
                if(sentenceId!=0){
                    article.setSentence(sentence);
                    articleList.add(article);
                    sentence ="";
                }

                article = new ArticleR();
                article.setCategory(rst.getString(6));
                article.setTitle(rst.getString(7));
                article.setAuthor(rst.getString(8));
                article.setYear(rst.getInt(9));
                article.setMonth(rst.getInt(10));
                article.setDay(rst.getInt(11));
                sentenceId=newSentenceId;
            }
            sentence += newWord+" ";
        }

        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory("all");
        ArticleR[] a = new ArticleR[articleList.size()];
        articlesForWord.setArticles(articleList.toArray(a));
        rst.close();
        stmt.close();
        return articlesForWord;
    }

    ///////////////////Frequencies around a word///////////////////////////////////////////////

    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, String category, int year, int range, int amount) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String  sql ="";
        if(range>0){
            sql = "select w.val, resp.frequency from (select w2.id, count(*) as frequency from word w1, word w2, sentence_word sw1,sentence_word sw2, sentence s, article a where w1.val='"+word+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and w1.id<>w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position<=sw1.position+? and sw2.position>sw1.position and s.id = sw1.sentence_id and a.id= s.article_id and a.year=? and a.category=? group by w2.id order by count(*) desc) resp, word w where w.id= resp.id and rownum<=?";
        }else{
            sql = "select w.val, resp.frequency from (select w2.id, count(*) as frequency from word w1, word w2, sentence_word sw1,sentence_word sw2, sentence s, article a where w1.val='"+word+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and w1.id<>w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position>=sw1.position-? and sw2.position<sw1.position and s.id = sw1.sentence_id and a.id= s.article_id and a.year=? and a.category=? group by w2.id order by count(*) desc) resp, word w where w.id= resp.id and rownum<=?";
        }
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,Math.abs(range));
        stmt.setInt(2,year);
        stmt.setString(3, category);
        stmt.setInt(4,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);

        FrequentWordsAroundWordR resp = new FrequentWordsAroundWordR();
        resp.setCategory(category);
        resp.setTime(year);
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, int year, int range, int amount) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="";
        if(range>0){
            sql = "select w.val, resp.frequency from (select w2.id, count(*) as frequency from word w1, word w2, sentence_word sw1,sentence_word sw2, sentence s, article a where w1.val='"+word+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and w1.id<>w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position<=sw1.position+? and sw2.position>sw1.position and s.id = sw1.sentence_id and a.id= s.article_id and a.year=? group by w2.id order by count(*) desc) resp, word w where w.id= resp.id and rownum<=?";
        }else{
            sql = "select w.val, resp.frequency from (select w2.id, count(*) as frequency from word w1, word w2, sentence_word sw1,sentence_word sw2, sentence s, article a where w1.val='"+word+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and w1.id<>w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position>=sw1.position-? and sw2.position<sw1.position and s.id = sw1.sentence_id and a.id= s.article_id and a.year=? group by w2.id order by count(*) desc) resp, word w where w.id= resp.id and rownum<=?";
        }
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,Math.abs(range));
        stmt.setInt(2,year);
        stmt.setInt(3,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);

        FrequentWordsAroundWordR resp = new FrequentWordsAroundWordR();
        resp.setCategory("all");
        resp.setTime(year);
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, String category, int range, int amount) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="";
        if(range>0){
            sql = "select w.val, resp.frequency from (select w2.id, count(*) as frequency from word w1, word w2, sentence_word sw1,sentence_word sw2, sentence s, article a where w1.val='"+word+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and w1.id<>w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position<=sw1.position+? and sw2.position>sw1.position and s.id = sw1.sentence_id and a.id= s.article_id and a.category=? group by w2.id order by count(*) desc) resp, word w where w.id= resp.id and rownum<=?";
        }else{
            sql = "select w.val, resp.frequency from (select w2.id, count(*) as frequency from word w1, word w2, sentence_word sw1,sentence_word sw2, sentence s, article a where w1.val='"+word+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and w1.id<>w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position>=sw1.position-? and sw2.position<sw1.position and s.id = sw1.sentence_id and a.id= s.article_id and a.category=? group by w2.id order by count(*) desc) resp, word w where w.id= resp.id and rownum<=?";
        }
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,Math.abs(range));
        stmt.setString(2, category);
        stmt.setInt(3,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);

        FrequentWordsAroundWordR resp = new FrequentWordsAroundWordR();
        resp.setCategory(category);
        resp.setTime(0);
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }

    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, int range, int amount) throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql = "";
        if(range>0){
            sql = "select w.val, resp.frequency from (select w2.id, count(*) as frequency from word w1, word w2, sentence_word sw1,sentence_word sw2 where w1.val='"+word+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and w1.id<>w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position<=sw1.position+? and sw2.position>sw1.position group by w2.id order by count(*) desc) resp, word w where w.id= resp.id and rownum<=?";
        }else{
            sql = "select w.val, resp.frequency from (select w2.id, count(*) as frequency from word w1, word w2, sentence_word sw1,sentence_word sw2 where w1.val='"+word+"' and sw1.word_id=w1.id and sw2.word_id=w2.id and w1.id<>w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position>=sw1.position-? and sw2.position<sw1.position group by w2.id order by count(*) desc) resp, word w where w.id= resp.id and rownum<=?";
        }
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,Math.abs(range));
        stmt.setInt(2,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);

        FrequentWordsAroundWordR resp = new FrequentWordsAroundWordR();
        resp.setCategory("all");
        resp.setTime(0);
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }
    //////////////////////////Get word in position ////////////////

    public WordPositionR getFrequentWordsInPosition(int position,int year,String category,int amount)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w.val, resp.frequency  from (select sw.word_id, count(*) as frequency from sentence_word sw, sentence s, article a where sw.position = ? and s.id = sw.sentence_id and a.id = s.article_id and a.year =? and a.category=? group by sw.word_id order by count(*) desc) resp, word w where w.id= resp.word_id and rownum<=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,position);
        stmt.setInt(2,year);
        stmt.setString(3, category);
        stmt.setInt(4,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);
        WordPositionR resp = new WordPositionR();
        resp.setTime(year);
        resp.setCategory(category);
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }

    public WordPositionR getFrequentWordsInPosition(int position,int year,int amount)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w.val, resp.frequency  from (select sw.word_id, count(*) as frequency from sentence_word sw, sentence s, article a where sw.position = ? and s.id = sw.sentence_id and a.id = s.article_id and a.year =? group by sw.word_id order by count(*) desc) resp, word w where w.id= resp.word_id and rownum<=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,position);
        stmt.setInt(2, year);
        stmt.setInt(3,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);
        WordPositionR resp = new WordPositionR();
        resp.setTime(year);
        resp.setCategory("all");
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }


    public WordPositionR getFrequentWordsInPosition(int position,String category,int amount)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w.val, resp.frequency  from (select sw.word_id, count(*) as frequency from sentence_word sw, sentence s, article a where sw.position = ? and s.id = sw.sentence_id and a.id = s.article_id and a.category=? group by sw.word_id order by count(*) desc) resp, word w where w.id= resp.word_id and rownum<=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,position);
        stmt.setString(2, category);
        stmt.setInt(3,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);
        WordPositionR resp = new WordPositionR();
        resp.setTime(0);
        resp.setCategory(category);
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }

    public WordPositionR getFrequentWordsInPosition(int position,int amount)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w.val, resp.frequency  from (select sw.word_id, count(*) as frequency from sentence_word sw where sw.position = ? group by sw.word_id order by count(*) desc) resp, word w where w.id= resp.word_id and rownum<=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1, position);
        stmt.setInt(2,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);
        WordPositionR resp = new WordPositionR();
        resp.setTime(0);
        resp.setCategory("all");
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }


    ///////////////////////reverse /////////

    public WordPositionR getFrequentWordsInPositionReverse(int position,int year,String category,int amount)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w.val, resp.frequency  from (select sw.word_id, count(*) as frequency from sentence_word sw, sentence s, article a where sw.position = ((s.words+1) - ?) and s.id = sw.sentence_id and a.id = s.article_id and a.year =? and a.category=? group by sw.word_id order by count(*) desc) resp, word w where w.id= resp.word_id and rownum<=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,position);
        stmt.setInt(2,year);
        stmt.setString(3, category);
        stmt.setInt(4,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);
        WordPositionR resp = new WordPositionR();
        resp.setTime(year);
        resp.setCategory(category);
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }

    public WordPositionR getFrequentWordsInPositionReverse(int position,String category,int amount)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w.val, resp.frequency  from (select sw.word_id, count(*) as frequency from sentence_word sw, sentence s, article a where sw.position = ((s.words+1) - ?) and s.id = sw.sentence_id and a.id = s.article_id and a.category=? group by sw.word_id order by count(*) desc) resp, word w where w.id= resp.word_id and rownum<=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,position);
        stmt.setString(2, category);
        stmt.setInt(3,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);
        WordPositionR resp = new WordPositionR();
        resp.setTime(0);
        resp.setCategory(category);
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }

    public WordPositionR getFrequentWordsInPositionReverse(int position,int year,int amount)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w.val, resp.frequency  from (select sw.word_id, count(*) as frequency from sentence_word sw, sentence s, article a where sw.position = ((s.words+1) - ?) and s.id = sw.sentence_id and a.id = s.article_id and a.year =? group by sw.word_id order by count(*) desc) resp, word w where w.id= resp.word_id and rownum<=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,position);
        stmt.setInt(2, year);
        stmt.setInt(3,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);
        WordPositionR resp = new WordPositionR();
        resp.setTime(year);
        resp.setCategory("all");
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }

    public WordPositionR getFrequentWordsInPositionReverse(int position,int amount)throws SQLException,ClassNotFoundException{
        getDBConnection();
        String sql ="select w.val, resp.frequency  from (select sw.word_id, count(*) as frequency from sentence_word sw, sentence s where s.id = sw.sentence_id and sw.position = ((s.words+1) - ?) group by sw.word_id order by count(*) desc) resp, word w where w.id= resp.word_id and rownum<=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1, position);
        stmt.setInt(2,amount);
        ResultSet rst = stmt.executeQuery();

        List<WordR> words = new ArrayList<>();
        while(rst.next()){
            WordR w = new WordR();
            w.setValue(rst.getString(1));
            w.setFrequency(rst.getInt(2));
            words.add(w);
        }
        WordR[] wordArr = new WordR[words.size()];
        wordArr = words.toArray(wordArr);
        WordPositionR resp = new WordPositionR();
        resp.setTime(0);
        resp.setCategory("all");
        resp.setWords(wordArr);
        rst.close();
        stmt.close();
        return resp;
    }

    @Override
    public FrequentWordsAfterWordR getFrequentWordsAfterWordTimeRange(String word, String category, int year1, int year2, int amount) throws Exception {
        getDBConnection();
        String sql = "select w.val,resp3.year,resp3.frequency2 from (select resp2.id , a.year, count(*) as frequency2 from(select * from ( select b.id, count(*) as frequency from word w, bigram b, sentence_bigram sb , sentence s, article a where w.val='"+word+"' and b.word1 = w.id and sb.bigram_id=b.id and s.id = sb.sentence_id and a.id= s.article_id and a.category=? and a.year>=? and a.year<=? group by b.id order by count(*) desc ) resp where rownum<=?) resp2, sentence_bigram sb, sentence s, article a where sb.bigram_id=resp2.id and s.id = sb.sentence_id and a.id = s.article_id and a.year>=? and a.year<=? group by resp2.id , a.year order by resp2.id , a.year) resp3, bigram b, word w where b.id = resp3.id and w.id=b.word2";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1,category);
        stmt.setInt(2,year1);
        stmt.setInt(3,year2);
        stmt.setInt(4,amount);
        stmt.setInt(5,year1);
        stmt.setInt(6,year2);
        ResultSet rst = stmt.executeQuery();
        List<WordWithTimeR> words = new ArrayList<>();
        while(rst.next()){
            String value = rst.getString(1);
            int year = rst.getInt(2);
            int frequency =rst.getInt(3);
            WordWithTimeR w = new WordWithTimeR();
            w.setFrequency(frequency);
            w.setWord(value);
            w.setYear(year);
            words.add(w);
        }
        FrequentWordsAfterWordR resp = new FrequentWordsAfterWordR();
        resp.setCategory(category);
        WordWithTimeR[] wordArr = new WordWithTimeR[words.size()];
        resp.setWords(words.toArray(wordArr));
        rst.close();
        stmt.close();
        return resp;
    }

    @Override
    public FrequentWordsAfterWordR getFrequentWordsAfterWordTimeRange(String word, int year1, int year2, int amount) throws Exception {
        getDBConnection();
        String sql = "select w.val,resp3.year,resp3.frequency2 from (select resp2.id , a.year, count(*) as frequency2 from(select * from ( select b.id, count(*) as frequency from word w, bigram b, sentence_bigram sb , sentence s, article a where w.val='"+word+"' and b.word1 = w.id and sb.bigram_id=b.id and s.id = sb.sentence_id and a.id= s.article_id and a.year>=? and a.year<=? group by b.id order by count(*) desc ) resp where rownum<=?) resp2, sentence_bigram sb, sentence s, article a where sb.bigram_id=resp2.id and s.id = sb.sentence_id and a.id = s.article_id and a.year>=? and a.year<=? group by resp2.id , a.year order by resp2.id , a.year) resp3, bigram b, word w where b.id = resp3.id and w.id=b.word2";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year1);
        stmt.setInt(2,year2);
        stmt.setInt(3,amount);
        stmt.setInt(4,year1);
        stmt.setInt(5,year2);
        ResultSet rst = stmt.executeQuery();
        List<WordWithTimeR> words = new ArrayList<>();
        while(rst.next()){
            String value = rst.getString(1);
            int year = rst.getInt(2);
            int frequency =rst.getInt(3);
            WordWithTimeR w = new WordWithTimeR();
            w.setFrequency(frequency);
            w.setWord(value);
            w.setYear(year);
            words.add(w);
        }
        FrequentWordsAfterWordR resp = new FrequentWordsAfterWordR();
        resp.setCategory("all");
        WordWithTimeR[] wordArr = new WordWithTimeR[words.size()];
        resp.setWords(words.toArray(wordArr));
        rst.close();
        stmt.close();
        return resp;
    }

    @Override
    public FrequentWordsAfterWordR getFrequentWordsAfterBigramTimeRange(String word1, String word2, String category, int year1, int year2, int amount) throws Exception {
        getDBConnection();
        String sql = "select w.val,resp3.year,resp3.frequency2 from (select resp2.id , a.year, count(*) as frequency2 from(select * from ( select t.id, count(*) as frequency from word w1, word w2, trigram t, sentence_trigram st , sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and t.word1 = w1.id and t.word2 = w2.id and st.trigram_id=t.id and s.id = st.sentence_id and a.id= s.article_id and a.category=? and a.year>=? and a.year<=? group by t.id order by count(*) desc ) resp where rownum<=?) resp2, sentence_trigram st, sentence s, article a where st.trigram_id=resp2.id and s.id = st.sentence_id and a.id = s.article_id and a.year>=? and a.year<=? group by resp2.id , a.year order by resp2.id , a.year) resp3, trigram t, word w where t.id = resp3.id and w.id=t.word3";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1,category);
        stmt.setInt(2,year1);
        stmt.setInt(3,year2);
        stmt.setInt(4,amount);
        stmt.setInt(5,year1);
        stmt.setInt(6,year2);
        ResultSet rst = stmt.executeQuery();
        List<WordWithTimeR> words = new ArrayList<>();
        while(rst.next()){
            String value = rst.getString(1);
            int year = rst.getInt(2);
            int frequency =rst.getInt(3);
            WordWithTimeR w = new WordWithTimeR();
            w.setFrequency(frequency);
            w.setWord(value);
            w.setYear(year);
            words.add(w);
        }
        FrequentWordsAfterWordR resp = new FrequentWordsAfterWordR();
        resp.setCategory(category);
        WordWithTimeR[] wordArr = new WordWithTimeR[words.size()];
        resp.setWords(words.toArray(wordArr));
        rst.close();
        stmt.close();
        return resp;
    }

    @Override
    public FrequentWordsAfterWordR getFrequentWordsAfterBigramTimeRange(String word1, String word2, int year1, int year2, int amount) throws Exception {
        getDBConnection();
        String sql = "select w.val,resp3.year,resp3.frequency2 from (select resp2.id , a.year, count(*) as frequency2 from(select * from ( select t.id, count(*) as frequency from word w1, word w2, trigram t, sentence_trigram st , sentence s, article a where w1.val='"+word1+"' and w2.val='"+word2+"' and t.word1 = w1.id and t.word2 = w2.id and st.trigram_id=t.id and s.id = st.sentence_id and a.id= s.article_id and a.year>=? and a.year<=? group by t.id order by count(*) desc ) resp where rownum<=?) resp2, sentence_trigram st, sentence s, article a where st.trigram_id=resp2.id and s.id = st.sentence_id and a.id = s.article_id and a.year>=? and a.year<=? group by resp2.id , a.year order by resp2.id , a.year) resp3, trigram t, word w where t.id = resp3.id and w.id=t.word3";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year1);
        stmt.setInt(2,year2);
        stmt.setInt(3,amount);
        stmt.setInt(4,year1);
        stmt.setInt(5,year2);
        ResultSet rst = stmt.executeQuery();
        List<WordWithTimeR> words = new ArrayList<>();
        while(rst.next()){
            String value = rst.getString(1);
            int year = rst.getInt(2);
            int frequency =rst.getInt(3);
            WordWithTimeR w = new WordWithTimeR();
            w.setFrequency(frequency);
            w.setWord(value);
            w.setYear(year);
            words.add(w);
        }
        FrequentWordsAfterWordR resp = new FrequentWordsAfterWordR();
        resp.setCategory("all");
        WordWithTimeR[] wordArr = new WordWithTimeR[words.size()];
        resp.setWords(words.toArray(wordArr));
        rst.close();
        stmt.close();
        return resp;
    }

    @Override
    public WordCountR getWordCount(String category, int year) throws  Exception{
        getDBConnection();
        String sql = "select count(*) from sentence_word sw, sentence s, article a where s.id= sw.sentence_id and a.id= s.article_id and a.year=? and a.category=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setString(2,category);
        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory(category);
        resp.setYear(year);
        return resp;
    }

    @Override
    public WordCountR getWordCount(String category) throws Exception{
        getDBConnection();
        String sql = "select count(*) from sentence_word sw, sentence s, article a where s.id= sw.sentence_id and a.id= s.article_id and a.category=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1,category);
        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory(category);
        resp.setYear(0);
        return resp;
    }

    @Override
    public WordCountR getWordCount(int year) throws Exception{
        getDBConnection();
        String sql = "select count(*) from sentence_word sw, sentence s, article a where s.id= sw.sentence_id and a.id= s.article_id and a.year=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1, year);
        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory("all");
        resp.setYear(year);
        return resp;
    }

    @Override
    public WordCountR getWordCount() throws Exception {
        getDBConnection();
        String sql = "select count(*) from sentence_word sw";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);

        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory("all");
        resp.setYear(0);
        return resp;
    }


    /////////////////////////

    public WordCountR getBigramCount(String category, int year) throws  Exception{
        getDBConnection();
        String sql = "select count(*) from sentence_bigram sw, sentence s, article a where s.id= sw.sentence_id and a.id= s.article_id and a.year=? and a.category=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setString(2,category);
        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory(category);
        resp.setYear(year);
        return resp;
    }

    @Override
    public WordCountR getBigramCount(String category) throws Exception{
        getDBConnection();
        String sql = "select count(*) from sentence_bigram sw, sentence s, article a where s.id= sw.sentence_id and a.id= s.article_id and a.category=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1,category);
        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory(category);
        resp.setYear(0);
        return resp;
    }

    @Override
    public WordCountR getBigramCount(int year) throws Exception{
        getDBConnection();
        String sql = "select count(*) from sentence_bigram sw, sentence s, article a where s.id= sw.sentence_id and a.id= s.article_id and a.year=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1, year);
        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory("all");
        resp.setYear(year);
        return resp;
    }

    @Override
    public WordCountR getBigramCount() throws Exception {
        getDBConnection();
        String sql = "select count(*) from sentence_bigram sw";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);

        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory("all");
        resp.setYear(0);
        return resp;
    }

    /////////////////////////

    public WordCountR getTrigramCount(String category, int year) throws  Exception{
        getDBConnection();
        String sql = "select count(*) from sentence_trigram sw, sentence s, article a where s.id= sw.sentence_id and a.id= s.article_id and a.year=? and a.category=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1,year);
        stmt.setString(2,category);
        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory(category);
        resp.setYear(year);
        return resp;
    }

    @Override
    public WordCountR getTrigramCount(String category) throws Exception{
        getDBConnection();
        String sql = "select count(*) from sentence_trigram sw, sentence s, article a where s.id= sw.sentence_id and a.id= s.article_id and a.category=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setString(1,category);
        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory(category);
        resp.setYear(0);
        return resp;
    }

    @Override
    public WordCountR getTrigramCount(int year) throws Exception{
        getDBConnection();
        String sql = "select count(*) from sentence_trigram sw, sentence s, article a where s.id= sw.sentence_id and a.id= s.article_id and a.year=?";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);
        stmt.setInt(1, year);
        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory("all");
        resp.setYear(year);
        return resp;
    }

    @Override
    public WordCountR getTrigramCount() throws Exception {
        getDBConnection();
        String sql = "select count(*) from sentence_trigram sw";
        OracleCallableStatement stmt = (OracleCallableStatement) dbConnection.prepareCall(sql);

        ResultSet rst = stmt.executeQuery();
        WordCountR resp = new WordCountR();
        while (rst.next()){
            resp.setCount(rst.getInt(1));
        }
        resp.setCategory("all");
        resp.setYear(0);
        return resp;
    }

    /////////////////////////
//select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id,sw.position, w.val,a.year,a.month,a.day from word w1, word w2, sentence_word sw1, sentence_word sw2, sentence s, article a where w1.val='මහින්ද' and w2.val='රාජපක්ෂ'  and sw1.word_id=w1.id and sw2.word_id=w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position=sw1.position+1 and s.id=sw1.sentence_id and a.id = s.article_id order by a.year desc,a.month desc,a.day desc) where rownum<=10) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position
//select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id,sw.position, w1.val,w2.val,a.year,a.month,a.day from word w1, word w2, sentence_word sw1, sentence_word sw2, sentence s, article a where w1.val='මහින්ද' and w2.val='රාජපක්ෂ'  and sw1.word_id=w1.id and sw2.word_id=w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position=sw1.position+1 and s.id=sw1.sentence_id and a.id = s.article_id order by a.year desc,a.month desc,a.day desc) where rownum<=10) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position
//select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id ,a.year,a.month,a.day from word w1, word w2, sentence_word sw1, sentence_word sw2, sentence s, article a where w1.val='මහින්ද' and w2.val='රාජපක්ෂ'  and sw1.word_id=w1.id and sw2.word_id=w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position=sw1.position+1 and s.id=sw1.sentence_id and a.id = s.article_id order by a.year desc,a.month desc,a.day desc) where rownum<=10) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position
//select res.article_id, sw.sentence_id, sw. word_id, sw.position,w.val, res.category, res.topic, res.author, res.year,res.month,res.day from (select * from (select a.id as article_id,a.topic, a.author, a.category, s.id as sentence_id ,a.year,a.month,a.day from word w1, word w2,word w3, sentence_word sw1, sentence_word sw2,sentence_word sw3, sentence s, article a where w1.val='මහින්ද' and w2.val='රාජපක්ෂ' and w3.val='මහතා' and sw1.word_id=w1.id and sw2.word_id=w2.id and sw3.word_id=w3.id and sw1.sentence_id=sw2.sentence_id and sw1.sentence_id=sw3.sentence_id and sw2.position=sw1.position+1 and sw3.position=sw1.position+2  and s.id=sw1.sentence_id and a.id = s.article_id order by a.year desc,a.month desc,a.day desc) where rownum<=10) res, sentence_word sw, word w where sw.sentence_id=res.sentence_id and w.id=sw.word_id order by res.year desc,res.month desc, res.day desc, sw.sentence_id, sw.position
//select w2.id, count(*) from word w1, word w2, sentence_word sw1,sentence_word sw2, sentence s, article a where w1.val='මහින්ද' and sw1.word_id=w1.id and sw2.word_id=w2.id and sw1.sentence_id=sw2.sentence_id and sw2.position<sw1.position+3 and s.id = sw1.sentence_id and a.id= s.article_id and a.year=2014 group by w2.id order by count(*) desc
//select w.val, resp.frequency from (select w2.id, count(*) as frequency from word w1, word w2, sentence_word sw1,sentence_word sw2, sentence s, article a where w1.val='මහින්ද' and sw1.word_id=w1.id and sw2.word_id=w2.id and w1.id<>w2.id and sw1.sentence_id=sw2.sentence_id and (sw2.position<sw1.position+3 or sw2.position>sw1.position-3 )and s.id = sw1.sentence_id and a.id= s.article_id and a.year=2014 group by w2.id order by count(*) desc) resp, word w where w.id= resp.id and rownum<=10
//select w.val,resp3.year,resp3.frequency2 from (select resp2.id , a.year, count(*) as frequency2 from(select * from ( select t.id, count(*) as frequency from word w1, word w2, trigram t, sentence_trigram st , sentence s, article a where w1.val='ලංකා' and w2.val='රජය' and t.word1 = w1.id and t.word2 = w2.id and st.trigram_id=t.id and s.id = st.sentence_id and a.id= s.article_id and a.year>=2011 and a.year<=2014 group by t.id order by count(*) desc ) resp where rownum<=10) resp2, sentence_trigram st, sentence s, article a where st.trigram_id=resp2.id and s.id = st.sentence_id and a.id = s.article_id and a.year>=2011 and a.year<=2014 group by resp2.id , a.year order by resp2.id , a.year) resp3, trigram t, word w where t.id = resp3.id and w.id=t.word3;
}
