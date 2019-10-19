package com.example.wgu_c196.ui;

public enum RecContext {
    MAIN {
        @Override
        public String toString() {
            return "Parent";
        }
    },
    CHILD {
        @Override
        public String toString() {
            return "Child";
        }
    },
    ADD {
        @Override
        public String toString() {
            return "Add";
        }
    }
}
