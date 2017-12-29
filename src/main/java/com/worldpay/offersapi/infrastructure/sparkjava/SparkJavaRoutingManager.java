package com.worldpay.offersapi.infrastructure.sparkjava;

import com.worldpay.offersapi.controller.CancelOfferController;
import com.worldpay.offersapi.controller.CreateOfferController;
import com.worldpay.offersapi.controller.DisplayOfferController;
import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;
import com.worldpay.offersapi.domain.entity.parameters.RequestParameterBuilder;
import com.worldpay.offersapi.library.RoutingManager;
import spark.QueryParamsMap;
import spark.Request;
import spark.ResponseTransformer;

import static com.worldpay.offersapi.library.OutputMapForJson.output;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.notFound;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

public class SparkJavaRoutingManager implements RoutingManager {

    int httpServerPort;
    private CreateOfferController createOfferController;
    private DisplayOfferController displayOfferController;
    private CancelOfferController cancelOfferController;
    private ResponseTransformer responseTransformer;

    public SparkJavaRoutingManager(
        CreateOfferController createOfferController,
        DisplayOfferController displayOfferController,
        CancelOfferController cancelOfferController,
        ResponseTransformer responseTransformer,
        int httpServerPort
    ) {
        this.createOfferController = createOfferController;
        this.displayOfferController = displayOfferController;
        this.cancelOfferController = cancelOfferController;
        this.responseTransformer = responseTransformer;
        this.httpServerPort = httpServerPort;
    }

    private static RequestParameter extractRequestParameters(
        Request request
    ) {

        QueryParamsMap parameterMap = request.queryMap();

        RequestParameterBuilder requestParameterBuilder = RequestParameterBuilder.getInstance();

        requestParameterBuilder
            .setMerchantId(
                request.params("merchantId")
            );

        if (request
                .params("offerId") != null) {

            requestParameterBuilder
                .setOfferId(
                    request
                        .params("offerId")
                );
        }

        if (parameterMap
            .hasKey("description")) {

            requestParameterBuilder
                .setDescription(
                    parameterMap
                        .get("description")
                        .value()
                );
        }

        if (parameterMap
            .hasKey("currency")) {

            requestParameterBuilder
                .setCurrency(
                    parameterMap
                        .get("currency")
                        .value()
                );
        }

        if (parameterMap
            .hasKey("price")) {

            requestParameterBuilder
                .setPrice(
                    parameterMap
                        .get("price")
                        .value()
                );
        }

        if (parameterMap
            .hasKey("expiryTime")) {

            requestParameterBuilder
                .setExpiryTime(
                    parameterMap
                        .get("expiryTime")
                        .value()
                );
        }

        return
            requestParameterBuilder
                .build();
    }

    public void route() {

        port(httpServerPort);

        exception(IllegalStateException.class, (exception, request, response) -> {
            response.status(500);
            try {
                response.body(responseTransformer.render(output("error", exception.getMessage())));
            } catch (Exception e) {
                //noop
            }
        });

        notFound((req, res) -> responseTransformer.render(output("error", "No resource found - 404")));

        put(
            "/:merchantId/create-offer",
            (request, response) -> createOfferController.createOfferAction(extractRequestParameters(request)),
            responseTransformer
        );

        get(
            "/:merchantId/display-offer/:offerId",
            (request, response) -> displayOfferController.displayOfferAction(extractRequestParameters(request)),
            responseTransformer
        );

        post(
            "/:merchantId/cancel-offer/:offerId",
            (request, response) -> cancelOfferController.cancelOfferAction(extractRequestParameters(request)),
            responseTransformer
        );

        after((request, response) -> {
            response.type("application/json");
        });
    }
}
