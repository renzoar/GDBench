Graph Data Benchmark (GDBench)
=======

A micro-benchmark for benchmarking graph databases based on a social network data model.   

Description.
GDBench is a microbenchmark oriented to evaluate the performance of graph database systems based on social network applications. The benchmark includes a generator that synthetically creates graphs resembling real world social networks, and define a set of representative graph and use-case oriented queries, such as getting the friends of a friend, looking for similar like pages, or finding shortest paths between people.
Structure of the benchmark
Data model.
GDBench is based on the data model described in Figure 1. The data model considers two types of entities, person and Webpage. Persons are linked to other persons by a "friend" relationship, indicating that those two persons are friends. On the other side, persons are linked to Webpages by a "like" relationship indicating that a person likes a Webpage. The attributes of a person are his/her identifier (pid), his/her name, and two optional fields, the age and the location. Finally, a Webpage has an attribute wpid as identifier, its URL, and optionally a creation date.




Figure 1. Data model of GDBench

Queries.
A micro-benchmark is used to evaluate the individual performances of basic operations (such as joins and aggregations in relational databases), rather than more complex queries. In the context of graph databases we find several queries which can be considered essential in graphs. We group them in adjacency, reachability, pattern matching and summarization queries. Also, we add select queries which are also relevant in the context of graph databases.
Based on a brief analysis of the user interaction with a social network system (Facebook), and taking into account the essential graph queries defined above, we defined a representative query mix in the following Table. In spite of the apparent similarity among some of the selected queries, they present simple variations that may be implemented differently, and hence optimized, by the database systems.
Q
Description
Type
1
People having a name N
Select
2
The name of people that likes a given Web page W
Adjacency
3
The URL of Web pages that person P likes
Adjacency
4
The name of the person with a given PID
Select
5
The name of friends of the friends of a given person P
Reachability
6
The URL of Web pages liked by the friends of a given person P
Reachability
7
Get the name of people that likes a Web page which a person P likes
Reachability
8
Is there a connection (path) between people P1 and P2?
Reachability
9
Get the shortest path between people P1 and P2
Reachability
10
Get the name of common friends between people P1 and P2
Pattern matching
11
Get the URL of common Web pages that people P1 and P2 like
Pattern matching
12
Get the name and the number of friends of a person
Summarization
13
The friends of the friends of the friends a given person P
Reachability
Performance metrics.
In the literature of benchmarking databases systems several performance metrics are considered: throughput, which is the rate at which operations are complete (e.g., transactions per second); response time of the operation, which is the elapsed time for the execution of the operation; and cost metric, which normalizes the price of a system with respect to the throughput. We concentrate our interest on measuring the response time for load and query operations.
	•	Data loading Time. It refers to the time that a database system needs to load the data from the source file. This metric includes any time spend by the system to built index structures and statistical data. The loading time is measured in milliseconds.
	•	Query execution time. This is the central performance metric of the benchmark. It refers to the time spend by a database system to execute a single query. The execution time of a query type Q is given by the average time of executing several instances of Q. The execution time of a query instance is measured in microseconds. In order to obtain measures similar to those of a working server, the benchmark executes a warm-up run before executing the timed query. The warm-up executes all the query instances once before the timed execution to populate the database cache system.
Graph data and test data generation.
We have developed a java application which allows the generation of syntetic graph data following the social network structure defined above. First we developed a general-purpose graph data generator based on the Recursive Matrix (R-Mat) model, but optimized to generate large graphs in a streamed fashion (i.e., reducing memory restrictions).
To produce synthetic social-network data we take as reference the information published by current social networks applications, in particular Facebook, The number of users in Facebook is significantly larger than the number of webpages. To simulate this, we set 80% of the nodes as persons and 20% as webpages. The identifier of a person node (i.e., the attribute pid) is an integer value in the range [1, N × 0.8], where N is the number of nodes in the graph. The names of persons and locations are selected randomly from dictionaries including 5494 first names, 88799 last names, and 656 locations. Hence, the probability of having duplicated pairs of these two attributes depends on the size of the graph and the distributions used. The occurrence of attributes age and location follows probabilities 0.6 and 0.3 respectively. The age of a person is a random integer between 10 and 73. The identifier of a node webpage (i.e., the attribute wpid) is an integer in the range [(N × 0.8) + 1, N ]. The attribute URL follows the pattern http://www.site.org/webpageID.html where ID is the wpid of the webpage. The probability of including a random creation date for a webpage is 0.6.
The generator supports two types of distributions for edges: normal and power law. The user is able to select an specific conbination of distribution for the two types of edges (e.g. normal and power law for friend and like respectively).
Social network data generator
The data generator is available as the java application GDGenerator. The output of the data generator is a data file and a test data file.
The data file, named sndata.*, contains the graph data in an specific format. The data format used by default is a non-standard compressed format used called graphdata (*.gd).
The current version of the data generator allows to specify four parameters:
	•	the number of nodes in the graph (by default, 50% of the nodes are people and 50% webpages);
	•	the type of edge distribution (1: power law for both friend and like -Default-, 2: power law / normal for friend / like, 3: normal / power law for friend / like, and 4: normal for both friend and like); and
	•	the format of the output data file (gd = graph data -default-, gml = Graphml and n3 = Notation3-RDF).
The GDBenchmark Java Library.
This library allows the execution of data loading and query tests. The source code of the java library is available here
The main class of GDBenchmark is TestDriver.java. This is an abstract class that defines an interface to operate with a database system. It includes methods for database definition (createDB, openDB, closeDB), data loading (openTransaction, closeTransaction, insertPerson, insertWebpage, insertFriend, insertLike) and query execution (Q1, ..., Q13).
How to evaluate a database system with GDBench
The procedure to evaluate the performance of a database system, by using the benchmark described above, is defined by the following steps.
Data generation.
The first step is the generation of the data by using the graph data generator GDGenerator. It results in the data file sndata.gd and the test data file testdata.xml.
Test driver implementation.
It consists in the development of a java class that implements the class TestDriver with the code corresponding to the target database system. We provide the class MyTestDriver.java as an example of such class.
Data loading test.
The benchmark allows transactional or bulk data loading.
Transactional data loading is supported by calling the method runDataLoading() provided by the class MyTestDriver (and implemented in the class TestDriver) . The application reads the data file sndata.gd and use transactions to insert the data in the target database, according to the data loading methods defined in the class MyTestDriver (recall that these methods must be implemented by the user).
Bulk data loading depends on the support of a database system to read specific data formats. For example, RDF databases are able to read N3 and RDF-XML files. Unfortunately, current graph database systems define different and non-standard data formats, therefore different data loaders must be developed (GraphML is not useful here). The benchmark includes a tool (GDExporter) to convert a compressed data file into different data formats. In the context of this work, we have implemented the conversion to a PostgreSQL dump encoding SQL instructions, and we plan to extend this tool to support other data formats.
Query execution test.
The query execution requires GDBenchmark Java library. During test execution proceeds to the generation of input test data. The test data contain data to be used in the creation of consultative instances, in addition, they can be random, selection of intervals and selection of median data, this should be established by the user in the config file. config.conf. For example, if the benchmark contains 10,000 instances of Q1, then the test data contain 10,000 names. Test data are grouped as follows: IDs of people (used for Q3, Q4, Q5, Q6, Q7, Q12, Q13 consultations); personal names (used in the query Q1); Web IDs (used in query Q2); pairs of IDs to see if those people are connected by a relationship 'friend' (used in query Q10); pairs of IDs to see if those people are connected by a relationship 'like' between the person and the website; and pairs of IDs to see if those people there is a path through a relationship 'friend' between the persons (used in Q8 and Q9 consultations); The selection of the test data is performed during execution of the warmup of database, once generated the data it's proceeds to load it. The test considers the creation and execution of 100 instances (default) for each query type, this parameter can also be modified by the user, in order to generate a greater number of test data. The user is enabled to run the tests in a single query, or execute all queries. The user can select between loading data or testing. The user can set the number of times each query was executed instance of these, will report the best time, worst time and the average time. Additionally it is possible to execute query instances sequentially or in random order.
The class MyTestDriver provides the following methods to execute the query test:
	•	runQueryTest()  It allows the execution of 100 instances (default) for each query type.
	•	runQueryTest(int instances_for_query, String querymix_type, int repetition, int queryMixType)  It allows to specify the number of instances for query type and the type of query mix (either sequential or random) Also allows you to set the number of executions for each instance and the type of test data input.
	•	runQueryTestByQuery(int query_number, String querymix_type, int repetition, int queryMixType):  it allows to specifies the query to be executed and the number of instances to query and set the sort order of execution (sequential or random). Also allows you to set the number of executions for each instance and the type of test data input.
The execution of the query test consider two steps: the first step is oriented to prepare the database system and the test data (warm-up) ; and the second is devoted to measure the performance of the system. In both cases the same query mix is executed.
Execution rules
The implementation of a test driver must hold the following rules:
	•	The user is able to choose between transactional or bulk data loading.
	•	The queries must be implemented in the corresponding methods defined by the TestDriver java class.
	•	The number of results for a query must be calculated by traversing the result-set. This restriction does not apply for query Q12 where an aggregate operator can be used.
Benchmarking results.
The benchmark provides reports for the transactional loading test and the query execution test. The data loading report includes information about the data loaded (e.g., number of nodes and edges) and the total loading time. The query execution report contains general and detailed information about the queries executed. A first part of the report presents, for each query type, the number of instances executed plus the total, maximum, minimal, average execution time, variance and standard deviation. A second part shows the list of query instances executed during the test, including the query type, the input data, the output data, and the execution time for query instance.
Implementation samples
An example of Netbeans project that uses the GDBenchmark java library is available here.
We provide the source code of java projects implementing test drivers for Dex and Neo4j.
Acknowledgements
Creator: Renzo Angles
Collaborators:
	•	Sebastián Arancibia (DCC, Univ. de Talca): Design and development of initial versions the data generator.
	•	Roberto García (DCC, Univ. de Talca): Design and development of second version of the data generator and benchmark.
	•	Sergio Silva (DCC, Univ. de Talca): Implementations of test drivers for graph database systems and RDF Stores.
	•	Josep-Lluis Larriba-Pey (DAMA-UPC): Design of the bechmark
	•	David Dominguez (Sparsity Technologies): Design of the bechmark
	•	Arnau Prat (DAMA-UPC: Implementation of the data generator and the test drivers.
Support:
	•	Comparison of Graph Databases and RDF Databases. Initiation into Research, Project No 1070348, Fondecyt, Chile. 2011-2013.
	•	Linked Data Benchmark Council Project (LDBC). European Community's Seventh Framework Programme FP7/2007-2013.
Last update: September 23, 2014


