package com.grabit.api.member;

import com.grabit.service.member.OrderDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
public class GetOrders {

    private static final String PAGE_NUMBER="0";
    private static final String PAGE_SIZE="10";
    private static final String ORDERING="ASC";

    @Autowired
    OrderDetailsService orderDetailsService;

    @GetMapping(value = "/member/{member_id}/orders",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getOrders(@RequestParam(value = "page_number",required = false,defaultValue = PAGE_NUMBER) int pageNumber,
                            @RequestParam(value = "page_size",required = false,defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(value = "order_by",required = false,defaultValue = ORDERING) String orderBy,
                            @PathVariable(value = "member_id") String id){
        return orderDetailsService.getOrderWithPaginationAndSorting(pageNumber,pageSize,orderBy,id);
    }
}
