#!/usr/bin/env python3
from pathlib import Path
import shutil
r=Path(__file__).resolve().parents[1]
for backend in ['gwt','teavm']:
 site=r/f'showcase-{backend}/target/site'
 if not site.exists():continue
 assets=site/'domino-widgets'
 shutil.copytree(r/'target/intake/assets/META-INF/resources/domino-widgets', assets, dirs_exist_ok=True)
 shutil.copy2(r/'upstream/showcase/showcase-image.jpg',site/'showcase-image.jpg')
 css=assets/'css/domino-ui'
 script='<script src="showcase/showcase.nocache.js"></script>' if backend=='gwt' else '<script src="showcase.js"></script><script>main();</script>'
 (site/'index.html').write_text('<!doctype html><html><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>Domino Widgets — '+backend+'</title><link rel="stylesheet" href="domino-widgets/css/domino-ui/domino-ui.css"><style>body{margin:0;padding:24px;background:#f5f7fb}#gallery-header{max-width:1200px;margin:0 auto 24px}#gallery-header h1{margin:0;color:#24334d}#gallery-header p{color:#58677d}#gallery-header nav{display:flex;gap:20px;flex-wrap:wrap}#gallery-header a{color:#2856b0;font-weight:600}#gallery-examples,#screen{max-width:1200px;margin:auto}#screen>button{margin:6px}</style></head><body>'+script+'</body></html>')

site=r/'compat-reuse-smoke/target/site'
if site.exists():
 (site/'index.html').write_text('<!doctype html><title>Compatibility reuse test</title><body><script src="showcase.js"></script><script>main();</script></body>')
