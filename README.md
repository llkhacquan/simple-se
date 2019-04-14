# simple search-engine

#problem
Given some products (50k strings); implement a simple searcher using inverted index; BM25
#terminology
- *Document*: holds information about one object which can be searched later.
    - Object here can be a web-page, a product, a movie, etc; depends on the type of search-engine  
- *Term*: atomic unit of a *document*. *Term*s are actually words in a *document*s  
    - A *document* can contains many *term*s many times
    - A *term* can appear in many *document*
- *Dictionary*: a collection of all terms which appear in any document
- Query: holds information about input string for searching
    - In this product, 
#implementation
Big components:
- QueryParser: processing input raw-query to the formal *Query*
    - the query parser does some stuffs: normalization, tokenization , etc 
- Document Storage: holds all documents
- Indexer: holds internal data
    - inverted map: *term* => collection of *document*s containing the *term*
    - note that (for memory-saving) we don't actually hold the above map but we hold the map *termId* => collection of id of *document*s containing the *term*
- Ranker: compute relevant between a given *query* and a given *document*
    - here we can implement BM25 or some other similarity functions
- Searcher: combine *Indexer* and *Ranker* to search for a given *query*
    - use *indexer* to get collections of *document*s which contains any *term* from *query*
    - then use *ranker* to select the most relevant *document*s
    - render search-engine-result-page which are not included in this project
#build code
from termimal: `./gradlew build copyLib`
#run and search
- start simple-search-server `bash bin/start-server.sh <data_file> [optinal_port]`
    - example: `bash bin/start-server.sh product_names.txt 5050`
    - `5050` is the default port and can be removed
- search by simple-search-server `http://localhost:5050/search?algorithm=bm25&q=the-query-here` 
    - example: search for "cha giau cha ngheo" with bm25: `http://localhost:5050/search?algorithm=bm25&q=cha%20giau%20cha%20ngheo`
    - example: search for "usb" with inverted-index only: `http://localhost:5050/search?q=usb`
    - note that simple-search-server returns at most 10 *document*s (*product*)
- You can search for multiple queries using `quannk.test.se.search.Runner`
    
#note
- The code is implemented such that the searcher can be improved and upgraded in many way. 
Many abstract stuffs which can be replaced by very simple solution were placed in the project.
- There are a lot of duplications in the given *product_names.txt*. However, the duplications were ignored. It might be the same book from different stores.
- Code is tested under ubuntu 18.04; jdk-8
- **enhance_accent** is a branch which support term with accent; btw, it's WIP  