package com.personalieltscoach.data.seed

import org.junit.Test
import java.io.File
import com.personalieltscoach.domain.service.TextSegmenter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** Reproducible editorial inventory. Generated data stays in the ignored build directory. */
class ContentInventoryTest {
    @Test fun exportExamplesForEditorialReview() {
        val words = Nce1WordPack.words(0) + Nce2WordPack.words(0) + Paul1000SentencePack.words(0)
        val output = File("build/reports/content/examples.tsv")
        output.parentFile?.mkdirs()
        output.writeText(words.joinToString("\n") {
            listOf(it.source, it.word, it.meaning, it.example, it.exampleTranslation)
                .joinToString("\t") { field -> field.replace('\t', ' ').replace('\n', ' ') }
        })
        val sentences = (words.map { it.example } + SeedData.words(0).map { it.example } +
            SeedData.sampleSentences + SeedData.readings(0).flatMap { TextSegmenter.sentences(it.content) }).distinct()
        File("build/reports/content/public-sentences.json").writeText(Json.encodeToString(sentences))
    }
}
