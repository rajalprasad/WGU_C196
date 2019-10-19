package com.example.wgu_c196.model;

public enum mCourseStat {
    INPROGRESS {
        @Override
        public String toString() {
            return "In_Progress";
        }
    },

    COMPLETED {
        @Override
        public String toString() {
            return "Completed";
        }
    },

    DROPPED {
        @Override
        public String toString() {
            return "Dropped";
        }
    },

    PLANTOTAKE {
        @Override
        public String toString() {
            return "Plan_to_take";
        }
    }
}

