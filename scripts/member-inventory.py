#!/usr/bin/env python3
"""Inventory symbolic field/method references from compiled class constant pools."""
import json,struct
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
def references(path):
 data=path.read_bytes();pos=8
 def u1():
  nonlocal pos
  n=data[pos];pos+=1;return n
 def u2():
  nonlocal pos
  n=struct.unpack_from('>H',data,pos)[0];pos+=2;return n
 count=u2();pool=[None]*count;i=1
 while i<count:
  tag=u1()
  if tag==1:
   n=u2();pool[i]=(tag,data[pos:pos+n].decode('utf-8',errors='replace'));pos+=n
  elif tag in (7,8,16,19,20):pool[i]=(tag,u2())
  elif tag in (9,10,11,12,17,18):pool[i]=(tag,u2(),u2())
  elif tag in (3,4):pos+=4
  elif tag in (5,6):pos+=8;i+=1
  elif tag==15:pos+=3
  else:raise ValueError(f'Unknown constant pool tag {tag}')
  i+=1
 for entry in pool:
  if entry and entry[0] in (9,10,11):
   owner=pool[pool[entry[1]][1]][1];name_type=pool[entry[2]]
   if owner.startswith(('elemental2/','jsinterop/','org/gwtproject/','org/slf4j/','com/google/gwt/')):
    yield ('field' if entry[0]==9 else 'method',owner.replace('/','.'),pool[name_type[1]][1],pool[name_type[2]][1])
def main():
 refs={}
 for module in ['domino-widgets-gwt','gwt-modular-services']:
  classes=ROOT/module/'target/classes'
  for p in sorted(classes.rglob('*.class')):
   for ref in references(p):refs.setdefault(ref,set()).add(module+'/'+str(p.relative_to(classes)))
 result=[dict(zip(('kind','owner','member','descriptor'),key),callers=sorted(callers)) for key,callers in sorted(refs.items())]
 out=ROOT/'reports/member-inventory.json';out.parent.mkdir(exist_ok=True);out.write_text(json.dumps(result,indent=2)+'\n')
 print(len(result),'distinct external member references')
if __name__=='__main__':main()
