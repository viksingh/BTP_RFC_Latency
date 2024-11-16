package org.saki;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CreateGraphFromExtractedData extends Application {

	//Set manually depending on payload size. Look at the file and update this
	public static final String graphTitle = "RFC Processing Comparison using Cloud Integration";

	@Override public void start(Stage stage) {

		 try {
// Creating long arrays as I'm not sure how big these times in
//Calling these variable names as path1 etc to repeat them easily
			 long processingTimes[][] = new long[3][50];
			 URL path1 = CreateGraphFromExtractedData.class.getResource("/_LANCOL.txt");
			 File file1 = new File(path1.getFile());
			 byte[] bytes1 = new byte[(int) file1.length()];
			 FileInputStream fis1 = new FileInputStream(file1);
			 fis1.read(bytes1);
			 fis1.close();
// file has pipe as the separator
			 String[] valueStr1 = new String(bytes1).trim().split("\\|");
//Print to console
			 System.out.print("Column LAN processing:");

			 for (int i = 0; i < valueStr1.length; i++) {
			     processingTimes[0][i] = Long.parseLong(valueStr1[i]);
			 System.out.print("|"+processingTimes[0][i]);}
			 

//Do the same for WANCOL - read the file and feed into array
			 URL path2 = CreateGraphFromExtractedData.class.getResource("/_WANCOL.txt");
			 File file2 = new File(path2.getFile());
			 byte[] bytes2 = new byte[(int) file2.length()];
			 FileInputStream fis2 = new FileInputStream(file2);
			 fis2.read(bytes2);
			 fis2.close();
			 String[] valueStr2 = new String(bytes2).trim().split("\\|");
//Print to console
			 System.out.print("\n"+"Column WAN processing:");
			 for (int i = 0; i < valueStr2.length; i++) {
			     processingTimes[1][i] = Long.parseLong(valueStr2[i]);
			 System.out.print("|"+ processingTimes[1][i]);}

//Do the same for ROWCOL - read the file and feed into array
			 URL path3 = CreateGraphFromExtractedData.class.getResource("/_ROW.txt");
			 File file3 = new File(path3.getFile());
			 byte[] bytes3 = new byte[(int) file3.length()];
			 FileInputStream fis3 = new FileInputStream(file3);
			 fis3.read(bytes3);
			 fis3.close();
			 String[] valueStr3 = new String(bytes3).trim().split("\\|");

//
			 System.out.print("\n"+"Row      processing:");
			 for (int i = 0; i < valueStr3.length; i++) {
			     processingTimes[2][i] = Long.parseLong(valueStr3[i]);
			 System.out.print("|"+ processingTimes[2][i]);}

			  stage.setTitle("RFC Processing Comparison" );
		        final NumberAxis xAxis = new NumberAxis(0, 50, 1);
		        final NumberAxis yAxis = new NumberAxis(0,5500,10 );
		        final AreaChart<Number,Number> ac = 
		            new AreaChart<Number,Number>(xAxis,yAxis);
		        xAxis.setForceZeroInRange(true);
		        

		        
		        XYChart.Series series1 = new XYChart.Series();
		        series1.setName("Column LAN Serialization");
		        
		        XYChart.Series series2 = new XYChart.Series();
		        series2.setName("Column WAN Serialization");
		        
		        XYChart.Series series3 = new XYChart.Series();
		        series3.setName("Row Serialization");
		        ac.setTitle(graphTitle);
		        
		        for ( int i= 0 ; i < 50 ; i++) {
		  			  series1.getData().add(new XYChart.Data(i, processingTimes[0][i]));
		  			  series2.getData().add(new XYChart.Data(i, processingTimes[1][i]));
		  			  series3.getData().add(new XYChart.Data(i, processingTimes[2][i]));
												}
		          Scene scene  = new Scene(ac,800,600);
		          
		          ac.setHorizontalZeroLineVisible(true);
		          ac.getData().addAll(series1, series2,series3);
		          stage.setScene(scene);
		          stage.show();	        

		} catch (Exception e) {
//I should do something with these exceptions
			e.printStackTrace();}
		}
    	public static void main(String[] args) {
            launch(args);
        }    

}