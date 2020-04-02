package com.audiokontroller.timecard.data.room.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.audiokontroller.timecard.data.model.TimeCard;
import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.room.relation.TimeCardWithEntries;

import java.util.List;

public class UserWithTimeCards {
    @Embedded public User user;
    @Relation(entity = TimeCard.class, parentColumn = "userID", entityColumn = "userID")
    public List<TimeCardWithEntries> usersWithTimeCards;
}
