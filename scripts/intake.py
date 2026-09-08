#!/usr/bin/env python3
"""Verify immutable intake and materialize both backends' common inputs."""
from pathlib import Path
import hashlib,json,tarfile,shutil
ROOT=Path(__file__).resolve().parents[1]
def main():
 lock=json.loads((ROOT/'upstream/source-lock.json').read_text())
 for item in json.loads((ROOT/'upstream/bindings-lock.json').read_text()):
  path=ROOT/'upstream'/item['file']
  if hashlib.sha256(path.read_bytes()).hexdigest()!=item['sha256']:
   raise SystemExit('Binding checksum mismatch: '+item['file'])
 archive=ROOT/'upstream'/lock['archive']
 if hashlib.sha256(archive.read_bytes()).hexdigest()!=lock['sha256']:
  raise SystemExit('Source archive checksum mismatch')
 out=ROOT/'target/intake'
 if out.exists(): shutil.rmtree(out)
 inventory=[]
 with tarfile.open(archive) as tar:
  for m in tar.getmembers():
   if not m.isfile(): continue
   data=tar.extractfile(m).read()
   inventory.append({'path':m.name,'size':len(data),'sha256':hashlib.sha256(data).hexdigest()})
   dest=None
   for prefix in ['domino-ui/src/main/java/','domino-ui-shared/src/main/java/']:
    if m.name.startswith(prefix): dest=out/'java'/m.name[len(prefix):]
   prefix='domino-ui/src/main/resources/org/dominokit/domino/ui/public/'
   if m.name.startswith(prefix): dest=out/'assets/META-INF/resources/domino-widgets'/m.name[len(prefix):]
   if m.name=='domino-ui/src/main/module.gwt.xml': dest=out/'resources/org/dominokit/domino/ui/DominoUI.gwt.xml'
   if dest:
    dest.parent.mkdir(parents=True,exist_ok=True)
    if dest.exists(): raise SystemExit('Duplicate source input: '+str(dest))
    dest.write_bytes(data)
 # Upstream ships components; assemble one deterministic, version-matched stylesheet.
 css=out/'assets/META-INF/resources/domino-widgets/css/domino-ui'
 parts=sorted((css/'dui-components').rglob('*.css'))
 (css/'domino-ui.css').write_text('\n'.join(p.read_text() for p in parts if '.min.' not in p.name))
 (out/'inventory.json').write_text(json.dumps(inventory,indent=2)+'\n')
 print('Verified',lock['commit'],';',len(inventory),'archived files')
if __name__=='__main__': main()

# Pin the original showcase examples separately from the widget source.
showcase_lock=json.loads((ROOT/"upstream/showcase-lock.json").read_text())
for source in showcase_lock["sources"]+showcase_lock.get("supporting",[])+showcase_lock.get("assets",[]):
 p=ROOT/"upstream/showcase"/source["path"]
 if hashlib.sha256(p.read_bytes()).hexdigest()!=source["sha256"]:raise SystemExit("Showcase checksum mismatch: "+str(p))

asset=showcase_lock["asset"]
if hashlib.sha256((ROOT/"upstream/showcase"/asset["path"]).read_bytes()).hexdigest()!=asset["sha256"]:raise SystemExit("Showcase asset checksum mismatch")
