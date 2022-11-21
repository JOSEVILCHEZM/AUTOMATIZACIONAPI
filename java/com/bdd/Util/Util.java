package com.bdd.Util;

import io.cucumber.datatable.DataTable;
import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Util {

    public static String getValueFromDataTable(DataTable dataTable, String title) {
        //[
        // { cabecera=conten-type, value=aplication/json },
        // {  cabecera=app-code, value=aplication/json }


        // { method = GET , url = https: //regres.in/api/users }

        //]
        List<Map<String, String>> list = dataTable.asMaps();
        return (String) list.get(0).get(title);
    }
    public static Headers configurarHeaders (DataTable dataTable) {
        List<Header> headerList = new LinkedList<>();// declarar variable tipo headerList
        //[
        //
        //]

        List<Map<String, String>> listCabeceras = dataTable.asMaps();

        // [
        // { cabeceras = conten-type , value = aplication/json },
        // { cabeceras= header2, value = valor2}
        //  ]

        listCabeceras.forEach( map -> {
            // { cabeceras=conten-type, value=aplication/json }
            String nameCabecera = map.get("cabeceras");//Content-Type

            String valor = map.get("value");//aplication/json

            Header header = new Header(nameCabecera,valor);
         //Content-Type=aplication/json
            headerList.add(header);
        });
        Headers headers = new Headers(headerList);
         //[
        // Content-Type=aplication/json
        // App-code=ZZZ
        //]
        return headers;
    }


}
