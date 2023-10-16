package com.futurebizops.kpi.utils;

import com.futurebizops.kpi.enums.PageDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class KPIUtils {

    KPIUtils() {
    }

    public static String getDirection(PageDirection pageDirection) {
        if (pageDirection == PageDirection.DESC) {
            return PageDirection.DESC.getCode();
        } else {
            return PageDirection.ASC.getCode();
        }
    }


    public static Pageable sort(Pageable page, String sortByParam, String pageDirection) {
        Pageable pageable = null;

        /**
         * if sort value and param value present, then sort as per direction and Param name
         * else if Param is present but direction is missing then its defaulted to ASC
         * */

        if (sortByParam != null && pageDirection != null && !pageDirection.isEmpty()) {
            if (pageDirection.equalsIgnoreCase("ASC"))
                pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(sortByParam).ascending());
            else if (pageDirection.equalsIgnoreCase("DESC"))
                pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(sortByParam).descending());
        } else if (sortByParam != null) {
            pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(sortByParam).ascending());
        } else {
            pageable = page;
        }
        return pageable;
    }
}
