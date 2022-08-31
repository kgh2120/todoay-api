package com.todoay.api.domain.todo.utility.repeat.impl.selector;

import com.todoay.api.domain.todo.utility.repeat.DateRepeator;
import com.todoay.api.domain.todo.utility.repeat.DateSelector;

import java.time.LocalDate;
import java.util.List;

public class NoEndDateSelector implements DateSelector {
    @Override
    public List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate) {
        LocalDate repeatEndDate = repeatStandardDate.plusYears(3);
        return addDateIntoList(dateRepeator, repeatStandardDate, repeatEndDate);
    }
}
