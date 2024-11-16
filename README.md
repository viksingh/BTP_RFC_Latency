#	How to use this repo:

###	This repo has the mtaerial I used to write the blog https://community.sap.com/t5/technology-blogs-by-members/improving-latency-between-sap-btp-and-on-prem-s4-system-over-rfc-connection/ba-p/13932129 

It has the following parts:

###	1) RFCs in the ABAP system .

   Z_RFC_GET_FLIGHTS - normal RFC extracting data from SFLIGHT table.
   Z_RFC_GET_LONG_TABLE - RFC with a very long column.
   Z_RFC_GET_SHORT_TABLE - RFC with a very short column string
   Z_RFC_TEST_BIG_DATA - RFC to generate a string as the return output
   
###	2) Integration flows to be deployed in Cloud Integration tenant.
   RFC_FLOW_DATA_CALL.zip - call a RFC from postman and get the payload size ( for charting label ) .
   Three flows to call different RFC destinations to extract data for different serialization config on RFC desti:
   
   BIG_RFC_FLOW.zip - Row based / default serialization param
   RFC_FLOW_COL_LAN.zip - Column Based LAN seialization 
   RFC_FLOW_COL_WAN.zip - Column based WAN serialization

###	3) getStartTime.groovy:
   It measures the time before and after the RFC call
      
###   4) callapi.sh ( in src/main/bashscript )
     This script calls the integration flow via curl and stored the output in files _ROW.txt , _WANCOL.txt and _LANCOL.txt . The RFC being called is controled via an input file to the shell script.
  
###   5) input.xml : Input file for script
    <ns0:Z_RFC_TEST_BIG_DATA xmlns:ns0="urn:sap-com:document:sap:rfc:functions"><IM_INPUT>Constant</IM_INPUT></ns0:Z_RFC_TEST_BIG_DATA>
      
###   6) Java Project :
     File CreateGraphFromExtractedData.java : reads the _ROW.txt , _WANCOL.txt and _LANCOL.txt to create the graph.
                     File CallRFCAndShowGraph.java makes the local RFC call and shows the graph . Required RFC destinations

                      rowA4H.jcoDestination
                      columnA4H.jcoDestination
                      columnWANA4H.jcoDestination

RFC being called is controlled via this constant:
	public static final String FM_NAME = "Z_RFC_GET_FLIGHTS";

 Graph title is set here - I call integration flow in RFC_FLOW_DATA_CALL.zip and set the paylod size and text .
 public static final String graphTitle = "RFC Processing Comparison "; 

 CallRFCAndShowGraph.java : for local RFC call 

### 7) The Java project uses maven and the SAPJCO needs to be installed

    mvn install:install-file 
-Dfile=sapjco3.jar 
-DgroupId=com.company.sap 
-DartifactId=com.sap.conn.jco.sapjco 
-Dversion=1.0 
-Dpackaging=jar

Lines to be added in POM.xml

<dependency>
    <groupId>com.company.sap</groupId>
    <artifactId>com.sap.conn.jco.sapjco</artifactId>
    <version>1.0</version>
</dependency>

JCO has this check  and hence needs name in this format. If you use the default maven install it'll not work as it'll create JCO jar with another name.

if (!jarname.equals("sapjco3.jar") 
&& !jarname.startsWith("com.sap.conn.jco") 
&& !jarname.equals("sapjco3_IDE.jar") 
&& Package.getPackage("org.apache.maven.surefire.booter") == null 
&& Package.getPackage("org.eclipse.jdt.internal.junit.runner") == null) {
    throw new ExceptionInInitializerError("Illegal JCo archive \"" + jarname + "\". It is not allowed to rename or repackage the original archive \"" + "sapjco3.jar" + "\".");
}

###	8) Running the project :

	a) For local RFC call change the main class in POM.xml to <mainClass>org.saki.CallRFCAndShowGraph</mainClass> and it should work if you have the .jcoDestination set up.
	b) For calls via cloud integration, create the RFC input file in step 5 and run the shell script. It will create files : _ROW.txt , _WANCOL.txt and _LANCOL.txt  . This will need to added as resources in 	the Java project 
and it should work. Change the main class in POM.xml to 
<mainClass>org.saki.CreateGraphFromExtractedData</mainClass>

For either a or b, the project is run as 
####	mvn clean javafx:run

###	9. module-info.java
If not using maven, you'll need a module-info.java file and add the required libraries as dependencies.

module RFCDemo {
	requires sapjco3;
	requires javafx.graphics;
	requires javafx.controls;
	
    opens org.saki to javafx.fxml;
    exports org.saki;	
	
}
   
