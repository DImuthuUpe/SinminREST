package com.sinmin.rest.oracle;

import com.sinmin.rest.beans.response.FrequentWordR;
import com.sinmin.rest.beans.response.WordFrequencyR;
import com.sinmin.rest.beans.response.WordR;
import oracle.jdbc.OracleCallableStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
            int frequency = rst.getInt(3);
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
            int frequency = rst.getInt(3);
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
            int frequency = rst.getInt(3);
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
            int frequency = rst.getInt(3);
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


}
