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
(out/'index.html').write_text('''<!doctype html><html lang="en"><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>Domino Widgets showcases</title><style>body{font:18px/1.6 system-ui;max-width:800px;margin:64px auto;padding:24px;color:#24334d;background:#f5f7fb}a{color:#2856b0}li{margin:16px 0}</style><h1>Domino Widgets</h1><p>Original Domino examples compiled from shared Java sources for two browser backends.</p><ul><li><a href="gwt/?page=buttons">GWT showcase</a></li><li><a href="teavm/?page=buttons">TeaVM showcase</a></li></ul><p>Includes buttons, forms, dialogs, and a table/lifecycle contract screen. This initial port has limited verified coverage.</p><p><a href="https://github.com/cstainton/domino-widgets">Source, compatibility coverage and test reports</a></p></html>''')
(out/'.nojekyll').touch()
print(out)
