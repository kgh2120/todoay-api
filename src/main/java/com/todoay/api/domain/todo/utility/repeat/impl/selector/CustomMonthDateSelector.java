package com.todoay.api.domain.todo.utility.repeat.impl.selector;


import com.todoay.api.domain.todo.utility.repeat.DateRepeator;
import com.todoay.api.domain.todo.utility.repeat.DateSelector;

import java.time.LocalDate;
import java.util.List;

public class CustomMonthDateSelector implements DateSelector {
    @Override
    public List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate) {
        LocalDate customMonth = repeatStandardDate.plusMonths(count);
        return addDateIntoList(dateRepeator, repeatStandardDate, customMonth);
    }
}
