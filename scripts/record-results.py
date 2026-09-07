#!/usr/bin/env python3
from pathlib import Path
import json,sys,hashlib,xml.etree.ElementTree as E
r=Path(__file__).resolve().parents[1];mode=sys.argv[1] if len(sys.argv)>1 else 'development'
out=r/'reports'/mode;out.mkdir(parents=True,exist_ok=True)
browser=json.loads((r/'browser-tests/test-results/results.json').read_text())
if browser['stats']['unexpected']:raise SystemExit('Refusing to record a passing report with unexpected browser failures')
(out/'browser.json').write_text(json.dumps(browser,indent=2).replace(str(r),'.')+'\n')
summary={'mode':mode,'source':json.loads((r/'upstream/source-lock.json').read_text()),'browser':browser['stats'],'compilers':{'gwt':'2.13.1','teavm':'0.15.0','jdk':'21','javaRelease':17},'unitTests':[],'bundleBytes':{}}
for p in r.glob('*/target/surefire-reports/TEST-*.xml'):
 root=E.parse(p).getroot();summary['unitTests'].append({k:root.get(k) for k in ['name','tests','failures','errors','skipped']})
 (out/p.name).write_text(p.read_text().replace(str(r),'.'))
for backend in ['gwt','teavm']:
 site=r/f'showcase-{backend}/target/site'
 files=list((site/'showcase').glob('*.cache.js')) if backend=='gwt' else [site/'showcase.js']
 summary['bundleBytes'][backend]=sum(p.stat().st_size for p in files)
summary['showcase']={'pages':len(json.loads((r/'upstream/showcase-lock.json').read_text())['sources']),'sampleMethods':sum(len(x['methods']) for x in json.loads((r/'upstream/showcase-lock.json').read_text())['sources'])}
summary['browserProjects']=[p['name'] for p in browser['config']['projects']]
summary['spotbugs']=json.loads((r/'target/spotbugs/index.json').read_text())
(out/'summary.json').write_text(json.dumps(summary,indent=2)+'\n')
manifest={str(p.relative_to(r/'target/compat')):hashlib.sha256(p.read_bytes()).hexdigest() for p in sorted((r/'target/compat').rglob('*.java'))}
(out/'generated-sha256.json').write_text(json.dumps(manifest,indent=2)+'\n')
print('Recorded',mode, browser['stats'])
