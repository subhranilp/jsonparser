package com.sample.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class JsonParser {

    public static void main(String[] args) {
        URL url;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        int sum = 0;
        try {
            url = new URL(resourceBundle.getString("jsonarray.url"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }
            System.out.println(content.toString());

            ObjectMapper mapper = new ObjectMapper();
            List<Map> jsonOutput = mapper.readValue(content.toString(), new TypeReference<List<Map>>() {
            });
            reader.close();
            for (int i = 0; i < jsonOutput.size(); i++) {
                List numbers = (List) (jsonOutput.get(i)).get("numbers");
                sum += sumOfNumbersInAList(numbers);
            }
            System.out.println("Sum of all the numbers: " + sum);

        } catch (IOException ex) {
            System.out.println("Exception calling get request: " + ex);
        }
    }


    public static int sumOfNumbersInAList(List<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }
}