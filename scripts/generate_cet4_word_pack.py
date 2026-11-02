"""Import only the user-selected CET4 headword list; never execute website JavaScript.

Generate deterministic offline Kotlin rows from a checked-in JSON snapshot.
Examples are curated separately by Cet4WordPack from existing app-authored content.
"""
import argparse
import hashlib
import json
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
SNAPSHOT = ROOT / 'content/cet4/vocabulary.json'
OUTPUT = ROOT / 'app/src/main/java/com/personalieltscoach/data/seed/Cet4Vocabulary.kt'
EXPECTED_COUNT = 4458


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--source-html', type=Path)
    args = parser.parse_args()
    if args.source_html:
        raw = args.source_html.read_bytes()
        match = re.search(r'let rawData = (\[.*?\]);', raw.decode('utf-8'), re.S)
        if not match:
            raise ValueError('Public embedded vocabulary array not found')
        rows = json.loads(match[1])
        assert len(rows) == EXPECTED_COUNT
        assert len({r['word'].casefold() for r in rows}) == EXPECTED_COUNT
        # The website shuffles on refresh. Use stable source IDs, not random DOM order.
        rows = sorted(rows, key=lambda r: r['id'])
        snapshot = dict(source='https://www.ncego.com/words/topic/cet4', checked='2026-11-02',
                        html_sha256=hashlib.sha256(raw).hexdigest(), entries=rows)
        SNAPSHOT.parent.mkdir(parents=True, exist_ok=True)
        SNAPSHOT.write_text(json.dumps(snapshot, ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    rows = json.loads(SNAPSHOT.read_text(encoding='utf-8'))['entries']
    assert len(rows) == len({r['word'].casefold() for r in rows}) == EXPECTED_COUNT
    assert [r['id'] for r in rows] == sorted({r['id'] for r in rows})
    lines = []
    for row in rows:
        values = [row['word'].strip(), row['phonetic'].strip(), row['definition'].strip()]
        assert values[0] and values[2]
        assert all(not any(c in field for c in ['\t', '\n', '\r', '$', '"""']) for field in values)
        lines.append('\t'.join(values))
    chunks = ',\n'.join('        """\n' + '\n'.join(lines[i:i+80]) + '\n        """'
                        for i in range(0, len(lines), 80))
    code = '''package com.personalieltscoach.data.seed

/** Generated from content/cet4/vocabulary.json; no lesson prose or audio. */
internal object Cet4Vocabulary {
    const val COUNT = 4458
    val entries: List<List<String>> by lazy {
        rows.flatMap { chunk -> chunk.lineSequence().map(String::trim)
            .filter(String::isNotBlank).map { it.split('\\t').also { fields -> require(fields.size == 3) } }.toList() }
    }
    private val rows = listOf(
''' + chunks + '\n    )\n}\n'
    OUTPUT.write_text(code, encoding='utf-8')
    print(f'Generated {len(rows)} unique CET4 words; {sum(not r["phonetic"] for r in rows)} source phonetics absent.')


if __name__ == '__main__':
    main()
