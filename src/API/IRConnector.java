package API;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateClassifierOptions.Builder;

import Component.XML;

public class IRConnector {
	public String key;
	public String version;
	public IamOptions options;
	public VisualRecognition service;
	
	public IRConnector() {
		key = XML.IRkey;
		version = XML.IRversion;
		options = new IamOptions.Builder().apiKey(key).build();
		service = new VisualRecognition(version, options);
	}
	
	public String Scan(String filename, String owner) throws FileNotFoundException {
		InputStream imagesStream = new FileInputStream(filename);
		ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
		.imagesFile(imagesStream)
		.imagesFilename(filename)
		.threshold((float) 0.6)
		.owners(Arrays.asList(owner))
		.build();
		ClassifiedImages result = service.classify(classifyOptions).execute();
		System.out.println(result);
		return result.toString();
	}
	
	public void createClassifier(String name, Map<String, String> images) throws FileNotFoundException {
		System.out.println("creating objects...");
		
		Builder builder = new CreateClassifierOptions.Builder();
		builder.name(name);
		Iterator<Map.Entry<String, String> > itr = images.entrySet().iterator();
		Map.Entry<String, String> negative = null;
		while(itr.hasNext()) {
			Map.Entry<String, String> tmp = itr.next();
			if(tmp.getKey().trim().equals("negative")) {
				negative = tmp;
				continue;
			}
			builder.addClass(tmp.getKey(), new File(XML.ZipDir + tmp.getValue()));
		}
		
		if(negative != null)
			builder.negativeExamples(new File(XML.ZipDir + negative.getValue()));
		
		CreateClassifierOptions createClassifierOptions = builder.build();

		Classifier classifier = service.createClassifier(createClassifierOptions).execute();
		System.out.println(classifier);
	}
}
