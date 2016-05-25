package edu.tsu.stochastic.automats.service.rest;

import edu.tsu.stochastic.automats.service.model.UzFormulaResultModel;
import edu.tsu.stochastic.automats.service.model.WnFormulaResultModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/formula")
public class FormulaService {
    //http://localhost:8080/stochastic-automats-service/formula/calculateWn
    @GET
    @Path("/calculateWn")
    @Produces("application/json")
    public WnFormulaResultModel calculateWnFormula() {
        WnFormulaResultModel resultModel = new WnFormulaResultModel();
        resultModel.setResult(122);
        return resultModel;
    }


    @GET
    @Path("/calculateUz/{r}/{a}/{e}/{m}")
    @Produces("application/json")
    public UzFormulaResultModel calculateEzFormula(
            @PathParam("r") double r,
            @PathParam("a") double a,
            @PathParam("e") double e,
            @PathParam("m") double m
    ) {
        double z = 1;
        double l = 1;
/*
        UzFormulaModel model = new UzFormulaModel(r, a, e, m, z, l);
        UzFormulaCalculator calculator = new UzFormulaCalculator(model);

        UzFormulaResultModel result = new UzFormulaResultModel();
        result.setResult(calculator.getResult());
        result.setR(calculator.getR());
        result.setP(calculator.getP());
        result.setQ(calculator.getQ());

        return result;*/

        return null;
    }

}
