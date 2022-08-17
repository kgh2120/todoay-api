package com.todoay.api.domain.todo.utility.inter.select;


import com.todoay.api.domain.todo.utility.inter.DateRepeator;
import com.todoay.api.domain.todo.utility.inter.Selector;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OneMonthSelector implements Selector {
    @Override
    public List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate) {
        List<LocalDate> selectedDates = new ArrayList<>();
        LocalDate repeatEndDate = repeatStandardDate.plusMonths(1);
        while (true) {
            LocalDate weeks = dateRepeator.plus(repeatStandardDate);
            if(weeks.isAfter(repeatEndDate))
                break;
            selectedDates.add(weeks);
            repeatStandardDate = weeks;
        }
        return selectedDates;
    }
}
