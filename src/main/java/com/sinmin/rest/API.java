package com.sinmin.rest;

import com.sinmin.rest.beans.request.*;
import com.sinmin.rest.beans.response.*;
import com.sinmin.rest.oracle.OracleClient;
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
            "range" : 12,
            "time" : [2012],
            "category" : ["cat1"],
            "amount" : 12
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

            if(time==null && category==null && value!=null){

                OracleClient client = new OracleClient();
                WordFrequencyR resp = client.getWordFrequency(value);
                WordFrequencyR freqArr[] ={resp};
                return Response.status(200).entity(freqArr).build();

            }else if(time==null && category!=null && value!=null){

                WordFrequencyR freqArr[] = new WordFrequencyR[category.length];
                OracleClient client = new OracleClient();
                for (int i=0; i<category.length;i++){
                    WordFrequencyR resp = client.getWordFrequency(value,category[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            }else if(category==null && time!=null && value!=null){

                WordFrequencyR freqArr[] = new WordFrequencyR[time.length];
                OracleClient client = new OracleClient();
                for (int i=0; i<time.length;i++){
                    WordFrequencyR resp = client.getWordFrequency(value, time[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            }else if(category!=null&& time!=null && value!=null){
                WordFrequencyR freqArr[] = new WordFrequencyR[time.length*category.length];
                OracleClient client = new OracleClient();
                for(int i=0;i<category.length;i++){
                    for(int j=0;j<time.length;j++){
                        WordFrequencyR resp = client.getWordFrequency(value, time[j],category[i]);
                        freqArr[i*time.length+j] = resp;
                    }
                }
                return Response.status(200).entity(freqArr).build();
            }else{
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex){
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

            if(time==null && category==null && value1!=null && value2!=null){

                OracleClient client = new OracleClient();
                WordFrequencyR resp = client.getBigramFrequency(value1, value2);
                WordFrequencyR freqArr[] ={resp};
                return Response.status(200).entity(freqArr).build();

            }else if(time==null && category!=null && value1!=null && value2!=null){

                WordFrequencyR freqArr[] = new WordFrequencyR[category.length];
                OracleClient client = new OracleClient();
                for (int i=0; i<category.length;i++){
                    WordFrequencyR resp = client.getBigramFrequency(value1, value2, category[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            }else if(category==null && time!=null && value1!=null && value2!=null){

                WordFrequencyR freqArr[] = new WordFrequencyR[time.length];
                OracleClient client = new OracleClient();
                for (int i=0; i<time.length;i++){
                    WordFrequencyR resp = client.getBigramFrequency(value1, value2, time[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            }else if(category!=null&& time!=null && value1!=null && value2!=null){
                WordFrequencyR freqArr[] = new WordFrequencyR[time.length*category.length];
                OracleClient client = new OracleClient();
                for(int i=0;i<category.length;i++){
                    for(int j=0;j<time.length;j++){
                        WordFrequencyR resp = client.getBigramFrequency(value1, value2, time[j], category[i]);
                        freqArr[i*time.length+j] = resp;
                    }
                }
                return Response.status(200).entity(freqArr).build();
            }else{
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex){
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

            if(time==null && category==null && value1!=null && value2!=null && value3!=null){

                OracleClient client = new OracleClient();
                WordFrequencyR resp = client.getTrigramFrequency(value1, value2, value3);
                WordFrequencyR freqArr[] ={resp};
                return Response.status(200).entity(freqArr).build();

            }else if(time==null && category!=null && value1!=null && value2!=null && value3!=null){

                WordFrequencyR freqArr[] = new WordFrequencyR[category.length];
                OracleClient client = new OracleClient();
                for (int i=0; i<category.length;i++){
                    WordFrequencyR resp = client.getTrigramFrequency(value1,value2,value3,category[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            }else if(category==null && time!=null && value1!=null && value2!=null && value3!=null){

                WordFrequencyR freqArr[] = new WordFrequencyR[time.length];
                OracleClient client = new OracleClient();
                for (int i=0; i<time.length;i++){
                    WordFrequencyR resp = client.getTrigramFrequency(value1,value2, value3,time[i]);
                    freqArr[i] = resp;
                }
                return Response.status(200).entity(freqArr).build();

            }else if(category!=null&& time!=null && value1!=null && value2!=null && value3!=null){
                WordFrequencyR freqArr[] = new WordFrequencyR[time.length*category.length];
                OracleClient client = new OracleClient();
                for(int i=0;i<category.length;i++){
                    for(int j=0;j<time.length;j++){
                        WordFrequencyR resp = client.getTrigramFrequency(value1,value2,value3, time[j],category[i]);
                        freqArr[i*time.length+j] = resp;
                    }
                }
                return Response.status(200).entity(freqArr).build();
            }else{
                return Response.status(500).entity("Invalid input parameters").build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();

        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
            return Response.status(500).entity(ex.getMessage()).build();
        }

    }

    @POST
    @Path("/frequentWords")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentWords(FrequentWord frqWord) {

        String[] category=frqWord.getCategory();
        int year[] = frqWord.getTime();
        int amount = frqWord.getAmount();


        if(category==null && year==null){


        }else if(category==null && year!=null){

        }else if(category!=null && year==null){

        }else if(category!=null && year!=null){

        }else{
            return Response.status(500).entity("Invalid input parameters").build();
        }


//
//        FrequentWordR freqWordR1 = new FrequentWordR();
//        freqWordR1.setValue1("value 1");
//        freqWordR1.setFrequency(10);
//        freqWordR1.setCategory("Cat 1");
//        freqWordR1.setTime(2011);
//
//        FrequentWordR freqWordR2 = new FrequentWordR();
//        freqWordR2.setValue1("value 2");
//        freqWordR2.setFrequency(11);
//        freqWordR2.setCategory("Cat 2");
//        freqWordR2.setTime(2012);
//
//        FrequentWordR frequentWordArr[] = {freqWordR1, freqWordR2};
        return Response.status(200).entity(null).build();
    }

    @POST
    @Path("/frequentBigrams")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentBigrams(FrequentWord frqWord) {

//        FrequentWordR freqWordR1 = new FrequentWordR();
//        freqWordR1.setValue1("value 1");
//        freqWordR1.setValue1("value 11");
//        freqWordR1.setFrequency(10);
//        freqWordR1.setCategory("Cat 1");
//        freqWordR1.setTime(2011);
//
//        FrequentWordR freqWordR2 = new FrequentWordR();
//        freqWordR2.setValue1("value 2");
//        freqWordR1.setValue1("value 22");
//        freqWordR2.setFrequency(11);
//        freqWordR2.setCategory("Cat 2");
//        freqWordR2.setTime(2012);

//        FrequentWordR frequentWordArr[] = {freqWordR1, freqWordR2};
        return Response.status(200).entity(null).build();
    }

    @POST
    @Path("/frequentTrigrams")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentTrigrams(FrequentWord frqWord) {

//        FrequentWordR freqWordR1 = new FrequentWordR();
//        freqWordR1.setValue1("value 1");
//        freqWordR1.setValue1("value 11");
//        freqWordR1.setValue1("value 111");
//        freqWordR1.setFrequency(10);
//        freqWordR1.setCategory("Cat 1");
//        freqWordR1.setTime(2011);
//
//        FrequentWordR freqWordR2 = new FrequentWordR();
//        freqWordR2.setValue1("value 2");
//        freqWordR1.setValue1("value 22");
//        freqWordR1.setValue1("value 222");
//        freqWordR2.setFrequency(11);
//        freqWordR2.setCategory("Cat 2");
//        freqWordR2.setTime(2012);

//        FrequentWordR frequentWordArr[] = {freqWordR1, freqWordR2};
        return Response.status(200).entity(null).build();
    }


    @POST
    @Path("/latestArticlesForWord")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response latestArticlesForWord(ArticlesForWord articlesForWord) {

        ArticleR articleR1 = new ArticleR();
        articleR1.setAuthor("Author 1");
        articleR1.setDay(10);
        articleR1.setMonth(1);
        articleR1.setYear(2012);
        articleR1.setLink("Link 1");
        articleR1.setTitle("Title 1");
        articleR1.setCategory("Cat 1");

        ArticleR articleR2 = new ArticleR();
        articleR2.setAuthor("Author 2");
        articleR2.setDay(11);
        articleR2.setMonth(2);
        articleR2.setYear(2013);
        articleR2.setLink("Link 2");
        articleR2.setTitle("Title 2");
        articleR2.setCategory("Cat 2");

        ArticleR[] articles = {articleR1, articleR2};

        ArticlesForWordR articlesForWordR1 = new ArticlesForWordR();
        articlesForWordR1.setTime(2012);
        articlesForWordR1.setCategory("Cat 1");
        articlesForWordR1.setArticles(articles);

        ArticlesForWordR articlesForWordR2 = new ArticlesForWordR();
        articlesForWordR2.setTime(2013);
        articlesForWordR2.setCategory("Cat 3");
        articlesForWordR2.setArticles(articles);

        ArticlesForWordR[] arr = {articlesForWordR1, articlesForWordR2};

        return Response.status(200).entity(arr).build();
    }

    @POST
    @Path("/latestArticlesForBigram")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response latestArticlesForBigram(ArticlesForBigram articlesForBigram) {

        ArticleR articleR1 = new ArticleR();
        articleR1.setAuthor("Author 1");
        articleR1.setDay(10);
        articleR1.setMonth(1);
        articleR1.setYear(2012);
        articleR1.setLink("Link 1");
        articleR1.setTitle("Title 1");
        articleR1.setCategory("Cat 1");

        ArticleR articleR2 = new ArticleR();
        articleR2.setAuthor("Author 2");
        articleR2.setDay(11);
        articleR2.setMonth(2);
        articleR2.setYear(2013);
        articleR2.setLink("Link 2");
        articleR2.setTitle("Title 2");
        articleR2.setCategory("Cat 2");

        ArticleR[] articles = {articleR1, articleR2};

        ArticlesForWordR articlesForWordR1 = new ArticlesForWordR();
        articlesForWordR1.setTime(2012);
        articlesForWordR1.setCategory("Cat 1");
        articlesForWordR1.setArticles(articles);

        ArticlesForWordR articlesForWordR2 = new ArticlesForWordR();
        articlesForWordR2.setTime(2013);
        articlesForWordR2.setCategory("Cat 3");
        articlesForWordR2.setArticles(articles);

        ArticlesForWordR[] arr = {articlesForWordR1, articlesForWordR2};

        return Response.status(200).entity(arr).build();
    }

    @POST
    @Path("/latestArticlesForTrigram")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response latestArticlesForTrigram(ArticlesForTrigram articlesForTrigram) {

        ArticleR articleR1 = new ArticleR();
        articleR1.setAuthor("Author 1");
        articleR1.setDay(10);
        articleR1.setMonth(1);
        articleR1.setYear(2012);
        articleR1.setLink("Link 1");
        articleR1.setTitle("Title 1");
        articleR1.setCategory("Cat 1");

        ArticleR articleR2 = new ArticleR();
        articleR2.setAuthor("Author 2");
        articleR2.setDay(11);
        articleR2.setMonth(2);
        articleR2.setYear(2013);
        articleR2.setLink("Link 2");
        articleR2.setTitle("Title 2");
        articleR2.setCategory("Cat 2");

        ArticleR[] articles = {articleR1, articleR2};

        ArticlesForWordR articlesForWordR1 = new ArticlesForWordR();
        articlesForWordR1.setTime(2012);
        articlesForWordR1.setCategory("Cat 1");
        articlesForWordR1.setArticles(articles);

        ArticlesForWordR articlesForWordR2 = new ArticlesForWordR();
        articlesForWordR2.setTime(2013);
        articlesForWordR2.setCategory("Cat 3");
        articlesForWordR2.setArticles(articles);

        ArticlesForWordR[] arr = {articlesForWordR1, articlesForWordR2};

        return Response.status(200).entity(arr).build();
    }

    //FrequentWordsAroundWord
    @POST
    @Path("/frequentWordsAroundWord")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentWordsAroundWord(FrequentWordsAroundWord frqWords) {

        WordR wordR1 = new WordR();
        wordR1.setFrequency(10);
        wordR1.setValue("val 1");

        WordR wordR2 = new WordR();
        wordR2.setFrequency(14);
        wordR2.setValue("val 2");

        WordR words[] = {wordR1, wordR2};

        FrequentWordsAroundWordR frequentWordsAroundWordR1 = new FrequentWordsAroundWordR();
        frequentWordsAroundWordR1.setCategory("cat 1");
        frequentWordsAroundWordR1.setTime(2012);
        frequentWordsAroundWordR1.setWords(words);

        FrequentWordsAroundWordR frequentWordsAroundWordR2 = new FrequentWordsAroundWordR();
        frequentWordsAroundWordR2.setCategory("cat 2");
        frequentWordsAroundWordR2.setTime(2013);
        frequentWordsAroundWordR2.setWords(words);

        FrequentWordsAroundWordR[] arr = {frequentWordsAroundWordR1, frequentWordsAroundWordR2};

        return Response.status(200).entity(arr).build();
    }


    @POST
    @Path("/frequentWordsInPosition")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentWordsInPosition(WordPosition position) {

        WordR wordR1 = new WordR();
        wordR1.setFrequency(10);
        wordR1.setValue("val 1");

        WordR wordR2 = new WordR();
        wordR2.setFrequency(14);
        wordR2.setValue("val 2");

        WordR words[] = {wordR1, wordR2};

        WordPositionR wordPositionR1 = new WordPositionR();
        wordPositionR1.setCategory("cat 1");
        wordPositionR1.setTime(2010);
        wordPositionR1.setWords(words);

        WordPositionR wordPositionR2 = new WordPositionR();
        wordPositionR2.setCategory("cat 2");
        wordPositionR2.setTime(2011);
        wordPositionR2.setWords(words);

        WordPositionR[] arr = {wordPositionR1, wordPositionR2};
        //OracleClient.getDBConnection();
        System.out.println("Got db Connection");
        return Response.status(200).entity(arr).build();
    }


}

