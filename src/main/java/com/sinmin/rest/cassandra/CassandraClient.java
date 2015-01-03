package com.sinmin.rest.cassandra;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.sinmin.rest.beans.request.FrequentWord;
import com.sinmin.rest.beans.response.ArticleR;
import com.sinmin.rest.beans.response.FrequentWordR;
import com.sinmin.rest.beans.response.WordFrequencyR;
import com.sinmin.rest.beans.response.WordPositionR;
import com.sinmin.rest.beans.response.WordR;

public class CassandraClient {

	private Cluster cluster;
	private Session session;
	

	
	
	public CassandraClient(String node) {
		 this.connect(node);
		}


	public void connect(String node) {
	      cluster = Cluster.builder()
	            .addContactPoint(node).build();
	      Metadata metadata = cluster.getMetadata();
	      System.out.printf("Connected to cluster: %s\n", 
	            metadata.getClusterName());
	      for ( Host host : metadata.getAllHosts() ) {
	         System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
	               host.getDatacenter(), host.getAddress(), host.getRack());
	      }
	      session = cluster.connect();
	   }
	
	
	public WordFrequencyR getWordFrequency(String word,int year, String category){
		PreparedStatement query = session.prepare(
				"select frequency from corpus.word_time_category_frequency WHERE word=? AND year=? AND category=?");
		ResultSet results = session.execute(query.bind(word,year,category));
		WordFrequencyR endObject = new  WordFrequencyR();
		endObject.setCategory(category);
		endObject.setDate(year);
		endObject.setFrequency(0);
		for (Row row : results) {
			System.out.format("%d\n", row.getInt("frequency"));
			endObject.setFrequency(row.getInt("frequency"));
		}
		
		return endObject;
	}
	
	public WordFrequencyR getWordFrequency(String word,int year){
		PreparedStatement query = session.prepare(
				"select frequency from corpus.word_time_frequency WHERE word=? AND year=?");
		ResultSet results = session.execute(query.bind(word,year));
		
		WordFrequencyR endObject = new  WordFrequencyR();
		endObject.setCategory("all");
		endObject.setDate(year);
		endObject.setFrequency(0);
		for (Row row : results) {
			System.out.format("%d\n", row.getInt("frequency"));
			endObject.setFrequency(row.getInt("frequency"));
		}
		
		return endObject;

	}
	
	public WordFrequencyR getWordFrequency(String word, String category){
		PreparedStatement query = session.prepare(
				"select frequency from corpus.word_category_frequency WHERE word=? AND category=?");
		ResultSet results = session.execute(query.bind(word,category));
		WordFrequencyR endObject = new  WordFrequencyR();
		endObject.setCategory(category);
		endObject.setDate(0);
		endObject.setFrequency(0);
		for (Row row : results) {
			System.out.format("%d\n", row.getInt("frequency"));
			endObject.setFrequency(row.getInt("frequency"));
		}
		
		return endObject;
	}
	
	public WordFrequencyR getWordFrequency(String word){
		PreparedStatement query = session.prepare(
				"select frequency from corpus.word_frequency WHERE word=?");
		ResultSet results = session.execute(query.bind(word));
		
		WordFrequencyR endObject = new  WordFrequencyR();
		endObject.setCategory("all");
		endObject.setDate(0);
		endObject.setFrequency(0);
		for (Row row : results) {
			System.out.format("%d\n", row.getInt("frequency"));
			endObject.setFrequency(row.getInt("frequency"));
		}
		
		return endObject;
	}
	
	public WordFrequencyR getBigramFrequency(String word1, String word2,int year, String category){
		System.out.println("getBigramFrequency(String word1, String word2,int year, String category)");
		PreparedStatement query = session.prepare(
				"select frequency from corpus.bigram_time_category_frequency WHERE word1=? AND word2=? AND year=? AND category=?");
		ResultSet results = session.execute(query.bind(word1,word2,year,category));
		
		WordFrequencyR endObject = new  WordFrequencyR();
		endObject.setCategory(category);
		endObject.setDate(year);
		endObject.setFrequency(0);
		for (Row row : results) {
			System.out.format("%d\n", row.getInt("frequency"));
			endObject.setFrequency(row.getInt("frequency"));
		}
		
		return endObject;
	}
	
	public WordFrequencyR getBigramFrequency(String word1, String word2,int year){
		System.out.println("getBigramFrequency(String word1, String word2,int year)");
		PreparedStatement query = session.prepare(
				"select frequency from corpus.bigram_time_frequency WHERE word1=? AND word2=? AND year=?");
		ResultSet results = session.execute(query.bind(word1,word2,year));
		
		WordFrequencyR endObject = new  WordFrequencyR();
		endObject.setCategory("all");
		endObject.setDate(year);
		endObject.setFrequency(0);
		for (Row row : results) {
			System.out.format("%d\n", row.getInt("frequency"));
			endObject.setFrequency(row.getInt("frequency"));
		}
		
		return endObject;
	}
	
	public WordFrequencyR getBigramFrequency(String word1, String word2, String category){
		System.out.println("getBigramFrequency(String word1, String word2,String category)");
		PreparedStatement query = session.prepare(
				"select frequency from corpus.bigram_category_frequency WHERE word1=? AND word2=? AND category=?");
		ResultSet results = session.execute(query.bind(word1,word2,category));
		
		WordFrequencyR endObject = new  WordFrequencyR();
		endObject.setCategory(category);
		endObject.setDate(0);
		endObject.setFrequency(0);
		for (Row row : results) {
			System.out.format("%d\n", row.getInt("frequency"));
			endObject.setFrequency(row.getInt("frequency"));
		}
		
		return endObject;
	}
	
	public WordFrequencyR getBigramFrequency(String word1, String word2){
		System.out.println("getBigramFrequency(String word1, String word2)");
		PreparedStatement query = session.prepare(
				"select frequency from corpus.bigram_category_frequency WHERE word1=? AND word2=?");
		ResultSet results = session.execute(query.bind(word1,word2));
		
		WordFrequencyR endObject = new  WordFrequencyR();
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
	
	public WordFrequencyR getTrigramFrequency(String word1, String word2,String word3,int year, String category){
		System.out.println("getTrigramFrequency(String word1, String word2,String word3,int year, String category)");
		PreparedStatement query = session.prepare(
				"select frequency from corpus.trigram_time_category_frequency WHERE word1=? AND word2=? AND word3=? AND year=? AND category=?");
		ResultSet results = session.execute(query.bind(word1,word2,word3,year,category));
		
		WordFrequencyR endObject = new  WordFrequencyR();
		endObject.setCategory(category);
		endObject.setDate(year);
		endObject.setFrequency(0);
		for (Row row : results) {
			System.out.format("%d\n", row.getInt("frequency"));
			endObject.setFrequency(row.getInt("frequency"));
		}
		
		return endObject;
	}
	
	public WordFrequencyR getTrigramFrequency(String word1, String word2, String word3,int year){
		System.out.println("getTrigramFrequency(String word1, String word2,String word3,int year)");
		PreparedStatement query = session.prepare(
				"select frequency from corpus.trigram_time_frequency WHERE word1=? AND word2=? AND word3=? AND year=?");
		ResultSet results = session.execute(query.bind(word1,word2,word3,year));
		
		WordFrequencyR endObject = new  WordFrequencyR();
		endObject.setCategory("all");
		endObject.setDate(year);
		endObject.setFrequency(0);
		for (Row row : results) {
			System.out.format("%d\n", row.getInt("frequency"));
			endObject.setFrequency(row.getInt("frequency"));
		}
		
		return endObject;
	}
	
	public WordFrequencyR getTrigramFrequency(String word1, String word2, String word3, String category){
		System.out.println("getTrigramFrequency(String word1, String word2,String word3,String category)");
		PreparedStatement query = session.prepare(
				"select frequency from corpus.trigram_category_frequency WHERE word1=? AND word2=? AND word3=? AND category=?");
		ResultSet results = session.execute(query.bind(word1,word2,word3,category));
		
		WordFrequencyR endObject = new  WordFrequencyR();
		endObject.setCategory(category);
		endObject.setDate(0);
		endObject.setFrequency(0);
		for (Row row : results) {
			System.out.format("%d\n", row.getInt("frequency"));
			endObject.setFrequency(row.getInt("frequency"));
		}
		
		return endObject;
	}
	
	public WordFrequencyR getTrigramFrequency(String word1, String word2, String word3){
		System.out.println("getTrigramFrequency(String word1, String word2,String word3)");
		PreparedStatement query = session.prepare(
				"select frequency from corpus.trigram_category_frequency WHERE word1=? AND word2=? AND word3=?");
		ResultSet results = session.execute(query.bind(word1,word2,word3));
		
		WordFrequencyR endObject = new  WordFrequencyR();
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
	
	public FrequentWordR getFrequentWords(int year, String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_time_category_ordered_frequency WHERE year=? and category=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(year,category,amount));
		
		WordR[] val1 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
		int i=0;
		
		for (Row row : results) {
			val1[i]= new WordR();
			val1[i].setValue(row.getString("word"));
			val1[i].setFrequency(row.getInt("frequency"));
			System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
			i++;
			if(i==amount){
				break;
			}
		}

        resp.setCategory(category);
        resp.setTime(year);
        resp.setValue1(val1);

		return resp;
	}
	
	public FrequentWordR getFrequentWords(int year,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_time_ordered_frequency WHERE year=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(year,amount));

        WordR[] val1 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i=0;

        for (Row row : results) {
            val1[i]= new WordR();
            val1[i].setValue(row.getString("word"));
            val1[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
            i++;
            if(i==amount){
                break;
            }
        }

        resp.setCategory("all");
        resp.setTime(year);
        resp.setValue1(val1);

        return resp;
	}
	
	public FrequentWordR getFrequentWords(String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_category_ordered_frequency WHERE category=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(category,amount));

        WordR[] val1 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i=0;

        for (Row row : results) {
            val1[i]= new WordR();
            val1[i].setValue(row.getString("word"));
            val1[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
            i++;
            if(i==amount){
                break;
            }
        }

        resp.setCategory(category);
        resp.setTime(0);
        resp.setValue1(val1);

        return resp;
	}
	
	public FrequentWordR getFrequentWords(int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_category_ordered_frequency WHERE category IN (?,?,?,?,?) order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind("N","C","A","S","G",amount));

        WordR[] val1 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i=0;

        for (Row row : results) {
            val1[i]= new WordR();
            val1[i].setValue(row.getString("word"));
            val1[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
            i++;
            if(i==amount){
                break;
            }
        }

        resp.setCategory("all");
        resp.setTime(0);
        resp.setValue1(val1);

        return resp;
	}
	
	//////////////////////////////////////////////////////////////////////////////
	
	public FrequentWordR getFrequentBigrams(int year, String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.bigram_time_category_ordered_frequency WHERE year=? and category=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(year,category,amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
		FrequentWordR resp = new FrequentWordR();
		int i=0;
		
		for (Row row : results) {
			val1[i]= new WordR();
            val2[i] = new WordR();
			val1[i].setValue(row.getString("word1"));
			val2[i].setValue(row.getString("word2"));
			val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
			System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
			i++;
			if(i==amount){
				break;
			}
		}
		resp.setCategory(category);
        resp.setTime(year);
        resp.setValue1(val1);
        resp.setValue2(val2);
		return resp;
	}
	
	public FrequentWordR getFrequentBigrams(int year,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.bigram_time_ordered_frequency WHERE year=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(year,amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i=0;

        for (Row row : results) {
            val1[i]= new WordR();
            val2[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
            i++;
            if(i==amount){
                break;
            }
        }
        resp.setCategory("all");
        resp.setTime(year);
        resp.setValue1(val1);
        resp.setValue2(val2);
        return resp;
	}
	
	public FrequentWordR getFrequentBigrams(String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.bigram_category_ordered_frequency WHERE category=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(category,amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i=0;

        for (Row row : results) {
            val1[i]= new WordR();
            val2[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
            i++;
            if(i==amount){
                break;
            }
        }
        resp.setCategory(category);
        resp.setTime(0);
        resp.setValue1(val1);
        resp.setValue2(val2);
        return resp;
	}
	
	public FrequentWordR getFrequentBigrams(int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.bigram_category_ordered_frequency WHERE category IN (?,?,?,?,?) order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind("N","C","A","S","G",amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i=0;

        for (Row row : results) {
            val1[i]= new WordR();
            val2[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
            i++;
            if(i==amount){
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
	
	public FrequentWordR getFrequentTrigrams(int year, String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.trigram_time_category_ordered_frequency WHERE year=? and category=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(year,category,amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i=0;

        for (Row row : results) {
            val1[i]= new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val3[i].setValue(row.getString("word3"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            val3[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
            i++;
            if(i==amount){
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
	
	public FrequentWordR getFrequentTrigrams(int year,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.trigram_time_ordered_frequency WHERE year=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(year,amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i=0;

        for (Row row : results) {
            val1[i]= new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val3[i].setValue(row.getString("word3"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            val3[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
            i++;
            if(i==amount){
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
	
	public FrequentWordR getFrequentTrigrams(String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.trigram_category_ordered_frequency WHERE category=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(category,amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i=0;

        for (Row row : results) {
            val1[i]= new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val3[i].setValue(row.getString("word3"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            val3[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
            i++;
            if(i==amount){
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
	
	public FrequentWordR getFrequentTrigrams(int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.trigram_category_ordered_frequency WHERE category IN (?,?,?,?,?) order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind("N","C","A","S","G",amount));

        WordR[] val1 = new WordR[amount];
        WordR[] val2 = new WordR[amount];
        WordR[] val3 = new WordR[amount];
        FrequentWordR resp = new FrequentWordR();
        int i=0;

        for (Row row : results) {
            val1[i]= new WordR();
            val2[i] = new WordR();
            val3[i] = new WordR();
            val1[i].setValue(row.getString("word1"));
            val2[i].setValue(row.getString("word2"));
            val3[i].setValue(row.getString("word3"));
            val1[i].setFrequency(row.getInt("frequency"));
            val2[i].setFrequency(row.getInt("frequency"));
            val3[i].setFrequency(row.getInt("frequency"));
            System.out.format("%s %d\n", row.getString("word"),row.getInt("frequency"));
            i++;
            if(i==amount){
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
	
	@SuppressWarnings("deprecation")
	public ArticleR[] getLatestArticlesForWord(String word,int year, String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_year_category_usage WHERE word=? AND year=? AND category =? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word,year,category,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory(category);
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	public ArticleR[] getLatestArticlesForWord(String word,int year,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_year_usage WHERE word=? AND year=? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word,year,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory("all");
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	public ArticleR[] getLatestArticlesForWord(String word,String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_category_usage WHERE word=? AND category =? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word,category,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory(category);
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	public ArticleR[] getLatestArticlesForWord(String word,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_usage WHERE word=? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory("all");
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	public ArticleR[] getLatestArticlesForBigram(String word1, String word2,int year, String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.bigram_year_category_usage WHERE word1=? AND word2=? AND year=? AND category =? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word1,word2,year,category,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory(category);
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	public ArticleR[] getLatestArticlesForBigram(String word1, String word2,int year,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.bigram_year_usage WHERE word1=? AND word2=? AND year=? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word1,word2,year,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory("all");
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	public ArticleR[] getLatestArticlesForBigram(String word1,String word2,String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.bigram_category_usage WHERE word1=? AND word2=? AND category =? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word1,word2,category,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory(category);
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	public ArticleR[] getLatestArticlesForBigram(String word1,String word2,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.bigram_usage WHERE word1=? AND word2=? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word1,word2,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory("all");
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	
	public ArticleR[] getLatestArticlesForTrigram(String word1, String word2,String word3, int year, String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.trigram_year_category_usage WHERE word1=? AND word2=? AND word3=? AND year=? AND category =? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word1,word2,word3,year,category,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory(category);
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	public ArticleR[] getLatestArticlesForTrigram(String word1, String word2,String word3,int year,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.trigram_year_usage WHERE word1=? AND word2=? AND word3=? AND year=? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word1,word2,word3,year,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory("all");
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	public ArticleR[] getLatestArticlesForTrigram(String word1,String word2,String word3,String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.trigram_category_usage WHERE word1=? AND word2=? AND word3=? AND category =? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word1,word2,word3,category,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory(category);
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	public ArticleR[] getLatestArticlesForTrigram(String word1,String word2, String word3,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.trigram_usage WHERE word1=? AND word2=? AND word3=? order by date DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(word1,word2,word3,amount));
		
		ArticleR[] array = new ArticleR[amount];
		int i=0;
		Date datetime;
		for (Row row : results) {
			array[i]= new ArticleR();
			array[i].setTitle(row.getString("postname"));
			array[i].setAuthor(row.getString("author"));
			array[i].setLink(row.getString("url"));
			datetime=row.getDate("date");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			//System.out.println(cal);
			array[i].setSentence(row.getString("sentence"));
			array[i].setYear(cal.get(Calendar.YEAR));
			array[i].setMonth(cal.get(Calendar.MONTH)+1);
			array[i].setDay(cal.get(Calendar.DAY_OF_MONTH));
			array[i].setCategory("all");
			i++;
			if(i==amount){
				break;
			}
		}
		
		return array;
	}
	
	//////////////////////////////////////////////////////////////////
	
	public WordPositionR getFrequentWordsInPosition(int position,int year,String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_pos_year_category_frequency WHERE position=? AND year=? AND category=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(position,year, category,amount));
		WordPositionR out = new WordPositionR();
		out.setCategory(category);
		out.setTime(year);
		int i=0;
		WordR[] words = new WordR[amount];
		for (Row row : results) {
			words[i] = new WordR();
			words[i].setValue(row.getString("word"));
			words[i].setFrequency(row.getInt("frequency"));
			i++;
			if(i==amount){
				break;
			}
		}
		out.setWords(words);
		return out;
	}
	
	public WordPositionR getFrequentWordsInPositionReverse(int position,int year,String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_inv_pos_year_category_frequency WHERE inv_position=? AND year=? AND category=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(position,year, category,amount));
		
		WordPositionR out = new WordPositionR();
		out.setCategory(category);
		out.setTime(year);
		int i=0;
		WordR[] words = new WordR[amount];
		for (Row row : results) {
			words[i] = new WordR();
			words[i].setValue(row.getString("word"));
			words[i].setFrequency(row.getInt("frequency"));
			i++;
			if(i==amount){
				break;
			}
		}
		out.setWords(words);
		return out;
	}
	
	//----------------
	
	public WordPositionR getFrequentWordsInPosition(int position,int year,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_pos_year_frequency WHERE position=? AND year=?  order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(position,year, amount));
		
		WordPositionR out = new WordPositionR();
		out.setCategory("all");
		out.setTime(year);
		int i=0;
		WordR[] words = new WordR[amount];
		for (Row row : results) {
			words[i] = new WordR();
			words[i].setValue(row.getString("word"));
			words[i].setFrequency(row.getInt("frequency"));
			i++;
			if(i==amount){
				break;
			}
		}
		out.setWords(words);
		return out;
	}
	
	public WordPositionR getFrequentWordsInPositionReverse(int position,int year,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_inv_pos_year_frequency WHERE inv_position=? AND year=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(position,year, amount));
		
		WordPositionR out = new WordPositionR();
		out.setCategory("all");
		out.setTime(year);
		int i=0;
		WordR[] words = new WordR[amount];
		for (Row row : results) {
			words[i] = new WordR();
			words[i].setValue(row.getString("word"));
			words[i].setFrequency(row.getInt("frequency"));
			i++;
			if(i==amount){
				break;
			}
		}
		out.setWords(words);
		return out;
	}
	
	public WordPositionR getFrequentWordsInPosition(int position,String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_pos_category_frequency WHERE position=? AND category=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(position, category,amount));
		
		WordPositionR out = new WordPositionR();
		out.setCategory(category);
		out.setTime(0);
		int i=0;
		WordR[] words = new WordR[amount];
		for (Row row : results) {
			words[i] = new WordR();
			words[i].setValue(row.getString("word"));
			words[i].setFrequency(row.getInt("frequency"));
			i++;
			if(i==amount){
				break;
			}
		}
		out.setWords(words);
		return out;
	}
	
	public WordPositionR getFrequentWordsInPositionReverse(int position,String category,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_inv_pos_category_frequency WHERE inv_position=? AND category=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(position,category,amount));
		
		WordPositionR out = new WordPositionR();
		out.setCategory(category);
		out.setTime(0);
		int i=0;
		WordR[] words = new WordR[amount];
		for (Row row : results) {
			words[i] = new WordR();
			words[i].setValue(row.getString("word"));
			words[i].setFrequency(row.getInt("frequency"));
			i++;
			if(i==amount){
				break;
			}
		}
		out.setWords(words);
		return out;
	}
	
	//----------------
	
	public WordPositionR getFrequentWordsInPosition(int position,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_pos_frequency WHERE position=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(position, amount));
		
		WordPositionR out = new WordPositionR();
		out.setCategory("all");
		out.setTime(0);
		int i=0;
		WordR[] words = new WordR[amount];
		for (Row row : results) {
			words[i] = new WordR();
			words[i].setValue(row.getString("word"));
			words[i].setFrequency(row.getInt("frequency"));
			i++;
			if(i==amount){
				break;
			}
		}
		out.setWords(words);
		return out;
	}
	
	public WordPositionR getFrequentWordsInPositionReverse(int position,int amount){
		PreparedStatement query = session.prepare(
				"select * from corpus.word_inv_pos_frequency WHERE inv_position=? order by frequency DESC LIMIT ?");
		ResultSet results = session.execute(query.bind(position,amount));
		
		WordPositionR out = new WordPositionR();
		out.setCategory("all");
		out.setTime(0);
		int i=0;
		WordR[] words = new WordR[amount];
		for (Row row : results) {
			words[i] = new WordR();
			words[i].setValue(row.getString("word"));
			words[i].setFrequency(row.getInt("frequency"));
			i++;
			if(i==amount){
				break;
			}
		}
		out.setWords(words);
		return out;
	}
	
	public static void main(String[] args){
		
		
	}
	
}