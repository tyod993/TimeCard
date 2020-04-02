package com.audiokontroller.timecard.data.room.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.audiokontroller.timecard.data.model.TimeCard;
import com.audiokontroller.timecard.data.model.TimeEntry;
import com.audiokontroller.timecard.data.room.relation.EntryWithTasksAndBreaks;

import java.util.List;

public class TimeCardWithEntries {
    @Embedded public TimeCard timeCard;
    @Relation(entity = TimeEntry.class, parentColumn = "cardID", entityColumn = "timeCardID")
    public List<EntryWithTasksAndBreaks> timeEntries;
}
