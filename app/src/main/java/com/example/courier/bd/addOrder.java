package com.example.courier.bd;

//

public class addOrder {
    public String id, deliveryFrom, deliveryTo, cargoName, cargoSize, cargoWeight;

    public addOrder() {
    }

    public addOrder(String id, String deliveryFrom, String deliveryTo, String cargoName,
                    String cargoSize, String cargoWeight) {
        this.id = id;
        this.deliveryFrom = deliveryFrom;
        this.deliveryTo = deliveryTo;
        this.cargoName = cargoName;
        this.cargoSize = cargoSize;
        this.cargoWeight = cargoWeight;                                                             // when courier pick order this var !null and consist courier name

    }

}
