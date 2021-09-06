/* Copyright © 2021 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
  CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.
 */
package iview.rental.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sshchahratsou
 */
public class RentalMain {
    public static void main(String[] args) {
        List<Building> buildings = init();

        //How many apartments per owner
        Map<String, Integer> result = buildings.stream()
                .flatMap(building -> building.getApartments().stream())
                .collect(
                        HashMap::new,
                        (map, appt) -> {
                            String ownerName = appt.getOwner().getName();
                            map.putIfAbsent(ownerName, 0);
                            map.computeIfPresent(ownerName, (name, count) -> count + 1);
                        },
                        HashMap::putAll);
        result.size();
    }

    private static List<Building> init() {
        Owner bill = new Owner();
        bill.setName("bill");

        Building main = new Building();
        main.setAddress("Main str");

        Apartment pent = new Penthouse();
        pent.setUnit("1");
        pent.setOwner(bill);

        Apartment stud = new Studio();
        stud.setUnit("2");
        stud.setOwner(bill);

        main.setApartments(List.of(pent, stud));

        return Collections.singletonList(main);
    }
}
