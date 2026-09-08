#!/usr/bin/env python3
from pathlib import Path
import shutil
r=Path(__file__).resolve().parents[1]
for backend in ['teavm']:
 site=r/f'showcase-{backend}/target/site'
 if not site.exists():continue
 assets=site/'domino-widgets'
 shutil.copytree(r/'target/intake/assets/META-INF/resources/domino-widgets', assets, dirs_exist_ok=True)
 shutil.copy2(r/'upstream/showcase/showcase-image.jpg',site/'showcase-image.jpg')
 if (r/'upstream/showcase/images').exists():shutil.copytree(r/'upstream/showcase/images',site/'images',dirs_exist_ok=True)
 (site/'countries.json').write_text('[{"name":"United Kingdom"},{"name":"Spain"},{"name":"Jordan"},{"name":"France"}]')
 script='<script src="showcase/showcase.nocache.js"></script>' if backend=='gwt' else '<script src="showcase.js"></script><script>main();</script>'
 shutil.copy2(r/'showcase-shared/showcase.css', site/'showcase.css')
 peer='gwt' if backend=='teavm' else 'teavm'
 compiler='GWT' if backend=='gwt' else 'TeaVM'
 (site/'index.html').write_text(f'''<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Domino Widgets — {compiler}</title>
<link rel="stylesheet" href="domino-widgets/css/domino-ui/domino-ui.css">
<link rel="stylesheet" href="showcase.css">
</head>
<body data-compiler="{compiler}">
<noscript>This interactive widget gallery needs JavaScript. <a href="https://github.com/cstainton/domino-widgets/blob/main/docs/SHOWCASE.md">Read the showcase guide</a>.</noscript>
{script}
</body>
</html>
''')


site=r/'compat-reuse-smoke/target/site'
if site.exists():
 (site/'index.html').write_text('<!doctype html><title>Compatibility reuse test</title><body><script src="showcase.js"></script><script>main();</script></body>')
