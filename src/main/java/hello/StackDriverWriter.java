package hello;

//import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.monitoring.v3.Monitoring;
import com.google.api.services.monitoring.v3.MonitoringScopes;
import com.google.api.services.monitoring.v3.model.CreateTimeSeriesRequest;
import com.google.api.services.monitoring.v3.model.Metric;
import com.google.api.services.monitoring.v3.model.MonitoredResource;
import com.google.api.services.monitoring.v3.model.Point;
import com.google.api.services.monitoring.v3.model.TimeInterval;
import com.google.api.services.monitoring.v3.model.TimeSeries;
import com.google.api.services.monitoring.v3.model.TypedValue;

public class StackDriverWriter {
	/**
	 * Cloud Monitoring v3 REST client.
	 */
	private Monitoring monitoringService;

	public StackDriverWriter() {
		try {
			monitoringService = monitoringService();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Builds and returns a CloudMonitoring service object authorized with the
	 * application default credentials.
	 * 
	 * @return CloudMonitoring service object that is ready to make requests.
	 * @throws GeneralSecurityException
	 *             if authentication fails.
	 * @throws IOException
	 *             if authentication fails.
	 */
	GoogleCredential authenticate() throws GeneralSecurityException,
			IOException {
		// Grab the Application Default Credentials from the environment.
		/*
		 * GoogleCredential credential = GoogleCredential.fromStream(new
		 * FileInputStream
		 * ("/home/kiran/Downloads/My First Project-b2f016269322.json"))
		 * .createScoped(MonitoringScopes.all());
		 */
		// Getting ClassLoader obj  
	    ClassLoader classLoader = StackDriverWriter.class.getClassLoader();  
	    // Getting resource(File) from class loader  
	    //File file=new File(classLoader.getResource("classpath:stackdrivercreateaccess.json").getFile());  
	    InputStream inputstream=classLoader.getResource("stackdrivercreateaccess.json").openStream();
		GoogleCredential credential = GoogleCredential
				.fromStream(
						inputstream)
				.createScoped(MonitoringScopes.all());
		return credential;
	}

	/**
	 * Builds and returns a CloudMonitoring service object authorized with the
	 * application default credentials.
	 * 
	 * @return CloudMonitoring service object that is ready to make requests.
	 * @throws GeneralSecurityException
	 *             if authentication fails.
	 * @throws IOException
	 *             if authentication fails.
	 */
	Monitoring monitoringService() throws GeneralSecurityException, IOException {
		// Grab the Application Default Credentials from the environment.
		GoogleCredential credential = authenticate();

		// Create and return the CloudMonitoring service object
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		Monitoring service = new Monitoring.Builder(httpTransport, jsonFactory,
				credential).setApplicationName("Monitoring Sample").build();
		return service;
	}

	public void send(MetricDescriptor metricDescriptor, long value) {
		try {
			createTimeSeries(metricDescriptor, value);
		} catch (IOException e) {
			System.out.println("In StackDriverWriter Exception");
			e.printStackTrace();
		}
	}

	private void createTimeSeries(MetricDescriptor descriptor, long value)
			throws IOException {
		CreateTimeSeriesRequest content = new CreateTimeSeriesRequest();
		// Refer below url for a sample timeseries json
		// https://cloud.google.com/monitoring/api/v3/metrics#time-series
		TimeSeries ts = new TimeSeries();

		Metric metric = new Metric();
		metric.setType(descriptor.getType());
		ts.setMetric(metric);
		// metric labels can be
		// present.https://cloud.google.com/monitoring/api/v3/metrics.
		// In our case we need instance_id and project_id labels and which are
		// anywayz monitoredresource labels
		/*
		 * Map<String,String> labels = new HashMap<>();
		 * labels.put("instance-id","");
		 */

		MonitoredResource monitoredResource = new MonitoredResource();
		monitoredResource.setType(descriptor.getResourceType());
		Map<String, String> labels = new HashMap<>();
		// getGoogleMetadataValue
		// get the instanceif from here below
		// https://github.com/spinnaker/kork/blob/master/kork-stackdriver/src/main/java/com/netflix/spectator/stackdriver/MonitoredResourceBuilder.java
		/*
		 * labels.put("instance_id","6966342050480297690");
		 * labels.put("zone","us-east1-b");
		 * labels.put("project_id","core-crossing-145006");
		 */
		getGceInstanceLabels(labels);
		monitoredResource.setLabels(labels);
		ts.setResource(monitoredResource);

		// use guage(because we calculate no of requests in code ) and for which
		// starttime is not needed and which is equal to end time
		ts.setMetricKind(descriptor.getKind());
		ts.setValueType(descriptor.getValueType());

		Point point = new Point();
		TypedValue tValue = new TypedValue();
		tValue.setInt64Value(value);
		point.setValue(tValue);
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String currentTime = sdf.format(new Date());
		String startTime = sdf.format(new Date(
				new Date().getTime() - 1000 * 60 * 5));
		//System.out.println("current time : " + currentTime); // -prints->
																// 2015-01-22T03:23:26Z
																// (https://cloud.google.com/monitoring/api/troubleshooting)
		TimeInterval timeInterval = new TimeInterval();
		//timeInterval.setStartTime(startTime);
		timeInterval.setEndTime(currentTime);
		point.setInterval(timeInterval);
		ts.setPoints(Arrays.asList(point));

		content.setTimeSeries(Arrays.asList(ts));
		String project = "lunar-marker-157606";
	    String projectResource = "projects/" + project;
		Monitoring.Projects.TimeSeries.Create request = monitoringService
				.projects().timeSeries().create(projectResource, content);
		request.execute();
	}

	private String getConnectionValue(HttpURLConnection con) throws IOException {
		int responseCode = con.getResponseCode();
		if (responseCode < 200 || responseCode > 299) {
			throw new IOException("Unexpected responseCode " + responseCode);
		}
		BufferedReader input = new BufferedReader(new InputStreamReader(
				con.getInputStream(), "US-ASCII"));
		StringBuffer value = new StringBuffer();
		for (String line = input.readLine(); line != null; line = input
				.readLine()) {
			value.append(line);
		}
		input.close();
		return value.toString();
	}

	private String getGoogleMetadataValue(String key) throws IOException {
		URL url = new URL(String.format(
				"http://169.254.169.254/computeMetadata/v1/%s", key));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(1000);
		con.setInstanceFollowRedirects(true);
		con.setRequestMethod("GET");
		con.setRequestProperty("Metadata-Flavor", "Google");
		return getConnectionValue(con);
	}

	private boolean getGceInstanceLabels(Map<String, String> labels) {
		try {
			String id = getGoogleMetadataValue("instance/id");
			String zone = getGoogleMetadataValue("instance/zone");
			zone = zone.substring(zone.lastIndexOf('/') + 1);
			labels.put("instance_id", id);
			labels.put("zone", zone);
			labels.put("project_id", "lunar-marker-157606");
			// project_id is only intended for reading since it is already a URL
			// parameter.

			return true;
		} catch (IOException ioex) {
			return false;
		}
	}
}
