package com.todoay.api.domain.todo.utility.repeat.impl.selector;


import com.todoay.api.domain.todo.utility.repeat.DateRepeator;
import com.todoay.api.domain.todo.utility.repeat.DateSelector;

import java.time.LocalDate;
import java.util.List;

public class CustomWeeksDateSelector implements DateSelector {
    @Override
    public List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate) {
        LocalDate customeWeeks = repeatStandardDate.plusWeeks(count);
        return addDateIntoList(dateRepeator, repeatStandardDate, customeWeeks);
    }
}
