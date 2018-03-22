package com.graction.developer.zoocaster.Model.Address;

import com.graction.developer.zoocaster.Interface.ListItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JeongTaehyun on 2018-02-07.
 * Powered by Google
 *
 * @url : https://developers.google.com/places/web-service/policies#logo_requirements
 */

/*
 * 구글 주소 검색 Model
 */

public class AddressModel {
    private ArrayList<Prediction> predictions;

    public static Map<String, String> getParameter(String input) {
        Map<String, String> params = new HashMap<>();
        params.put("input", input);
        params.put("language", "ko");
        params.put("components", "country:kr");
        params.put("key", "AIzaSyBVNOVufLmeLx4sWqAjJDSwR8mIrGawdbw");
        return params;
    }

    public ArrayList<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(ArrayList<Prediction> predictions) {
        this.predictions = predictions;
    }

    @Override
    public String toString() {
        return "AddressModel{" +
                "predictions=" + predictions +
                '}';
    }

    public class Prediction implements ListItem {
        private String description, id, place_id, reference;
        private ArrayList<String> types;
        private Matched_Substring[] matched_substrings;
        private ArrayList<Matched_Substring> terms;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public ArrayList<String> getTypes() {
            return types;
        }

        public void setTypes(ArrayList<String> types) {
            this.types = types;
        }

        public Matched_Substring[] getMatched_substrings() {
            return matched_substrings;
        }

        public void setMatched_substrings(Matched_Substring[] matched_substrings) {
            this.matched_substrings = matched_substrings;
        }

        public ArrayList<Matched_Substring> getTerms() {
            return terms;
        }

        public void setTerms(ArrayList<Matched_Substring> terms) {
            this.terms = terms;
        }

        @Override
        public String toString() {
            return "Prediction{" +
                    "description='" + description + '\'' +
                    ", id='" + id + '\'' +
                    ", place_id='" + place_id + '\'' +
                    ", reference='" + reference + '\'' +
                    ", types=" + types +
                    ", matched_substrings=" + Arrays.toString(matched_substrings) +
                    ", terms=" + terms +
                    '}';
        }

        public class Matched_Substring implements Serializable{
            private int length, offset;

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            @Override
            public String toString() {
                return "Matched_Substring{" +
                        "length=" + length +
                        ", offset=" + offset +
                        '}';
            }
        }

        public class Structured_formatting implements Serializable{
            private String main_text, secondary_text;
            private Matched_Substring[] main_text_matched_substrings;

            public String getMain_text() {
                return main_text;
            }

            public void setMain_text(String main_text) {
                this.main_text = main_text;
            }

            public String getSecondary_text() {
                return secondary_text;
            }

            public void setSecondary_text(String secondary_text) {
                this.secondary_text = secondary_text;
            }

            public Matched_Substring[] getMain_text_matched_substrings() {
                return main_text_matched_substrings;
            }

            public void setMain_text_matched_substrings(Matched_Substring[] main_text_matched_substrings) {
                this.main_text_matched_substrings = main_text_matched_substrings;
            }

            @Override
            public String toString() {
                return "Structured_formatting{" +
                        "main_text='" + main_text + '\'' +
                        ", secondary_text='" + secondary_text + '\'' +
                        ", main_text_matched_substrings=" + Arrays.toString(main_text_matched_substrings) +
                        '}';
            }
        }

    }
}
