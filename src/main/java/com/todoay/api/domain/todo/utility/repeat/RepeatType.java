package com.todoay.api.domain.todo.utility.repeat;


import com.todoay.api.domain.todo.utility.repeat.impl.repeator.DailyDateRepeator;
import com.todoay.api.domain.todo.utility.repeat.impl.repeator.MonthDateRepeator;
import com.todoay.api.domain.todo.utility.repeat.impl.repeator.WeeksDateRepeator;
import com.todoay.api.domain.todo.utility.repeat.impl.repeator.YearDateRepeator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RepeatType {
    DAILY(new DailyDateRepeator()),
    WEEKS(new WeeksDateRepeator()),
    MONTH(new MonthDateRepeator()),
    YEARS(new YearDateRepeator())
;
    private final DateRepeator dateRepeator;


}
