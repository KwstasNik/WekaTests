 import java.awt.List;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import javax.print.DocFlavor.INPUT_STREAM;

import com.google.gson.Gson;

import weka.clusterers.AbstractClusterer;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.clusterers.RandomizableClusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;



public class ClassifHelper {
	static String DATASOURCE_PATH="C:\\Users\\Kwstas\\Desktop\\wekaTest\\userInfo.arff";

	static int ClasifierChoise=0;
	public static String classificationresults (String Inputarff) throws Exception {
		// TODO Auto-generated method stub

		String[] options={} ;
		switch(ClasifierChoise){
		case 0:
		 options = new String[8];
		 options[0] = "-N";                 // max. iterations
		 options[1] = "2";
		 options[2] = "-A";                 // max. iterations
		 options[3] = "weka.core.EuclideanDistance -R first-last";
		 options[4] = "-I";                 // max. iterations
		 options[5] = "500";
		 options[6] = "-S";                 // max. iterations
		 options[7] = "10";
		  //clusterer = new SimpleKMeans(); 
		  break;
		case 1:
			  options = new String[8];
			 options[0] = "-I";                 // max. iterations
			 options[1] = "100";
			 options[2] = "-N";                 // max. iterations
			 options[3] = "2";
			 options[4] = "-M";                 // max. iterations
			 options[5] = "1.0E-6";
			 options[6] = "-S";                 // max. iterations
			 options[7] = "10";
			 // clusterer = new SimpleKMeans(); 
			  //weka.clusterers.EM -I 100 -N 2 -M 1.0E-6 -S 100
			  break;
		}
	//	 weka.clusterers.SimpleKMeans -N 2   -I 500 -S 10
		
		//SimpleKMeans  clusterer = new SimpleKMeans(); 
		EM clusterer=new EM();
		PrintWriter out = new PrintWriter(DATASOURCE_PATH);
		out.print(Inputarff);
		out.close();
		DataSource sourceTrain = new DataSource(DATASOURCE_PATH);
		
		Instances data = sourceTrain.getDataSet();
		Instances datanoDate= sourceTrain.getDataSet();
		datanoDate.deleteAttributeAt(0);
		 ClusterEvaluation eval = new ClusterEvaluation();
		                                // new clusterer instance, default options
		 clusterer.setOptions(options);
		 clusterer.buildClusterer(datanoDate);                                 // build clusterer
		 //eval.setClusterer(clusterer);                                   // the cluster to evaluate
		 //eval.evaluateClusterer(newData);                                // data to evaluate the clusterer on
		 System.out.println("# of clusters: " + clusterer.getNumClusters());  // output # of clusters
		 System.out.println("info " + clusterer.globalInfo());  // output # of clusters
			int i=0;
			ArrayList<PeriodClustered> pcLstList=new 	ArrayList<PeriodClustered>();
		 for (Instance instance : datanoDate) {
			 
			 PeriodClustered pClustered=new PeriodClustered();
			 pClustered.dateAsString=data.get(i).stringValue(0);
			 pClustered.PointOfInterest=String.valueOf(clusterer.clusterInstance(instance)); 
			 pcLstList.add(pClustered);
		System.out.println(data.get(i).stringValue(0) +" is in cluster "+	clusterer.clusterInstance(instance));
		i++;

		 }
		 
		/*	ArrayList<PeriodClustered> pcLstList=new 	ArrayList<PeriodClustered>();
			
		 PeriodClustered pClustered=new PeriodClustered();
		 pClustered.dateAsString="0";
		 pClustered.PointOfInterest="7";
		 pcLstList.add(pClustered);*/
		 
		 Gson gson = new Gson();
		 
		return  gson.toJson(pcLstList);
		
	}

}
