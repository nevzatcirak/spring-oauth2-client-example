package com.nevzatcirak.examples.oauth2.opa.voter;

import java.util.Map;

/**
 * @author Nevzat ÇIRAK
 * @mail ncirak@havelsan.com.tr
 * Created by ncirak at 17/07/2020
 */
public class OPADataRequest {

    Map<String, Object> input;

    public OPADataRequest(Map<String, Object> input) {
        this.input = input;
    }

    public Map<String, Object> getInput() {
        return this.input;
    }

}
