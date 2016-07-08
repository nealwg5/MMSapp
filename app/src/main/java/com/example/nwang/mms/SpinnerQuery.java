package com.example.nwang.mms;


public class SpinnerQuery {

    private String spinQuery;
    private String spinQueryJW;

    public String[] NEJMitem()
    {
        String[] items = new String[]{"All Speciality",
                "Allergy/Immunology",
                "Cardiology",
                "Dermatology",
                "Emergency Medicine",
                "Endocrinology",
                "Gastroenterology",
                "Genetics",
                "Geriatrics/Aging",
                "Health Policy and Reform",
                "Hematology/Oncology",
                "Infectious Disease",
                "Nephrology",
                "Neurology/Neurosurgery",
                "Obstetrics/Gynecology",
                "Pediatrics",
                "Primary Care/Hospitalist",
                "Psychiatry",
                "Pulmonary/Critical Care",
                "Rheumatology",
                "Surgery"};
        return items;
    }

    public String[] JWitem()
    {
        String[] items = new String[]{"All Speciality",
                "Cardiology",
                "Dermatology",
                "Emergency Medicine",
                "Gastroenterology",
                "General Medicine",
                "HIV/AIDS Clinical Care",
                "Hospital Medicine",
                "Infectious Disease",
                "Neurology/Neurosurgery",
                "Oncology and Hematology",
                "Pediatrics and Adolescent Medicine",
                "Psychiatry",
                "Women's Health"};
        return items;
    }

    public String SpinnerNEJM(int SpinnerValue) {
        switch (SpinnerValue) {
            case 0:
                spinQuery = null;
                break;
            case 1:
                spinQuery = "topic:\"Allergy/Immunology\"";
                break;
            case 2:
                spinQuery = "topic:\"Cardiology\"";
                break;
            case 3:
                spinQuery = "topic:\"Dermatology\"";
                break;
            case 4:
                spinQuery = "topic:\"Emergency%20Medicine\"";
                break;
            case 5:
                spinQuery = "topic:\"Endocrinology\"";
                break;
            case 6:
                spinQuery = "topic:\"Gastroenterology\"";
                break;
            case 7:
                spinQuery = "topic:\"Genetics\"";
                break;
            case 8:
                spinQuery = "topic:\"Geriatrics/Aging\"";
                break;
            case 9:
                spinQuery = "topic:\"Health%20Policy%20and%20Reform\"";
                break;
            case 10:
                spinQuery = "topic:\"Hematology/Oncology\"";
                break;
            case 11:
                spinQuery = "topic:\"Infectious%20Disease\"";
                break;
            case 12:
                spinQuery = "topic:\"Nephrology\"";
                break;
            case 13:
                spinQuery = "topic:\"Neurology/Neurosurgery\"";
            case 14:
                spinQuery = "topic:\"Obstetrics/Gynecology\"";
                break;
            case 15:
                spinQuery = "topic:\"Pediatrics\"";
                break;
            case 16:
                spinQuery = "topic:\"Primary%20Care/Hospitalist\"";
                break;
            case 17:
                spinQuery = "topic:\"Psychiatry\"";
                break;
            case 18:
                spinQuery = "topic:\"Pulmonary/Critical Care\"";
                break;
            case 19:
                spinQuery = "topic:\"Rheumatology\"";
                break;
            case 20:
                spinQuery = "topic:\"Surgery\"";
                break;
        }


        return spinQuery;
    }

    public String SpinnerJW(int SpinnerValue) {
        switch (SpinnerValue) {
            case 0:
                spinQueryJW = null;
                break;
            case 1:
                //Cardiology
                spinQueryJW = "specialty:jwc";
                break;
            case 2:
                //Dermatology
                spinQueryJW = "specialty:jwd";
                break;
            case 3:
                //Emergency Med
                spinQueryJW = "specialty:jwe";
                break;
            case 4:
                //Gastro
                spinQueryJW = "specialty:jwg";
                break;
            case 5:
                //general med
                spinQueryJW = "specialty:jwa";
                break;
            case 6:
                //HIV
                spinQueryJW = "specialty:acc";
                break;
            case 7:
                //Hospital Med
                spinQueryJW = "specialty:jhm";
                break;
            case 8:
                //infectious disease
                spinQueryJW = "specialty:jwi";
                break;
            case 9:
                //Neuro
                spinQueryJW = "specialty:jwn";
            case 10:
                //Oncology
                spinQueryJW = "specialty:joh";
                break;
            case 11:
                //pediatric
                spinQueryJW = "specialty:jpa";
                break;
            case 12:
                //Pyshciatry
                spinQueryJW = "specialty:jwp";
                break;
            case 13:
                //Women's health
                spinQueryJW = "specialty:jww";
                break;
        }
        return spinQueryJW;
    }

}
