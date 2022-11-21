package com.bdd.Step;


import com.bdd.Util.Util;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.http.Headers;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class ApiStep {

    private RequestSpecification requestSpecification;
    private Response response;
    private String bodyRequest;


    public void queConfiguroLascabeceras(DataTable dataTable) {

        Headers cabeceras = Util.configurarHeaders(dataTable);

        Logger.getGlobal().log(Level.INFO, "HEADERS: {0}", cabeceras); //IMPRIMIR EN CONSOLA

        requestSpecification = RestAssured.given().log().all().headers(cabeceras);
    }

    public void configuroLosQueryParams(DataTable dataTable) {
        List<Map<String, String>> mapList = dataTable.asMaps();
        // [
        // { key = page , value = 2 },

        //  ]

        mapList.forEach(map -> { // map
            String pathName = map.get("key");
            String pathValue = map.get("value");
            requestSpecification.queryParam(pathName, pathValue);
        });
    }

    public void ejecutoElApi(DataTable dataTable) {
        String method = Util.getValueFromDataTable(dataTable, "method");
        String url = Util.getValueFromDataTable(dataTable, "url");


        Logger.getGlobal().log(Level.INFO, "HEADERS: {0}", method); //IMPRIMIR EN CONSOLA
        Logger.getGlobal().log(Level.INFO, "HEADERS: {0}", url); //IMPRIMIR EN CONSOLA
        switch (method) {
            case "POST":
                response = requestSpecification.body(bodyRequest).when().log().all().post(url);
                break;
            case "UPDATE":
                response = requestSpecification.body(bodyRequest).when().log().all().put(url);
                break;
            case "GET":
                response = requestSpecification.when().log().all().get(url);
                break;
            case "DELETE":
                response = requestSpecification.when().log().all().delete(url);
                break;
        }
        response.prettyPeek();
    }

    public void validoElStatusCodeSea(String statusCode) {
        Logger.getGlobal().log(Level.INFO, "STATUS CODE: {0}", statusCode); //IMPRIMIR EN CONSOLA
        int statusCodeEsperado = Integer.parseInt(statusCode);
        response.then().assertThat().statusCode(statusCodeEsperado);
    }

    public void validoLaRespuestaDelServicio(DataTable dataTable) {

        String caracter = response.asPrettyString().trim().substring(0, 1);
        List<Map<String, String>> mapList = dataTable.asMaps();
        mapList.forEach(map -> {
            String nodo = map.get("nodo");
            String valorEsperado = map.get("value");
            String valorObtenido = "";
            JsonPath jsonPath = new JsonPath(response.getBody().asString());
            if (caracter.equalsIgnoreCase("{") || caracter.equalsIgnoreCase("[")) {
                valorObtenido = String.valueOf(jsonPath.getString(nodo)).trim();//data.first_name
                Logger.getGlobal().log(Level.INFO, "NODO: {0}", nodo); //IMPRIMIR EN CONSOLA
                Logger.getGlobal().log(Level.INFO, "VALOR OBTENIDO: {0}", valorObtenido); //IMPRIMIR EN CONSOLA
                Logger.getGlobal().log(Level.INFO, "VALOR ESPERADO: {0}", valorEsperado); //IMPRIMIR EN CONSOLA
                Assert.assertEquals(valorEsperado, valorObtenido);
            } else {
                Assert.fail("Respuesta de la api no tiene formato correcto");
            }
        });


    }

    public void configuroLosPathVariables(DataTable dataTable) {
        List<Map<String, String>> mapList = dataTable.asMaps();
        mapList.forEach(map -> { // map
            String pathName = map.get("key");
            String pathValue = map.get("value");
            requestSpecification.pathParam(pathName, pathValue);

        });
    }


    public void configuroElBodyRequest(String path , DataTable dataTable) {

        try {
            List<Map<String,String>> mapList = dataTable.asMaps();
            String pathFile = System.getProperty("user.dir") + "/src/test/resources/request.json/" + path;
            FileReader fileReader = new FileReader(pathFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bodyRequest = bufferedReader.lines().collect(Collectors.joining());
            JsonPath jsonPath = new JsonPath(bodyRequest);
            mapList.forEach(map -> {
                String nodo = map.get("key"); //name
                String valor = map.get("value"); // Test Automation
                String valueVariablePath = String.valueOf(jsonPath.getString(nodo)).trim(); //$name
                bodyRequest = bodyRequest.replace(valueVariablePath,valor);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Logger.getGlobal().log(Level.INFO, "BODY REQUES: {0}", bodyRequest); //IMPRIMIR EN CONSOLA



        }
    public void validoElEsquemaDeRespuesta (String path){
        File schema = new File(System.getProperty("user.dir") + "/src/test/resources/schemas/" + path);

        //C:\Users\Jose Vilchez\Desktop\CAPACITACION\Capacitacion01\src\test\resources\schemas\crear-usuario-schema.json

        Logger.getGlobal().log(Level.INFO, "BODY REQUES: {0}", response.getBody().asString()); //IMPRIMIR EN CONSOLA

        Assert.assertThat(response.getBody().asString(), JsonSchemaValidator.matchesJsonSchema(schema));


    }

}


