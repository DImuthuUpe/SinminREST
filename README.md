<H5>Installation </H5>

* clone and build https://github.com/lasandun/corpus.sinhala.wildcard.search

* Install Oracle jar to local maven repository

	mvn install:install-file -Dfile=ojdbc7.jar -DgroupId=ojdbc -DartifactId=ojdbc -Dversion=7 -Dpackaging=jar

	ojdbc7.jar can be found from lib folder

* Build project using mvn clean install


<H5> API Functions </H5>



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

        Req :
        {
            "value":"Value1",
            "time":["2011"],
            "category":["Cat1"]
        }

        Resp :
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

        Req :
        {
            "value1":"Value1",
            "value2":"Value2",
            "time":["84"],
            "category":["Cat1"]
        }

        Resp :
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

        Req :
        {
            "value1":"Value1",
            "value2":"Value2",
            "value3":"Value3",
            "time":["84"],
            "category":["Cat1"]
        }

        Resp :
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

        Req :
        {
            amount : 10,
            category: ["cat1"],
            time : [2011]

        }

        Resp :
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
        
        Req :
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
        
        Req:
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
        
        Req:
        {
            "position" : 10,
            "time" : [2010],
            "category" : ["cat1"],
            "amount" : 10
        }

        Resp:
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
