package com.example.wgu_c196.model;

public enum mAssessType {
    OBJECTIVEASSESSMENT {
        @Override
        public String toString() {
            return "Objective_Assessment";
        }
    },

    PERFORMANCEASSESSMENT {
        @Override
        public String toString() {
            return "Performance_Assessment";
        }
    }
}
