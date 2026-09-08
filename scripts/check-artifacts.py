#!/usr/bin/env python3
"""Ensure each backend owns each packaged class once and widgets don't bundle assets."""
from pathlib import Path
from zipfile import ZipFile
ROOT=Path(__file__).resolve().parents[1]
owners={}
jars=list((ROOT/'domino-widgets-teavm/target').glob('domino-widgets-teavm-*.jar'))
jars += list((ROOT/'domino-widgets-teavm/target/compat-dependencies').glob('*.jar'))
jars=[p for p in jars if not p.name.endswith(('-sources.jar','-javadoc.jar'))]
if len(jars)!=4:raise SystemExit('Expected widgets plus three resolved compatibility JARs')
for path in jars:
 with ZipFile(path) as jar:
  for name in jar.namelist():
   if name.endswith('.class'):
    if name in owners:raise SystemExit(f'Duplicate class {name}: {owners[name]} and {path.name}')
    owners[name]=path.name
   if name.endswith(('.css','.woff','.woff2','.ttf','.eot')):raise SystemExit(f'Asset duplicated in code artifact: {path.name}/{name}')
print('teavm',len(owners),'unique project classes')

import hashlib
manifest=ROOT/'target/compat/sources.sha256'
if not manifest.exists():raise SystemExit('Missing generated-source manifest')
for line in manifest.read_text().splitlines():
 expected,name=line.split('  ',1)
 if hashlib.sha256((manifest.parent/name).read_bytes()).hexdigest()!=expected:
  raise SystemExit('Generated source edited after generation: '+name)
print('Generated sources match their generator manifest')
