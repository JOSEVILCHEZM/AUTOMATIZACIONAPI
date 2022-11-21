package com.bdd.StepDefinition;

import com.bdd.Step.ApiStep;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

public class ApiStepDefiniton {

    ApiStep apiStep;


    @Before
    public void beforeScenario(){
        apiStep = new ApiStep();

    }
    @Dado("que configuro las cabeceras")
    public void queConfiguroLasCabeceras(DataTable dataTable) {
        apiStep.queConfiguroLascabeceras(dataTable);
    }


    @Y("configuro los query params")
    public void configuroLosQueryParams(DataTable dataTable) {
        apiStep.configuroLosQueryParams(dataTable);
    }


    @Cuando("ejecuto el api")
    public void ejecutoElApi(DataTable dataTable) {
        apiStep.ejecutoElApi(dataTable);


    }


    @Entonces("valido el status code sea {string}")
    public void validoElStatusCodeSea(String statusCode) {
        apiStep.validoElStatusCodeSea(statusCode);

    }


    @Y("valido la respuesta del servicio")
    public void validoLaRespuestaDelServicio(DataTable dataTable) {
        apiStep.validoLaRespuestaDelServicio(dataTable);

    }

    @Y("configuro los path variables")
    public void configuroLosPathVariables(DataTable dataTable) {
        apiStep.configuroLosPathVariables(dataTable);
    }


    @Y("configuro el body request {string}")
    public void configuroElBodyRequest(String path, DataTable dataTable) {
        apiStep.configuroElBodyRequest(path, dataTable);

    }

    @Y("valido el esquema de respuesta {string}")
    public void validoElEsquemaDeRespuesta(String path) {
        apiStep.validoElEsquemaDeRespuesta(path);
    }


}
