<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>

<h1>Graph Data Benchmark (GDBench)</h1>

<p>
A micro-benchmark for benchmarking graph databases based on a social network data model.   
</p>

<h2>Introduction</h2>

<p>
GDBench is a microbenchmark oriented to evaluate the performance of  
graph database systems based on social network applications. 
The benchmark includes a generator that synthetically creates graphs resembling real world
social networks, and define a set of representative graph and use-case oriented queries, 
such as getting the friends of a friend, 
looking for similar like pages, or finding shortest paths between people. 
</p>


<h2>Structure of the benchmark</h2>

<p><b>Data model.</b></p>

<p>
GDBench is based on the data model described in Figure 1.
The data model considers two types of entities, person and Webpage. 
Persons are linked to other persons by a "friend" relationship, indicating that those 
two persons are friends. 
On the other side, persons are linked to Webpages by a "like" relationship 
indicating that a person likes a Webpage. 
The attributes of a person are 
his/her identifier (pid), 
his/her name, and two optional fields, the age and the location. 
Finally, a Webpage has an attribute wpid as identifier, its URL, 
and optionally a creation date.
</p>

<figure>
<img src="./data_schema.jpg" border="1">
<figcaption>Figure 1. Data model of GDBench</figcaption>
</figure>

<p><b>Queries.</b>

</p><p>A micro-benchmark is used to evaluate the individual performances of basic operations 
(such as joins and aggregations in relational databases), rather than more complex queries. 
In the context of graph databases we find several queries which can be considered 
essential in graphs. 
We group them in adjacency, reachability, pattern matching and summarization queries. 
Also, we add select queries which are also relevant in the context of graph databases.</p>

<p>Based on a brief analysis of the user interaction with a social network system (Facebook), 
and taking into account the essential graph queries defined above, 
we defined a representative query mix in the following Table. 
In spite of the apparent similarity among some of the selected queries, 
they present simple variations that may be implemented differently, and hence
optimized, by the database systems.</p> 

<table border="1">
<tbody>
<tr>
<td>Q</td>
<td>Description</td>
<td>Type</td>
</tr>
<tr>
<td>1</td>
<td>People having a name N</td>
<td>Select</td>
</tr>
<tr>
<td>2</td>
<td>The name of people that likes a given Web page W</td>
<td>Adjacency</td>
</tr>
<tr>
<td>3</td>
<td>The URL of Web pages that person P likes</td>
<td>Adjacency</td>
</tr>
<tr>
<td>4</td>
<td>The name of the person with a given PID</td>
<td>Select</td>
</tr>
<tr>
<td>5</td>
<td>The name of friends of the friends of a given person P</td>
<td>Reachability</td>
</tr>
<tr>
<td>6</td>
<td>The URL of Web pages liked by the friends of a given person P</td>
<td>Reachability</td>
</tr>
<tr>
<td>7</td>
<td>Get the name of people that likes a Web page which a person P likes</td>
<td>Reachability</td>
</tr>
<tr>
<td>8</td>
<td>Is there a connection (path) between people P1 and P2?</td>
<td>Reachability</td>
</tr>
<tr>
<td>9</td>
<td>Get the shortest path between people P1 and P2</td>
<td>Reachability</td>
</tr>
<tr>
<td>10</td>
<td>Get the name of common friends between people P1 and P2</td>
<td>Pattern matching</td>
</tr>
<tr>
<td>11</td>
<td>Get the URL of common Web pages that people P1 and P2 like</td>
<td>Pattern matching</td>
</tr>
<tr>
<td>12</td>
<td>Get the name and the number of friends of a person</td>
<td>Summarization</td>
</tr>
<tr>
<td>13</td>
<td>The friends of the friends of the friends a given person P</td>
<td>Reachability</td>
</tr>
</tbody>
</table>



<p><b>Performance metrics.</b></p>
<p>
In the literature of benchmarking databases systems several performance metrics 
are considered: 
throughput, which is the rate at which operations are complete (e.g., transactions per second); 
response time of the operation, which is the elapsed time for the execution of the operation; 
and cost metric, which normalizes the price of a system with respect to the throughput. 
We concentrate our interest on measuring the response time for load and query operations.
</p>

<ul>
<li><b>Data loading Time.</b> 
It refers to the time that a database system needs to load the data from the source file. 
This metric includes any time spend by the system to built index structures and statistical data. 
The loading time is measured in milliseconds.</li>

<li><b>Query execution time.</b> 
This is the central performance metric of the benchmark. 
It refers to the time spend by a database system to execute a single query. 
The execution time of a query type Q is given by the average time of executing several 
instances of Q. The execution time of a query instance is measured in microseconds. 
In order to obtain measures similar to those of a working server, 
the benchmark executes a warm-up run before executing the timed query. 
The warm-up executes all the query instances once before the timed execution 
to populate the database cache system.</li>

</ul>



<h2>GDGenerator: A social network data generator</h2>

<p>
GDBench includes <a href="https://github.com/renzoar/GDBench/tree/master/GDGenerator">GDGenerator</a>, a java application which allows the generation of synthetic graph data following the social network structure defined above.
</p>

<p>The implementation of GDGenerator is based on a general-purpose graph data generator (<a href="https://github.com/renzoar/GraphGenerator">GraphGenerator</a>) which is based on the Recursive Matrix (R-Mat) model, but optimized to generate large graphs in a streamed fashion (i.e., reducing memory restrictions). GraphGenerator allows to create directed/undirected graphs following normal/powerlaw distributions. 
</p>

<p>
To produce synthetic social-network data we take as reference the information published by 
current social networks applications, in particular Facebook, 
The number of users in Facebook is significantly larger than the number of webpages.
By default, we set 50% of the nodes as persons and 50% as webpages. 
The identifier of a person node (i.e., the attribute pid) is an integer value in 
the range [1, N × 0.5], where N is the number of nodes in the graph. 
The names of persons and locations are selected randomly from dictionaries including
5494 first names, 88799 last names, and 656 locations. 
Hence, the probability of having duplicated pairs of these two 
attributes depends on the size of the graph and the distributions used. 
 The occurrence of attributes age and location follows probabilities
0.6 and 0.3 respectively. 
The age of a person is a random integer between 10 and 73. 
The identifier of a node webpage (i.e., the attribute wpid) 
is an integer in the range [(N × 0.8) + 1, N ]. 
The attribute URL follows the pattern http://www.site.org/webpageID.html 
where ID is the wpid of the webpage. 
The probability of including a random creation date for a webpage is 0.6.

<p>
Resembling the data generator of the benchmark for the Facebook social graph 
(<a href="https://www.facebook.com/notes/facebook-engineering/linkbench-a-database-benchmark-for-the-social-graph/10151391496443920">LinkBench</a>), GDGenerator allows to obtain power-law distributions 
for the edges corresponding to relationships friend and like.
</p>

<p>
The current version of GDGenerator allows to specify three parameters: 
</p>
<ul>
<li>the number of nodes in the graph (by default, 50% of the nodes are people and 50% webpages);</li> 
<li>the format of the output data file (gd = graph data -default-, gml = Graphml and n3 = Notation3-RDF);</li>
<li>the statistical distribution of edges friend/like (1 = powerlaw/powerlaw, 2 = powerlaw/normal, 3 = normal/powerlaw, 4 = normal/normal).</li> 
</ul>

<p>
The output of the data generator is a file named <b>sndata.*</b> which contains the graph data in an specific format. 
The data format used by default is a non-standard compressed format used called graphdata (*.gd).
</p>




<h2>The GDBench Test Driver</h2>


<p>The GDBench test driver is available as a java library <b>GDBench.jar</b> which allows the execution of data loading and query tests.
This is obtained by compiling the code available in the <a href="https://github.com/renzoar/GDBench/tree/master/GDBench">GDBench directory</a>.
</p>

<p>
The main class of the library, <b>TestDriver.java</b>, is an abstract class that defines an interface to operate with a database system. It includes methods for database definition (createDB, openDB, closeDB), 
data loading (openTransaction, closeTransaction, insertPerson, insertWebpage, insertFriend, insertLike) 
and query execution (Q1, ..., Q13). 
Next we describe the steps to use the GDBench.jar library.   
</p>



<h2>How to evaluate a database system with GDBench</h2>


<p>The procedure to evaluate the performance of a database system, by using the benchmark described above, is defined by the following steps.</p>


<p><b>Data generation.</b></p>
<p>
The first step is the generation of the data by using the graph data generator <a href="https://github.com/renzoar/GDBench/tree/master/GDGenerator">GDGenerator</a> to created the data file <b>sndata.gd</b>. 

</p><p><b>Test driver implementation.</b></p>
<p>
It consists in the development of a java class that implements the abstract class <b>TestDriver.java</b> with the code corresponding to the target database system.
<a href="https://github.com/renzoar/GDBench/tree/master/MyGDBench">MyGDBench</a> is a java project that can be used as a starting point for implemeting your own test driver.
</p>

<p>
The execution of the benchmark is defined by a configuration file called <b>gdbench.conf</b>. 
If this file does not exists, it is created with the following content:

</p>
DataLoading=true</br>
QueryTest=true</br>
Query=0</br>
Instances=100</br>
ExecutionOrder=s</br>
Repetitions=3</br>
TestData=r</br>

--- Parameters ---</br>
DataLoading=true|false : Run data loading test.</br>
QueryTest=true|false   : Run query test for all queries.</br>
Query=#                : Run query test for query type # (1 <= # <= 13). If # = 0 then run query test for all queries.</br>
Instances=#            : Defines the number # of instances for query to be executed.</br>
ExecutionOrder=s|r     : Defines the query execution order (s = sequential, r = random).</br>
Repetitions=#          : Defines the number # of times that each instance query will be executed.</br>
TestData=r|i|m          : Defines the method for test data selection (r = random, i = interval, m = media-based)
</p>



<p><b>Data loading test.</b></p> 

<p>The benchmark allows transactional or bulk data loading.</p>

<p> 
Transactional data loading is supported by calling the method runDataLoading() 
provided by the class MyTestDriver (and implemented in the class TestDriver) .
The application reads the data file sndata.gd and use transactions to insert the 
data in the target database according to the data loading methods defined in the 
class MyTestDriver (recall that these methods must be implemented by the user).</p>

<p>
Bulk data loading depends on the support of a database system to read specific data formats. 
For example, RDF databases are able to read N3 and RDF-XML files. 
Unfortunately, current graph database systems define different and non-standard data formats, 
therefore different data loaders must be developed. 
</p>

<p><b>Query execution test.</b></p> 
The query execution test (which must be executed after the data is stored in the database) allows to run a collection of queries called a "query mix".
A query mix is constructed by creating a given number of instances for each query.   
By default, the benchmark creates 100 instances but it can be defined by using the parameter "Instances" in the configuration file.
The test data used to create the query instances can be generated by one of three methods (random, interval and media-based).    
The parameter "ExecutionOrder" allows to select between a sequential or random order of query instances in the query mix.
Additionally, the user is able to run the test for a specific query type or to run the complete query mix.
</p>

<p>The execution of the query test consider two steps: 
the first step is oriented to prepare the database system (warm-up); 
and the second is devoted to measure the performance of the system. 
In both cases the same query mix is executed.</p>

<p><b>Execution rules</b></p>
<p>The implementation of a test driver must hold the following rules:</p>
<ul>
<li>The user is able to choose between transactional or bulk data loading.</li>
<li>The queries must be implemented in the corresponding methods defined by the TestDriver java class.</li>
<li>The number of results for a query must be calculated by traversing the result-set. This restriction does not apply for query Q12 where an aggregate operator can be used.</li>
</ul>

<p><b>Benchmarking results.</b></p> 

<p>
The benchmark provides reports, in HTML and CSV formats, for the transactional loading test and the query execution 
test. 
The data loading report includes information about the data loaded 
(e.g., number of nodes and edges) and the total loading time. 
The query execution report contains general and detailed information about the queries 
executed. 
A first part of the report presents, for each query type, the number of 
instances executed plus the total, maximum, minimal, average, variance and standard deviation execution times. 
A second part shows the list of query instances executed during the test, 
including the query type, the input data, the output data, 
and the execution time for query instance.
</p>


<p><b>Implementation samples and experiments</b></p>

<p>The source code of java projects implementing test drivers for different database systems (including Dex and Neo4j) are available <a href="http://ing.utalca.cl/~rangles/gdbench/">here</a>.
Experimental results are also available.
</p>


<h2>Acknowledgements</h2>

<p><b>Creator:</b> <a href="http://ing.utalca.cl/~rangles/">Renzo Angles</a></p> 

<p><b>Collaborators:</b></p> 
<ul>
<li>Roberto García (DCC, Univ. de Talca): Design and development of the current version of GDBench.</li>
<li>Sebastián Arancibia (DCC, Univ. de Talca): Design and development of initial versions the data generator.</li>
<li>Sergio Silva (DCC, Univ. de Talca): Implementations of test drivers for graph database systems and RDF Stores.</li>
<li>Josep-Lluis Larriba-Pey (<a href="http://www.dama.upc.edu/">DAMA-UPC</a>): Design of the bechmark</li>
<li>David Dominguez (<a href="http://www.sparsity-technologies.com/">Sparsity Technologies</a>): Design of the bechmark</li>
<li>Arnau Prat (<a href="http://www.dama.upc.edu/">DAMA-UPC</a>: Implementation of the data generator and the test drivers.</li>
</ul>


<p><b>Support:</b></p> 
<ul>
<li><a href="http://ri.conicyt.cl/575/article-38780.html">Comparison of Graph Databases and RDF Databases</a>. Initiation into Research, Project No 1070348, Fondecyt, Chile. 2011-2013.</li>
<li><a href="http://www.ldbc.eu/">Linked Data Benchmark Council Project (LDBC)</a>. European Community's Seventh Framework Programme FP7/2007-2013.</li>
</ul>


<p>Last update: Oct 16, 2014</p> 


</body></html>
