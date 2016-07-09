package com.ibm.bluemix.services.watson.toneanalyzer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Response;

import ch.belsoft.tools.Logging;
import ch.belsoft.tools.XPagesUtil;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ibm.bluemix.services.CloudService;
import com.ibm.xsp.bluemix.util.BluemixContextUtil;
import ch.belsoft.tools.RestUtil;

public class ToneAnalyzerService extends CloudService {

	private static final String SERVICE_NAME = "tone_analyzer";
	private static final String API_URL = "https://gateway.watsonplatform.net/tone-analyzer/api/v3/tone?version=2016-05-19";

	private boolean connected = false;

	private final ObjectMapper mapper = new ObjectMapper();

	public ToneAnalyzerService() {

	}

	public String getServiceName() {
		return SERVICE_NAME;
	}

	public void analyzeTone() {
		try {
			super.connect();
			ToneAnalyzerRequest req = new ToneAnalyzerRequest();
			req.setText("This is very good!");
			String postDataString = mapper.writeValueAsString(req);
			XPagesUtil.showErrorMessage("JSON:" + postDataString,
					"pnlWatsonResponse");
			String result = RestUtil.post(API_URL, bluemixUtil
					.getAuthorizationHeader(), postDataString);
			mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
			ToneAnalyzerResponse resp = mapper.readValue(result,
					ToneAnalyzerResponse.class);
			XPagesUtil.showErrorMessage("resp:" + resp, "pnlWatsonResponse");

		} catch (Exception e) {
			Logging.logError(e);
		}

	}
}