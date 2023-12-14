package com.healthfirst.memberfunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MemberFunctionApplication implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{

	private APIGatewayProxyRequestEvent input;
	private Context context;

	public static void main(String[] args) {
		SpringApplication.run(MemberFunctionApplication.class, args);
	}

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		// Retrieve path parameters
		String product = input.getPathParameters().get("product");
		String cycle = input.getPathParameters().get("cycle");

		// Your logic using product and cycle parameters

		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		// Set response properties, e.g., response.setBody("...");
		return response;
	}

	@Bean
	public String fetchRepos() {
		List<Object> responses = new ArrayList<>();

		try {
			for (int i = 1; i <= 4; i++) {
				URL url = new URL("https://api.github.com/orgs/az-soft-eng/repos?per_page=100&page=" + i);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Authorization","Bearer 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");
				conn.connect();
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String inputLine;
					//List<Object> response = new ArrayList<>();
					StringBuilder response = new StringBuilder();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
						//response.add(inputLine);
					}

					in.close();
					//responses.add(response.toString());
					responses.add(response);
				} else {
					System.out.println("GET request failed. Response Code: " + responseCode);
					responses.add(null);
				}
			}

			return responses.toString();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}

	@Bean
	public String getRepoData(APIGatewayProxyRequestEvent input){
		List<Object> responses = new ArrayList<>();

		try{
			String repo = input.getPathParameters().get("repo");
			String path = input.getPathParameters().get("path");
			String dash = "";
			path = path.replace(",","/");
			if (repo.equals("azqrcode-web") && path.equals( "configs/deployment-specs.json")) {
				dash = "/";
			}
			URL url = new URL("https://api.github.com/repos/az-soft-eng/" + repo + "/contents/"+ path + dash + "?ref=develop");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization","Bearer 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");
			conn.connect();
			int responseCode = conn.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK){
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();


				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
					//response.add(inputLine);
				}

				in.close();
				//responses.add(response.toString());
				responses.add(response);
			}else{
				System.out.println("GET request failed. Response Code: " + responseCode);
				responses.add(null);
			}
			return responses.toString();
		}
		catch (Exception exception){
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}

	@Bean
	public String getEndOfLife(APIGatewayProxyRequestEvent input){
		try{
			String product = input.getPathParameters().get("product");
			String cycle = input.getPathParameters().get("cycle");
			URL url = new URL("https://endoflife.date/api/" + product + "/" + cycle + ".json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}

				in.close();
				return response.toString();
			}

			else {
				System.out.println("GET request failed. Response Code: " + responseCode);
				return null;
			}
		}catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}
}
