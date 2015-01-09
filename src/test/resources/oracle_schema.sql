CREATE TABLE "ARTICLE"(
    "ID" NUMBER NOT NULL,
    "TOPIC" VARCHAR2(2000),
    "AUTHOR" VARCHAR2(2000),
    "CATEGORY" VARCHAR2(200),
    "SUBCAT1" VARCHAR2(200),
    "YEAR" NUMBER,
    "MONTH" NUMBER,
    "DAY" NUMBER,
    CONSTRAINT "ARTICLE_PK" PRIMARY KEY ("ID")
    );

CREATE TABLE "BIGRAM"(
    "ID" NUMBER NOT NULL,
	"WORD1" NUMBER,
	"WORD2" NUMBER,
	"FREQUENCY" NUMBER,
    CONSTRAINT "BIGRAM_PK" PRIMARY KEY ("ID")
    );

CREATE TABLE "SENTENCE" (
    "ID" NUMBER NOT NULL,
	"WORDS" NUMBER,
	"ARTICLE_ID" NUMBER,
	"POSITION" NUMBER,
	CONSTRAINT "SENTENCE_PK" PRIMARY KEY ("ID")
	);

CREATE TABLE "SENTENCE_BIGRAM"(
    "SENTENCE_ID" NUMBER NOT NULL,
    "BIGRAM_ID" NUMBER NOT NULL,
	"POSITION" NUMBER NOT NULL,
	CONSTRAINT "SENTENCE_BIGRAM_PK" PRIMARY KEY ("SENTENCE_ID", "BIGRAM_ID", "POSITION")
	);

CREATE TABLE "SENTENCE_TRIGRAM"(
    "SENTENCE_ID" NUMBER NOT NULL,
	"TRIGRAM_ID" NUMBER NOT NULL,
	"POSITION" NUMBER NOT NULL,
	 CONSTRAINT "SENTENCE_TRIGRAM_PK" PRIMARY KEY ("SENTENCE_ID", "TRIGRAM_ID", "POSITION")
	 );

CREATE TABLE "SENTENCE_WORD"(
    "SENTENCE_ID" NUMBER NOT NULL,
	"WORD_ID" NUMBER NOT NULL,
	"POSITION" NUMBER NOT NULL,
	 CONSTRAINT "SENTENCE_WORD_PK" PRIMARY KEY ("SENTENCE_ID", "WORD_ID", "POSITION")
	 );

CREATE TABLE "TRIGRAM" (
    "ID" NUMBER NOT NULL,
	"WORD1" NUMBER NOT NULL,
	"WORD2" NUMBER NOT NULL,
	"WORD3" NUMBER NOT NULL,
	"FREQUENCY" NUMBER,
	 CONSTRAINT "TRIGRAM_PK" PRIMARY KEY ("ID")
	 );

CREATE TABLE "WORD" (
    "ID" NUMBER,
	"VAL" NCHAR(100),
	"FREQUENCY" NUMBER,
	 PRIMARY KEY ("ID")
	 );