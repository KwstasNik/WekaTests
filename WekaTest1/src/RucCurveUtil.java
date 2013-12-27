import java.awt.BorderLayout;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.core.Instances;
import weka.core.Utils;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;


public  class RucCurveUtil {

	
	
	public static void createCurve(Evaluation eval,Classifier cl,   int classIndex) throws Exception
	{
		//data.setClassIndex(data.numAttributes() - 1);
		 
	     // train classifier
	     
	    // Evaluation eval = new Evaluation(data);
	    // eval.crossValidateModel(cl, data, 10, new Random(1));
		 ThresholdCurve tc = new ThresholdCurve();
	   //  int classIndex = 0;
	     Instances result = tc.getCurve(eval.predictions(), classIndex);
	 
	     // plot curve
	     ThresholdVisualizePanel vmc = new ThresholdVisualizePanel();
	     vmc.setROCString("(Area under ROC = " + 
	         Utils.doubleToString(tc.getROCArea(result), 4) + ")");
	     vmc.setName(result.relationName());
	     PlotData2D tempd = new PlotData2D(result);
	     tempd.setPlotName(result.relationName());
	     tempd.addInstanceNumberAttribute();
	     // specify which points are connected
	     boolean[] cp = new boolean[result.numInstances()];
	     for (int n = 1; n < cp.length; n++)
	       cp[n] = true;
	     tempd.setConnectPoints(cp);
	     // add plot
	     vmc.addPlot(tempd);
	 
	     // display curve
	     String plotName = vmc.getName(); 
	     final javax.swing.JFrame jf = 
	       new javax.swing.JFrame("Weka Classifier Visualize: "+plotName);
	     jf.setSize(500,400);
	     jf.getContentPane().setLayout(new BorderLayout());
	     jf.getContentPane().add(vmc, BorderLayout.CENTER);
	     jf.addWindowListener(new java.awt.event.WindowAdapter() {
	       public void windowClosing(java.awt.event.WindowEvent e) {
	       jf.dispose();
	       }
	     });
	     jf.setVisible(true);
	   }
	}

	

