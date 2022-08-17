package com.todoay.api.domain.todo.utility.inter.select;


import com.todoay.api.domain.todo.utility.inter.DateRepeator;
import com.todoay.api.domain.todo.utility.inter.DateSelector;

import java.time.LocalDate;
import java.util.List;

public class CustomWeeksDateSelector implements DateSelector {
    @Override
    public List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate) {
        LocalDate customeWeeks = repeatStandardDate.plusWeeks(count);
        return addDateIntoList(dateRepeator, repeatStandardDate, customeWeeks);
    }
}
