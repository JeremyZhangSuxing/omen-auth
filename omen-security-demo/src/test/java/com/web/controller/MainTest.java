package com.web.controller;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class MainTest {

    public static void main(String[] args) {
        FilterChain filterChain = new FilterChain();
        filterChain.add(new HtmlFilter()).add(new TextFilter());
        Request request = new Request();
        request.setString("request");
        Response response = new Response();
        response.setString("response");
        filterChain.doFilter(request, response);
    }


}

interface Filter {
    default FilterChain add(Filter filter) {
        return filter.add(filter);
    }

    void doFiler(Request request, Response response, FilterChain filterChain);
}

class HtmlFilter implements Filter {

    @Override
    public void doFiler(Request request, Response response, FilterChain filterChain) {
        String string = request.getString();
        System.out.println("HtmlFilter.doFiler request" + string);
        filterChain.doFilter(request, response);
        String responseString = response.getString();
        System.out.println("HtmlFilter.doFiler response" + responseString);
    }
}

class TextFilter implements Filter {

    @Override
    public void doFiler(Request request, Response response, FilterChain filterChain) {
        String string = request.getString();
        System.out.println("TextFilter.doFiler request" + string);
        filterChain.doFilter(request, response);
        String responseString = response.getString();
        System.out.println("TextFilter.doFiler response" + responseString);
    }
}

class FilterChain {
    private List<Filter> filters = new ArrayList<>();
    private int index = 0;

    public FilterChain add(Filter filter) {
        filters.add(filter);
        return this;
    }

    // 责任链到此判定会锻炼断链状态
    public void doFilter(Request request, Response response) {
        System.out.println("FilterChain.doFilter 处理所有的filter 开始");
        if (index == filters.size()) {
            System.out.println("FilterChain.doFilter the last filter 链条结束");
        } else {
            Filter filter = filters.get(index);
            index++;
            filter.doFiler(request, response, this);
        }

    }
}

@Data
class Request {
    private String string;
}

@Data
class Response {
    private String string;
}
