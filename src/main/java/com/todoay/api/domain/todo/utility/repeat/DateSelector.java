package com.todoay.api.domain.todo.utility.repeat;

import com.todoay.api.domain.todo.exception.BadRepeatConditionException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface DateSelector {

    List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate);


    default  List<LocalDate> addDateIntoList(DateRepeator dateRepeator, LocalDate repeatStandardDate,LocalDate endPoint) {
        List<LocalDate> dates = new ArrayList<>();
        while (true) {
            LocalDate repeatedDate = dateRepeator.plus(repeatStandardDate);
            if(repeatedDate.isAfter(endPoint))
                break;
            dates.add(repeatedDate);
            repeatStandardDate = repeatedDate;
        }

        if(dates.isEmpty())
            throw new BadRepeatConditionException();
        return dates;
    }

}
