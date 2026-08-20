param(
    [string]$SourceUrl = "https://www.ncego.com/books/words/nce1",
    [string]$OutputPath = "app/src/main/java/com/personalieltscoach/data/seed/Nce1WordPack.kt"
)

$ErrorActionPreference = "Stop"

function ConvertFrom-HtmlText([string]$Value) {
    $withoutTags = [regex]::Replace($Value, "<[^>]+>", " ")
    $decoded = [Net.WebUtility]::HtmlDecode($withoutTags)
    return [regex]::Replace($decoded, "\s+", " ").Trim()
}

function ConvertTo-KotlinRawText([string]$Value) {
    return $Value.Replace("`t", " ").Replace("`r", " ").Replace("`n", " ").Replace('$', '${''$''}')
}

$html = (Invoke-WebRequest -UseBasicParsing -Uri $SourceUrl).Content
$tokenPattern = '(?s)<h3\s+class="h4 my-3 text-center text-md-start">(.*?)</h3>|<div\s+class="col-12 col-md-6 col-lg-4 border-bottom pt-1 mb-1">(.*?)</div>'
$tokens = [regex]::Matches($html, $tokenPattern)

$sourceEntryCount = 0
$lesson = "NCE1"
$orderedKeys = [System.Collections.Generic.List[string]]::new()
$items = @{}

foreach ($token in $tokens) {
    if ($token.Groups[1].Success) {
        $heading = ConvertFrom-HtmlText $token.Groups[1].Value
        $lessonMatch = [regex]::Match($heading, 'Lesson\s+([^\s]+)')
        if ($lessonMatch.Success) {
            $lesson = "NCE1 Lesson $($lessonMatch.Groups[1].Value)"
        }
        continue
    }

    $block = $token.Groups[2].Value
    $wordMatch = [regex]::Match($block, 'data-word="([^"]+)"')
    if (-not $wordMatch.Success) { continue }

    $sourceEntryCount++
    $word = [Net.WebUtility]::HtmlDecode($wordMatch.Groups[1].Value).Trim()
    $key = $word.ToLowerInvariant()

    $smallMatches = [regex]::Matches($block, '(?s)<small\s+class="text-gray">(.*?)</small>')
    $phonetic = if ($smallMatches.Count -gt 0) {
        ConvertFrom-HtmlText $smallMatches[0].Groups[1].Value
    } else { "" }
    $phonetic = $phonetic -replace '^/\s*', '/' -replace '\s*/$', '/'
    $gloss = if ($smallMatches.Count -gt 1) {
        ConvertFrom-HtmlText $smallMatches[$smallMatches.Count - 1].Groups[1].Value
    } else { "" }
    $partMatch = [regex]::Match($block, '(?s)<span\s+class="badge[^"]*">(.*?)</span>')
    $part = if ($partMatch.Success) { ConvertFrom-HtmlText $partMatch.Groups[1].Value } else { "" }
    $meaning = [regex]::Replace("$part $gloss".Trim(), '\s+', ' ')

    if (-not $items.ContainsKey($key)) {
        $items[$key] = [ordered]@{
            Word = $word
            Phonetic = $phonetic
            Meanings = [System.Collections.Generic.List[string]]::new()
            Lessons = [System.Collections.Generic.List[string]]::new()
        }
        $orderedKeys.Add($key)
    }

    $item = $items[$key]
    if ($meaning -and -not $item.Meanings.Contains($meaning)) { $item.Meanings.Add($meaning) }
    if (-not $item.Lessons.Contains($lesson)) { $item.Lessons.Add($lesson) }
    if (-not $item.Phonetic -and $phonetic) { $item.Phonetic = $phonetic }
}

if ($sourceEntryCount -ne 1108) {
    throw "Expected 1108 source entries, found $sourceEntryCount. The source page may have changed."
}
if ($orderedKeys.Count -ne 1021) {
    throw "Expected 1021 unique words, found $($orderedKeys.Count). The source page may have changed."
}

$rows = foreach ($key in $orderedKeys) {
    $item = $items[$key]
    $word = ConvertTo-KotlinRawText $item.Word
    $phonetic = ConvertTo-KotlinRawText $item.Phonetic
    $meaning = ConvertTo-KotlinRawText ($item.Meanings -join "；")
    $lessons = ConvertTo-KotlinRawText ($item.Lessons -join ", ")
    "$word`t$phonetic`t$meaning`t$lessons"
}

$kotlin = @"
package com.personalieltscoach.data.seed

import com.personalieltscoach.data.local.entity.WordItemEntity
import com.personalieltscoach.data.local.entity.SentenceCardEntity

/**
 * Offline vocabulary pack generated from $SourceUrl.
 * The source contains 1,108 lesson entries and 1,021 unique study words.
 * Repeated entries are merged so learners do not receive duplicate cards.
 */
object Nce1WordPack {
    const val SOURCE_ENTRY_COUNT = 1_108
    const val UNIQUE_WORD_COUNT = 1_021

    fun words(
        now: Long,
        practiceCards: List<SentenceCardEntity> = SentenceTrialData.cards(now)
    ): List<WordItemEntity> = rows.lineSequence()
        .map(String::trim)
        .filter(String::isNotBlank)
        .map { row ->
            val parts = row.split('\t')
            require(parts.size == 4) { "Invalid NCE1 word row" }
            val word = parts[0]
            val practiceCard = practiceCards.firstOrNull { containsTerm(it.sentence, word) }
            WordItemEntity(
                word = word,
                phonetic = parts[1],
                meaning = parts[2],
                example = practiceCard?.sentence ?: "Please remember the word \"`$word\".",
                exampleTranslation = practiceCard?.translation ?: "请记住单词“`$word”。",
                level = parts[3],
                createdAt = now,
                updatedAt = now
            )
        }
        .toList()

    private fun containsTerm(sentence: String, term: String): Boolean =
        Regex(
            pattern = "(?<![A-Za-z])`$`{Regex.escape(term)}(?![A-Za-z])",
            option = RegexOption.IGNORE_CASE
        ).containsMatchIn(sentence)

    private val rows = """
$($rows -join "`n")
    """.trimIndent()
}
"@

$resolvedOutput = Join-Path (Get-Location) $OutputPath
$outputDirectory = Split-Path -Parent $resolvedOutput
New-Item -ItemType Directory -Force -Path $outputDirectory | Out-Null
[IO.File]::WriteAllText($resolvedOutput, $kotlin, [Text.UTF8Encoding]::new($false))

Write-Output "Generated $($orderedKeys.Count) unique words from $sourceEntryCount source entries at $resolvedOutput"
