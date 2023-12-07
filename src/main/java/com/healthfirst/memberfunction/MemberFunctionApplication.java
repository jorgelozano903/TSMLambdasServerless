package com.healthfirst.memberfunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SpringBootApplication
public class MemberFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberFunctionApplication.class, args);
	}

	@Bean
	public Function<HealthFirstMemberRequest, HealthFirstMemberResponse> members() {
		return member -> {
			HealthFirstMemberResponse response = new HealthFirstMemberResponse();
			response.setMemberId(member.getMemberId());
			response.setCoverage(HealthFirstMemberResponse.Coverage.MEDICAL);
			return response;
		};
	}

	@Bean
	public String getMoreData(){
		return "Hello world";
	}

	@Bean
	public List<String> fetchRepos() {
		List<String> responses = new ArrayList<>();

		try {
			for (int i = 1; i <= 4; i++) {
				URL url = new URL("https://api.github.com/orgs/az-soft-eng/repos?per_page=100&page=" + i);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");

				String authorizationHeader = "Bearer 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";
				String contentTypeHeader = "application/json";
				List<String> headers = List.of("Authorization", authorizationHeader, "Content-Type", contentTypeHeader);

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
					responses.add(response.toString());
				} else {
					System.out.println("GET request failed. Response Code: " + responseCode);
					responses.add(null);
				}
			}

			return responses;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}


	@Bean
	public String fetchData(){
		try {
			URL url = new URL("https://api.github.com/orgs/az-soft-eng/repos?per_page=100&page=1");
			URL url2 = new URL("https://api.github.com/orgs/az-soft-eng/repos?per_page=100&page=2");
			URL url3 = new URL("https://api.github.com/orgs/az-soft-eng/repos?per_page=100&page=3");
			URL url4 = new URL("https://api.github.com/orgs/az-soft-eng/repos?per_page=100&page=4");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
			HttpURLConnection conn3 = (HttpURLConnection) url3.openConnection();
			HttpURLConnection conn4 = (HttpURLConnection) url4.openConnection();

			conn.setRequestMethod("GET");
			conn2.setRequestMethod("GET");
			conn3.setRequestMethod("GET");
			conn4.setRequestMethod("GET");

			String authorizationHeader = "Bearer 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";
			String contentTypeHeader = "application/json";
			List<String> headers = List.of("Authorization", authorizationHeader, "Content-Type", contentTypeHeader);

			/*conn.setRequestProperty("Authorization","Bearer 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");
			conn2.setRequestProperty("Authorization","Bearer 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");
			conn3.setRequestProperty("Authorization","Bearer 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");
			conn4.setRequestProperty("Authorization","Bearer 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");*/

			conn.connect();
			conn2.connect();
			conn3.connect();
			conn4.connect();

			int responseCode = conn.getResponseCode();
			int responseCode2 = conn2.getResponseCode();
			int responseCode3 = conn3.getResponseCode();
			int responseCode4 = conn4.getResponseCode();

			String fullResponse = "";

			if (responseCode == HttpURLConnection.HTTP_OK && responseCode2 == HttpURLConnection.HTTP_OK && responseCode3 == HttpURLConnection.HTTP_OK && responseCode4 == HttpURLConnection.HTTP_OK) {
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
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}
}
