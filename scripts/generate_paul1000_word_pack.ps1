param(
    [string]$SourceUrl = "https://www.ncego.com/words/topic/Paul1000",
    [string]$OutputPath = "app/src/main/java/com/personalieltscoach/data/seed/Paul1000WordPack.kt"
)

$ErrorActionPreference = "Stop"

$html = (Invoke-WebRequest -Uri $SourceUrl -UseBasicParsing).Content
$match = [regex]::Match(
    $html,
    'let rawData\s*=\s*(\[.*?\]);',
    [System.Text.RegularExpressions.RegexOptions]::Singleline
)
if (-not $match.Success) {
    throw "Paul1000 rawData was not found at $SourceUrl"
}

$words = @($match.Groups[1].Value | ConvertFrom-Json) |
    Sort-Object { $_.word.ToLowerInvariant() }

$duplicates = $words |
    Group-Object { $_.word.ToLowerInvariant() } |
    Where-Object Count -gt 1
if ($duplicates) {
    throw "Paul1000 contains duplicate words: $($duplicates.Name -join ', ')"
}

# The source currently leaves six phonetic fields blank. Keep the bundled pack
# complete with standard British dictionary transcriptions.
$phoneticOverrides = @{
    budget = "ˈbʌdʒɪt"
    negative = "ˈneɡətɪv"
    option = "ˈɒpʃ(ə)n"
    relevant = "ˈreləv(ə)nt"
    strategy = "ˈstrætədʒi"
    typical = "ˈtɪpɪk(ə)l"
}

function Clean-Field([object]$Value) {
    return ([string]$Value).Replace("`t", " ").Replace("`r", " ").Replace("`n", " ").Trim()
}

$builder = [System.Text.StringBuilder]::new()
[void]$builder.AppendLine("package com.personalieltscoach.data.seed")
[void]$builder.AppendLine()
[void]$builder.AppendLine("internal data class Paul1000Entry(")
[void]$builder.AppendLine("    val word: String,")
[void]$builder.AppendLine("    val phonetic: String,")
[void]$builder.AppendLine("    val meaning: String")
[void]$builder.AppendLine(")")
[void]$builder.AppendLine()
[void]$builder.AppendLine("/**")
[void]$builder.AppendLine(" * Offline copy of the public Paul1000 list from $SourceUrl.")
[void]$builder.AppendLine(" * The page currently exposes $($words.Count) unique entries despite the topic name.")
[void]$builder.AppendLine(" */")
[void]$builder.AppendLine("object Paul1000WordPack {")
[void]$builder.AppendLine("    const val SOURCE_ENTRY_COUNT = $($words.Count)")
[void]$builder.AppendLine()
[void]$builder.AppendLine("    internal val entries: List<Paul1000Entry> by lazy {")
[void]$builder.AppendLine("        rows.lineSequence()")
[void]$builder.AppendLine("            .map(String::trim)")
[void]$builder.AppendLine("            .filter(String::isNotBlank)")
[void]$builder.AppendLine("            .map { row ->")
[void]$builder.AppendLine('                val parts = row.split(''\t'')')
[void]$builder.AppendLine("                require(parts.size == 3) { `"Invalid Paul1000 word row`" }")
[void]$builder.AppendLine("                Paul1000Entry(parts[0], parts[1], parts[2])")
[void]$builder.AppendLine("            }")
[void]$builder.AppendLine("            .toList()")
[void]$builder.AppendLine("    }")
[void]$builder.AppendLine()
[void]$builder.AppendLine('    private val rows = """')
foreach ($item in $words) {
    $word = Clean-Field $item.word
    $phonetic = Clean-Field $item.phonetic
    if (-not $phonetic -and $phoneticOverrides.ContainsKey($word.ToLowerInvariant())) {
        $phonetic = $phoneticOverrides[$word.ToLowerInvariant()]
    }
    $definition = Clean-Field $item.definition
    [void]$builder.AppendLine("$word`t$phonetic`t$definition")
}
[void]$builder.AppendLine('    """.trimIndent()')
[void]$builder.AppendLine("}")

$resolvedOutput = Join-Path (Get-Location) $OutputPath
[System.IO.File]::WriteAllText(
    $resolvedOutput,
    $builder.ToString(),
    [System.Text.UTF8Encoding]::new($false)
)

Write-Host "Generated $($words.Count) Paul1000 entries at $resolvedOutput"
