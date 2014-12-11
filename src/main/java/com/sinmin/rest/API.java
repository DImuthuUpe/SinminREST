package com.sinmin.rest;

import com.sinmin.rest.beans.request.*;
import com.sinmin.rest.beans.response.*;
import com.sinmin.rest.oracle.OracleClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response wordFrequency(WordFrequency wordF){


        String value = wordF.getValue();

        WordFrequencyR resp1 = new WordFrequencyR();
        resp1.setCategory("News");
        resp1.setDate(2011);
        resp1.setFrequency(20);
        WordFrequencyR resp2 = new WordFrequencyR();
        resp2.setCategory("Article");
        resp2.setDate(2011);
        resp2.setFrequency(20);
        WordFrequencyR freqArr[] ={resp1,resp2};
        return Response.status(200).entity(freqArr).build();
    }

    @POST
    @Path("/bigramFrequency")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bigramFrequency(BigramFrequency bigF){
        String value = bigF.getValue1();
        WordFrequencyR resp1 = new WordFrequencyR();
        resp1.setCategory("News");
        resp1.setDate(2011);
        resp1.setFrequency(20);
        WordFrequencyR resp2 = new WordFrequencyR();
        resp2.setCategory("Article");
        resp2.setDate(2011);
        resp2.setFrequency(20);
        WordFrequencyR freqArr[] ={resp1,resp2};
        return Response.status(200).entity(freqArr).build();

    }


    @POST
    @Path("/trigramFrequency")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response trigramFrequency(TrigramFrequency triF){
        String value = triF.getValue1();
        WordFrequencyR resp1 = new WordFrequencyR();
        resp1.setCategory("News");
        resp1.setDate(2011);
        resp1.setFrequency(20);
        WordFrequencyR resp2 = new WordFrequencyR();
        resp2.setCategory("Article");
        resp2.setDate(2011);
        resp2.setFrequency(20);
        WordFrequencyR freqArr[] ={resp1,resp2};
        return Response.status(200).entity(freqArr).build();

    }

    @POST
    @Path("/frequentWords")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentWords(FrequentWord frqWord){

        FrequentWordR freqWordR1 = new FrequentWordR();
        freqWordR1.setValue1("value 1");
        freqWordR1.setFrequency(10);
        freqWordR1.setCategory("Cat 1");
        freqWordR1.setTime(2011);

        FrequentWordR freqWordR2 = new FrequentWordR();
        freqWordR2.setValue1("value 2");
        freqWordR2.setFrequency(11);
        freqWordR2.setCategory("Cat 2");
        freqWordR2.setTime(2012);

        FrequentWordR frequentWordArr[] = {freqWordR1,freqWordR2};
        return Response.status(200).entity(frequentWordArr).build();
    }

    @POST
    @Path("/frequentBigrams")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentBigrams(FrequentWord frqWord){

        FrequentWordR freqWordR1 = new FrequentWordR();
        freqWordR1.setValue1("value 1");
        freqWordR1.setValue1("value 11");
        freqWordR1.setFrequency(10);
        freqWordR1.setCategory("Cat 1");
        freqWordR1.setTime(2011);

        FrequentWordR freqWordR2 = new FrequentWordR();
        freqWordR2.setValue1("value 2");
        freqWordR1.setValue1("value 22");
        freqWordR2.setFrequency(11);
        freqWordR2.setCategory("Cat 2");
        freqWordR2.setTime(2012);

        FrequentWordR frequentWordArr[] = {freqWordR1,freqWordR2};
        return Response.status(200).entity(frequentWordArr).build();
    }

    @POST
    @Path("/frequentTrigrams")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentTrigrams(FrequentWord frqWord){

        FrequentWordR freqWordR1 = new FrequentWordR();
        freqWordR1.setValue1("value 1");
        freqWordR1.setValue1("value 11");
        freqWordR1.setValue1("value 111");
        freqWordR1.setFrequency(10);
        freqWordR1.setCategory("Cat 1");
        freqWordR1.setTime(2011);

        FrequentWordR freqWordR2 = new FrequentWordR();
        freqWordR2.setValue1("value 2");
        freqWordR1.setValue1("value 22");
        freqWordR1.setValue1("value 222");
        freqWordR2.setFrequency(11);
        freqWordR2.setCategory("Cat 2");
        freqWordR2.setTime(2012);

        FrequentWordR frequentWordArr[] = {freqWordR1,freqWordR2};
        return Response.status(200).entity(frequentWordArr).build();
    }


    @POST
    @Path("/latestArticlesForWord")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response latestArticlesForWord(ArticlesForWord articlesForWord){

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

        ArticleR[] articles = {articleR1,articleR2};

        ArticlesForWordR articlesForWordR1 = new ArticlesForWordR();
        articlesForWordR1.setTime(2012);
        articlesForWordR1.setCategory("Cat 1");
        articlesForWordR1.setArticles(articles);

        ArticlesForWordR articlesForWordR2 = new ArticlesForWordR();
        articlesForWordR2.setTime(2013);
        articlesForWordR2.setCategory("Cat 3");
        articlesForWordR2.setArticles(articles);

        ArticlesForWordR[] arr = {articlesForWordR1,articlesForWordR2};

        return Response.status(200).entity(arr).build();
    }

    @POST
    @Path("/latestArticlesForBigram")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response latestArticlesForBigram(ArticlesForBigram articlesForBigram){

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

        ArticleR[] articles = {articleR1,articleR2};

        ArticlesForWordR articlesForWordR1 = new ArticlesForWordR();
        articlesForWordR1.setTime(2012);
        articlesForWordR1.setCategory("Cat 1");
        articlesForWordR1.setArticles(articles);

        ArticlesForWordR articlesForWordR2 = new ArticlesForWordR();
        articlesForWordR2.setTime(2013);
        articlesForWordR2.setCategory("Cat 3");
        articlesForWordR2.setArticles(articles);

        ArticlesForWordR[] arr = {articlesForWordR1,articlesForWordR2};

        return Response.status(200).entity(arr).build();
    }

    @POST
    @Path("/latestArticlesForTrigram")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response latestArticlesForTrigram(ArticlesForTrigram articlesForTrigram){

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

        ArticleR[] articles = {articleR1,articleR2};

        ArticlesForWordR articlesForWordR1 = new ArticlesForWordR();
        articlesForWordR1.setTime(2012);
        articlesForWordR1.setCategory("Cat 1");
        articlesForWordR1.setArticles(articles);

        ArticlesForWordR articlesForWordR2 = new ArticlesForWordR();
        articlesForWordR2.setTime(2013);
        articlesForWordR2.setCategory("Cat 3");
        articlesForWordR2.setArticles(articles);

        ArticlesForWordR[] arr = {articlesForWordR1,articlesForWordR2};

        return Response.status(200).entity(arr).build();
    }

    //FrequentWordsAroundWord
    @POST
    @Path("/frequentWordsAroundWord")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentWordsAroundWord(FrequentWordsAroundWord frqWords){

        WordR wordR1 = new WordR();
        wordR1.setFrequency(10);
        wordR1.setValue("val 1");

        WordR wordR2 = new WordR();
        wordR2.setFrequency(14);
        wordR2.setValue("val 2");

        WordR words[] ={wordR1,wordR2};

        FrequentWordsAroundWordR frequentWordsAroundWordR1 = new FrequentWordsAroundWordR();
        frequentWordsAroundWordR1.setCategory("cat 1");
        frequentWordsAroundWordR1.setTime(2012);
        frequentWordsAroundWordR1.setWords(words);

        FrequentWordsAroundWordR frequentWordsAroundWordR2 = new FrequentWordsAroundWordR();
        frequentWordsAroundWordR2.setCategory("cat 2");
        frequentWordsAroundWordR2.setTime(2013);
        frequentWordsAroundWordR2.setWords(words);

        FrequentWordsAroundWordR[] arr = {frequentWordsAroundWordR1,frequentWordsAroundWordR2};

        return Response.status(200).entity(arr).build();
    }



    @POST
    @Path("/frequentWordsInPosition")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response frequentWordsInPosition(WordPosition position){

        WordR wordR1 = new WordR();
        wordR1.setFrequency(10);
        wordR1.setValue("val 1");

        WordR wordR2 = new WordR();
        wordR2.setFrequency(14);
        wordR2.setValue("val 2");

        WordR words[] ={wordR1,wordR2};

        WordPositionR wordPositionR1 = new WordPositionR();
        wordPositionR1.setCategory("cat 1");
        wordPositionR1.setTime(2010);
        wordPositionR1.setWords(words);

        WordPositionR wordPositionR2 = new WordPositionR();
        wordPositionR2.setCategory("cat 2");
        wordPositionR2.setTime(2011);
        wordPositionR2.setWords(words);

        WordPositionR[] arr = {wordPositionR1,wordPositionR2};
        OracleClient.getDBConnection();
        System.out.println("Got db Connection");
        return Response.status(200).entity(arr).build();
    }


}

