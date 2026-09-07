#!/usr/bin/env python3
"""Extract selected original sample methods into shared, framework-free launchers.
Run explicitly when updating showcase-lock.json. Never modifies widget sources.
"""
from pathlib import Path
import re,json,hashlib,sys
def write(path,code):
 if "--check" in sys.argv:
  if not path.exists() or path.read_text()!=code:raise SystemExit("Regenerate showcase adapter: "+str(path))
 else:path.write_text(code)
r=Path(__file__).resolve().parents[1]
lock=json.loads((r/'upstream/showcase-lock.json').read_text())
for item in lock['sources']:
 source=r/'upstream/showcase'/item['path'];s=source.read_text()
 if hashlib.sha256(source.read_bytes()).hexdigest()!=item['sha256']:raise SystemExit('Source drift: '+str(source))
 imports='\n'.join(x for x in s.splitlines() if x.startswith('import ') and any(prefix in x for prefix in ['org.dominokit.domino.ui.','elemental2.','java.','jsinterop.','org.gwtproject.','org.slf4j.']))
 methods=[]
 for name in item['methods']+item.get('helpers',[]):
  match=re.search(r'    (?:private|protected|public) [^\n]+\b'+name+r'\([^;]*?\)\s*\{',s)
  if not match:raise SystemExit('Missing method '+name)
  start=match.start();opening=match.end()-1;depth=0;end=None
  for m in re.finditer(r'"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'|//[^\n]*|/\*[\s\S]*?\*/|[{}]',s[opening:]):
   if m.group()=='{':depth+=1
   elif m.group()=='}':
    depth-=1
    if depth==0:end=opening+m.end();break
  if end is None:raise SystemExit('Unbalanced sample '+name)
  methods.append(s[start:end])
 name=Path(item['adapter']).stem
 code='// Adapted from DominoKit/domino-ui-demo at '+lock['commit']+'; see upstream/showcase-lock.json.\npackage io.instanto.domino.client;\n'+imports+'\nimport elemental2.dom.HTMLDivElement;\nimport org.dominokit.domino.ui.elements.DivElement;\nimport static org.dominokit.domino.ui.utils.Domino.*;\npublic final class '+name+' implements org.dominokit.domino.ui.style.DominoCss {\n private final DivElement element=div();\n'+item.get('fields','')+'\n public HTMLDivElement render(){\n'+item.get('before','')+''.join(m+'();\n' for m in item['methods'])+item.get('after','')+'return element.element();}\n'+'\n'.join(methods)+'\n}\n'
 for old,new in item.get('replacements',{}).items():
  if old not in code:raise SystemExit('Adaptation no longer applies: '+old)
  code=code.replace(old,new)
 code=code.replace('GWT.getModuleBaseURL() + "/images/image-gallery/9.jpg"','"showcase-image.jpg"')
 if item.get('nativeElement'):code=code.replace('private final DivElement element=div();','private final HTMLDivElement element=div().element();').replace('return element.element();','return element;')
 write(r/item['adapter'],code)
rows=lock['sources']
code='package io.instanto.domino.client;\nimport elemental2.dom.HTMLElement;\npublic final class GalleryCatalog {\npublic static final java.util.List<String> ROUTES=java.util.List.of('+','.join('"'+x['route']+'"' for x in rows)+');\npublic static HTMLElement render(String route){\n'
for x in rows:code+='if(route.equals("'+x['route']+'"))return new '+Path(x['adapter']).stem+'().render();\n'
code+='return null;\n}}\n'
write(r/'showcase-shared/src/main/java/io/instanto/domino/client/GalleryCatalog.java',code)
print(len(rows),'pages;',sum(len(x['methods']) for x in rows),'original sample methods')

for item in lock.get('supporting',[]):
 source=r/'upstream/showcase'/item['path'];code=source.read_text()
 if hashlib.sha256(source.read_bytes()).hexdigest()!=item['sha256']:raise SystemExit('Supporting source drift')
 code=re.sub(r'package [^;]+;', 'package io.instanto.domino.client;',code,count=1)
 code=re.sub(r'^import (?:com.fasterxml.jackson|org.dominokit.domino.datatable)[^;]+;\s*','',code,flags=re.M)
 code=re.sub(r'@JsonIgnoreProperties\([^)]*\)|@JsonIgnore\b','',code)
 for old,new in item.get('replacements',{}).items():
  if old not in code:raise SystemExit('Supporting adaptation drift: '+old)
  code=code.replace(old,new)
 write(r/'showcase-shared/src/main/java/io/instanto/domino/client'/(item['name']+'.java'),'// Original showcase helper; see upstream/showcase-lock.json.\n'+code)
