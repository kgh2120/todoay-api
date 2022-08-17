package com.todoay.api.domain.todo.utility.repeat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface DateSelector {

    List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate);


    default  List<LocalDate> addDateIntoList(DateRepeator dateRepeator, LocalDate repeatStandardDate,LocalDate endPoint) {
        List<LocalDate> dates = new ArrayList<>();
        while (true) {
            LocalDate weeks = dateRepeator.plus(repeatStandardDate);
            if(weeks.isAfter(endPoint))
                break;
            dates.add(weeks);
            repeatStandardDate = weeks;
        }
        return dates;
    }

}
