package com.personalieltscoach.data.seed

import com.personalieltscoach.data.local.entity.SentenceCardEntity

object SentenceTrialData {
    fun cards(now: Long): List<SentenceCardEntity> = Paul1000SentencePack.cards(now)
}
