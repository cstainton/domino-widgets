from pathlib import Path
import shutil
r=Path(__file__).resolve().parent
for backend in ['gwt','teavm']:
 site=r/backend/'target/site';site.mkdir(parents=True,exist_ok=True)
 shutil.copytree(r/backend/'target/assets/META-INF/resources/domino-widgets',site/'domino-widgets',dirs_exist_ok=True)
 script='<script src="app/app.nocache.js"></script>' if backend=='gwt' else '<script src="app.js"></script><script>main();</script>'
 (site/'index.html').write_text('<!doctype html><meta charset="utf-8"><link rel="stylesheet" href="domino-widgets/css/domino-ui/domino-ui.css"><body>'+script+'</body>')
