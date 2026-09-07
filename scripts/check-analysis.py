#!/usr/bin/env python3
"""Release/CI gate: no missing analysis, analyzer errors or untriaged high findings."""
from pathlib import Path
import json,xml.etree.ElementTree as E
r=Path(__file__).resolve().parents[1]
def key(typ,cls,field,method):return (typ,cls,field,method)
allowed={key(x['type'],x['class'],x['field'],x['method']) for x in json.loads((r/'docs/spotbugs-triage.json').read_text())}
problems=[]
for module in r.iterdir():
 if not (module/'pom.xml').exists() or not list((module/'target/classes').rglob('*.class')):continue
 p=module/'target/spotbugs'/f'{module.name}-spotbugs.xml'
 if not p.exists():problems.append(f'{module.name}: missing report');continue
 root=E.parse(p).getroot();errors=root.find('Errors')
 if errors is not None and any(int(errors.get(x,'0')) for x in ['errors','missingClasses']):problems.append(f'{module.name}: analyzer errors/missing classes {errors.attrib}')
 for b in root.findall('BugInstance'):
  if b.get('priority')!='1':continue
  field=b.find('Field');method=b.find('Method')
  k=key(b.get('type'),b.find('Class').get('classname'),field.get('name') if field is not None else None,method.get('name') if method is not None else None)
  if k not in allowed:problems.append(f'{module.name}: untriaged {k}')
if problems:raise SystemExit('\n'.join(problems))
print('All analyzers complete; high-priority findings match the explicit triage manifest.')
