package com.todoay.api.domain.todo.utility;


import com.todoay.api.domain.todo.utility.inter.DateRepeator;
import com.todoay.api.domain.todo.utility.inter.repeat.DailyDateRepeator;
import com.todoay.api.domain.todo.utility.inter.repeat.MonthDateRepeator;
import com.todoay.api.domain.todo.utility.inter.repeat.WeeksDateRepeator;
import com.todoay.api.domain.todo.utility.inter.repeat.YearDateRepeator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RepeatOption {
    DAILY(new DailyDateRepeator()),
    WEEKS(new WeeksDateRepeator()),
    MONTH(new MonthDateRepeator()),
    YEARS(new YearDateRepeator()),
    CUSTOM(null)
;
    private final DateRepeator dateRepeator;


}
