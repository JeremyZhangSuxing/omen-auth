package com.web.controller;

public class MainTest {

    public static void main(String[] args) {

    }


    interface Filter {
        default FilterChain add(Filter filter) {
            return filter.add(filter);
        }

        boolean doFiler(Request request, Response response, FilterChain filterChain);
    }

    class HtmlFilter implements Filter {

        @Override
        public boolean doFiler(Request request, Response response, FilterChain filterChain) {
            return false;
        }
    }

    class TextFilter implements Filter {

        @Override
        public boolean doFiler(Request request, Response response, FilterChain filterChain) {
            return false;
        }
    }

    class FilterChain {
        boolean doFilter(Request request, Response response) {
            return false;
        }
    }

    class Request {
        private String string;
    }

    class Response {
        private String string;
    }
}
