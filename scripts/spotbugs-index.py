#!/usr/bin/env python3
from pathlib import Path
import xml.etree.ElementTree as E
import json,html
r=Path(__file__).resolve().parents[1];rows=[]
for module in sorted(r.iterdir()):
 if not (module/'pom.xml').exists():continue
 report=module/'target/spotbugs'/f'{module.name}-spotbugs.xml'
 if not report.exists():
  if list((module/'target/classes').rglob('*.class')):rows.append({'module':module.name,'status':'missing-report'})
  continue
 root=E.parse(report).getroot();findings=root.findall('BugInstance');errors=root.find('Errors')
 rows.append({'module':module.name,'status':'analyzed','findings':len(findings),'high':sum(b.get('priority')=='1' for b in findings),'errors':errors.attrib if errors is not None else {},'missingClasses':[x.text for x in root.findall('.//MissingClass')]})
out=r/'target/spotbugs';out.mkdir(parents=True,exist_ok=True)
(out/'index.json').write_text(json.dumps(rows,indent=2)+'\n')
(out/'index.html').write_text('<!doctype html><title>Domino Widgets SpotBugs</title><h1>Reactor SpotBugs reports</h1><table><tr><th>Module</th><th>Status</th><th>Findings</th><th>High</th></tr>'+''.join('<tr><td><a href="../../'+x['module']+'/target/spotbugs/'+x['module']+'-spotbugs.html">'+html.escape(x['module'])+'</a></td><td>'+x['status']+'</td><td>'+str(x.get('findings',''))+'</td><td>'+str(x.get('high',''))+'</td></tr>' for x in rows)+'</table>')
print(json.dumps(rows,indent=2))
