package com.todoay.api.domain.todo.utility.repeat.impl.selector;


import com.todoay.api.domain.todo.utility.repeat.DateRepeator;
import com.todoay.api.domain.todo.utility.repeat.DateSelector;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomNumberDateSelector implements DateSelector {
    @Override
    public List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate) {
        List<LocalDate> selectedDates = new ArrayList<>();
        for (int i = 0; i < count ; i++) {
            repeatStandardDate = dateRepeator.plus(repeatStandardDate);
            selectedDates.add(repeatStandardDate);
        }
        return selectedDates;
    }

}
