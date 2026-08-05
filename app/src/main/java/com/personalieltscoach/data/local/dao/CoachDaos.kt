package com.personalieltscoach.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.personalieltscoach.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun observe(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE id = 1")
    suspend fun get(): UserProfileEntity?

    @Upsert
    suspend fun upsert(profile: UserProfileEntity)

    @Query("DELETE FROM user_profile")
    suspend fun clear()
}

@Dao
interface WordDao {
    @Query("SELECT * FROM words ORDER BY id")
    fun observeAll(): Flow<List<WordItemEntity>>

    @Query("SELECT * FROM words WHERE wrongCount > 0 ORDER BY wrongCount DESC, lastWrongAt DESC")
    fun observeWrong(): Flow<List<WordItemEntity>>

    @Query("SELECT * FROM words WHERE status = 'NEW' ORDER BY id LIMIT :limit")
    suspend fun getNew(limit: Int): List<WordItemEntity>

    @Query("SELECT * FROM words WHERE status != 'NEW' AND status != 'MASTERED' AND nextReviewAt <= :now ORDER BY nextReviewAt LIMIT :limit")
    suspend fun getDue(now: Long, limit: Int): List<WordItemEntity>

    @Query("SELECT * FROM words WHERE LOWER(word) = LOWER(:word) LIMIT 1")
    suspend fun find(word: String): WordItemEntity?

    @Query("SELECT COUNT(*) FROM words")
    suspend fun count(): Int

    @Query("SELECT COUNT(*) FROM words WHERE status = :status")
    fun observeStatusCount(status: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM words WHERE wrongCount > 0")
    fun observeWrongCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(words: List<WordItemEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: WordItemEntity): Long

    @Upsert
    suspend fun upsert(word: WordItemEntity)

    @Query("DELETE FROM words")
    suspend fun clear()
}

@Dao
interface PlanDao {
    @Query("SELECT * FROM daily_plans WHERE date = :date LIMIT 1")
    fun observePlan(date: String): Flow<DailyPlanEntity?>

    @Query("SELECT * FROM daily_plans WHERE date = :date LIMIT 1")
    suspend fun getPlan(date: String): DailyPlanEntity?

    @Query("SELECT * FROM study_tasks WHERE date = :date ORDER BY id")
    fun observeTasks(date: String): Flow<List<StudyTaskEntity>>

    @Query("SELECT * FROM study_tasks WHERE date = :date ORDER BY id")
    suspend fun getTasks(date: String): List<StudyTaskEntity>

    @Query("SELECT * FROM study_tasks WHERE date = :date AND type = :type LIMIT 1")
    suspend fun getTask(date: String, type: String): StudyTaskEntity?

    @Upsert
    suspend fun upsertPlan(plan: DailyPlanEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTasks(tasks: List<StudyTaskEntity>)

    @Upsert
    suspend fun upsertTask(task: StudyTaskEntity)

    @Query("DELETE FROM daily_plans")
    suspend fun clearPlans()

    @Query("DELETE FROM study_tasks")
    suspend fun clearTasks()
}

@Dao
interface ContentDao {
    @Query("SELECT * FROM placement_questions ORDER BY id")
    suspend fun questions(): List<PlacementQuestionEntity>

    @Query("SELECT * FROM reading_texts ORDER BY id")
    fun observeReadings(): Flow<List<ReadingTextEntity>>

    @Query("SELECT COUNT(*) FROM placement_questions")
    suspend fun questionCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestions(items: List<PlacementQuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReadings(items: List<ReadingTextEntity>)

    @Query("SELECT COUNT(*) FROM reading_texts")
    suspend fun readingCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveSentence(item: SavedSentenceEntity)

    @Query("DELETE FROM saved_sentences")
    suspend fun clearSavedSentences()
}

@Dao
interface SentenceCardDao {
    @Query("SELECT COUNT(*) FROM sentence_cards")
    fun observeCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM sentence_cards WHERE status != 'NEW'")
    fun observeStartedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM sentence_cards WHERE status = 'MASTERED'")
    fun observeMasteredCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM sentence_cards")
    suspend fun count(): Int

    @Query(
        "SELECT * FROM sentence_cards " +
            "WHERE status != 'NEW' AND status != 'MASTERED' AND nextReviewAt <= :now " +
            "ORDER BY nextReviewAt, id LIMIT :limit"
    )
    suspend fun getDue(now: Long, limit: Nx×]­¢G§²ÚîÆ­yÑ5…á]¥‘Ñ  ¤(€€€€€€€€€€€€¤ìQ•áĞ ‹’â/¢ö÷š"[º‡Bšïêÿ¢.Ç¦~Ìˆ¤ô(€€€€€€€€€€€Q•áĞ (€€€€€€€€€€€€€€€€‹šr_¢¾ïRÇš&/šrë¢¾·¦~Ïšr7–*‡š>C’úo¾ò3’â7¢ÂR =Á•¹'’â7¦r¢šA$-•ç¾ò3’æ’â7’êŸR|Q½­•¸ƒ¢ÒçR£ˆ€¬(€€€€€€€€€€€€€€€€€€€€‹’â7–B3š&/šrë–>¿R£–Ã¦~Ï–>¿¢÷V—šr'–Ş»–òˆ°(€€€€€€€€€€€€€€€ÍÑå±”€ô5…Ñ•É¥…±Q¡•µ”¹ÑåÁ½É…Á¡ä¹‰½‘åMµ…±°°(€€€€€€€€€€€€€€€½±½È€ô5…Ñ•É¥…±Q¡•µ”¹½±½ÉM¡•µ”¹½¹MÕÉ™…•Y…É¥…¹Ğ(€€€€€€€€€€€€¤(€€€€€€€ô((€€€€€€€M•Ñ¥½¹…É ‰$ƒ¢ºûö¸ˆ¤ì(€€€€€€€€€€€Q•áĞ ‹–æÏ–>Ã¾òiAS¾ò#–Û’î[–æÏ–>Ã¦ŠVg¾ò3šjšr«–B¿R£¾ò$ˆ¤(€€€€€€€€€€€=ÕÑ±¥¹•‘Q•áÑ¥•± (€€€€€€€€€€€€€€€Ù…±Õ”€ô…Á¥-•ä°(€€€€€€€€€€€€€€€½¹Y…±Õ•¡…¹”€ôì…Á¥-•ä€ô¥Ğô°(€€€€€€€€€€€€€€€±…‰•°€ôìQ•áĞ ‰=Á•¹$A$-•äˆ¤ô°(€€€€€€€€€€€€€€€Ù¥ÍÕ…±QÉ…¹Í™½Éµ…Ñ¥½¸€ô¥˜€¡Í¡½İ-•ä¤Y¥ÍÕ…±QÉ…¹Í™½Éµ…Ñ¥½¸¹9½¹”•±Í”A…ÍÍİ½É‘Y¥ÍÕ…±QÉ…¹Í™½Éµ…Ñ¥½¸ ¤°(€€€€€€€€€€€€€€€ÑÉ…¥±¥¹%½¸€ôì(€€€€€€€€€€€€€€€€€€€%½¹	ÕÑÑ½¸¡½¹±¥¬€ôìÍ¡½İ-•ä€ô€…Í¡½İ-•äô¤ì(€€€€€€€€€€€€€€€€€€€€€€€%½¸ (€€€€€€€€€€€€€€€€€€€€€€€€€€€¥˜€¡Í¡½İ-•ä¤%½¹Ì¹•™…Õ±Ğ¹Y¥Í¥‰¥±¥Ñå=™˜•±Í”%½¹Ì¹•™…Õ±Ğ¹Y¥Í¥‰¥±¥Ñä°(€€€€€€€€€€€€€€€€€€€€€€€€€€€½¹Ñ•¹Ñ•ÍÉ¥ÁÑ¥½¸€ô¥˜€¡Í¡½İ-•ä¤€‹¦jC¢^<ˆ•±Í”€‹šbû’èˆ(€€€€€€€€€€€€€€€€€€€€€€€€¤(€€€€€€€€€€€€€€€€€€€ô(€€€€€€€€€€€€€€€ô°(€€€€€€€€€€€€€€€µ½‘¥™¥•È€ô5½‘¥™¥•È¹™¥±±5…á]¥‘Ñ  ¤°(€€€€€€€€€€€€€€€Í¥¹±•1¥¹”€ôÑÉÕ”(€€€€€€€€€€€€¤(€€€€€€€€€€€Q•áĞ (€€€€€€€€€€€€€€€€‰-•äƒ’öÿR ¹‘É½¥ƒ–*ƒ–¾–¶c–
£¾ò3’î’şw–¶c–r£šr³šrë¾ò3’â7–g–—’î‚š"[–¶›’æƒšVÃš6»–êOˆ°(€€€€€€€€€€€€€€€ÍÑå±”€ô5…Ñ•É¥…±Q¡•µ”¹ÑåÁ½É…Á¡ä¹‰½‘åMµ…±°(€€€€€€€€€€€€¤(€€€€€€€€€€€=ÕÑ±¥¹•‘	ÕÑÑ½¸ (€€€€€€€€€€€€€€€½¹±¥¬€ôìÙ¥•İ5½‘•°¹Í…Ù•Á¥-•ä¡…Á¥-•ä¤ô°(€€€€€€€€€€€€€€€µ½‘¥™¥•È€ô5½‘¥™¥•È¹™¥±±5…á]¥‘Ñ  ¤(€€€€€€€€€€€€¤ìQ•áĞ ‹’şw–¶`A$-•äˆ¤ô((€€€€€€€€€€€áÁ½Í•‘É½Á‘½İ¹5•¹Õ	½à¡•áÁ…¹‘•€ôµ½‘•±5•¹Ô°½¹áÁ…¹‘•‘¡…¹”€ôìµ½‘•±5•¹Ô€ô¥Ğô¤ì(€€€€€€€€€€€€€€€=ÕÑ±¥¹•‘Q•áÑ¥•± (€€€€€€€€€€€€€€€€€€€Ù…±Õ”€ôÍ•ÑÑ¥¹Ì¹µ½‘•°°(€€€€€€€€€€€€€€€€€€€½¹Y…±Õ•¡…¹”€ôíô°(€€€€€€€€€€€€€€€€€€€É•…‘=¹±ä€ôÑÉÕ”°(€€€€€€€€€€€€€€€€€€€±…‰•°€ôìQ•áĞ ‹¦îc¢º“š¢‡–z,ˆ¤ô°(€€€€€€€€€€€€€€€€€€€ÑÉ…¥±¥¹%½¸€ôìáÁ½Í•‘É½Á‘½İ¹5•¹Õ•™…Õ±ÑÌ¹QÉ…¥±¥¹%½¸¡µ½‘•±5•¹Ô¤ô°(€€€€€€€€€€€€€€€€€€€µ½‘¥™¥•È€ô5½‘¥™¥•È¹µ•¹Õ¹¡½È ¤¹™¥±±5…á]¥‘Ñ  ¤(€€€€€€€€€€€€€€€€¤(€€€€€€€€€€€€€€€áÁ½Í•‘É½Á‘½İ¹5•¹Ô¡•áÁ…¹‘•€ôµ½‘•±5•¹Ô°½¹¥Íµ¥ÍÍI•ÅÕ•ÍĞ€ôìµ½‘•±5•¹Ô€ô™…±Í”ô¤ì(€€€€€€€€€€€€€€€€€€€µ½‘•±Ì¹™½É… ìµ½‘•°€´ø(€€€€€€€€€€€€€€€€€€€€€€€É½Á‘½İ¹5•¹Õ%Ñ•´ (€€€€€€€€€€€€€€€€€€€€€€€€€€€Ñ•áĞ€ôìQ•áĞ¡µ½‘•°¤ô°(€€€€€€€€€€€€€€€€€€€€€€€€€€€½¹±¥¬€ôì(€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€Ù¥•İ5½‘•°¹Í•Ñ5½‘•°¡µ½‘•°¤(€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€µ½‘•±5•¹Ô€ô™…±Í”(€€€€€€€€€€€€€€€€€€€€€€€€€€€ô(€€€€€€€€€€€€€€€€€€€€€€€€¤(€€€€€€€€€€€€€€€€€€€ô(€€€€€€€€€€€€€€€ô(€€€€€€€€€€€ô(€€€€€€€€€€€9Õµ‰•ÉM•ÑÑ¥¹œ ‹š¾?š^”$ƒ¢ÂR£’â+¦f@ˆ°Í•ÑÑ¥¹Ì¹‘…¥±å¥1¥µ¥Ğ°Ù¥•İ5½‘•°èéÍ•Ñ¥1¥µ¥Ğ¤(€€€€€€€€€€€Q•áĞ ‹’î+š^—–ŞË¢ÂR£¾òh‘ÕÍ…”€¼€‘íÍ•ÑÑ¥¹Ì¹‘…¥±å¥1¥µ¥Ñôˆ¤(€€€€€€€€€€€	ÕÑÑ½¸ (€€€€€€€€€€€€€€€½¹±¥¬€ôÙ¥•İ5½‘•°èéÑ•ÍÑ½¹¹•Ñ¥½¸°(€€€€€€€€€€€€€€€•¹…‰±•€ô€…½¹¹•Ñ¥½¸¹±½…‘¥¹œ°(€€€€€€€€€€€€€€€µ½‘¥™¥•È€ô5½‘¥™¥•È¹™¥±±5…á]¥‘Ñ  ¤(€€€€€€€€€€€€¤ì(€€€€€€€€€€€€€€€¥˜€¡½¹¹•Ñ¥½¸¹±½…‘¥¹œ¤¥ÉÕ±…ÉAÉ½É•ÍÍ%¹‘¥…Ñ½È¡5½‘¥™¥•È¹Í¥é” Äà¹‘À¤°ÍÑÉ½­•]¥‘Ñ €ô€È¹‘À¤(€€€€€€€€€€€€€€€•±Í”Q•áĞ ‹šÖ/¢¾W¢ş{š:”ˆ¤(€€€€€€€€€€€ô(€€€€€€€€€€€½¹¹•Ñ¥½¸¹Ù…±Õ”ü¹±•Ğì¥˜€¡¥Ğ¤Q•áĞ ‹¢ş{š:—š"C–*|ˆ°½±½È€ô5…Ñ•É¥…±Q¡•µ”¹½±½ÉM¡•µ”¹ÁÉ¥µ…Éä¤ô(€€€€€€€€€€€ÉÉ½ÉQ•áĞ¡½¹¹•Ñ¥½¸¹•ÉÉ½È¤(€€€€€€€€€€€=ÕÑ±¥¹•‘	ÕÑÑ½¸¡½¹±¥¬€ôÙ¥•İ5½‘•°èé±•…É¥…¡”°µ½‘¥™¥•È€ô5½‘¥™¥•È¹™¥±±5…á]¥‘Ñ  ¤¤ì(€€€€€€€€€€€€€€€Q•áĞ ‹šâ¦f$ƒòO–¶`ˆ¤(€€€€€€€€€€€ô(€€€€€€€ô((€€€€€€€M•Ñ¥½¹…É ‹–¶›’æƒ¢ºûö¸ˆ¤ì(€€€€€€€€€€€9Õµ‰•ÉM•ÑÑ¥¹œ ‹š¾?š^—šZÃ¢¾7šVÃ¦<ˆ°Í•ÑÑ¥¹Ì¹‘…¥±å9•İ]½É‘Ì°Ù¥•İ5½‘•°èéÍ•Ñ9•İ]½É‘Ì¤(€€€€€€€€€€€9Õµ‰•ÉM•ÑÑ¥¹œ ‹š¾?š^—–’7’æƒ¢¾7šVÃ¦<ˆ°Í•ÑÑ¥¹Ì¹‘…¥±åI•Ù¥•İ]½É‘Ì°Ù¥•İ5½‘•°èéÍ•ÑI•Ù¥•İ]½É‘Ì¤(€€€€€€€€€€€9Õµ‰•ÉM•ÑÑ¥¹œ ‹š¾?š^—Êû¢¾ï–>—–¶CšVÃ¦<ˆ°Í•ÑÑ¥¹Ì¹‘…¥±åM•¹Ñ•¹•Ì°Ù¥•İ5½‘•°èéÍ•ÑM•¹Ñ•¹•Ì¤(€€€€€€€€€€€Q•áĞ ‹–öO–&7n»š‚¾òi%1QL€Ü¸Àˆ¤(€€€€€€€€€€€Q•áĞ (€€€€€€€€€€€€€€€€‹šVÃ¦?’ş»šRç’òk’î;’â/’âš²‡Rš"Cš¾?š^—¢º‡–"K–ò–/RšV#ˆ°(€€€€€€€€€€€€€€€ÍÑå±”€ô5…Ñ•É¥…±Q¡•µ”¹ÑåÁ½É…Á¡ä¹‰½‘åMµ…±°(€€€€€€€€€€€€¤(€€€€€€€ô((€€€€€€€M•Ñ¥½¹…É ‹–êSR£šnÓšZÀˆ¤ì(€€€€€€€€€€€Q•áĞ ‹–öO–&7&#šr³¾òh‘í	Õ¥±‘½¹™¥œ¹YIM%=9}95ôˆ¤(€€€€€€€€€€€=ÕÑ±¥¹•‘Q•áÑ¥•± (€€€€€€€€€€€€€€€Ù…±Õ”€ôÕÁ‘…Ñ•I•Á½Í¥Ñ½Éä°(€€€€€€€€€€€€€€€½¹Y…±Õ•¡…¹”€ôìÕÁ‘…Ñ•I•Á½Í¥Ñ½Éä€ô¥Ğô°(€€€€€€€€€€€€€€€±…‰•°€ôìQ•áĞ ‰¥Ñ!Õˆƒ’îO–êLˆ¤ô°(€€€€€€€€€€€€€€€Á±…•¡½±‘•È€ôìQ•áĞ ‹R£š"ß–B4¿’îO–êO–B4ˆ¤ô°(€€€€€€€€€€€€€€€ÍÕÁÁ½ÉÑ¥¹Q•áĞ€ôì(€€€€€€€€€€€€€€€€€€€Q•áĞ ‹’öÿR£–³–ò ¥Ñ!ÕˆI•±•…Í•Ìƒšš~—–J3’â/¢ö÷šnÓšZÀˆ¤(€€€€€€€€€€€€€€€ô°(€€€€€€€€€€€€€€€µ½‘¥™¥•È€ô5½‘¥™¥•È¹™¥±±5…á]¥‘Ñ  ¤°(€€€€€€€€€€€€€€€Í¥¹±•1¥¹”€ôÑÉÕ”(€€€€€€€€€€€€¤(€€€€€€€€€€€I½Ü (€€€€€€€€€€€€€€€µ½‘¥™¥•È€ô5½‘¥™¥•È¹™¥±±5…á]¥‘Ñ  ¤°(€€€€€€€€€€€€€€€¡½É¥é½¹Ñ…±ÉÉ…¹•µ•¹Ğ€ôÉÉ…¹•µ•¹Ğ¹MÁ…•	•Ñİ••¸(€€€€€€€€€€€€¤ì(€€€€€€€€€€€€€€€½±Õµ¸¡5½‘¥™¥•È¹İ•¥¡Ğ Å˜¤¤ì(€€€€€€€€€€€€€€€€€€€Q•áĞ ‹¢«–*£šš~—šnÓšZÀˆ¤(€€€€€€€€€€€€€€€€€€€Q•áĞ ‹š¾?–’§–B;–>Ãšš~—’âš²„ˆ°ÍÑå±”€ô5…Ñ•É¥…±Q¡•µ”¹ÑåÁ½É…Á¡ä¹‰½‘åMµ…±°¤(€€€€€€€€€€€€€€€ô(€€€€€€€€€€€€€€€Mİ¥Ñ  (€€€€€€€€€€€€€€€€€€€¡•­•€ô…ÕÑ½¡•­UÁ‘…Ñ•Ì°(€€€€€€€€€€€€€€€€€€€½¹¡•­•‘¡…¹”€ôì…ÕÑ½¡•­UÁ‘…Ñ•Ì€ô¥Ğô(€€€€€€€€€€€€€€€€¤(€€€€€€€€€€€ô(€€€€€€€€€€€	ÕÑÑ½¸ (€€€€€€€€€€€€€€€½¹±¥¬€ôì(€€€€€€€€€€€€€€€€€€€Ù¥•İ5½‘•°¹Í…Ù•UÁ‘…Ñ•M•ÑÑ¥¹Ì¡ÕÁ‘…Ñ•I•Á½Í¥Ñ½Éä°…ÕÑ½¡•­UÁ‘…Ñ•Ì¤(€€€€€€€€€€€€€€€€€€€¥˜€¡…ÕÑ½¡•­UÁ‘…Ñ•Ì€˜˜	Õ¥±¹YIM%=8¹M-}%9P€øô	Õ¥±¹YIM%=9}=L¹Q%I5%MT¤ì(€€€€€€€€€€€€€€€€€€€€€€€¹½Ñ¥™¥…Ñ¥½¹A•Éµ¥ÍÍ¥½¹1…Õ¹¡•È¹±…Õ¹ ¡5…¹¥™•ÍĞ¹Á•Éµ¥ÍÍ¥½¸¹A=MQ}9=Q%%Q%=9L¤(€€€€€€€€€€€€€€€€€€€ô(€€€€€€€€€€€€€€€ô°(€€€€€€€€€€€€€€€µ½‘¥™¥•È€ô5½‘¥™¥•È¹™¥±±5…á]¥‘Ñ  ¤(€€€€€€€€€€€€¤ìQ•áĞ ‹’şw–¶c–æÛšš~—šnÓšZÀˆ¤ô(€€€€€€€€€€€=ÕÑ±¥¹•‘	ÕÑÑ½¸ (€€€€€€€€€€€€€€€½¹±¥¬€ôìÙ¥•İ5½‘•°¹¡•­½ÉUÁ‘…Ñ”¡µ…¹Õ…°€ôÑÉÕ”¤ô°(€€€€€€€€€€€€€€€•¹…‰±•€ô€…ÕÁ‘…Ñ•MÑ…Ñ”¹¡•­¥¹œ°(€€€€€€€€€€€€€€€µ½‘¥™¥•È€ô5½‘¥™¥•È¹™¥±±5…á]¥‘Ñ  ¤(€€€€€€€€€€€€¤ì(€€€€€€€€€€€€€€€¥˜€¡ÕÁ‘…Ñ•MÑ…Ñ”¹¡•­¥¹œ¤ì(€€€€€€€€€€€€€€€€€€€¥ÉÕ±…ÉAÉ½É•ÍÍ%¹‘¥…Ñ½È¡5½‘¥™¥•È¹Í¥é” Äà¹‘À¤°ÍÑÉ½­•]¥‘Ñ €ô€È¹‘À¤(€€€€€€€€€€€€€€€€€€€MÁ…•È¡5½‘¥™¥•È¹İ¥‘Ñ  à¹‘À¤¤(€€€€€€€€€€€€€€€ô(€€€€€€€€€€€€€€€Q•áĞ¡¥˜€¡ÕÁ‘…Ñ•MÑ…Ñ”¹¡•­¥¹œ¤€‹š¶–r£šš~—Š˜ˆ•±Í”€‹®/–6Ïšš~—šnÓšZÀˆ¤(€€€€€€€€€€€ô(€€€€€€€€€€€Q•áĞ (€€€€€€€€€€€€€€€€‹¢šn[–º'¢’òk’şwVgšr³–rÃ–¶›’æƒšVÃš6»	¹‘É½¥ƒ’òk¢ššÆ’öƒ†»¢º“–º'¢¾ò3–êSR£š^ƒšÎW¦vg¦îcšnÓšZÃˆ°(€€€€€€€€€€€€€€€ÍÑå±”€ô5…Ñ•É¥…±Q¡•µ”¹ÑåÁ½É…Á¡ä¹‰½‘åMµ…±°(€€€€€€€€€€€€¤(€€€€€€€ô((€€€€€€€M•Ñ¥½¹…É ‹šVÃš6»¢ºûö¸ˆ¤ì(€€€€€€€€€€€=ÕÑ±¥¹•‘	ÕÑÑ½¸ (€€€€€€€€€€€€€€€½¹±¥¬€ôì½¹™¥ÉµI•Í•Ğ€ôÑÉÕ”ô°(€€€€€€€€€€€€€€€½±½ÉÌ€ô	ÕÑÑ½¹•™…Õ±ÑÌ¹½ÕÑ±¥¹•‘	ÕÑÑ½¹½±½ÉÌ¡½¹Ñ•¹Ñ½±½È€ô5…Ñ•É¥…±Q¡•µ”¹½±½ÉM¡•µ”¹•ÉÉ½È¤°(€€€€€€€€€€€€€€€µ½‘¥™¥•È€ô5½‘¥™¥•È¹™¥±±5…á]¥‘Ñ  ¤(€€€€€€€€€€€€¤ìQ•áĞ ‹šâ¦ë–¶›’æƒšVÃš6¸ˆ¤ô(€€€€€€€€€€€Q•áĞ ‹–¾ó–ë–¶›’æƒ¢ºÃ–öW–Â–r£–B;î·&#šr³š>C’úoˆ°ÍÑå±”€ô5…Ñ•É¥…±Q¡•µ”¹ÑåÁ½É…Á¡ä¹‰½‘åMµ…±°¤(€€€€€€€ô(€€€ô((€€€¥˜€¡½¹™¥ÉµI•Í•Ğ¤ì(€€€€€€€±•ÉÑ¥…±½œ (€€€€€€€€€€€½¹¥Íµ¥ÍÍI•ÅÕ•ÍĞ€ôì½¹™¥ÉµI•Í•Ğ€ô™…±Í”ô°(€€€€€€€€€€€Ñ¥Ñ±”€ôìQ•áĞ ‹šâ¦ë–¶›’æƒšVÃš6»¾ò|ˆ¤ô°(€€€€€€€€€€€Ñ•áĞ€ôìQ•áĞ ‹šÂÓ–æÏšÖ/¢¾W–6W¢¾7¢şo–ê›¦Rg¢¾7–g’ös–J3š*—–F+¦÷’òk¢Š¯šâ¦ëš¶“šN7’ösš^ƒšÎWšJ“¦Rˆ¤ô°(€€€€€€€€€€€½¹™¥Éµ	ÕÑÑ½¸€ôì(€€€€€€€€€€€€€€€Q•áÑ	ÕÑÑ½¸¡½¹±¥¬€ôì(€€€€€€€€€€€€€€€€€€€½¹™¥ÉµI•Í•Ğ€ô™…±Í”(€€€€€€€€€€€€€€€€€€€Ù¥•İ5½‘•°¹±•…É1•…É¹¥¹…Ñ„¡½¹I•Í•Ğ¤(€€€€€€€€€€€€€€€ô¤ìQ•áĞ ‹†»¢º“šâ¦èˆ°½±½È€ô5…Ñ•É¥…±Q¡•µ”¹½±½ÉM¡•µ”¹•ÉÉ½È¤ô(€€€€€€€€€€€ô°(€€€€€€€€€€€‘¥Íµ¥ÍÍ	ÕÑÑ½¸€ôì(€€€€€€€€€€€€€€€Q•áÑ	ÕÑÑ½¸¡½¹±¥¬€ôì½¹™¥ÉµI•Í•Ğ€ô™…±Í”ô¤ìQ•áĞ ‹–>[šÚ ˆ¤ô(€€€€€€€€€€€ô(€€€€€€€€¤(€€€ô)ô()½µÁ½Í…‰±”)ÁÉ¥Ù…Ñ”™Õ¸9Õµ‰•ÉM•ÑÑ¥¹œ¡±…‰•°èMÑÉ¥¹œ°Ù…±Õ”è%¹Ğ°½¹¡…¹”è€¡%¹Ğ¤€´øU¹¥Ğ¤ì(€€€I½Ü¡5½‘¥™¥•È¹™¥±±5…á]¥‘Ñ  ¤°¡½É¥é½¹Ñ…±ÉÉ…¹•µ•¹Ğ€ôÉÉ…¹•µ•¹Ğ¹MÁ…•	•Ñİ••¸¤ì(€€€€€€€Q•áĞ¡±…‰•°¤(€€€€€€€I½Ü¡¡½É¥é½¹Ñ…±ÉÉ…¹•µ•¹Ğ€ôÉÉ…¹•µ•¹Ğ¹ÍÁ…•‘	ä à¹‘À¤¤ì(€€€€€€€€€€€¥±±•‘Q½¹…±	ÕÑÑ½¸¡½¹±¥¬€ôì½¹¡…¹”¡Ù…±Õ”€´€Ä¤ô¤ìQ•áĞ ‹Š"Hˆ¤ô(€€€€€€€€€€€Q•áĞ ˆ‘Ù…±Õ”ˆ°µ½‘¥™¥•È€ô5½‘¥™¥•È¹Á…‘‘¥¹œ¡Ñ½À€ô€ÄÀ¹‘À¤¤(€€€€€€€€€€€¥±±•‘Q½¹…±	ÕÑÑ½¸¡½¹±¥¬€ôì½¹¡…¹”¡Ù…±Õ”€¬€Ä¤ô¤ìQ•áĞ ˆ¬ˆ¤ô(€€€€€€€ô(€€€ô)ô(