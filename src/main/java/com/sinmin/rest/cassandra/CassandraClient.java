/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package com.sinmin.rest.cassandra;

import java.util.Calendar;
import java.util.Date;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.sinmin.rest.ConfigManager;
import com.sinmin.rest.CorpusDBClient;
import com.sinmin.rest.beans.response.*;

public class CassandraClient implements CorpusDBClient {

    private static Cluster cluster;
    private static Session session;


    public void connect(String node) {
        String userName = ConfigManager.getProperty(ConfigManager.CASSANDRA_DB_USER);
        String password = ConfigManager.getProperty(ConfigManager.CASSANDRA_DB_PASSWORD);
        cluster = Cluster.builder().addContactPoint(node).withCredentials(userName, password).build();
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack());
        }
        session = cluster.connect();
    }

    private synchronized void init(){
        if(session==null){
            connect(ConfigManager.getProperty(ConfigManager.CASSANDRA_SERVER_IP));
        }
    }

    public CassandraClient(){
        init();
    }


    public WordFrequencyR getWordFrequency(String word, int year, String category) {
        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select frequency from corpus.word_time_category_frequency WHERE word=? AND year=? AND category IN (?,?)");
            results = session.execute(query.bind(word, year, "C", "B"));
        } else {
            query = session.prepare(
                    "select frequency from corpus.word_time_category_frequency WHERE word=? AND year=? AND category=?");
            results = session.execute(query.bind(word, year, category.charAt(0) + ""));
        }

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory(category);
        endObject.setDate(year);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;
    }

    public WordFrequencyR getWordFrequency(String word, int year) {
        PreparedStatement query = session.prepare(
                "select frequency from corpus.word_time_frequency WHERE word=? AND year=?");
        ResultSet results = session.execute(query.bind(word, year));

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory("all");
        endObject.setDate(year);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;

    }

    public WordFrequencyR getWordFrequency(String word, String category) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select frequency from corpus.word_category_frequency WHERE word=? AND category IN (?,?)");
            results = session.execute(query.bind(word, "C", "B"));
        } else {
            query = session.prepare(
                    "select frequency from corpus.word_category_frequency WHERE word=?  AND category=?");
            results = session.execute(query.bind(word, category.charAt(0) + ""));
        }

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory(category);
        endObject.setDate(0);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;
    }

    public WordFrequencyR getWordFrequency(String word) {
        PreparedStatement query = session.prepare(
                "select frequency from corpus.word_frequency WHERE word=?");
        ResultSet results = session.execute(query.bind(word));

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory("all");
        endObject.setDate(0);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;
    }

    public WordFrequencyR getBigramFrequency(String word1, String word2, int year, String category) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select frequency from corpus.bigram_time_category_frequency WHERE word1=? AND word2=? AND year=? AND category IN (?,?)");
            results = session.execute(query.bind(word1, word2, year, "C", "B"));
        } else {
            query = session.prepare(
                    "select frequency from corpus.bigram_time_category_frequency WHERE word1=? AND word2=? AND year=? AND category=?");
            results = session.execute(query.bind(word1, word2, year, category.charAt(0) + ""));
        }

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory(category);
        endObject.setDate(year);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;
    }

    public WordFrequencyR getBigramFrequency(String word1, String word2, int year) {
        System.out.println("getBigramFrequency(String word1, String word2,int year)");
        PreparedStatement query = session.prepare(
                "select frequency from corpus.bigram_time_frequency WHERE word1=? AND word2=? AND year=?");
        ResultSet results = session.execute(query.bind(word1, word2, year));

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory("all");
        endObject.setDate(year);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;
    }

    public WordFrequencyR getBigramFrequency(String word1, String word2, String category) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select frequency from corpus.bigram_category_frequency WHERE word1=? AND word2=? AND year=? AND category IN (?,?)");
            results = session.execute(query.bind(word1, word2, "C", "B"));
        } else {
            query = session.prepare(
                    "select frequency from corpus.bigram_category_frequency WHERE word1=? AND word2=? AND year=? AND category=?");
            results = session.execute(query.bind(word1, word2, category.charAt(0) + ""));
        }

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory(category);
        endObject.setDate(0);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;
    }

    public WordFrequencyR getBigramFrequency(String word1, String word2) {
        System.out.println("getBigramFrequency(String word1, String word2)");
        PreparedStatement query = session.prepare(
                "select frequency from corpus.bigram_category_frequency WHERE word1=? AND word2=?");
        ResultSet results = session.execute(query.bind(word1, word2));

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory("all");
        endObject.setDate(0);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;
    }

    //////////////////////////////////////////////////////////////////////////////////

    public WordFrequencyR getTrigramFrequency(String word1, String word2, String word3, int year, String category) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select frequency from corpus.trigram_time_category_frequency WHERE word1=? AND word2=? AND word3=? AND year=? IN (?,?)");
            results = session.execute(query.bind(word1, word2, word3, year, "C", "B"));
        } else {
            query = session.prepare(
                    "select frequency from corpus.trigram_time_category_frequency WHERE word1=? AND word2=? AND word3=? AND year=? AND category=?");
            results = session.execute(query.bind(word1, word2, word3, year, category.charAt(0) + ""));
        }

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory(category);
        endObject.setDate(year);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;
    }

    public WordFrequencyR getTrigramFrequency(String word1, String word2, String word3, int year) {
        System.out.println("getTrigramFrequency(String word1, String word2,String word3,int year)");
        PreparedStatement query = session.prepare(
                "select frequency from corpus.trigram_time_frequency WHERE word1=? AND word2=? AND word3=? AND year=?");
        ResultSet results = session.execute(query.bind(word1, word2, word3, year));

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory("all");
        endObject.setDate(year);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;
    }

    public WordFrequencyR getTrigramFrequency(String word1, String word2, String word3, String category) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select frequency from corpus.trigram_category_frequency WHERE word1=? AND word2=? AND word3=? AND year=? AND category IN (?,?)");
            results = session.execute(query.bind(word1, word2, word3, "C", "B"));
        } else {
            query = session.prepare(
                    "select frequency from corpus.trigram_category_frequency WHERE word1=? AND word2=? AND word3=? AND year=? AND category=?");
            results = session.execute(query.bind(word1, word2, word3, category.charAt(0) + ""));
        }

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory(category);
        endObject.setDate(0);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;
    }

    public WordFrequencyR getTrigramFrequency(String word1, String word2, String word3) {
        System.out.println("getTrigramFrequency(String word1, String word2,String word3)");
        PreparedStatement query = session.prepare(
                "select frequency from corpus.trigram_category_frequency WHERE word1=? AND word2=? AND word3=?");
        ResultSet results = session.execute(query.bind(word1, word2, word3));

        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory("all");
        endObject.setDate(0);
        endObject.setFrequency(0);
        for (Row row : results) {
            System.out.format("%d\n", row.getInt("frequency"));
            endObject.setFrequency(row.getInt("frequency"));
        }

        return endObject;
    }

    //////////////////////////////////////////////////////

    public FrequentWordR getFrequentWords(int year, String category, int amount) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.word_time_category_ordered_frequency WHERE year=? and category IN (?,?) order by frequency DESC LIMIT ?");
            results = session.execute(query.bind(year, "C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.word_time_category_ordered_frequency WHERE year=? and category=? order by frequency DESC LIMIT ?");
            results = session.execute(query.bind(year, category.charAt(0) + "", amount));
        }

        WordR[] val1 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val1[i].setValue(row.getString("word"));
            val1[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }

        resp.setCategory(category);
        resp.setTime(year);
        resp.setValue1(val1);

        return resp;
    }

    public FrequentWordR getFrequentWords(int year, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.word_time_ordered_frequency WHERE year=? order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(year, amount));

        WordR[] val1 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val1[i].setValue(row.getString("word"));
            val1[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }

        resp.setCategory("all");
        resp.setTime(year);
        resp.setValue1(val1);

        return resp;
    }

    public FrequentWordR getFrequentWords(String category, int amount) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.word_category_ordered_frequency WHERE category IN (?,?) order by frequency DESC LIMIT ?");
            results = session.execute(query.bind("C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.word_category_ordered_frequency WHERE category=? order by frequency DESC LIMIT ?");
            results = session.execute(query.bind(category.charAt(0) + "", amount));
        }

        WordR[] val1 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val1[i].setValue(row.getString("word"));
            val1[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }

        resp.setCategory(category);
        resp.setTime(0);
        resp.setValue1(val1);

        return resp;
    }

    public FrequentWordR getFrequentWords(int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.word_category_ordered_frequency WHERE category IN (?,?,?,?,?) order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind("N", "C", "A", "S", "G", amount));

        WordR[] val1 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val1[i].setValue(row.getString("word"));
            val1[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }

        resp.setCategory("all");
        resp.setTime(0);
        resp.setValue1(val1);

        return resp;
    }

    //////////////////////////////////////////////////////////////////////////////

    public FrequentWordR getFrequentBigrams(int year, String category, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.bigram_time_category_ordered_frequency WHERE year=? and category=? order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(year, category.charAt(0) + "", amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        resp.setCategory(category);
        resp.setTime(year);
        resp.setValue1(val1);
        resp.setValue2(val2);
        return resp;
    }

    public FrequentWordR getFrequentBigrams(int year, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.bigram_time_ordered_frequency WHERE year=? order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(year, amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        resp.setCategory("all");
        resp.setTime(year);
        resp.setValue1(val1);
        resp.setValue2(val2);
        return resp;
    }

    public FrequentWordR getFrequentBigrams(String category, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.bigram_category_ordered_frequency WHERE category=? order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(category.charAt(0) + "", amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        resp.setCategory(category);
        resp.setTime(0);
        resp.setValue1(val1);
        resp.setValue2(val2);
        return resp;
    }

    public FrequentWordR getFrequentBigrams(int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.bigram_category_ordered_frequency WHERE category IN (?,?,?,?,?) order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind("N", "C", "A", "S", "G", amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        resp.setCategory("all");
        resp.setTime(0);
        resp.setValue1(val1);
        resp.setValue2(val2);
        return resp;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public FrequentWordR getFrequentTrigrams(int year, String category, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.trigram_time_category_ordered_frequency WHERE year=? and category=? order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(year, category.charAt(0) + "", amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val3[i].setValue(row.getString("word3"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            val3[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        resp.setCategory(category);
        resp.setTime(year);
        resp.setValue1(val1);
        resp.setValue2(val2);
        resp.setValue3(val3);
        return resp;
    }

    public FrequentWordR getFrequentTrigrams(int year, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.trigram_time_ordered_frequency WHERE year=? order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(year, amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val3[i].setValue(row.getString("word3"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            val3[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        resp.setCategory("all");
        resp.setTime(year);
        resp.setValue1(val1);
        resp.setValue2(val2);
        resp.setValue3(val3);
        return resp;
    }

    public FrequentWordR getFrequentTrigrams(String category, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.trigram_category_ordered_frequency WHERE category=? order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(category.charAt(0) + "", amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val3[i].setValue(row.getString("word3"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            val3[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        resp.setCategory(category);
        resp.setTime(0);
        resp.setValue1(val1);
        resp.setValue2(val2);
        resp.setValue3(val3);
        return resp;
    }

    public FrequentWordR getFrequentTrigrams(int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.trigram_category_ordered_frequency WHERE category IN (?,?,?,?,?) order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind("N", "C", "A", "S", "G", amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i = 0;

        for (Row row : results) {
            val1[i] = new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val3[i].setValue(row.getString("word3"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            val3[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"), row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        resp.setCategory("all");
        resp.setTime(0);
        resp.setValue1(val1);
        resp.setValue2(val2);
        resp.setValue3(val3);
        return resp;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public ArticlesForWordR getLatestArticlesForWord(String word, int year, String category, int amount) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.word_year_category_usage WHERE word=? AND year=? AND category IN (?,?) order by date DESC LIMIT ?");
            results = session.execute(query.bind(word, year, "C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.word_year_category_usage WHERE word=? AND year=? AND category =? order by date DESC LIMIT ?");
            results = session.execute(query.bind(word, year, category.charAt(0) + "", amount));
        }


        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory(category);
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory(category);
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForWord(String word, int year, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.word_year_usage WHERE word=? AND year=? order by date DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(word, year, amount));


        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory("all");
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory("all");
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForWord(String word, String category, int amount) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.word_year_category_usage WHERE word=? AND category IN (?,?) order by date DESC LIMIT ?");
            results = session.execute(query.bind(word, "C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.word_year_category_usage WHERE word=? AND category =? order by date DESC LIMIT ?");
            results = session.execute(query.bind(word, category.charAt(0) + "", amount));
        }

        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory(category);
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory(category);
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForWord(String word, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.word_usage WHERE word=? order by date DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(word, amount));

        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory("all");
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory("all");
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    public ArticlesForWordR getLatestArticlesForBigram(String word1, String word2, int year, String category, int amount) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.bigram_year_category_usage WHERE word1=? AND word2=? AND year=? AND category IN (?,?) order by date DESC LIMIT ?");
            results = session.execute(query.bind(word1, word2, year, "C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.bigram_year_category_usage WHERE word1=? AND word2=? AND year=? AND category =? order by date DESC LIMIT ?");
            results = session.execute(query.bind(word1, word2, year, category.charAt(0) + "", amount));
        }

        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory(category);
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory(category);
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForBigram(String word1, String word2, int year, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.bigram_year_usage WHERE word1=? AND word2=? AND year=? order by date DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(word1, word2, year, amount));

        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory("all");
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory("all");
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForBigram(String word1, String word2, String category, int amount) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.bigram_category_usage WHERE word1=? AND word2=? AND year=? AND category IN (?,?) order by date DESC LIMIT ?");
            results = session.execute(query.bind(word1, word2, "C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.bigram_category_usage WHERE word1=? AND word2=? AND year=? AND category =? order by date DESC LIMIT ?");
            results = session.execute(query.bind(word1, word2, category.charAt(0) + "", amount));
        }

        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory(category);
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory(category);
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForBigram(String word1, String word2, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.bigram_usage WHERE word1=? AND word2=? order by date DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(word1, word2, amount));

        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory("all");
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory("all");
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    /////////////////////////////////////////////////////////////////////////////////

    public ArticlesForWordR getLatestArticlesForTrigram(String word1, String word2, String word3, int year, String category, int amount) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.trigram_year_category_usage WHERE word1=? AND word2=? AND word3=? AND year=? AND category IN (?,?) order by date DESC LIMIT ?");
            results = session.execute(query.bind(word1, word2, word3, year, "C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.trigram_year_category_usage WHERE word1=? AND word2=? AND word3=? AND year=? AND category =? order by date DESC LIMIT ?");
            results = session.execute(query.bind(word1, word2, word3, year, category.charAt(0) + "", amount));
        }

        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory(category);
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory(category);
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForTrigram(String word1, String word2, String word3, int year, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.trigram_year_usage WHERE word1=? AND word2=? AND word3=? AND year=? order by date DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(word1, word2, word3, year, amount));

        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory("all");
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(year);
        articlesForWord.setCategory("all");
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForTrigram(String word1, String word2, String word3, String category, int amount) {
        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.trigram_year_category_usage WHERE word1=? AND word2=? AND word3=? AND category IN (?,?) order by date DESC LIMIT ?");
            results = session.execute(query.bind(word1, word2, word3, "C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.trigram_year_category_usage WHERE word1=? AND word2=? AND word3=?  AND category =? order by date DESC LIMIT ?");
            results = session.execute(query.bind(word1, word2, word3, category.charAt(0) + "", amount));
        }

        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory(category);
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory(category);
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    public ArticlesForWordR getLatestArticlesForTrigram(String word1, String word2, String word3, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.trigram_usage WHERE word1=? AND word2=? AND word3=? order by date DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(word1, word2, word3, amount));

        ArticleR[] array = new ArticleR[amount];
        int i = 0;
        Date datetime;
        for (Row row : results) {
            array[i] = new ArticleR();
            array[i].setTitle(row.getString("postname"));
            array[i].setAuthor(row.getString("author"));
            array[i].setLink(row.getString("url"));
            datetime = row.getDate("date");

            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            array[i].setSentence(row.getString("sentence"));
            array[i].setYear(cal.get(Calendar.YEAR));
            array[i].setMonth(cal.get(Calendar.MONTH) + 1);
            array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
            array[i].setCategory("all");
            i++;
            if (i == amount) {
                break;
            }
        }
        ArticleR[] arrayToSend = new ArticleR[i];
        for (int j = 0; j < i; j++) {
            arrayToSend[j] = array[j];
        }
        ArticlesForWordR articlesForWord = new ArticlesForWordR();
        articlesForWord.setTime(0);
        articlesForWord.setCategory("all");
        articlesForWord.setArticles(arrayToSend);
        return articlesForWord;
    }

    //////////////////////////////////////////////////////////////////

    public WordPositionR getFrequentWordsInPosition(int position, int year, String category, int amount) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.word_pos_year_category_frequency WHERE position=? AND year=? AND category IN (?,?) order by date DESC LIMIT ?");
            results = session.execute(query.bind(position, year, "C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.word_pos_year_category_frequency WHERE position=? AND year=? AND category=? order by frequency DESC LIMIT ?");
            results = session.execute(query.bind(position, year, category.charAt(0) + "", amount));
        }

        WordPositionR out = new WordPositionR();
        out.setCategory(category);
        out.setTime(year);
        int i = 0;
        WordR[] words = new WordR[amount];
        for (Row row : results) {
            words[i] = new WordR();
            words[i].setValue(row.getString("word"));
            words[i].setFrequency(row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        WordR[] wordsToSend = new WordR[i];
        for (int j = 0; j < i; j++) {
            wordsToSend[j] = words[j];
        }
        out.setWords(wordsToSend);
        return out;
    }

    public WordPositionR getFrequentWordsInPositionReverse(int position, int year, String category, int amount) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.word_inv_pos_year_category_frequency WHERE position=? AND year=? AND category IN (?,?) order by date DESC LIMIT ?");
            results = session.execute(query.bind(position, year, "C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.word_inv_pos_year_category_frequency WHERE position=? AND year=? AND category=? order by frequency DESC LIMIT ?");
            results = session.execute(query.bind(position, year, category.charAt(0) + "", amount));
        }

        WordPositionR out = new WordPositionR();
        out.setCategory(category);
        out.setTime(year);
        int i = 0;
        WordR[] words = new WordR[amount];
        for (Row row : results) {
            words[i] = new WordR();
            words[i].setValue(row.getString("word"));
            words[i].setFrequency(row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        WordR[] wordsToSend = new WordR[i];
        for (int j = 0; j < i; j++) {
            wordsToSend[j] = words[j];
        }
        out.setWords(wordsToSend);
        return out;
    }

    //----------------

    public WordPositionR getFrequentWordsInPosition(int position, int year, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.word_pos_year_frequency WHERE position=? AND year=?  order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(position, year, amount));

        WordPositionR out = new WordPositionR();
        out.setCategory("all");
        out.setTime(year);
        int i = 0;
        WordR[] words = new WordR[amount];
        for (Row row : results) {
            words[i] = new WordR();
            words[i].setValue(row.getString("word"));
            words[i].setFrequency(row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        WordR[] wordsToSend = new WordR[i];
        for (int j = 0; j < i; j++) {
            wordsToSend[j] = words[j];
        }
        out.setWords(wordsToSend);
        return out;
    }

    public WordPositionR getFrequentWordsInPositionReverse(int position, int year, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.word_inv_pos_year_frequency WHERE inv_position=? AND year=? order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(position, year, amount));

        WordPositionR out = new WordPositionR();
        out.setCategory("all");
        out.setTime(year);
        int i = 0;
        WordR[] words = new WordR[amount];
        for (Row row : results) {
            words[i] = new WordR();
            words[i].setValue(row.getString("word"));
            words[i].setFrequency(row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        WordR[] wordsToSend = new WordR[i];
        for (int j = 0; j < i; j++) {
            wordsToSend[j] = words[j];
        }
        out.setWords(wordsToSend);
        return out;
    }

    public WordPositionR getFrequentWordsInPosition(int position, String category, int amount) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.word_pos_category_frequency WHERE position=? AND category IN (?,?) order by date DESC LIMIT ?");
            results = session.execute(query.bind(position, "C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.word_pos_category_frequency WHERE position=? AND category=? order by frequency DESC LIMIT ?");
            results = session.execute(query.bind(position, category.charAt(0) + "", amount));
        }

        WordPositionR out = new WordPositionR();
        out.setCategory(category);
        out.setTime(0);
        int i = 0;
        WordR[] words = new WordR[amount];
        for (Row row : results) {
            words[i] = new WordR();
            words[i].setValue(row.getString("word"));
            words[i].setFrequency(row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        WordR[] wordsToSend = new WordR[i];
        for (int j = 0; j < i; j++) {
            wordsToSend[j] = words[j];
        }
        out.setWords(wordsToSend);
        return out;
    }

    public WordPositionR getFrequentWordsInPositionReverse(int position, String category, int amount) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.word_inv_pos_category_frequency WHERE position=? AND category IN (?,?) order by date DESC LIMIT ?");
            results = session.execute(query.bind(position, "C", "B", amount));
        } else {
            query = session.prepare(
                    "select * from corpus.word_inv_pos_category_frequency WHERE position=? AND category=? order by frequency DESC LIMIT ?");
            results = session.execute(query.bind(position, category.charAt(0) + "", amount));
        }

        WordPositionR out = new WordPositionR();
        out.setCategory(category);
        out.setTime(0);
        int i = 0;
        WordR[] words = new WordR[amount];
        for (Row row : results) {
            words[i] = new WordR();
            words[i].setValue(row.getString("word"));
            words[i].setFrequency(row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        WordR[] wordsToSend = new WordR[i];
        for (int j = 0; j < i; j++) {
            wordsToSend[j] = words[j];
        }
        out.setWords(wordsToSend);
        return out;
    }

    //----------------

    public WordPositionR getFrequentWordsInPosition(int position, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.word_pos_frequency WHERE position=? order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(position, amount));

        WordPositionR out = new WordPositionR();
        out.setCategory("all");
        out.setTime(0);
        int i = 0;
        WordR[] words = new WordR[amount];
        for (Row row : results) {
            words[i] = new WordR();
            words[i].setValue(row.getString("word"));
            words[i].setFrequency(row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        WordR[] wordsToSend = new WordR[i];
        for (int j = 0; j < i; j++) {
            wordsToSend[j] = words[j];
        }
        out.setWords(wordsToSend);
        return out;
    }

    public WordPositionR getFrequentWordsInPositionReverse(int position, int amount) {
        PreparedStatement query = session.prepare(
                "select * from corpus.word_inv_pos_frequency WHERE inv_position=? order by frequency DESC LIMIT ?");
        ResultSet results = session.execute(query.bind(position, amount));

        WordPositionR out = new WordPositionR();
        out.setCategory("all");
        out.setTime(0);
        int i = 0;
        WordR[] words = new WordR[amount];
        for (Row row : results) {
            words[i] = new WordR();
            words[i].setValue(row.getString("word"));
            words[i].setFrequency(row.getInt("frequency"));
            i++;
            if (i == amount) {
                break;
            }
        }
        WordR[] wordsToSend = new WordR[i];
        for (int j = 0; j < i; j++) {
            wordsToSend[j] = words[j];
        }
        out.setWords(wordsToSend);
        return out;
    }

    @Override
    public FrequentWordsAfterWordR getFrequentWordsAfterWordTimeRange(String word, String category, int year1, int year2, int amount) throws Exception {
        return null;
    }

    @Override
    public FrequentWordsAfterWordR getFrequentWordsAfterWordTimeRange(String word, int year1, int year2, int amount) throws Exception {
        return null;
    }

    @Override
    public FrequentWordsAfterWordR getFrequentWordsAfterBigramTimeRange(String word1, String word2, String category, int year1, int year2, int amount) throws Exception {
        return null;
    }

    @Override
    public FrequentWordsAfterWordR getFrequentWordsAfterBigramTimeRange(String word1, String word2, int year1, int year2, int amount) throws Exception {
        return null;
    }

    @Override
    public WordCountR getWordCount(String category, int year) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.word_sizes WHERE year=? AND  category IN (?,?)");
            results = session.execute(query.bind(year + "", "C", "B"));
        } else {
            query = session.prepare(
                    "select * from corpus.word_sizes WHERE year=? AND category=?");
            results = session.execute(query.bind(year + "", category.charAt(0) + ""));
        }

        WordCountR obj = new WordCountR();
        obj.setCategory(category);
        obj.setYear(year);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordCountR getWordCount(String category) {

        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.word_sizes WHERE year=? AND  category IN (?,?)");
            results = session.execute(query.bind("ALL", "C", "B"));
        } else {
            query = session.prepare(
                    "select * from corpus.word_sizes WHERE year=? AND category=?");
            results = session.execute(query.bind("ALL", category.charAt(0) + ""));
        }

        WordCountR obj = new WordCountR();
        obj.setCategory(category);
        obj.setYear(0);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordCountR getWordCount(int year) {
        PreparedStatement query = session.prepare(
                "select * from corpus.word_sizes WHERE year=? AND category=?");
        ResultSet results = session.execute(query.bind(year + "", "ALL"));
        WordCountR obj = new WordCountR();
        obj.setCategory("all");
        obj.setYear(year);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordCountR getWordCount() {
        PreparedStatement query = session.prepare(
                "select * from corpus.word_sizes WHERE year=? AND category=?");
        ResultSet results = session.execute(query.bind("ALL", "ALL"));
        WordCountR obj = new WordCountR();
        obj.setCategory("all");
        obj.setYear(0);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordCountR getBigramCount(String category, int year) {
        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.bigram_sizes WHERE year=? AND  category IN (?,?)");
            results = session.execute(query.bind(year + "", "C", "B"));
        } else {
            query = session.prepare(
                    "select * from corpus.bigram_sizes WHERE year=? AND category=?");
            results = session.execute(query.bind(year + "", category.charAt(0) + ""));
        }

        WordCountR obj = new WordCountR();
        obj.setCategory(category);
        obj.setYear(year);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordCountR getBigramCount(String category) {
        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.bigram_sizes WHERE year=? AND  category IN (?,?)");
            results = session.execute(query.bind("ALL", "C", "B"));
        } else {
            query = session.prepare(
                    "select * from corpus.bigram_sizes WHERE year=? AND category=?");
            results = session.execute(query.bind("ALL", category.charAt(0) + ""));
        }

        WordCountR obj = new WordCountR();
        obj.setCategory(category);
        obj.setYear(0);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordCountR getBigramCount(int year) {
        PreparedStatement query = session.prepare(
                "select * from corpus.bigram_sizes WHERE year=? AND category=?");
        ResultSet results = session.execute(query.bind(year + "", "ALL"));
        WordCountR obj = new WordCountR();
        obj.setCategory("all");
        obj.setYear(year);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordCountR getBigramCount() {
        PreparedStatement query = session.prepare(
                "select * from corpus.bigram_sizes WHERE year=? AND category=?");
        ResultSet results = session.execute(query.bind("ALL", "ALL"));
        WordCountR obj = new WordCountR();
        obj.setCategory("all");
        obj.setYear(0);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordCountR getTrigramCount(String category, int year) {
        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.trigram_sizes WHERE year=? AND  category IN (?,?)");
            results = session.execute(query.bind(year + "", "C", "B"));
        } else {
            query = session.prepare(
                    "select * from corpus.trigram_sizes WHERE year=? AND category=?");
            results = session.execute(query.bind(year + "", category.charAt(0) + ""));
        }

        WordCountR obj = new WordCountR();
        obj.setCategory(category);
        obj.setYear(year);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordCountR getTrigramCount(String category) {
        PreparedStatement query;
        ResultSet results;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select * from corpus.trigram_sizes WHERE year=? AND  category IN (?,?)");
            results = session.execute(query.bind("ALL", "C", "B"));
        } else {
            query = session.prepare(
                    "select * from corpus.trigram_sizes WHERE year=? AND category=?");
            results = session.execute(query.bind("ALL", category.charAt(0) + ""));
        }

        WordCountR obj = new WordCountR();
        obj.setCategory(category);
        obj.setYear(0);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordCountR getTrigramCount(int year) {
        PreparedStatement query = session.prepare(
                "select * from corpus.trigram_sizes WHERE year=? AND category=?");
        ResultSet results = session.execute(query.bind(year + "", "ALL"));
        WordCountR obj = new WordCountR();
        obj.setCategory("all");
        obj.setYear(year);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordCountR getTrigramCount() {
        PreparedStatement query = session.prepare(
                "select * from corpus.trigram_sizes WHERE year=? AND category=?");
        ResultSet results = session.execute(query.bind("ALL", "ALL"));
        WordCountR obj = new WordCountR();
        obj.setCategory("all");
        obj.setYear(0);
        obj.setCount(0);
        for (Row row : results) {
            System.out.println(row.getInt("size"));
            obj.setCount(row.getInt("size"));
            break;
        }

        return obj;
    }

    @Override
    public WordFrequencyR getWordCountInPosition(String category, int year, int position, String word) {
        PreparedStatement query;
        ResultSet results;
        int frequency = 0;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select frequency from corpus.word_pos_year_category_id WHERE position=? AND word=? AND year=? AND category =?");
            results = session.execute(query.bind(position, word, year, "C"));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
            query = session.prepare(
                    "select frequency from corpus.word_pos_year_category_id WHERE position=? AND word=? AND year=? AND category =?");
            results = session.execute(query.bind(position, word, year, "B"));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
        } else {
            query = session.prepare(
                    "select frequency from corpus.word_pos_year_category_id WHERE position=? AND word=? AND year=? AND category=?");
            results = session.execute(query.bind(position, word, year, category.charAt(0) + ""));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
        }
        System.out.println(frequency);
        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory(category);
        endObject.setDate(year);
        endObject.setFrequency(frequency);

        return endObject;
    }

    @Override
    public WordFrequencyR getWordCountInPosition(int year, int position, String word) {
        PreparedStatement query;
        ResultSet results;
        int frequency = 0;

        query = session.prepare(
                "select frequency from corpus.word_pos_year_id WHERE position=? AND word=? AND year=?");
        results = session.execute(query.bind(position, word, year));
        for (Row row : results) {
            frequency += row.getInt("frequency");
            break;
        }

        System.out.println(frequency);
        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory("all");
        endObject.setDate(year);
        endObject.setFrequency(frequency);

        return endObject;
    }

    @Override
    public WordFrequencyR getWordCountInPosition(String category, int position, String word) {
        PreparedStatement query;
        ResultSet results;
        int frequency = 0;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select frequency from corpus.word_pos_category_id WHERE position=? AND word=? AND category =?");
            results = session.execute(query.bind(position, word, "C"));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
            query = session.prepare(
                    "select frequency from corpus.word_pos_category_id WHERE position=? AND word=? AND category =?");
            results = session.execute(query.bind(position, word, "B"));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
        } else {
            query = session.prepare(
                    "select frequency from corpus.word_pos_category_id WHERE position=? AND word=? AND category=?");
            results = session.execute(query.bind(position, word, category.charAt(0) + ""));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
        }
        System.out.println(frequency);
        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory(category);
        endObject.setDate(0);
        endObject.setFrequency(frequency);

        return endObject;
    }

    @Override
    public WordFrequencyR getWordCountInPosition(int position, String word) {
        PreparedStatement query;
        ResultSet results;
        int frequency = 0;

        query = session.prepare(
                "select frequency from corpus.word_pos_id WHERE position=? AND word=?");
        results = session.execute(query.bind(position, word));
        for (Row row : results) {
            frequency += row.getInt("frequency");
            break;
        }

        System.out.println(frequency);
        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory("all");
        endObject.setDate(0);
        endObject.setFrequency(frequency);

        return endObject;
    }

    @Override
    public WordFrequencyR getWordCountInPositionReverse(String category, int year, int position, String word) {
        PreparedStatement query;
        ResultSet results;
        int frequency = 0;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select frequency from corpus.word_inv_pos_year_category_id WHERE inv_position=? AND word=? AND year=? AND category =?");
            results = session.execute(query.bind(position, word, year, "C"));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
            query = session.prepare(
                    "select frequency from corpus.word_inv_pos_year_category_id WHERE inv_position=? AND word=? AND year=? AND category =?");
            results = session.execute(query.bind(position, word, year, "B"));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
        } else {
            query = session.prepare(
                    "select frequency from corpus.word_inv_pos_year_category_id WHERE inv_position=? AND word=? AND year=? AND category=?");
            results = session.execute(query.bind(position, word, year, category.charAt(0) + ""));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
        }
        System.out.println(frequency);
        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory(category);
        endObject.setDate(year);
        endObject.setFrequency(frequency);

        return endObject;
    }

    @Override
    public WordFrequencyR getWordCountInPositionReverse(int year, int position, String word) {
        PreparedStatement query;
        ResultSet results;
        int frequency = 0;

        query = session.prepare(
                "select frequency from corpus.word_inv_pos_year_id WHERE inv_position=? AND word=? AND year=?");
        results = session.execute(query.bind(position, word, year));
        for (Row row : results) {
            frequency += row.getInt("frequency");
            break;
        }

        System.out.println(frequency);
        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory("all");
        endObject.setDate(year);
        endObject.setFrequency(frequency);

        return endObject;
    }

    @Override
    public WordFrequencyR getWordCountInPositionReverse(String category, int position, String word) {
        PreparedStatement query;
        ResultSet results;
        int frequency = 0;
        if (category.equals("CREATIVE")) {
            query = session.prepare(
                    "select frequency from corpus.word_inv_pos_category_id WHERE inv_position=? AND word=? AND category =?");
            results = session.execute(query.bind(position, word, "C"));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
            query = session.prepare(
                    "select frequency from corpus.word_inv_pos_category_id WHERE inv_position=? AND word=? AND category =?");
            results = session.execute(query.bind(position, word, "B"));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
        } else {
            query = session.prepare(
                    "select frequency from corpus.word_inv_pos_category_id WHERE inv_position=? AND word=? AND category=?");
            results = session.execute(query.bind(position, word, category.charAt(0) + ""));
            for (Row row : results) {
                frequency += row.getInt("frequency");
                break;
            }
        }
        System.out.println(frequency);
        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory(category);
        endObject.setDate(0);
        endObject.setFrequency(frequency);

        return endObject;
    }

    @Override
    public WordFrequencyR getWordCountInPositionReverse(int position, String word) {
        PreparedStatement query;
        ResultSet results;
        int frequency = 0;

        query = session.prepare(
                "select frequency from corpus.word_inv_pos_id WHERE inv_position=? AND word=?");
        results = session.execute(query.bind(position, word));
        for (Row row : results) {
            frequency += row.getInt("frequency");
            break;
        }

        System.out.println(frequency);
        WordFrequencyR endObject = new WordFrequencyR();
        endObject.setCategory("all");
        endObject.setDate(0);
        endObject.setFrequency(frequency);

        return endObject;
    }


    @Override
    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, String category, int year, int range, int amount) throws Exception {
        return null;
    }

    @Override
    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, int year, int range, int amount) throws Exception {
        return null;
    }

    @Override
    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, String category, int range, int amount) throws Exception {
        return null;
    }

    @Override
    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, int range, int amount) throws Exception {
        return null;
    }


    public static void main(String[] args) {
        CassandraClient cl = new CassandraClient();
        cl.getWordCountInPositionReverse(0, "අපි");
    }

}
