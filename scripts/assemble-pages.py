#!/usr/bin/env python3
"""Assemble the already-tested production launchers for GitHub Pages."""
from pathlib import Path
import shutil
root=Path(__file__).resolve().parents[1]
out=root/'target/pages'
if out.exists():shutil.rmtree(out)
out.mkdir(parents=True)
for backend in ['gwt','teavm']:
 shutil.copytree(root/f'showcase-{backend}/target/site',out/backend)
 peer='gwt' if backend=='teavm' else 'teavm'
 index=out/backend/'index.html'
 index.write_text(index.read_text().replace(f'../../../showcase-{peer}/target/site/',f'../{peer}/'))
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
<p><a href="teavm/">Explore the TeaVM widget gallery</a> or <a href="gwt/">open the GWT gallery</a>.</p>
<p>Widgets by <a href="https://dominokit.com/domino-ui/demo/v2/home">DominoKit</a>, adapted for this independent port.</p>
</body>
</html>
''')
(out/'.nojekyll').touch()
print(out)
