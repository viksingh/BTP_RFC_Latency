package org.saki;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;



// For creating the graphs with the data extracted by the script callApi.sh
public class CallRFCAndShowGraph extends Application {

	//Set manually depending on payload size. Get rhe value  from postman and update this
	public static final String graphTitle = "RFC Processing Comparison ";
	public static final String FM_NAME = "Z_RFC_TEST_BIG_DATA";

	@Override public void start(Stage stage) {

//We need to compare three results with 50 iterations
		long processingTimes[][] = new long[3][50];

// Going in order , column based LAN , column based WAN and defaut / row serialization

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Column LAN Serialization");

		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Column WAN Serialization");

		XYChart.Series series3 = new XYChart.Series();
		series3.setName("Row Serialization");


// Call the RFCs for each of the destinations and store the processing times
		for ( int i= 0 ; i < 50 ; i++) {
			try {
				long columnLANProcessingTime = callRFC(Destinations.DestList.ABAP_COLUMN_LAN_DEST);
				long columnWANProcessingTime = callRFC(Destinations.DestList.ABAP_COLUMN_WAN_DEST);
				long rowProcessingTime = callRFC(Destinations.DestList.ABAP_ROW_DEST);

				//Set the data for the charts
				series1.getData().add(new XYChart.Data(i, columnLANProcessingTime));
				series2.getData().add(new XYChart.Data(i, columnWANProcessingTime));
				series3.getData().add(new XYChart.Data(i, rowProcessingTime));


//Add to the array to create output log
				processingTimes[0][i] = columnLANProcessingTime;
				processingTimes[1][i] = columnWANProcessingTime;
				processingTimes[2][i] = rowProcessingTime;


			} catch (JCoException e) {
				e.printStackTrace();
			}
		}

		long maxNumber = 0;
		// Printing the whole exception trace as it is giving full details
		System.out.print("Column LAN processing: ");
		for (int j = 0 ; j < 50 ; j++)
		{
			System.out.print(processingTimes[0][j] + "|");

			if ( processingTimes[0][j] > maxNumber )
			{
				maxNumber = processingTimes[0][j];
			}

		}



		System.out.print("\n"+"Column WAN processing: ");
		for (int j = 0 ; j < 50 ; j++)
		{
			System.out.print(processingTimes[1][j] + "|");
			if ( processingTimes[1][j] > maxNumber )
			{
				maxNumber = processingTimes[1][j];}

		}

		System.out.print("\n"+"Row      processing:   ");
		for (int j = 0 ; j < 50 ; j++)
		{
			System.out.print(processingTimes[2][j] + "|");
			if ( processingTimes[2][j] > maxNumber )
			{
				maxNumber = processingTimes[2][j];}

		}

		System.out.print("Max Number is " + maxNumber);

		long maxForGraph = roundNumber(maxNumber);
		long spacing = 1;

		if ( maxForGraph > 1000 ) spacing = 10;

		System.out.println("Max Number is " + maxNumber);
		System.out.print("Max graph is  " + maxForGraph);

// Set the stage for the image yAxis controls the "y axis" and granularity of the graph
		stage.setTitle("RFC Processing Comparison" );
		final NumberAxis xAxis = new NumberAxis(0, 50, 1);
		final NumberAxis yAxis = new NumberAxis(0,maxForGraph,spacing );
		final AreaChart<Number,Number> ac =
				new AreaChart<Number,Number>(xAxis,yAxis);
		xAxis.setForceZeroInRange(true);




//Set the scene , set the stage and show !
		Scene scene  = new Scene(ac,800,600);

		ac.setHorizontalZeroLineVisible(true);
		ac.getData().addAll(series1, series2,series3);
		ac.setTitle(graphTitle);
		stage.setScene(scene);
		stage.show();




	}

	private long roundNumber(long maxNumber) {
		if ( maxNumber < 100 )
			return Math.round(maxNumber/10.0) * 10 + 1;
		else
			return Math.round(maxNumber/10.0 + 1) * 10;
	}


	private static long callRFC(String Destination) throws JCoException {
//Call the RFC locally, I'm interested in only the exeuction time as the data transfer is determined by the RFC call
		JCoDestination destination=JCoDestinationManager.getDestination(Destination);
		JCoFunction function=destination.getRepository().getFunction(FM_NAME);
		if (function==null)
			throw new RuntimeException(FM_NAME);
		try
		{
			long start = System.currentTimeMillis();
			function.execute(destination);
			return  System.currentTimeMillis() - start;
		}
		catch (AbapException e)
		{
			System.out.println(e);
			return 0;
		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}




