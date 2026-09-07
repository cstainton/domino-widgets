#!/usr/bin/env python3
"""Ensure each backend owns each packaged class once and widgets don't bundle assets."""
from pathlib import Path
from zipfile import ZipFile
ROOT=Path(__file__).resolve().parents[1]
for backend, modules in {'gwt':['gwt-modular-services','domino-widgets-gwt'],'teavm':['teavm-jsinterop-compat','teavm-elemental2-compat','teavm-gwt-modular-services','domino-widgets-teavm']}.items():
 owners={}
 for module in modules:
  jars=list((ROOT/module/'target').glob(f'{module}-*.jar'))
  jars=[p for p in jars if not p.name.endswith(('-sources.jar','-javadoc.jar'))]
  if len(jars)!=1:raise SystemExit(f'Expected one binary jar for {module}')
  with ZipFile(jars[0]) as jar:
   for n in jar.namelist():
    if n.endswith('.class'):
     if n in owners:raise SystemExit(f'Duplicate {backend} class {n}: {owners[n]} and {module}')
     owners[n]=module
    if n.endswith(('.css','.woff','.woff2','.ttf','.eot')):raise SystemExit(f'Asset duplicated in code artifact: {module}/{n}')
 print(backend,len(owners),'unique project classes')

import hashlib
manifest=ROOT/'target/compat/sources.sha256'
if not manifest.exists():raise SystemExit('Missing generated-source manifest')
for line in manifest.read_text().splitlines():
 expected,name=line.split('  ',1)
 if hashlib.sha256((manifest.parent/name).read_bytes()).hexdigest()!=expected:
  raise SystemExit('Generated source edited after generation: '+name)
print('Generated sources match their generator manifest')
