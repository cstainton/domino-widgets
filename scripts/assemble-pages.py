#!/usr/bin/env python3
"""Assemble the already-tested production launchers for GitHub Pages."""
from pathlib import Path
import shutil
root=Path(__file__).resolve().parents[1]
out=root/'target/pages'
if out.exists():shutil.rmtree(out)
out.mkdir(parents=True)
for backend in ['teavm']:
 shutil.copytree(root/f'showcase-{backend}/target/site',out/backend)
(out/'index.html').write_text('''<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta http-equiv="refresh" content="0;url=teavm/">
<title>Domino Widgets showcase</title>
</head>
<body>
<h1>Domino Widgets showcase</h1>
<p><a href="teavm/">Explore the TeaVM widget gallery</a> or <a href="https://dominokit.com/domino-ui/demo/v2/home">compare with DominoKit’s showcase</a>.</p>
<p>Widgets by <a href="https://dominokit.com/domino-ui/demo/v2/home">DominoKit</a>, adapted for this independent port.</p>
</body>
</html>
''')
# Preserve old bookmarks without maintaining a second compiler distribution.
(out/'gwt').mkdir()
(out/'gwt/index.html').write_text('<!doctype html><meta http-equiv="refresh" content="0;url=https://dominokit.com/domino-ui/demo/v2/home"><title>DominoKit showcase</title><a href="https://dominokit.com/domino-ui/demo/v2/home">Open the upstream DominoKit showcase</a>')
(out/'.nojekyll').touch()
print(out)
