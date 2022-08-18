package com.todoay.api.domain.todo.utility.repeat.impl.selector;


import com.todoay.api.domain.todo.utility.repeat.DateRepeator;
import com.todoay.api.domain.todo.utility.repeat.DateSelector;

import java.time.LocalDate;
import java.util.List;

public class OneMonthDateSelector implements DateSelector {
    @Override
    public List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate) {
        LocalDate repeatEndDate = repeatStandardDate.plusMonths(1);
        return addDateIntoList(dateRepeator, repeatStandardDate, repeatEndDate);
    }
}
