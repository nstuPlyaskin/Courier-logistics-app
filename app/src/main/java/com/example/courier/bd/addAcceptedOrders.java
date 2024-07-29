package com.example.courier.bd;

public class addAcceptedOrders extends addOrder {

    public String id, deliveryFrom, deliveryTo, cargoName, cargoSize, cargoWeight, shippingBy, courierID, courierUsername;

    public addAcceptedOrders() {
    }

    public addAcceptedOrders(String id, String deliveryFrom, String deliveryTo, String cargoName,
                    String cargoSize, String cargoWeight, String shippingBy, String courierID, String courierUsername) {
        this.id = id;
        this.deliveryFrom = deliveryFrom;
        this.deliveryTo = deliveryTo;
        this.cargoName = cargoName;
        this.cargoSize = cargoSize;
        this.cargoWeight = cargoWeight;
        this.shippingBy = shippingBy;                                                               // when courier pick order this var !null and consist courier name


        this.courierID = courierID;
        this.courierUsername = courierUsername;

    }

}
