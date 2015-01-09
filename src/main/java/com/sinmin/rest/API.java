package com.sinmin.rest;

import com.sinmin.rest.beans.request.*;
import com.sinmin.rest.beans.response.*;
import com.sinmin.rest.cassandra.CassandraClient;
import com.sinmin.rest.oracle.OracleClient;
import com.sinmin.rest.solr.SolrClient;
import oracle.jdbc.OracleCallableStatement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dimuthuupeksha on 12/9/14.
 */

/*
Method : wildCardSearch
        Req :
        {
            "value" : "විසි*"
        }

        Resp :
        [
            {
            value: "විසින්"
            }
        ]

Method : wordFrequency
        Req
        {
            "value":"Value1",
            "time":["2011"],
            "category":["Cat1"]
        }

        Resp
        [
            {
            "frequency":20,
            "date":2011,
            "category":"News"
            },
            {"frequency":20,
            "date":2011,
            "category":"News"
            }
        ]

Method : bigramFrequency
        Req
        {
            "value1":"Value1",
            "value2":"Value2",
            "time":["84"],
            "category":["Cat1"]
        }

        Resp
        [
            {
            "frequency":20,
            "date":2011,
            "category":"News"
            },
            {"frequency":20,
            "date":2011,
            "category":"News"
            }
        ]

Method : trigramFrequency
        Req
        {
            "value1":"Value1",
            "value2":"Value2",
            "value3":"Value3",
            "time":["84"],
            "category":["Cat1"]
        }

        Resp
        [
            {
            "frequency":20,
            "date":2011,
            "category":"News"
            },
            {"frequency":20,
            "date":2011,
            "category":"News"
            }
        ]

Method : frequent words

        Request
        {
            amount : 10,
            category: ["cat1"],
            time : [2011]

        }

        Response
        [
            {
                value1: "value1",
                frequency: 20,
                category : "Category1"
                time : 2011
            },
            {
                value1: "value2",
                frequency: 20,
                category : "Category1"
                time : 2011
            }
        ]

Method : latestArticlesForWord
        Req:
        {
            "value":"value1",
            "time" : [2012],
            "category":["cat1"],
            "amount" : 20
        }
        Resp :
        [
            {
                time : 2012,
                category : "cat1",
                articles :[
                    {
                        title: "title"
                        author: "author1"
                        link: "link1"
                        year : 2012
                        month : 09
                        day :12
                    }
                ]

            }
        ]

Method : latestArticlesForBigram
        Req
        {
            "value1":"value1",
            "value2":"value2",
            "time" : [2012],
            "category":["cat1"],
            "amount" : 20
        }
        Resp :
        [
            {
                time : 2012,
                category : "cat1",
                articles :[
                    {
                        title: "title"
                        author: "author1"
                        link: "link1"
                        year : 2012
                        month : 09
                        day :12
                    }
                ]

            }
        ]

Method : latestArticlesForTrigram
        Req
        {
            "value1":"value1",
            "value2":"value2",
            "value3":"value3",
            "time" : [2012],
            "category":["cat1"],
            "amount" : 20
        }
        Resp :
        [
            {
                time : 2012,
                category : "cat1",
                articles :[
                    {
                        title: "title"
                        author: "author1"
                        link: "link1"
                        year : 2012
                        month : 09
                        day :12
                    }
                ]

            }
        ]

Method : frequentWordsAroundWord
        Req :
        {
            "value" : "value1",
            "range" : 1,
            "time" : [2012],
            "category" : ["cat1"],
            "amount" : 10
        }

        Resp :

        [
            {
                "time" : 2012,
                "category" : "Cat1",
                "words" :[
                    {
                        "value" : "value2",
                        "frequency" : 10
                    },
                    {
                        "value" : "value3",
                        "frequency" : 12
                    }
                ]

            }
        ]

Method : frequentWordsInPosition
        Req
        {
            "position" : 10,
            "time" : [2010],
            "category" : ["cat1"],
            "amount" : 10
        }

        Resp
        [
            {
                time : 2010,
                category : cat1,
                word : [
                    {
                        value : val1,
                        frequency : 10
                    },
                    {
                        value : val2,
                        frequency : 10
                    }

                ]


            },
            {
                time : 2011,
                category : cat2,
                words : [
                    {
                        value : val1,
                        frequency : 10
                    },
                    {
                        value : val2,
                        frequency : 10
                    }

                ]


            }
        ]

Method : frequentWordsAfterWordTimeRange
        Req
        {
            "value": "ලංකා",
            "time" :[2011,2014],
            "category":["NEWS"],
            "amount": 10
        }

Method : frequentWordsAfterBigramTimeRange
        Req
        {
            "value1": "නිදහස්‌",
            "value2": "වෙළෙඳ",
            "time" :[2011,2014],
            "category":["NEWS"],
            "amount": 10
        }

Method : wordCount
        {
            "category" : ['News'],
            "time" : [2014]
        }

Method : bigramCount
        {
            "category" : ['News'],
            "time" : [2014]
        }

Method : trigramCount
        {
            "category" : ['News'],
            "time" : [2014]
        }
*/

@Path("/api")
public class API {

    @GET
    @Path("/sample/{param}")
    public Response getMsg(@PathParam("param") String msg) {

        String output = "Jersey say : " + msg;

        return Response.status(200).entity(output).build();

    }

    @POST
    @Path("/wordFrequency")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response wordFrequency(WordFrequency wordF) {
        String value = wordF.getValue();
        int time[] = wordF.getTime();
        String category[] = wordF.getCategory();
        try {
            CorpusDBClient client = new CassandraClient("192.248.15.239");
            if (time == null && category == null && value != null) {


                WordFrequencyR resp = client.getWordFrequency(value);
                WordFrequencyR freqArr[] = {resp};
                return Response.status(200).entity(freqArr).build();

            } else if (time == null && category != null && value != null) {

                WordFrequencyR freqArr[] = new WordFrequencyR[category.length];
                for (int i = 0; i < category.length; i++) {
                    WordFrequencyR resp = client.getWordFrequency(value, category[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category == null && time != null && value != null) {

                WordFrequencyR freqArr[] = new WordFrequencyR[time.length];
                for (int i = 0; i < time.length; i++) {
                    WordFrequencyR resp = client.getWordFrequency(value, time[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category != null && time != null && value != null) {
                WordFrequencyR freqArr[] = new WordFrequencyR[time.length * category.length];
                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < time.length; j++) {
                        WordFrequencyR resp = client.getWordFrequency(value, time[j], category[i]);
                        freqArr[i * time.length + j] = resp;
                    }
                }
                return Response.status(200).entity(freqArr).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }


    }

    @POST
    @Path("/bigramFrequency")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bigramFrequency(BigramFrequency bigF) {
        String value1 = bigF.getValue1();
        String value2 = bigF.getValue2();
        String category[] = bigF.getCategory();
        int time[] = bigF.getTime();

        //String sql ="select count(sb.sentence_id) from word w1,word w2,bigram b,sentence_bigram sb,sentence s, article a where w1.val='මහින්ද' and w2.val='රාජපක්ෂ' and b.word1=w1.id and b.word2=w2.id and sb.bigram_id=b.id and s.id = sb.sentence_id and a.id = s.article_id and a.year=2008";

        try {

            if (time == null && category == null && value1 != null && value2 != null) {

                CorpusDBClient client = new OracleClient();
                WordFrequencyR resp = client.getBigramFrequency(value1, value2);
                WordFrequencyR freqArr[] = {resp};
                return Response.status(200).entity(freqArr).build();

            } else if (time == null && category != null && value1 != null && value2 != null) {

                WordFrequencyR freqArr[] = new WordFrequencyR[category.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    WordFrequencyR resp = client.getBigramFrequency(value1, value2, category[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category == null && time != null && value1 != null && value2 != null) {

                WordFrequencyR freqArr[] = new WordFrequencyR[time.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < time.length; i++) {
                    WordFrequencyR resp = client.getBigramFrequency(value1, value2, time[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category != null && time != null && value1 != null && value2 != null) {
                WordFrequencyR freqArr[] = new WordFrequencyR[time.length * category.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < time.length; j++) {
                        WordFrequencyR resp = client.getBigramFrequency(value1, value2, time[j], category[i]);
                        freqArr[i * time.length + j] = resp;
                    }
                }
                return Response.status(200).entity(freqArr).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }

    }


    @POST
    @Path("/trigramFrequency")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response trigramFrequency(TrigramFrequency triF) {
        String value1 = triF.getValue1();
        String value2 = triF.getValue2();
        String value3 = triF.getValue3();
        String category[] = triF.getCategory();
        int time[] = triF.getTime();


        try {

            if (time == null && category == null && value1 != null && value2 != null && value3 != null) {

                CorpusDBClient client = new OracleClient();
                WordFrequencyR resp = client.getTrigramFrequency(value1, value2, value3);
                WordFrequencyR freqArr[] = {resp};
                return Response.status(200).entity(freqArr).build();

            } else if (time == null && category != null && value1 != null && value2 != null && value3 != null) {

                WordFrequencyR freqArr[] = new WordFrequencyR[category.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    WordFrequencyR resp = client.getTrigramFrequency(value1, value2, value3, category[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category == null && time != null && value1 != null && value2 != null && value3 != null) {

                WordFrequencyR freqArr[] = new WordFrequencyR[time.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < time.length; i++) {
                    WordFrequencyR resp = client.getTrigramFrequency(value1, value2, value3, time[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category != null && time != null && value1 != null && value2 != null && value3 != null) {
                WordFrequencyR freqArr[] = new WordFrequencyR[time.length * category.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < time.length; j++) {
                        WordFrequencyR resp = client.getTrigramFrequency(value1, value2, value3, time[j], category[i]);
                        freqArr[i * time.length + j] = resp;
                    }
                }
                return Response.status(200).entity(freqArr).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }

    }

    @POST
    @Path("/frequentWords")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentWords(FrequentWord frqWord) {

        String[] category = frqWord.getCategory();
        int year[] = frqWord.getTime();
        int amount = frqWord.getAmount();

        try {
            if (category == null && year == null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR resp = client.getFrequentWords(amount);
                FrequentWordR[] freqArr = {resp};
                return Response.status(200).entity(freqArr).build();
            } else if (category == null && year != null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR[] freqArr = new FrequentWordR[year.length];
                for (int i = 0; i < year.length; i++) {
                    freqArr[i] = client.getFrequentWords(year[i], amount);
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category != null && year == null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR[] freqArr = new FrequentWordR[category.length];
                for (int i = 0; i < category.length; i++) {
                    freqArr[i] = client.getFrequentWords(category[i], amount);
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category != null && year != null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR[] freqArr = new FrequentWordR[category.length * year.length];
                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < year.length; j++) {
                        freqArr[i * year.length + j] = client.getFrequentWords(year[j], category[i], amount);
                    }
                }
                return Response.status(200).entity(freqArr).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }

    }

    @POST
    @Path("/frequentBigrams")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentBigrams(FrequentWord frqWord) {

        String[] category = frqWord.getCategory();
        int year[] = frqWord.getTime();
        int amount = frqWord.getAmount();

        try {
            if (category == null && year == null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR resp = client.getFrequentBigrams(amount);
                FrequentWordR[] freqArr = {resp};
                return Response.status(200).entity(freqArr).build();
            } else if (category == null && year != null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR[] freqArr = new FrequentWordR[year.length];
                for (int i = 0; i < year.length; i++) {
                    freqArr[i] = client.getFrequentBigrams(year[i], amount);
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category != null && year == null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR[] freqArr = new FrequentWordR[category.length];
                for (int i = 0; i < category.length; i++) {
                    freqArr[i] = client.getFrequentBigrams(category[i], amount);
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category != null && year != null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR[] freqArr = new FrequentWordR[category.length * year.length];
                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < year.length; j++) {
                        freqArr[i * year.length + j] = client.getFrequentBigrams(year[j], category[i], amount);
                    }
                }
                return Response.status(200).entity(freqArr).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/frequentTrigrams")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentTrigrams(FrequentWord frqWord) {
        String[] category = frqWord.getCategory();
        int year[] = frqWord.getTime();
        int amount = frqWord.getAmount();

        try {
            if (category == null && year == null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR resp = client.getFrequentTrigrams(amount);
                FrequentWordR[] freqArr = {resp};
                return Response.status(200).entity(freqArr).build();
            } else if (category == null && year != null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR[] freqArr = new FrequentWordR[year.length];
                for (int i = 0; i < year.length; i++) {
                    freqArr[i] = client.getFrequentTrigrams(year[i], amount);
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category != null && year == null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR[] freqArr = new FrequentWordR[category.length];
                for (int i = 0; i < category.length; i++) {
                    freqArr[i] = client.getFrequentTrigrams(category[i], amount);
                }
                return Response.status(200).entity(freqArr).build();

            } else if (category != null && year != null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordR[] freqArr = new FrequentWordR[category.length * year.length];
                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < year.length; j++) {
                        freqArr[i * year.length + j] = client.getFrequentTrigrams(year[j], category[i], amount);
                    }
                }
                return Response.status(200).entity(freqArr).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }


    @POST
    @Path("/latestArticlesForWord")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response latestArticlesForWord(ArticlesForWord articlesForWord) {

        int amount = articlesForWord.getAmount();
        String category[] = articlesForWord.getCategory();
        int year[] = articlesForWord.getTime();
        String value = articlesForWord.getValue();
        try {
            if (category != null && year != null && value != null) {
                ArticlesForWordR[] articlesForWordRs = new ArticlesForWordR[category.length * year.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < year.length; j++) {
                        articlesForWordRs[i * year.length + j] = client.getLatestArticlesForWord(value, year[j], category[i], amount);
                    }
                }
                return Response.status(200).entity(articlesForWordRs).build();
            } else if (category == null && year != null && value != null) {
                ArticlesForWordR[] articlesForWordRs = new ArticlesForWordR[year.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < year.length; i++) {
                    articlesForWordRs[i] = client.getLatestArticlesForWord(value, year[i], amount);
                }
                return Response.status(200).entity(articlesForWordRs).build();
            } else if (category != null && year == null && value != null) {
                ArticlesForWordR[] articlesForWordRs = new ArticlesForWordR[category.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    articlesForWordRs[i] = client.getLatestArticlesForWord(value, category[i], amount);
                }
                return Response.status(200).entity(articlesForWordRs).build();
            } else if (category == null && year == null && value != null) {

                CorpusDBClient client = new OracleClient();
                ArticlesForWordR[] articlesForWordRs = {client.getLatestArticlesForWord(value, amount)};
                return Response.status(200).entity(articlesForWordRs).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/latestArticlesForBigram")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response latestArticlesForBigram(ArticlesForBigram articlesForBigram) {

        int amount = articlesForBigram.getAmount();
        String category[] = articlesForBigram.getCategory();
        int year[] = articlesForBigram.getTime();
        String value1 = articlesForBigram.getValue1();
        String value2 = articlesForBigram.getValue2();
        try {
            if (category != null && year != null && value1 != null && value2 != null) {
                ArticlesForWordR[] articlesForWordRs = new ArticlesForWordR[category.length * year.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < year.length; j++) {
                        articlesForWordRs[i * year.length + j] = client.getLatestArticlesForBigram(value1, value2, year[j], category[i], amount);
                    }
                }
                return Response.status(200).entity(articlesForWordRs).build();
            } else if (category == null && year != null && value1 != null && value2 != null) {
                ArticlesForWordR[] articlesForWordRs = new ArticlesForWordR[year.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < year.length; i++) {
                    articlesForWordRs[i] = client.getLatestArticlesForBigram(value1, value2, year[i], amount);
                }
                return Response.status(200).entity(articlesForWordRs).build();
            } else if (category != null && year == null && value1 != null && value2 != null) {
                ArticlesForWordR[] articlesForWordRs = new ArticlesForWordR[category.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    articlesForWordRs[i] = client.getLatestArticlesForBigram(value1, value2, category[i], amount);
                }
                return Response.status(200).entity(articlesForWordRs).build();
            } else if (category == null && year == null && value1 != null && value2 != null) {

                CorpusDBClient client = new OracleClient();
                ArticlesForWordR[] articlesForWordRs = {client.getLatestArticlesForBigram(value1, value2, amount)};
                return Response.status(200).entity(articlesForWordRs).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/latestArticlesForTrigram")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response latestArticlesForTrigram(ArticlesForTrigram articlesForTrigram) {

        int amount = articlesForTrigram.getAmount();
        String category[] = articlesForTrigram.getCategory();
        int year[] = articlesForTrigram.getTime();
        String value1 = articlesForTrigram.getValue1();
        String value2 = articlesForTrigram.getValue2();
        String value3 = articlesForTrigram.getValue3();
        try {
            if (category != null && year != null && value1 != null && value2 != null && value3 != null) {
                ArticlesForWordR[] articlesForWordRs = new ArticlesForWordR[category.length * year.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < year.length; j++) {
                        articlesForWordRs[i * year.length + j] = client.getLatestArticlesForTrigram(value1, value2, value3, year[j], category[i], amount);
                    }
                }
                return Response.status(200).entity(articlesForWordRs).build();
            } else if (category == null && year != null && value1 != null && value2 != null && value3 != null) {
                ArticlesForWordR[] articlesForWordRs = new ArticlesForWordR[year.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < year.length; i++) {
                    articlesForWordRs[i] = client.getLatestArticlesForTrigram(value1, value2, value3, year[i], amount);
                }
                return Response.status(200).entity(articlesForWordRs).build();
            } else if (category != null && year == null && value1 != null && value2 != null && value3 != null) {
                ArticlesForWordR[] articlesForWordRs = new ArticlesForWordR[category.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    articlesForWordRs[i] = client.getLatestArticlesForTrigram(value1, value2, value3, category[i], amount);
                }
                return Response.status(200).entity(articlesForWordRs).build();
            } else if (category == null && year == null && value1 != null && value2 != null && value3 != null) {

                CorpusDBClient client = new OracleClient();
                ArticlesForWordR[] articlesForWordRs = {client.getLatestArticlesForTrigram(value1, value2, value3, amount)};
                return Response.status(200).entity(articlesForWordRs).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }

    //FrequentWordsAroundWord
    @POST
    @Path("/frequentWordsAroundWord")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentWordsAroundWord(FrequentWordsAroundWord frqWords) {


        String value = frqWords.getValue();
        int[] year = frqWords.getTime();
        String[] category = frqWords.getCategory();
        int amount = frqWords.getAmount();
        int range = frqWords.getRange();

        try {
            if (category != null && year != null && value != null) {
                FrequentWordsAroundWordR[] frequentWordsAroundWords = new FrequentWordsAroundWordR[category.length * year.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < year.length; j++) {
                        frequentWordsAroundWords[i * year.length + j] = client.getFrequentWordsAroundWord(value, category[i], year[j], range, amount);
                    }
                }
                return Response.status(200).entity(frequentWordsAroundWords).build();
            } else if (category == null && year != null && value != null) {
                FrequentWordsAroundWordR[] frequentWordsAroundWords = new FrequentWordsAroundWordR[year.length];
                CorpusDBClient client = new OracleClient();
                for (int j = 0; j < year.length; j++) {
                    frequentWordsAroundWords[j] = client.getFrequentWordsAroundWord(value, year[j], range, amount);
                }
                return Response.status(200).entity(frequentWordsAroundWords).build();

            } else if (category != null && year == null && value != null) {
                FrequentWordsAroundWordR[] frequentWordsAroundWords = new FrequentWordsAroundWordR[category.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    frequentWordsAroundWords[i] = client.getFrequentWordsAroundWord(value, category[i], range, amount);
                }
                return Response.status(200).entity(frequentWordsAroundWords).build();
            } else if (category == null && year == null && value != null) {

                CorpusDBClient client = new OracleClient();
                FrequentWordsAroundWordR[] frequentWordsAroundWords = {client.getFrequentWordsAroundWord(value, range, amount)};
                return Response.status(200).entity(frequentWordsAroundWords).build();

            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }

    }


    @POST
    @Path("/frequentWordsInPosition")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentWordsInPosition(WordPosition position) {


        int[] year = position.getTime();
        String[] category = position.getCategory();
        int amount = position.getAmount();
        int pos = position.getPosition();


        try {
            if (category != null && year != null) {
                WordPositionR[] wordPositions = new WordPositionR[category.length * year.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < year.length; j++) {
                        if (pos > 0) {
                            wordPositions[i * year.length + j] = client.getFrequentWordsInPosition(pos, year[j], category[i], amount);
                        } else {
                            wordPositions[i * year.length + j] = client.getFrequentWordsInPositionReverse(-pos, year[j], category[i], amount);
                        }
                    }
                }
                return Response.status(200).entity(wordPositions).build();
            } else if (category == null && year != null) {
                WordPositionR[] wordPositions = new WordPositionR[year.length];
                CorpusDBClient client = new OracleClient();
                for (int j = 0; j < year.length; j++) {
                    if (pos > 0) {
                        wordPositions[j] = client.getFrequentWordsInPosition(pos, year[j], amount);
                    } else {
                        wordPositions[j] = client.getFrequentWordsInPositionReverse(-pos, year[j], amount);
                    }
                }
                return Response.status(200).entity(wordPositions).build();

            } else if (category != null && year == null) {
                WordPositionR[] wordPositions = new WordPositionR[category.length];
                CorpusDBClient client = new OracleClient();
                for (int i = 0; i < category.length; i++) {
                    if (pos > 0) {
                        wordPositions[i] = client.getFrequentWordsInPosition(pos, category[i], amount);
                    } else {
                        wordPositions[i] = client.getFrequentWordsInPositionReverse(-pos, category[i], amount);
                    }
                }
                return Response.status(200).entity(wordPositions).build();
            } else if (category == null && year == null) {

                CorpusDBClient client = new OracleClient();
                if (pos > 0) {
                    WordPositionR[] wordPositions = {client.getFrequentWordsInPosition(pos, amount)};
                    return Response.status(200).entity(wordPositions).build();
                } else {
                    WordPositionR[] wordPositions = {client.getFrequentWordsInPositionReverse(-pos, amount)};
                    return Response.status(200).entity(wordPositions).build();
                }

            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }


    @POST
    @Path("/frequentWordsAfterWordTimeRange")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentWordsAfterWordTimeRange(FrequentWordsAfterWord freqWord) {
        int time[] = freqWord.getTime();
        String category[] = freqWord.getCategory();
        int amount = freqWord.getAmount();
        String word = freqWord.getValue();

        try {
            if (time != null && time.length == 2 && category != null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordsAfterWordR[] resp = new FrequentWordsAfterWordR[category.length];
                for (int i = 0; i < category.length; i++) {
                    resp[i] = client.getFrequentWordsAfterWordTimeRange(word, category[i], time[0], time[1], amount);
                }
                return Response.status(200).entity(resp).build();
            } else if (time != null && time.length == 2 && category == null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordsAfterWordR[] resp = {client.getFrequentWordsAfterWordTimeRange(word, time[0], time[1], amount)};
                return Response.status(200).entity(resp).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }

    }

    @POST
    @Path("/frequentWordsAfterBigramTimeRange")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentWordsAfterBigramTimeRange(FrequentWordsAfterBigram freqWord) {
        int time[] = freqWord.getTime();
        String category[] = freqWord.getCategory();
        int amount = freqWord.getAmount();
        String word1 = freqWord.getValue1();
        String word2 = freqWord.getValue2();

        try {
            if (time != null && time.length == 2 && category != null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordsAfterWordR[] resp = new FrequentWordsAfterWordR[category.length];
                for (int i = 0; i < category.length; i++) {
                    resp[i] = client.getFrequentWordsAfterBigramTimeRange(word1, word2, category[i], time[0], time[1], amount);
                }
                return Response.status(200).entity(resp).build();
            } else if (time != null && time.length == 2 && category == null) {
                CorpusDBClient client = new OracleClient();
                FrequentWordsAfterWordR[] resp = {client.getFrequentWordsAfterBigramTimeRange(word1, word2, time[0], time[1], amount)};
                return Response.status(200).entity(resp).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }

    }

    @POST
    @Path("/wordCount")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response wordCount(WordCount wordCount) {
        String category[] = wordCount.getCategory();
        int year[] = wordCount.getTime();

        try {
            if (category != null && year != null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = new WordCountR[category.length * year.length];

                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < year.length; j++) {
                        resp[i * year.length + j] = client.getWordCount(category[i], year[j]);
                    }
                }
                return Response.status(200).entity(resp).build();
            } else if (category == null && year != null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = new WordCountR[year.length];
                for (int j = 0; j < year.length; j++) {
                    resp[j] = client.getWordCount(year[j]);
                }

                return Response.status(200).entity(resp).build();

            } else if (category != null && year == null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = new WordCountR[category.length];

                for (int i = 0; i < category.length; i++) {
                    resp[i] = client.getWordCount(category[i]);
                }
                return Response.status(200).entity(resp).build();

            } else if (category == null && year == null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = {client.getWordCount()};
                return Response.status(200).entity(resp).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }


    @POST
    @Path("/bigramCount")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bigramCount(WordCount wordCount) {
        String category[] = wordCount.getCategory();
        int year[] = wordCount.getTime();

        try {
            if (category != null && year != null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = new WordCountR[category.length * year.length];

                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < year.length; j++) {
                        resp[i * year.length + j] = client.getBigramCount(category[i], year[j]);
                    }
                }
                return Response.status(200).entity(resp).build();
            } else if (category == null && year != null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = new WordCountR[year.length];
                for (int j = 0; j < year.length; j++) {
                    resp[j] = client.getBigramCount(year[j]);
                }

                return Response.status(200).entity(resp).build();

            } else if (category != null && year == null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = new WordCountR[category.length];

                for (int i = 0; i < category.length; i++) {
                    resp[i] = client.getBigramCount(category[i]);
                }
                return Response.status(200).entity(resp).build();

            } else if (category == null && year == null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = {client.getBigramCount()};
                return Response.status(200).entity(resp).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/trigramCount")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response trigramCount(WordCount wordCount) {
        String category[] = wordCount.getCategory();
        int year[] = wordCount.getTime();

        try {
            if (category != null && year != null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = new WordCountR[category.length * year.length];

                for (int i = 0; i < category.length; i++) {
                    for (int j = 0; j < year.length; j++) {
                        resp[i * year.length + j] = client.getTrigramCount(category[i], year[j]);
                    }
                }
                return Response.status(200).entity(resp).build();
            } else if (category == null && year != null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = new WordCountR[year.length];
                for (int j = 0; j < year.length; j++) {
                    resp[j] = client.getTrigramCount(year[j]);
                }

                return Response.status(200).entity(resp).build();

            } else if (category != null && year == null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = new WordCountR[category.length];

                for (int i = 0; i < category.length; i++) {
                    resp[i] = client.getTrigramCount(category[i]);
                }
                return Response.status(200).entity(resp).build();

            } else if (category == null && year == null) {
                CorpusDBClient client = new OracleClient();
                WordCountR resp[] = {client.getTrigramCount()};
                return Response.status(200).entity(resp).build();
            } else {
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/wildCardSearch")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response wildCardSearch(WildCard wildCard) {
        String value = wildCard.getValue();
        System.out.println(value);

        if (value != null) {
            SolrClient solrClient = new SolrClient();
            WildCardR[] resp = solrClient.getWildCardWords(value);
            return Response.status(200).entity(resp).build();
        } else {
            return Response.status(500).entity("Invalid input parameters").build();
        }

    }

}

